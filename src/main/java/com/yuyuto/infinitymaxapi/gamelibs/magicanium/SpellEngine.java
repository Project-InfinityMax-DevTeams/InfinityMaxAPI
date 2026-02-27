package com.yuyuto.infinitymaxapi.gamelans.magicanium;

/**
 * SpellEngine
 *
 * 魔術単体の効果計算
 *
 * 式：
 * Φ = κ × M_flow × e^(−γρ⁻_target)
 * Φ_final = Φ × (1 + log(1 + Iκ))
 *
 * - κ : 術式構造係数
 * - M_flow : DensityFlowEngineによる流量
 * - γ : 減衰定数
 * - ρ⁻_target : 対象負密度
 * - I : 術式情報ベクトル
 */
public class SpellEngine {

    /**
     * 魔術を発動して対象に与える効果量を計算
     *
     * @param caster 魔術発動者
     * @param target 魔術対象
     * @param structure 術式構造
     * @param targetResponse 対象側情報ベクトル（共鳴・反発計算用）
     * @return 最終効果量
     */
    public static double castSpell(MagicalBody caster, MagicalBody target, SpellStructure structure, InformationVector targetResponse) {

        // ① 魔力流量計算（密度差 × 安定度）
        double flow = DensityFlowEngine.calculateFlow(caster, target);

        // ② 基本効果 Φ = κ × M_flow × e^(-γρ⁻)
        double baseEffect = structure.getKappa() * flow * Math.exp(-WorldConstants.GAMMA * target.getState().getRhoNegative());

        // ③ 術式情報と対象情報の相性（内積）
        double compatibility = Math.max(0, structure.getInfo().dot(targetResponse));

        // ④ 情報増幅 Φ_final = Φ × (1 + log(1 + Iκ))
        double amplification = 1 + Math.log(1 + structure.getKappa() * compatibility);

        return baseEffect * amplification;
    }
}