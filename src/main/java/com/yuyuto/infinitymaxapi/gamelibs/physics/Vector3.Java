package com.yuyuto.infinitymaxapi.gamelibs.physics;

/**
 * Vector3
 *
 * 3次元ベクトルクラス。
 * 位置・速度・加速度の計算に使用する。
 *
 * 単位:
 * 1 = 1m（=1block）
 */
public class Vector3 {

    public final double x;
    public final double y;
    public final double z;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /** ベクトル加算 */
    public Vector3 add(Vector3 other) {
        return new Vector3(
                this.x + other.x,
                this.y + other.y,
                this.z + other.z
        );
    }

    /** ベクトル減算 */
    public Vector3 subtract(Vector3 other) {
        return new Vector3(
                this.x - other.x,
                this.y - other.y,
                this.z - other.z
        );
    }

    /** スカラー倍 */
    public Vector3 multiply(double scalar) {
        return new Vector3(
                this.x * scalar,
                this.y * scalar,
                this.z * scalar
        );
    }

    /** ベクトル長さ（magnitude） */
    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    /** 正規化（単位ベクトル化） */
    public Vector3 normalize() {
        double len = length();
        if (len == 0) return new Vector3(0, 0, 0);
        return new Vector3(x / len, y / len, z / len);
    }

    /** 内積 */
    public double dot(Vector3 other) {
        return x * other.x +
               y * other.y +
               z * other.z;
    }

    @Override
    public String toString() {
        return "Vector3(" + x + ", " + y + ", " + z + ")";
    }
}