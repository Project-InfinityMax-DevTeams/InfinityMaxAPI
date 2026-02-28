package com.yuyuto.infinitymaxapi.gamelibs.physics;

import java.util.Objects;

/**
 * <h2>PhysicalState</h2>
 *
 * 物理状態のパッケージクラス。
 * 温度、圧力、密度、内部エネルギー、相、質量をまとめて保持。
 * 不変クラス（イミュータブル）で、状態更新は新しいインスタンスを返す。
 *
 * 小学生向け：
 * 「モノの今の状態を1つの箱にまとめて持っておく。温度や圧力も一緒」
 */
public final class PhysicalState {

    private final Temperature temperature;
    private final Pressure pressure;
    private final Density density;
    private final Energy internalEnergy;
    private final Phase phase;
    private final Mass mass;
    private final Material material;

    /**
     * コンストラクタ（全て指定）
     */
    public PhysicalState(Temperature temperature, Pressure pressure, Density density,
                         Energy internalEnergy, Phase phase, Mass mass, Material material) {
        this.temperature = Objects.requireNonNull(temperature);
        this.pressure = Objects.requireNonNull(pressure);
        this.density = Objects.requireNonNull(density);
        this.internalEnergy = Objects.requireNonNull(internalEnergy);
        this.phase = Objects.requireNonNull(phase);
        this.mass = Objects.requireNonNull(mass);
        this.material = material;

        validate();
    }

    /** 小学生向けチェック：ありえない物理量を排除 */
    private void validate() {
        if (temperature.getSI() < 0) 
            throw new IllegalArgumentException("Temperature below absolute zero");
        if (density.getSI() <= 0) 
            throw new IllegalArgumentException("Density must be positive");
        if (mass.getSI() <= 0)
            throw new IllegalArgumentException("Mass must be positive");
    }

    /** ゲッター群 */
    public Temperature getTemperature() { return temperature; }
    public Pressure getPressure() { return pressure; }
    public Density getDensity() { return density; }
    public Energy getInternalEnergy() { return internalEnergy; }
    public Phase getPhase() { return phase; }
    public Mass getMass() { return mass; }

    /** コピー */
    public PhysicalState copy() {
        return new PhysicalState(temperature, pressure, density, internalEnergy, phase, mass,material);
    }

    /**
     * 温度だけ更新して新しい状態を返す（イミュータブル更新）
     */
    public PhysicalState withTemperature(Temperature newTemp) {
        return new PhysicalState(newTemp, pressure, density, internalEnergy, phase, mass, material);
    }

    /**
     * 内部エネルギーを更新
     */
    public PhysicalState withInternalEnergy(Energy newEnergy) {
        return new PhysicalState(temperature, pressure, density, newEnergy, phase, mass, material);
    }

    /**
     * 圧力を更新
     */
    public PhysicalState withPressure(Pressure newPressure) {
        return new PhysicalState(temperature, newPressure, density, internalEnergy, phase, mass, material);
    }

    /**
     * 密度を更新
     */
    public PhysicalState withDensity(Density newDensity) {
        return new PhysicalState(temperature, pressure, newDensity, internalEnergy, phase, mass, material);
    }

    /**
     * 相を更新
     */
    public PhysicalState withPhase(Phase newPhase) {
        return new PhysicalState(temperature, pressure, density, internalEnergy, newPhase, mass, material);
    }

    /**
    * 材料更新
    */
    public Material getMaterial() {
        return material;
    }
}
