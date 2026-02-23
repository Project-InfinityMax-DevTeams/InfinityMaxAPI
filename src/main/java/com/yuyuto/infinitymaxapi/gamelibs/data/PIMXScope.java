package com.yuyuto.infinitymaxapi.gamelibs.data;

/**
 *データの有効範囲を定義する
 */
public enum PIMXScope {
    GLOBAL,    //全MOD共通
    WORLD,     //ワールド単位
    CHUNK,     //チャンク単位
    ENTITY,    //エンティティ単位
    MOD_LOCAL, //MOD内部のみ(プライベート)
    TEMP       //一時データ
}
