package com.yuyuto.infinitymaxapi.gamelans.magicanium;

import java.util.List;

/**
 * SpellInterferenceEngine
 *
 * 複数魔術の干渉計算（距離依存）
 *
 * 干渉効果：
 * - 情報ベクトルのコサイン類似度で共鳴・相殺
 * - 距離に応じて指数減衰
 * - 総合効果に干渉項を加算
 */
public class SpellInterferenceEngine {

    /**
     * 干渉計算
     * @param effects 単体魔術効果リスト
     * @param infos 魔術情報ベクトルリスト
     * @param positions 魔術座標リスト（Vector3d）
     * @return 干渉後総合効果
     */
    public static double applyInterference(List<Double> effects, List<InformationVector> infos, List<Vector3d> positions) {
        double total = 0;
        if (effects.size() != infos.size() || infos.size() != positions.size()) {
            throw new IllegalArgumentException("List sizes must match");
        }

        // ---- ① 単体効果加算 ----
        for (double effect : effects) {
            total += effect;
        }

        // ---- ② 干渉項計算（距離依存） ----
        for (int i = 0; i < effects.size(); i++) {
            for (int j = i + 1; j < effects.size(); j++) {

                // 情報ベクトル共鳴度（-1~1）
                double cos = infos.get(i).cosineSimilarity(infos.get(j));

                // 位置距離
                double distance = positions.get(i).distanceTo(positions.get(j));

                // 距離減衰（YOTAは距離スケール）
                double decay = Math.exp(-distance / WorldConstants.YOTA);

                // 干渉項の加算（ETAは干渉係数）
                total += WorldConstants.ETA * effects.get(i) * effects.get(j) * cos * decay;
            }
        }

        return total;
    }
}