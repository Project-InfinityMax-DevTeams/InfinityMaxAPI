package com.yuyuto.infinitymaxapi.gamelibs.physics;
/**
 * 物理法則に従って物体や現象を移動させる際に用いる指定タイプの列挙
 */

public enum MovementType {
    LINEAR,        // 等速直線運動
    PROJECTILE,    // 放物運動
    CUSTOM         // 魔術など独自制御
}