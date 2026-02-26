package com.yuyuto.infinitymaxapi.gamelibs.physics;

/**
 * 圧力を表す物理量クラス。
 * SI単位はPa（パスカル）。
 *
 * 小学生向け：
 * 「空気や水が押してくる力を数値で表す箱」  
 * 足したり引いたりできる。
 */
public final class Pressure extends PhysicalQuantity {

    public static final Dimension DIMENSION =
            new Dimension(-1, 1, -2, 0, 0, 0, 0); // M L^-1 T^-2

    public static final Unit PASCAL =
            new Unit("Pa", DIMENSION, 1.0);

    public Pressure(double value, Unit unit) {
        super(value, unit);
    }

    private Pressure(double siValue) {
        super(siValue, DIMENSION);
    }

    /**
     * 同じ次元の圧力を足す
     */
    public Pressure add(Pressure other) {
        assertSameDimension(other);
        return new Pressure(this.getSI() + other.getSI());
    }
}