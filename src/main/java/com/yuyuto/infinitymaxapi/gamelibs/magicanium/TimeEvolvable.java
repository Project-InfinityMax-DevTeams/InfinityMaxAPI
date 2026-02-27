package com.yuyuto.infinitymaxapi.gamelans.magicanium;

/**
 * 時間発展可能オブジェクトのインターフェース
 *
 * MagicaniumFieldやその他の魔力体シミュレーションで時間発展（tick）を統一的に扱うための契約。
 */
public interface TimeEvolvable {

    /**
     * deltaTime 秒だけ時間を進める
     * 実装例:
     * - Magicaniumの自然崩壊
     * - 魔力場内の密度平均化
     * - 干渉計算
     *
     * @param deltaTime 経過時間（秒）
     */
    void tick(double deltaTime);
}