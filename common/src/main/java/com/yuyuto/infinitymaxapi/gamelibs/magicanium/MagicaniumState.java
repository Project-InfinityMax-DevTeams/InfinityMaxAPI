package com.yuyuto.infinitymaxapi.gamelans.magicanium;

/**
 * MagicaniumState
 *
 * 物体のMagicanium状態を保持
 * - ρ⁺: 正密度（放出傾向）
 * - ρ⁻: 負密度（吸収傾向）
 */
public class MagicaniumState {

    private double rhoPositive; // ρ⁺
    private double rhoNegative; // ρ⁻

    public MagicaniumState(double rhoPositive, double rhoNegative) {
        this.rhoPositive = rhoPositive;
        this.rhoNegative = rhoNegative;
    }

    public double getRhoPositive() {
        return rhoPositive;
    }

    public double getRhoNegative() {
        return rhoNegative;
    }

    /**
     * 安定度 σ = 1 / (1 + λρ⁺)
     * - ρ⁺が高いほど不安定（σは小さい）
     */
    public double getStability() {
        return 1.0 / (1.0 + WorldConstants.LAMBDA * rhoPositive);
    }

    /**
     * 正密度に値を加算
     * @param amount 増減量（負値で減少）
     */
    public void addPositive(double amount) {
        this.rhoPositive += amount;

        // 安全策: 負値防止（暴走対策）
        if (this.rhoPositive < 0) this.rhoPositive = 0;
    }

    /**
     * 負密度に値を加算
     * @param amount 増減量
     */
    public void addNegative(double amount) {
        this.rhoNegative += amount;

        // 安全策: 負密度は負のまま保持可。必要なら上限チェックは MagicAbsorber 側
    }
}