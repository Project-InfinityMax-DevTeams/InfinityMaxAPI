package com.yuyuto.infinitymaxapi.gamelibs.physics;

/**
 * <h2>PhysicalPhenomenon</h2>
 *
 * 物理現象を表すインターフェース。
 * 与えられた物理状態（PhysicalState）を時間刻み deltaTime で更新して
 * 新しい状態を返す。
 *
 * 例：
 * - 熱伝導（HeatTransfer）
 * - ジュール加熱（JouleHeating）
 * - 圧力伝播（PressurePropagation）
 *
 * 小学生向け：
 * 「物理のルールを使って、モノがどう変わるか計算する魔法の箱」です。
 */
public interface PhysicalPhenomenon {

    /**
     * 状態を更新する
     *
     * @param state 現在の物理状態
     * @param deltaTime 経過時間 [秒]
     * @return 更新後の物理状態
     */
    PhysicalState apply(PhysicalState state, double deltaTime);
}