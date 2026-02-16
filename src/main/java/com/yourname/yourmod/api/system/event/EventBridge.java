package com.yourname.yourmod.api.system.event;

import com.yourname.yourmod.api.event.ModEvent;

public interface EventBridge {

    void registerForwarders();

    void emit(ModEvent event);
}
