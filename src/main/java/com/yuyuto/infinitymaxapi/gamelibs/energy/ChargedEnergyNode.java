package com.yuyuto.infinitymaxapi.gamelibs.energy;

public class ChargedEnergyNode extends EnergyNode implements ChargeStorage{

    private double charge;  //クローン
    private final double capacitance; //ファラド
    private final double internalResistance;

    public ChargedEnergyNode(double capacitance,double internalResistance){
        this.capacitance = capacitance;
        this.internalResistance = internalResistance;
    }

    @Override
    public double getCharge(){
        return charge;
    }

    @Override
    public void addCharge(double q){
        this.charge += q;
    }

    @Override
    public double getCapacitance(){
        return capacitance;
    }

    public double getInternalResistance(){
        return internalResistance;
    }

    //帯電から電圧を計算
    public double computeVoltageFromCharge(){
        return charge / capacitance;
    }
}
