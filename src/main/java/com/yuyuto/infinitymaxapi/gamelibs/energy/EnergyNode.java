package com.yuyuto.infinitymaxapi.gamelibs.energy;

public class EnergyNode {

    private double potential;      // 求めたい未知数
    private double injectedCurrent; // 外部から与えられる

    public double getPotential() {
        return potential;
    }
    public void setPotential(double potential) {
        this.potential = potential;
    }

    public double getInjectedCurrent() {
        return injectedCurrent;
    }
    public void setInjectedCurrent(double injectedCurrent) {
        this.injectedCurrent = injectedCurrent;
    }
}