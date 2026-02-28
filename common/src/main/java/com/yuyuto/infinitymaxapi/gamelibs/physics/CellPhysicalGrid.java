package com.yuyuto.infinitymaxapi.gamelibs.physics;

import java.util.Objects;

/**
 * <h1>CellPhysicalGrid（セル物理グリッド）</h1>
 *
 * <p>
 * このクラスは「チャンク内部の物理空間」を表す。
 * </p>
 *
 * <h2>どういうもの？</h2>
 * 空間を小さな立方体（セル）に分割し、
 * 各セルに PhysicalState（温度・圧力・密度など）を持たせる。
 *
 * <pre>
 * 例:
 *   [ ][ ][ ]
 *   [ ][ ][ ]
 *   [ ][ ][ ]
 * </pre>
 *
 * <h2>なぜ必要？</h2>
 * 圧力や温度は空間内で伝播するため、
 * 「場所ごとの状態」が必要。
 *
 * <h2>物理的意味</h2>
 * 各セルは「小さな空間体積」を表す。
 * セル同士の相互作用で、
 * ・圧力伝播
 * ・熱伝導
 * ・質量移動
 * が起こる。
 */
public final class CellPhysicalGrid {

    private final int sizeX;
    private final int sizeY;
    private final int sizeZ;

    private final PhysicalState[][][] states;
    private final BoundaryCondition[][][] boundaries;

    /**
     * グリッドを生成する。
     *
     * @param sizeX X方向セル数
     * @param sizeY Y方向セル数
     * @param sizeZ Z方向セル数
     * @param defaultState 初期状態（各セルにコピーされる）
     */
    public CellPhysicalGrid(int sizeX,
                            int sizeY,
                            int sizeZ,
                            PhysicalState defaultState) {

        if (sizeX <= 0 || sizeY <= 0 || sizeZ <= 0)
            throw new IllegalArgumentException("Grid size must be positive");

        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;

        states = new PhysicalState[sizeX][sizeY][sizeZ];
        boundaries = new BoundaryCondition[sizeX][sizeY][sizeZ];

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                for (int z = 0; z < sizeZ; z++) {

                    // defaultStateはコピーして格納
                    states[x][y][z] = defaultState.copy();

                    boundaries[x][y][z] = null; // 境界なし（内部セル）
                }
            }
        }
    }

    /**
     * 指定位置の物理状態を取得する。
     */
    public PhysicalState get(int x, int y, int z) {
        checkBounds(x, y, z);
        return states[x][y][z];
    }

    /**
     * 指定位置の物理状態を設定する。
     */
    public void set(int x, int y, int z, PhysicalState state) {
        checkBounds(x, y, z);
        states[x][y][z] = Objects.requireNonNull(state);
    }

    /**
     * 境界条件を設定する。
     */
    public void setBoundary(int x, int y, int z,
                            BoundaryCondition condition) {
        checkBounds(x, y, z);
        boundaries[x][y][z] = condition;
    }

    /**
     * 境界条件を取得する。
     */
    public BoundaryCondition getBoundary(int x, int y, int z) {
        checkBounds(x, y, z);
        return boundaries[x][y][z];
    }

    /**
     * 座標範囲チェック。
     */
    private void checkBounds(int x, int y, int z) {
        if (x < 0 || x >= sizeX ||
            y < 0 || y >= sizeY ||
            z < 0 || z >= sizeZ) {
            throw new IndexOutOfBoundsException(
                    "Cell index out of range"
            );
        }
    }

    public int getSizeX() { return sizeX; }
    public int getSizeY() { return sizeY; }
    public int getSizeZ() { return sizeZ; }
}