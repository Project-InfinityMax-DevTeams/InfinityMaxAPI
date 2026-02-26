package com.yuyuto.infinitymaxapi.gamelibs.physics;

/**
 * <h1>Density（密度）</h1>
 *
 * <p>
 * 密度とは「どれだけぎっしり詰まっているか」.
 * 物質の質量が体積に対してどれだけあるかを表す物理量.
 * </p>
 *
 * <h2>式</h2>
 * ρ = m / V
 *
 * ρ（ロー） = 密度  
 * m = 質量  
 * V = 体積
 *
 * <h2>単位</h2>
 * kg/m³
 *
 * <h2>物理的意味</h2>
 * ・密度が高い → 重い・圧力が上がりやすい  
 * ・密度が低い → 軽い・真空に近い
 */
public final class Density extends PhysicalQuantity {

    public static final Dimension DIMENSION =
            new Dimension(-3, 1, 0, 0, 0, 0, 0);

    public static final Unit KG_PER_M3 =
            new Unit("kg/m3", DIMENSION, 1.0);

    public Density(double value, Unit unit) {
        super(value, unit);

        if (getSI() <= 0) {
            throw new IllegalArgumentException("Density must be positive.");
        }
    }

    private Density(double siValue) {
        super(siValue, DIMENSION);

        if (siValue <= 0) {
            throw new IllegalArgumentException("Density must be positive.");
        }
    }

    /**
     * 密度生成（SI専用）
     */
    public static Density ofSI(double value) {
        return new Density(value);
    }
}