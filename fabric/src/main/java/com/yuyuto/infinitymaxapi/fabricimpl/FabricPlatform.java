package com.yuyuto.infinitymaxapi.fabricimpl;

import com.yuyuto.infinitymaxapi.api.libs.ModRegistries;
import com.yuyuto.infinitymaxapi.loader.LoaderExpectPlatform;

public final class FabricPlatform implements LoaderExpectPlatform {

    private static final String MOD_ID = "infinitymaxapi";

    private final ModRegistries registries = new FabricRegistriesImpl();
    private final Network network = new FabricNetworkImpl();
    private final Events events = new FabricEventsImpl();

    @Override
    public String id(String path) {
        return MOD_ID + ":" + path;
    }

    @Override
    public boolean isClient() {
        return false;
    }

    @Override
    public com.yuyuto.infinitymaxapi.api.libs.ModRegistries registries() {
        return registries;
    }

    @Override
    public Network network() {
        return network;
    }

    @Override
    public Events events() {
        return events;
    }

    @Override
    public void commit() {
        FabricRegistriesImpl r = (FabricRegistriesImpl) this.registries;

        // ---- Item ----
        r.items().forEach((id, entry) -> {
            Registry.register(
                Registries.ITEM,
                new Identifier(MOD_ID, id),
                (Item) entry.template()
            );
        });

        // ---- Block ----
        r.blocks().forEach((id, entry) -> {
            Registry.register(
                Registries.BLOCK,
                new Identifier(MOD_ID, id),
                (Block) entry.template()
            );
        });

        // ---- Entity ----
        r.entities().forEach((id, entry) -> {
            Registry.register(
                Registries.ENTITY_TYPE,
                new Identifier(MOD_ID, id),
                (EntityType<?>) entry.template()
            );
        });

        // ---- BlockEntity ----
        r.blockEntities().forEach((id, entry) -> {
            Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                new Identifier(MOD_ID, id),
                (BlockEntityType<?>) entry.template()
            );
        });

        // ---- Packet ----
        r.packets().forEach((id, entry) -> {
            PacketSettings settings = entry.settings();
            Identifier channelId = new Identifier(MOD_ID, settings.channel);

            if (settings.direction == PacketDirection.C2S) {
                ServerPlayNetworking.registerGlobalReceiver(
                    channelId,
                    (server, player, handler, buf, responseSender) ->
                        ((YourPacketType) entry.template()).handleC2S(server, player, buf)
                );
            }
        });

        // ---- GUI ----
        r.guis().forEach((id, entry) -> {
            ScreenRegistry.register(
                new Identifier(MOD_ID, entry.settings().screenId),
                (ScreenHandlerType<?>) entry.template()
            );
        });

        // World / Network は後回しでOK
    }
}
