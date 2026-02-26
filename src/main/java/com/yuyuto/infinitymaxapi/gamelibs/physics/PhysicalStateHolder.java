package com.yuyuto.infinitymaxapi.gamelibs.physics;

/**
 * <h2>PhysicalStateHolder</h2>
 *
 * 物理状態を保持するオブジェクト用のインターフェース。
 *
 * 小学生向け：
 * 「魔法の箱（PhysicalState）を持つモノには、このインターフェースをつけよう」
 */
public interface PhysicalStateHolder {

    /** 現在の物理状態を取得 */
    PhysicalState getPhysicalState();

    /** 物理状態を更新 */
    void setPhysicalState(PhysicalState state);
}