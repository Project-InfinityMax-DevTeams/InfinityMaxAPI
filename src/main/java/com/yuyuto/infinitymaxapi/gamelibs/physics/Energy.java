package com.yuyuto.infinitymaxapi.gamelibs.physics;

/**
 * <h2>Energy（エネルギー）</h2>
 *
 * 内部エネルギーなどを表す物理量クラス。
 * SI単位はジュール（J）。
 */
public final class Energy extends PhysicalQuantity {

    /** 次元: M L^2 T^-2 */
    public static final Dimension DIMENSION =
            new Dimension(2, 1, -2, 0, 0, 0, 0);

    /** SI単位: ジュール */
    public static final Unit JOULE =
            new Unit("J", DIMENSION, 1.0);

    public Energy(double value, Unit unit) {

        if (scalar == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        super(value, unit);
    }

    private Energy(double siValue) {
        super(siValue, DIMENSION);
    }

    public Energy add(Energy other) {
        return new Energy(this.getSI() + other.getSI());
    }

    public Energy subtract(Energy other) {
        return new Energy(this.getSI() - other.getSI());
    }

    public Energy multiply(double scalar) {
        return new Energy(this.getSI() * scalar);
    }

    public Energy divide(double scalar) {
        return new Energy(this.getSI() / scalar);
    }
}
