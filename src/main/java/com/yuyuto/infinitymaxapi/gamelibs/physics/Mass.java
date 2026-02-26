package com.yuyuto.infinitymaxapi.gamelibs.physics;

/**
 * <h2>Mass（質量）</h2>
 *
 * <p>
 * 物体の「重さのもと」を表します。
 * 単位は kg（キログラム）。
 * </p>
 *
 * <p>
 * 物理では質量は非常に重要です。
 * </p>
 *
 * 例：
 * ・運動方程式 F = m a
 * ・熱容量 Q = m c ΔT
 * ・密度 ρ = m / V
 *
 * 小学生向け：
 * どれだけ「たくさん物があるか」です。
 */
public final class Mass extends PhysicalQuantity {

    /**
     * 次元: M¹
     */
    public static final Dimension DIMENSION =
            new Dimension(0, 1, 0, 0, 0, 0, 0);

    /**
     * SI単位: キログラム
     */
    public static final Unit KILOGRAM =
            new Unit("kg", DIMENSION, 1.0);

    /**
     * 通常コンストラクタ
     */
    public Mass(double value, Unit unit) {
        super(value, unit);
    }

    /**
     * SI値直接生成（内部用）
     */
    private Mass(double siValue) {
        super(siValue, DIMENSION);
    }

    /**
     * 質量の加算
     */
    public Mass add(Mass other) {
        return new Mass(this.getSI() + other.getSI());
    }

    /**
     * 質量の減算
     */
    public Mass subtract(Mass other) {
        return new Mass(this.getSI() - other.getSI());
    }

    /**
     * スカラー倍
     */
    public Mass multiply(double scalar) {
        return new Mass(this.getSI() * scalar);
    }

    /**
     * スカラー除算
     */
    public Mass divide(double scalar) {
        return new Mass(this.getSI() / scalar);
    }
}