package com.yuyuto.infinitymaxapi.gamelibs.physics;

/**
 * <h2>Material（物質定義）</h2>
 *
 * 物質ごとの融点・沸点などを保持する。
 */
public final class Material {

    private final double meltingPoint;   // K
    private final double boilingPoint;   // K
    private final double ionizationPoint; // K

    public Material(double meltingPoint,
                    double boilingPoint,
                    double ionizationPoint) {

        this.meltingPoint = meltingPoint;
        this.boilingPoint = boilingPoint;
        this.ionizationPoint = ionizationPoint;
    }

    public Phase resolvePhase(double temperature) {

        if (temperature < meltingPoint) return Phase.SOLID;
        if (temperature < boilingPoint) return Phase.LIQUID;
        if (temperature < ionizationPoint) return Phase.GAS;

        return Phase.PLASMA;
    }
}