package com.yuyuto.infinitymaxapi.gamelibs.physics;

/**
 * ===============================================
 * StabilityController
 * ===============================================
 *
 * 【概要】
 * 数値流体シミュレーションにおける
 * 安定な時間刻み（Δt）を計算するクラスです。
 *
 * 【なぜ必要？】
 * コンピュータで流体を計算する時、
 * 時間を飛ばしすぎると計算が壊れます。
 *
 * これを防ぐために CFL条件 を使います。
 *
 * 【CFL条件とは？】
 * 「1ステップの間に、情報（音速）が
 * 1セル以上進んではいけない」
 *
 * 数式：
 *
 *    Δt <= Δx / c
 *
 * c = √(γ R T)
 *
 * γ = 比熱比
 * R = 気体定数
 * T = 絶対温度[K]
 *
 * 【物理的意味】
 * 音速は圧力の変化が伝わる速さ。
 * これを超える時間刻みは物理的に破綻します。
 */
public final class StabilityController {

    /** セルの大きさ（メートル） */
    private final double cellSize;

    /** 比熱比（空気なら約1.4） */
    private final double gamma;

    /** 気体定数（空気なら287.05） */
    private final double gasConstant;

    /** 安全係数（0〜1） */
    private final double safetyFactor;

    public StabilityController(double cellSize,
                               double gamma,
                               double gasConstant,
                               double safetyFactor) {

        if (cellSize <= 0)
            throw new IllegalArgumentException("cellSize must be positive");

        if (gamma <= 0)
            throw new IllegalArgumentException("gamma must be positive");

        if (gasConstant <= 0)
            throw new IllegalArgumentException("gasConstant must be positive");

        if (safetyFactor <= 0 || safetyFactor > 1)
            throw new IllegalArgumentException("safetyFactor must be (0,1]");

        this.cellSize = cellSize;
        this.gamma = gamma;
        this.gasConstant = gasConstant;
        this.safetyFactor = safetyFactor;
    }

    /**
     * 音速を計算します。
     *
     * c = √(γ R T)
     */
    private double computeSoundSpeed(Temperature temperature) {

        double T = temperature.getSI();

        if (T <= 0)
            throw new IllegalArgumentException("Temperature must be > 0 K");

        return Math.sqrt(gamma * gasConstant * T);
    }

    /**
     * CFL条件に基づく最大時間刻みを計算
     *
     * Δt = Δx / c
     */
    public double computeMaxTimeStep(Temperature temperature) {

        double c = computeSoundSpeed(temperature);

        return cellSize / c;
    }

    /**
     * 要求された時間刻みを安全な範囲に制限します。
     *
     * 安全係数を掛けることで
     * 数値誤差による暴走を防ぎます。
     */
    public double clampTimeStep(double requestedDt,
                                Temperature temperature) {

        if (requestedDt <= 0)
            throw new IllegalArgumentException("Time step must be positive");

        double maxDt = computeMaxTimeStep(temperature);

        return Math.min(requestedDt, maxDt * safetyFactor);
    }
}