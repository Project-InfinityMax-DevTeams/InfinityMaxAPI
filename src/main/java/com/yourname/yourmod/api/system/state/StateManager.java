package com.yourname.yourmod.api.system.state;

public interface StateManager {

    StateContainer attach(Object owner);

    StateContainer getOrCreate(Object owner);

    void detach(Object owner);
}
