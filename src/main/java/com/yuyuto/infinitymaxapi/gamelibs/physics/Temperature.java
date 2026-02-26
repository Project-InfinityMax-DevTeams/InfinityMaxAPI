package com.yuyuto.infinitymaxapi.gamelibs.physics;

/**
 * ===============================================
 * Temperature（温度）
 * ===============================================
 *
 * 【概要】
 * 絶対温度（ケルビン）を表す物理量クラス。
 *
 * 単位系：
 * K（ケルビン）
 *
 * 【物理的意味】
 * 温度は「分子の運動の激しさ」を表します。
 *
 * 高い温度 = 分子が激しく動いている
 * 低い温度 = 分子の動きが遅い
 *
 * 【重要】
 * 絶対温度は 0K 未満になれません。
 * 0K は理論上の最低温度です。
 */
public final class Temperature extends PhysicalQuantity {

    public static final Dimension DIMENSION =
            new Dimension(0, 0, 0, 0, 1, 0, 0);

    public static final Unit KELVIN =
            new Unit("K", DIMENSION, 1.0);

    public Temperature(double value, Unit unit) {
        super(validate(unit.toSI(value)), unit);
    }

    private Temperature(double siValue) {
        super(validate(siValue), DIMENSION);
    }

    /**
     * 温度の加算。
     *
     * ⚠ 注意：
     * 物理的に正しい温度の合成は
     * 「エネルギー保存」から計算する必要があります。
     *
     * これは単純な数値加算です。
     */
    public Temperature add(Temperature other) {
        assertSameDimension(other);
        return new Temperature(this.siValue + other.siValue);
    }

    private static double validate(double siValue) {
        if (siValue < 0)
            throw new IllegalArgumentException("Temperature cannot be below 0 K");
        return siValue;
    }
}