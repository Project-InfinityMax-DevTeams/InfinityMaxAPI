package com.yuyuto.infinitymaxapi.gamelans.magicanium;

import java.util.HashMap;
import java.util.Map;

/**
 * 多次元情報ベクトルクラス
 *
 * 魔術の情報（意志・術式構造など）を
 * 多次元ベクトルで表現する。
 *
 * 干渉計算や共鳴・相殺判定に使用。
 */
public class InformationVector {

    /** 軸名 → 値 のマッピング */
    private final Map<String, Double> components = new HashMap<>();

    /**
     * 指定軸に値を追加
     * @param axis 軸名
     * @param value 値
     */
    public void add(String axis, double value) {
        components.merge(axis, value, Double::sum);
    }

    /**
     * 指定軸の値を取得
     * @param axis 軸名
     * @return 値（存在しない場合は0.0）
     */
    public double get(String axis) {
        return components.getOrDefault(axis, 0.0);
    }

    /**
     * ノルム（||I||）を計算
     * @return ベクトル長
     */
    public double magnitude() {
        double sum = 0;
        for (double v : components.values()) {
            sum += v * v;
        }
        return Math.sqrt(sum);
    }

    /**
     * 内積計算
     * @param other 他の情報ベクトル
     * @return 内積
     */
    public double dot(InformationVector other) {
        double result = 0;

        // 注意: 他ベクトルにしか存在しない軸は無視される
        for (String key : components.keySet()) {
            result += this.get(key) * other.get(key);
        }

        return result;
    }

    /**
     * コサイン類似度計算
     * 干渉係数計算に使用
     * @param other 他ベクトル
     * @return cosθ
     */
    public double cosineSimilarity(InformationVector other) {

        double dot = this.dot(other);
        double mag1 = this.magnitude();
        double mag2 = other.magnitude();

        if (mag1 == 0 || mag2 == 0) return 0;

        return dot / (mag1 * mag2);
    }
}