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
     */
    public JouleHeating(EnergyConnection connection, double specificHeat) {
        this.connection = connection;
        this.specificHeat = specificHeat;
    }

    /**
     * 既存コード互換のための簡易コンストラクタ。
     * 内部で EnergyAPI の接続を組み立てて利用する。
     */
    public JouleHeating(double resistance, double current, double specificHeat) {
        EnergyNode source = new EnergyNode();
        EnergyNode sink = new EnergyNode();

        source.setPotential(current * resistance);
        sink.setPotential(0);

        this.connection = new EnergyConnection(source, sink, resistance);
        this.specificHeat = specificHeat;
    }

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
                PhaseResolver.resolve(newTemp),
                state.getMass()
        );
    }
}
