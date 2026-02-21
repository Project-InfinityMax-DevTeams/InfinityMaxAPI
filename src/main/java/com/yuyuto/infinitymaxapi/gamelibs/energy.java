package com.yuyuto.infinitymaxapi.gamelibs;

public class energy {
    private double resistance;
    private double voltage;
    private double amphea;
    private double watt;
    private double joule;
    private double second;

    public void setAmphea(int amphea) {
        this.amphea = amphea;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public double getAmphea() {
        return amphea;
    }

    public double getResistance() {
        return resistance;
    }
}