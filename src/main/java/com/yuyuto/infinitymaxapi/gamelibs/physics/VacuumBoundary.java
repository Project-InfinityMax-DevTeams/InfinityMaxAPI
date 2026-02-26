package com.yuyuto.infinitymaxapi.gamelibs.physics;

/**
 * ===============================================
 * VacuumBoundary
 * ===============================================
 *
 * 【概要】
 * 真空境界条件。
 *
 * 真空とは：
 * ・圧力ほぼゼロ
 * ・密度ほぼゼロ
 * ・質量ほぼゼロ
 *
 * 外部が宇宙空間のような状態を表します。
 *
 * 【物理的意味】
 * 内部流体は外に自由に膨張できます。
 * これは「開放境界」の一種です。
 */
public final class VacuumBoundary implements BoundaryCondition {

    private static final double EPSILON_DENSITY = 1e-8;
    private static final double EPSILON_MASS = 1e-8;

    @Override
    public PhysicalState apply(PhysicalState internal,
                               PhysicalState external) {

        /*
         * 真空は：
         * ・圧力 ≈ 0
         * ・密度 ≈ 0
         * ・質量 ≈ 0
         */

        return new PhysicalState(
                internal.getTemperature(), // 温度は仮保持
                new Pressure(0, Pressure.PASCAL),
                new Density(EPSILON_DENSITY, Density.KG_PER_M3),
                internal.getInternalEnergy(),
                Phase.GAS,
                new Mass(EPSILON_MASS, Mass.KILOGRAM)
        );
    }
}