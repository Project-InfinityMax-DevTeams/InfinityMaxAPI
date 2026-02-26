package com.yuyuto.infinitymaxapi.gamelibs.physics;

/**
 * <h1>ChunkPhysicalStorage</h1>
 *
 * <p>
 * チャンク単位の物理状態管理クラス。
 * </p>
 *
 * <h2>LODとは？</h2>
 * LOD（Level Of Detail）は、
 * 「遠くの物理をどれだけ正確に計算するか」を決める仕組み。
 *
 * <ul>
 *     <li>FULL → セル単位で正確に計算</li>
 *     <li>AVERAGED → チャンク全体を1つの塊として扱う</li>
 * </ul>
 *
 * <h2>物理的意味</h2>
 * FULL:
 *   空間を小さな立方体に分割して圧力や温度が伝播する。
 *
 * AVERAGED:
 *   「このチャンク全体は平均的にこういう状態」
 *   として扱う（軽量）。
 *
 * <h2>リアル寄り設計</h2>
 * 平均化では温度だけでなく、
 * 質量・エネルギー・圧力も整合を取る。
 */
public final class ChunkPhysicalStorage {

    private final LODLevel lodLevel;
    private final CellPhysicalGrid grid;

    private PhysicalState averagedState;

    private final double cellVolume = 1.0; // 仮定体積（m^3）

    public ChunkPhysicalStorage(LODLevel lodLevel, PhysicalState defaultState) {

        this.lodLevel = lodLevel;

        if (lodLevel == LODLevel.FULL) {
            this.grid = new CellPhysicalGrid(8, 8, 8, defaultState);
            this.averagedState = defaultState.copy();
        } else {
            this.grid = null;
            this.averagedState = defaultState.copy();
        }
    }

    public LODLevel getLodLevel() {
        return lodLevel;
    }

    public PhysicalState getAveragedState() {
        return averagedState;
    }

    /**
     * FULLモード時、
     * グリッド全体の物理量を平均化する。
     *
     * 保存則を崩さないように
     * 質量とエネルギーも合算する。
     */
    public void updateAverageFromGrid() {

        if (grid == null) return;

        double totalMass = 0;
        double totalEnergy = 0;
        double totalTemp = 0;

        int count = 0;

        for (int x = 0; x < grid.getSizeX(); x++) {
            for (int y = 0; y < grid.getSizeY(); y++) {
                for (int z = 0; z < grid.getSizeZ(); z++) {

                    PhysicalState s = grid.get(x, y, z);

                    totalMass += s.getMass().getSI();
                    totalEnergy += s.getInternalEnergy().getSI();
                    totalTemp += s.getTemperature().getSI();

                    count++;
                }
            }
        }

        double avgTemp = totalTemp / count;
        double avgMass = totalMass / count;

        Density avgDensity = new Density(avgMass / cellVolume, Density.KG_PER_M3);

        Pressure avgPressure = PhysicalCalculator.calculatePressure(avgDensity, new Temperature(avgTemp, Temperature.KELVIN));

        averagedState = new PhysicalState(
                new Temperature(avgTemp, Temperature.KELVIN),
                avgPressure,
                avgDensity,
                new Energy(totalEnergy / count, Energy.JOULE),
                PhaseResolver.resolve(material,new Temperature(avgTemp, Temperature.KELVIN)),
                new Mass(avgMass, Mass.KILOGRAM),material
        );
    }

    /**
     * 圧力伝播実行。
     *
     * CFL制限は外部のStabilityControllerで制御する。
     */
    public void propagatePressure(double deltaTime) {

        if (lodLevel == LODLevel.FULL && grid != null) {

            PressurePropagation propagation = new PressurePropagation(grid, 0.01, cellVolume);

            propagation.propagate(deltaTime);

            updateAverageFromGrid();
        }

        if (lodLevel == LODLevel.AVERAGED) {
            // 将来：隣接チャンクとの平衡化
        }
    }
}