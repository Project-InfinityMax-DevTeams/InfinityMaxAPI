package com.yuyuto.infinitymaxapi.gamelans.magicanium;

/**
 * MagicalBody
 *
 * 魔力体の基本クラス。
 * - 正/負のMagicanium密度を保持する
 * - 各種物理量・魔術情報は MagicaniumState で管理
 */
public class MagicalBody {

    /** この物体の魔力状態 */
    protected MagicaniumState state;

    /**
     * コンストラクタ
     * @param rhoPositive 初期正密度
     * @param rhoNegative 初期負密度
     */
    public MagicalBody(double rhoPositive, double rhoNegative) {
        this.state = new MagicaniumState(rhoPositive, rhoNegative);
    }

    /**
     * 魔力状態取得
     * @return MagicaniumState
     */
    public MagicaniumState getState() {
        return state;
    }
}