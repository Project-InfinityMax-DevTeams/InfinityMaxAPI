package com.yuyuto.infinitymaxapi.gamelibs.data;

/**
 * PIMXのデータ保存単位
 */
public enum PIMXScope {
    GLOBAL, // 全体データ
    WORLD,  // ワールド単位のデータ
    CHUNK,  // チャンク単位のデータ
    BLOCK,  // ブロック単位のデータ
    ENTITY, // エンティティ単位のデータ
    PLAYER  // プレイヤー単位のデータ
}
