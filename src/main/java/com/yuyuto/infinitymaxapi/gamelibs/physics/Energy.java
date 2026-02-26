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

    /**
     * 指定した単位で表したエネルギー量を持つ Energy インスタンスを生成する。
     *
     * @param value 単位 unit で表したエネルギーの大きさ
     * @param unit  value の単位（例: ジュールを表す単位）
     */
    public Energy(double value, Unit unit) {

        if (scalar == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        super(value, unit);
    }
    /**
     * SI単位（ジュール）から Energy インスタンスを初期化するコンストラクタ。
     *
     * @param siValue SI単位（J）で表したエネルギーの値
     */
    private Energy(double siValue) {
        super(siValue, DIMENSION);
    }

    /**
     * このエネルギーに指定したエネルギーを加算した新しい Energy を返す。
     *
     * @param other 加算するエネルギー
     * @return 加算結果を表す Energy（SI単位（ジュール）で表現された値）
     */
   public Energy add(Energy other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot add null Energy");
        }
        return new Energy(this.getSI() + other.getSI());
    }

    /**
     * 別のエネルギー量を引いた差を表す Energy を生成して返す。
     *
     * @param other 引かれるエネルギー
     * @return this から other を引いた差の Energy。結果は SI 単位（ジュール）で表現される。
     */
    public Energy subtract(Energy other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot subtract null Energy");
        }
        return new Energy(this.getSI() - other.getSI());
    }

    /**
     * このエネルギー量を指定した係数でスケーリングした新しい Energy を返す。
     *
     * @param scalar 乗算する無次元の係数（負の値も許容される）
     * @return スケーリング後のエネルギーを表す新しい Energy インスタンス（SI単位：ジュール）
     */
    public Energy multiply(double scalar) {
        return new Energy(this.getSI() * scalar);
    }

    /**
     * 指定したスカラーでこのエネルギーを除算し、その結果を新しい Energy として返す。
     *
     * @param scalar 除算に用いるスカラー値
     * @return 指定した `scalar` で除算した結果の Energy（SI単位：ジュール）
     */
    public Energy divide(double scalar) {
        return new Energy(this.getSI() / scalar);
    }
}
