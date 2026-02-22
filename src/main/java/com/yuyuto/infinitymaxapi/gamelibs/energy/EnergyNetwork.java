package com.yuyuto.infinitymaxapi.gamelibs.energy;

import java.util.HashSet;
import java.util.Set;

public class EnergyNetwork {
    private final Set<EnergyNode> nodes = new HashSet<>();
    private final Set<EnergyConnection> connections = new HashSet<>();
    private final MatrixEnergySolver solver;

    public EnergyNetwork(MatrixEnergySolver solver){
        this.solver = solver;
    }

    public void addNode(EnergyNode node){
        nodes.add(node);
    }

    public void connect(EnergyNode a, EnergyNode b, double resistance){
        connections.add(new EnergyConnection(a,b,resistance));
    }

    public Set<EnergyNode> getNodes(){
        return nodes;
    }

    public Set<EnergyConnection> getConnections(){
        return connections;
    }

    public void tick(double deltaTime){
        solver.solve(this, deltaTime);
    }
}
