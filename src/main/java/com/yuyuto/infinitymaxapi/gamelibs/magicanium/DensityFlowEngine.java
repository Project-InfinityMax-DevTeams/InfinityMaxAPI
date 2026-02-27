package com.yuyuto.infinitymaxapi.gamelans.magicanium;

/**
 * DensityFlowEngine
 *
 * 魔力密度の差に基づき、Magicanium（正密度）を
 * 高密度から低密度の物体へ流す処理を行うエンジン。
 *
 * 式：
 * Δρ = (ρ⁺_source − ρ⁺_target)
 * M_flow = α × Δρ × σ_source
 *
 * - Δρ : 正密度の差
 * - σ_source : 放出元の安定度（高密度ほど不安定＝流れやすい）
 * - α : 拡散係数（WorldConstantsで定義）
 */
public class DensityFlowEngine {

    /**
     * 流れる魔力量を計算する
     * @param source 放出元の魔術物体
     * @param target 受け取り先の魔術物体
     * @return 流れる正Magicanium量
     */
    public static double calculateFlow(MagicalBody source, MagicalBody target) {

        // 正密度差を計算
        double delta = source.getState().getRhoPositive() - target.getState().getRhoPositive();

        // 放出元の安定度（σ_source）
        double stability = source.getState().getStability();

        // 流れる量 = α × Δρ × σ_source
        return WorldConstants.ALPHA * delta * stability;
    }

    /**
     * 計算した魔力を実際に物体に適用
     * @param source 放出元
     * @param target 受け取り先
     */
    public static void applyFlow(MagicalBody source, MagicalBody target) {

        double flow = calculateFlow(source, target);

        // 放出元から減少
        source.getState().addPositive(-flow);

        // 受け取り先に加算
        target.getState().addPositive(flow);
    }
}