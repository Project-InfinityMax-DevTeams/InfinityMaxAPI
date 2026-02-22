package com.yuyuto.infinitymaxapi.gamelibs.energy;

public record EnergyConnection(EnergyNode from, EnergyNode to, double resistance) {
    public double computeCurrent(double vFrom, double vTo){
        return (vFrom - vTo) / resistance;
    }

    public double computeLoss(double vFrom, double vTo){
        double i = computeCurrent(vFrom,vTo);
        return i * i * resistance;
    }
}
