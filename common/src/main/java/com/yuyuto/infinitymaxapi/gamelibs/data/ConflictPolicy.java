package com.yuyuto.infinitymaxapi.gamelibs.data;

/**
 * 競合時ポリシー
 */
public enum ConflictPolicy {
    REPLACE,  //上書き
    ERROR,    //例外
    MERGE     //マージ
}
