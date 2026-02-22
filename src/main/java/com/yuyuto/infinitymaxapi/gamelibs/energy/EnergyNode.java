package com.yuyuto.infinitymaxapi.gamelibs.energy;

public class EnergyNode {

    private double potential;      // 求めたい未知数
    private double injectedCurrent; // 外部から与えられる
    private double voltageSourceContribution;//電圧源からの電流換算
    private double cumulativePowerLoss = 0;
    private double cumulativeExtraCurrent = 0;

    //未知数関連
    public double getPotential() {
        return potential;
    }
    public void setPotential(double potential) {
        this.potential = potential;
    }

    //外部からの数値
    public double getInjectedCurrent() {
        return injectedCurrent;
    }
    public void setInjectedCurrent(double injectedCurrent) {
        this.injectedCurrent = injectedCurrent;
    }

    //電圧源からの電流換算
    public double getVoltageSourceContribution(){
        return voltageSourceContribution;
    }
    public void setCurrentFromVoltage(double voltageSourceContribution){
        this.voltageSourceContribution = voltageSourceContribution;
    }

    //損失電力を累積する
    public void addPowerLoss(double joule) {
        cumulativePowerLoss += joule;
    }

    //累積損失を取得する
    public double getCumulativePowerLoss(){
       return cumulativePowerLoss;
    }

    //累積追加電流を取得する
    public double getCumulativeExtraCurrent() {
        return cumulativeExtraCurrent;
    }

    //次tickに向けてリセットする
    public void resetCumulativeValues(){
        cumulativePowerLoss = 0;
        cumulativeExtraCurrent = 0;
    }
}