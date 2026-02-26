package com.yuyuto.infinitymaxapi.gamelibs.physics;

/**
 * 圧力伝播シミュレーション。
 *
 * セルごとの圧力差に応じて質量移動を計算し、
 * 密度と圧力を更新する。
 *
 * 小学生向け：
 * 「高い圧力から低い圧力へ空気や水が動く様子を計算する魔法の箱」
 */
public final class PressurePropagation {

    private final CellPhysicalGrid grid;    // 計算対象グリッド
    private final double permeability;      // 物質の透過しやすさ
    private final double cellVolume;        // セルの体積（密度計算用）

    public PressurePropagation(CellPhysicalGrid grid,
                               double permeability,
                               double cellVolume) {
        this.grid = grid;
        this.permeability = permeability;
        this.cellVolume = cellVolume;
    }

    /** 圧力伝播を1ステップ実行 */
    public void propagate(double dt) {
        int size = 8; // 現状固定。将来的に grid.sizeX 等から取得可能に

        for (int x = 0; x < size - 1; x++) {
            for (int y = 0; y < size - 1; y++) {
                for (int z = 0; z < size - 1; z++) {
                    // 隣接セルとの圧力交換
                    exchange(x, y, z, x+1, y, z, dt);
                    exchange(x, y, z, x, y+1, z, dt);
                    exchange(x, y, z, x, y, z+1, dt);
                }
            }
        }
    }

    /** セル間の流れ量を計算（CFL制限＋ダンピング） */
    private double computeFlow(double p1, double p2, double dt) {
        double rawFlow = (p1 - p2) * permeability * dt;

        // ダンピング
        rawFlow *= 0.8;

        // CFL的上限制御
        double maxFlow = 0.25;
        if (rawFlow > maxFlow) rawFlow = maxFlow;
        if (rawFlow < -maxFlow) rawFlow = -maxFlow;

        return rawFlow;
    }

    /** 2セル間で圧力を伝播 */
    private void exchange(int x1, int y1, int z1,
                          int x2, int y2, int z2,
                          double dt) {

        PhysicalState s1 = grid.get(x1, y1, z1);
        PhysicalState s2 = grid.get(x2, y2, z2);

        double p1 = s1.getPressure().getSI();
        double p2 = s2.getPressure().getSI();

        double flow = computeFlow(p1, p2, dt); // 修正：CFL＋ダンピング適用

        Mass m1 = s1.getMass();
        Mass m2 = s2.getMass();

        double newM1 = m1.getSI() - flow;
        double newM2 = m2.getSI() + flow;

        if (newM1 <= 0 || newM2 <= 0) return; // 物理的破綻回避

        Density d1 = new Density(newM1 / cellVolume, Density.KG_PER_M3);
        Density d2 = new Density(newM2 / cellVolume, Density.KG_PER_M3);

        Pressure newP1 = PhysicalCalculator.calculatePressure(d1, s1.getTemperature());
        Pressure newP2 = PhysicalCalculator.calculatePressure(d2, s2.getTemperature());

        // 新しい物理状態に置き換え
        grid.set(x1, y1, z1,
                new PhysicalState(
                        s1.getTemperature(),
                        newP1,
                        d1,
                        s1.getInternalEnergy(),
                        s1.getPhase(),
                        new Mass(newM1, Mass.KILOGRAM)
                )
        );

        grid.set(x2, y2, z2,
                new PhysicalState(
                        s2.getTemperature(),
                        newP2,
                        d2,
                        s2.getInternalEnergy(),
                        s2.getPhase(),
                        new Mass(newM2, Mass.KILOGRAM)
                )
        );
    }
}