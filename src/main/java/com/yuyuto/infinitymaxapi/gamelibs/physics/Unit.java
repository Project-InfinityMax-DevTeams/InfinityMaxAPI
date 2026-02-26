package com.yuyuto.infinitymaxapi.gamelibs.physics;

/**
 * ===============================================
 * Unit（単位）
 * ===============================================
 *
 * 【概要】
 * 物理量の単位を定義するクラス。
 *
 * 例：
 * m（メートル）
 * kg（キログラム）
 * Pa（パスカル）
 *
 * 【仕組み】
 * すべての物理量は内部的に
 * SI単位で保存されます。
 *
 * scale は：
 *
 * value_SI = value × scale
 *
 * 例：
 * 1 km → scale = 1000
 */
public final class Unit {

    private final String symbol;
    private final Dimension dimension;
    private final double scale; // SI基準換算倍率

    public Unit(String symbol, Dimension dimension, double scale) {

        if (dimension == null)
            throw new IllegalArgumentException("Dimension cannot be null");

        if (scale <= 0)
            throw new IllegalArgumentException("Scale must be positive");

        this.symbol = symbol;
        this.dimension = dimension;
        this.scale = scale;
    }

    public double toSI(double value) {
        return value * scale;
    }

    public double fromSI(double siValue) {
        return siValue / scale;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public String getSymbol() {
        return symbol;
    }
}