package com.yourname.yourmod.api.system.network;

public interface NetworkChannel {

    <T> void register(MessageSpec<T> spec);

    <T> void sendToServer(String messageId, T payload);

    <T> void sendToPlayer(Object player, String messageId, T payload);
}
