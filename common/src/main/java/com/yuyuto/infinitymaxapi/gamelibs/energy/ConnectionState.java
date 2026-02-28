package com.yuyuto.infinitymaxapi.gamelibs.energy;

public class ConnectionState {

    private final EnergyConnection connection;
    private final double maxCurrent;
    private final double breakdownVoltage;

    private boolean broken;
    private double accumulatedLoss;
    private final double lossThreshold;

    public ConnectionState(EnergyConnection connection, double maxCurrent, double breakdownVoltage, double lossThreshold) {
        this.connection = connection;
        this.maxCurrent = maxCurrent;
        this.breakdownVoltage = breakdownVoltage;
        this.lossThreshold = lossThreshold;
    }

    public boolean isActive() {
        return !broken;
    }

    public void evaluate(double vFrom, double vTo, double deltaTime) {

        double current = connection.computeCurrent(vFrom, vTo);

        if (Math.abs(current) > maxCurrent) {
            broken = true;
        }

        if (Math.abs(vFrom - vTo) > breakdownVoltage) {
            broken = true;
        }

        double loss = connection.computeLoss(vFrom, vTo) * deltaTime;
        accumulatedLoss += loss;

        if (accumulatedLoss > lossThreshold) {
            broken = true;
        }
    }

    public EnergyConnection connection() {
        return connection;
    }
}
