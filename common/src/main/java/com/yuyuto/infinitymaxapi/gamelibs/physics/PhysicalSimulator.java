package com.yuyuto.infinitymaxapi.gamelibs.physics;

import java.util.ArrayList;
import java.util.List;

/**
 * <h2>PhysicalSimulator</h2>
 *
 * 物理現象をまとめてシミュレーションする管理クラス。
 *
 * 小学生向け：
 * 「いくつかの魔法の箱（PhysicalPhenomenon）をまとめて、
 * 1秒ごとにモノの状態を変える箱」です。
 */
public final class PhysicalSimulator {

    /** 登録された現象のリスト */
    private final List<PhysicalPhenomenon> phenomena = new ArrayList<>();

    /** 物理現象を追加 */
    public void register(PhysicalPhenomenon phenomenon) {
        phenomena.add(phenomenon);
    }

    /**
     * すべての現象を適用して新しい状態を計算
     * @param state 現在の物理状態
     * @param deltaTime 経過時間 [秒]
     * @return 更新後の物理状態
     */
    public PhysicalState simulate(PhysicalState state, double deltaTime) {

        PhysicalState current = state;

        // 登録済みのすべての現象を順番に適用
        for (PhysicalPhenomenon phenomenon : phenomena) {
            current = phenomenon.apply(current, deltaTime);
        }

        return current;
    }
}