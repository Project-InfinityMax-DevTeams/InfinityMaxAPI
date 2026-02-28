package com.yuyuto.infinitymaxapi.gamelibs.physics;

import java.util.Arrays;
import java.util.Objects;

/**
 * <h2>Dimension（物理次元クラス）</h2>
 *
 * <p>
 * 物理量が「何の単位でできているか」を表します。
 * </p>
 *
 * <p>
 * 例：
 * 長さ → L¹  
 * 面積 → L²  
 * 速度 → L¹ T⁻¹  
 * 力 → M¹ L¹ T⁻²
 * </p>
 *
 * <p>
 * このクラスでは、以下の基本次元を指数で管理します。
 * </p>
 *
 * <pre>
 * [0] 長さ L
 * [1] 質量 M
 * [2] 時間 T
 * [3] 電流 I
 * [4] 温度 Θ
 * [5] 物質量 N
 * [6] 光度 J
 * </pre>
 *
 * <p>
 * 例えば速度は:
 * L^1 T^-1 → {1, 0, -1, 0, 0, 0, 0}
 * </p>
 *
 * <p>
 * 小学生向け説明：
 * 「この数は何メートル？何キログラム？」を
 * コンピューターに分からせるためのクラスです。
 * </p>
 */
public final class Dimension {

    /** 基本次元数（SI 7次元） */
    public static final int BASE_DIMENSION_COUNT = 7;

    /** 各基本次元の指数 */
    private final int[] exponents;

    /**
     * コンストラクタ
     *
     * @param exponents 各基本次元の指数
     */
    public Dimension(int... exponents) {

        if (exponents.length != BASE_DIMENSION_COUNT) {
            throw new IllegalArgumentException(
                "Dimension must have exactly " + BASE_DIMENSION_COUNT + " elements."
            );
        }

        this.exponents = Arrays.copyOf(exponents, BASE_DIMENSION_COUNT);
    }

    /**
     * 次元の掛け算。
     *
     * 例:
     * 速度(L T^-1) × 時間(T) = 長さ(L)
     *
     * 指数は足し算になります。
     */
    public Dimension multiply(Dimension other) {
        checkCompatibility(other);

        int[] result = new int[BASE_DIMENSION_COUNT];

        for (int i = 0; i < BASE_DIMENSION_COUNT; i++) {
            result[i] = this.exponents[i] + other.exponents[i];
        }

        return new Dimension(result);
    }

    /**
     * 次元の割り算。
     *
     * 例:
     * 距離(L) ÷ 時間(T) = 速度(L T^-1)
     *
     * 指数は引き算になります。
     */
    public Dimension divide(Dimension other) {
        checkCompatibility(other);

        int[] result = new int[BASE_DIMENSION_COUNT];

        for (int i = 0; i < BASE_DIMENSION_COUNT; i++) {
            result[i] = this.exponents[i] - other.exponents[i];
        }

        return new Dimension(result);
    }

    /**
     * 次元の整合性チェック
     */
    private void checkCompatibility(Dimension other) {
        if (other.exponents.length != BASE_DIMENSION_COUNT) {
            throw new IllegalArgumentException("Dimension size mismatch.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Dimension)) return false;
        Dimension d = (Dimension) o;
        return Arrays.equals(this.exponents, d.exponents);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(exponents);
    }

    @Override
    public String toString() {
        return Arrays.toString(exponents);
    }
}