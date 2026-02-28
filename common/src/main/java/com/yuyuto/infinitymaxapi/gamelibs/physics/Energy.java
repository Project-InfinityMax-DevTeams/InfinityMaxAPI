package com.yuyuto.infinitymaxapi.gamelibs.physics;

/**
 * <h2>Energy（エネルギー）</h2>
 *
 * 内部エネルギーなどを表す物理量クラス。
 * SI単位はジュール（J）。
 *
 * 【物理的意味】
 * エネルギーとは「仕事をする能力」。
 *
 * 例：
 * ・熱エネルギー
 * ・運動エネルギー
 * ・位置エネルギー
 *
 * 単位：J（ジュール）
 *
 * 1 J = 1 kg·m²/s²
 */
public final class Energy extends PhysicalQuantity {

    /** 次元: M L^2 T^-2 */
    public static final Dimension DIMENSION =
            new Dimension(2, 1, -2, 0, 0, 0, 0);

    /** SI単位: ジュール */
    public static final Unit JOULE =
            new Unit("J", DIMENSION, 1.0);

    /**
     * 指定した単位でエネルギーを生成
     */
    public Energy(double value, Unit unit) {
        super(value, unit);
    }

    /**
     * SI値（J）から直接生成
     */
    private Energy(double siValue) {
        super(siValue, DIMENSION);
    }

    public Energy add(Energy other) {
        if (other == null)
            throw new IllegalArgumentException("Cannot add null Energy");

        return new Energy(this.getSI() + other.getSI());
    }

    public Energy subtract(Energy other) {
        if (other == null)
            throw new IllegalArgumentException("Cannot subtract null Energy");

        return new Energy(this.getSI() - other.getSI());
    }

    public Energy multiply(double scalar) {
        return new Energy(this.getSI() * scalar);
    }

    public Energy divide(double scalar) {
        if (scalar == 0)
            throw new IllegalArgumentException("Cannot divide by zero");

        return new Energy(this.getSI() / scalar);
    }
}