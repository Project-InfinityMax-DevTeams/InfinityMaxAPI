package com.yuyuto.infinitymaxapi.gamelibs.physics;

/**
 * <h2>JouleHeating（ジュール熱）</h2>
 *
 * <pre>
 * Q = I² R t
 * </pre>
 *
 * 電流が流れると抵抗で熱が出ます。
 *
 * 電気が流れると、こすれて「あつく」なります。
 */
public final class JouleHeating implements PhysicalPhenomenon {

    private final double resistance;
    private final double current;
    private final double specificHeat;

    public JouleHeating(double resistance, double current, double specificHeat) {

        this.resistance = resistance;
        this.current = current;
        this.specificHeat = specificHeat;
    }

    @Override
    public PhysicalState apply(PhysicalState state, double deltaTime) {
        /*
         * ジュール熱
         */
        double heat = current * current * resistance * deltaTime;
        double mass = state.getMass().getSI();
        double temperatureChange = heat / (mass * specificHeat);
        double newTempValue = state.getTemperature().getSI() + temperatureChange;
        Energy newEnergy = new Energy( state.getInternalEnergy().getSI() + heat,Energy.JOULE);
        Temperature newTemp = new Temperature(newTempValue, Temperature.KELVIN);

        return new PhysicalState(newTemp,state.getPressure(),state.getDensity(),newEnergy,PhaseResolver.resolve(newTemp),state.getMass());
    }
}