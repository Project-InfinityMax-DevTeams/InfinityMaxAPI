package com.yuyuto.infinitymaxapi.gamelans.magicanium;

/**
 * SpellStructure
 *
 * 術式構造
 * - κ : 構造係数（術式の完成度や強さ）
 * - info : 術式情報ベクトル（多次元情報）
 */
public class SpellStructure {

    private final double kappa;
    private final InformationVector info;

    public SpellStructure(double kappa, InformationVector info) {
        this.kappa = kappa;
        this.info = info;
    }

    public double getKappa() {
        return kappa;
    }

    public InformationVector getInfo() {
        return info;
    }
}