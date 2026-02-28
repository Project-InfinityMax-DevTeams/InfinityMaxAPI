package com.yuyuto.infinitymaxapi.gamelibs.physics;

/**
 * <h2>PhysicalCalculator</h2>
 * 
 * 物理量の基本計算を提供するユーティリティクラス。
 * 
 * 現状は「空気を想定した理想気体」モデルでの圧力・密度計算をサポート。
 *
 * 小学生向け：
 * 「温度とモノの量で空気の押し合いを計算する箱」です。
 */
public final class PhysicalCalculator {

    /** 空気用の気体定数 R [J/(kg*K)] */
    private static final double GAS_CONSTANT = 287.05;

    /** インスタンス化禁止 */
    private PhysicalCalculator() {}

    /**
     * 密度と温度から圧力を計算
     * p = ρ * R * T
     *
     * @param density 密度（kg/m³）
     * @param temp 温度（K）
     * @return Pressure 圧力（Pa）
     */
    public static Pressure calculatePressure(Density density, Temperature temp) {
        double p = density.getSI() * GAS_CONSTANT * temp.getSI();
        return new Pressure(p, Pressure.PASCAL);
    }

    /**
     * 圧力と温度から密度を計算
     * ρ = p / (R * T)
     *
     * @param pressure 圧力（Pa）
     * @param temp 温度（K）
     * @return Density 密度（kg/m³）
     */
    public static Density calculateDensity(Pressure pressure, Temperature temp) {
        double rho = pressure.getSI() / (GAS_CONSTANT * temp.getSI());
        return new Density(rho, Density.KG_PER_M3);
    }
}