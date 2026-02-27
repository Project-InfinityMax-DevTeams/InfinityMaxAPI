package com.yuyuto.infinitymaxapi.gamelans.magicanium;

/**
 * MagicAbsorber
 *
 * 負密度吸収を行う物体。
 * 上限(maxNegativeDensity)まで負密度を吸収可能。
 */
public class MagicAbsorber extends MagicalBody {

    /** 最大吸収可能負密度 */
    private final double maxNegativeDensity;

    /**
     * コンストラクタ
     * @param rhoPositive 初期正密度
     * @param rhoNegative 初期負密度
     * @param maxNegativeDensity 最大吸収負密度
     */
    public MagicAbsorber(double rhoPositive, double rhoNegative, double maxNegativeDensity) {
        super(rhoPositive, rhoNegative);
        this.maxNegativeDensity = maxNegativeDensity;
    }

    /**
     * 負密度を吸収
     * @param amount 吸収量
     * @return true:成功 / false:上限超えで失敗
     */
    public boolean absorb(double amount) {
        if (state.getRhoNegative() + amount > maxNegativeDensity) {
            return false;
        }
        state.addNegative(amount);
        return true;
    }

    /**
     * 最大吸収量取得
     * @return maxNegativeDensity
     */
    public double getMaxNegativeDensity() {
        return maxNegativeDensity;
    }
}