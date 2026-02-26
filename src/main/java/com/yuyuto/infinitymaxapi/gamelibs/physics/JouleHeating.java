package com.yuyuto.infinitymaxapi.gamelibs.physics;

import com.yuyuto.infinitymaxapi.gamelibs.energy.EnergyConnection;
import com.yuyuto.infinitymaxapi.gamelibs.energy.EnergyNode;

/**
 * <h2>JouleHeating（ジュール熱）</h2>
 *
 * <pre>
 * Q = I² R t
 * </pre>
 *
 * 電流が流れると抵抗で熱が出ます。
 */
public final class JouleHeating implements PhysicalPhenomenon {

    private final EnergyConnection connection;
    private final double specificHeat;

    /**
     * EnergyAPI の接続情報を使ってジュール熱を計算する。
     *
     * EnergyConnection を基にジュール加熱モデルを初期化する。
     *
     * @param connection ジュール熱（電力損失）計算に使用する電力接続情報。接続のポテンシャルと抵抗から消費電力を算出するために用いられる。
     * @param specificHeat 対象物質の比熱容量（J/(kg·K)）。
     */
    public JouleHeating(EnergyConnection connection, double specificHeat) {
        if(connection == null) {
            throw new IllegalArgumentException("connection must not be null");
        }
        if (specificHeat <= 0) {
            throw new IllegalArgumentException("specificHeat must be positive");
        }
        this.connection = connection;

        if (specificHeat <= 0) {
            throw new IllegalArgumentException("specificHeat must be positive");
        }
        this.specificHeat = specificHeat;
    }

    /**
     * 既存API互換のために簡易的なエネルギー接続を構築するコンストラクタ。
     *
     * 指定した抵抗と電流から供給側・吸収側の EnergyNode を作成して内部の EnergyConnection を初期化し、
     * 与えられた比熱を保存する。
     *
     * @param resistance 抵抗値（オーム）
     * @param current    電流（アンペア）
     * @param specificHeat 比熱（ジュール／（キログラム・ケルビン））
     */
    public JouleHeating(double resistance, double current, double specificHeat) {
        EnergyNode source = new EnergyNode();
        EnergyNode sink = new EnergyNode();

        source.setPotential(current * resistance);
        sink.setPotential(0);

        this.connection = new EnergyConnection(source, sink, resistance);
        this.specificHeat = specificHeat;
    }

    /**
     * 接続によるジュール熱を与え、与えられた状態を基に熱影響を反映した新しい物理状態を返す。
     *
     * <p>接続からの電力損失に基づき発生した熱量で内部エネルギーと温度を更新し、更新後の温度から相を決定した PhysicalState を生成して返します。</p>
     *
     * @param state 現在の物理状態
     * @param deltaTime 適用する時間間隔（秒）
     * @return 接続によるジュール熱を反映して更新された PhysicalState
     */
    @Override
    public PhysicalState apply(PhysicalState state, double deltaTime) {
        double powerLoss = connection.computeLoss(
                connection.from().getPotential(),
                connection.to().getPotential()
        );
        double heat = powerLoss * deltaTime;

        double mass = state.getMass().getSI();
        double temperatureChange = heat / (mass * specificHeat);
        double newTempValue = state.getTemperature().getSI() + temperatureChange;

        Energy newEnergy = new Energy(state.getInternalEnergy().getSI() + heat, Energy.JOULE);
        Temperature newTemp = new Temperature(newTempValue, Temperature.KELVIN);

        return new PhysicalState(
                newTemp,
                state.getPressure(),
                state.getDensity(),
                newEnergy,
                PhaseResolver.resolve(state.getMaterial(),newTemp),
                state.getMass(),
                state.getMaterial()
        );
    }
}
