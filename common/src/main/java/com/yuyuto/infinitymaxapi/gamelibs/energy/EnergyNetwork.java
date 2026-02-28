package com.yuyuto.infinitymaxapi.gamelibs.energy;

import java.util.*;

public class EnergyNetwork {

    private final Set<EnergyNode> nodes = new HashSet<>();
    private final Set<EnergyConnection> connections = new HashSet<>();
    private final Map<EnergyConnection, ConnectionState> states = new HashMap<>();

    private final MatrixEnergySolver solver;

    public EnergyNetwork(MatrixEnergySolver solver){
        this.solver = solver;
    }

    public void addNode(EnergyNode node){
        nodes.add(node);
    }

    public void connect(EnergyNode a, EnergyNode b, double resistance, double maxCurrent, double breakdownVoltage, double lossThreshold){
        EnergyConnection conn = new EnergyConnection(a,b,resistance);
        connections.add(conn);

        states.put(conn,
                new ConnectionState(conn, maxCurrent, breakdownVoltage, lossThreshold));
    }

    public Set<EnergyNode> getNodes(){
        return nodes;
    }

    public Set<EnergyConnection> getConnections(){
        return connections;
    }

    public Collection<ConnectionState> getConnectionStates(){
        return states.values();
    }

    public ConnectionState getState(EnergyConnection connection){
        return states.get(connection);
    }

    public void tick(double deltaTime){
        solver.solve(this, deltaTime);
    }
}