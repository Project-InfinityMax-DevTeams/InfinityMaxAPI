package com.yuyuto.infinitymaxapi.fabricimpl;

import com.yuyuto.infinitymaxapi.api.libs.ModRegistries;
import com.yuyuto.infinitymaxapi.api.libs.packet.Packet;
import com.yuyuto.infinitymaxapi.api.libs.registry.settings.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Fabric 実装の責務:
 * 1) DSL で集めた登録情報を Map に貯める
 * 2) commit() で Fabric API に一括反映する
 */
public final class FabricRegistriesImpl implements ModRegistries {
    private static final String MOD_ID = "infinitymaxapi";

    private record Entry<T, S>(T template, S settings) {}

    private final Map<String, Entry<?, ItemSettings>> items = new HashMap<>();
    private final Map<String, Entry<?, BlockSettings>> blocks = new HashMap<>();
    private final Map<String, Entry<?, EntitySettings<?>>> entities = new HashMap<>();
    private final Map<String, Entry<?, BlockEntitySettings>> blockEntities = new HashMap<>();
    private final Map<String, Entry<?, DataGenSettings>> dataGens = new HashMap<>();
    private final Map<String, Entry<?, GuiSettings>> guis = new HashMap<>();
    private final Map<String, Entry<?, WorldSettings>> worlds = new HashMap<>();
    private final Map<String, Entry<?, NetworkSettings>> networks = new HashMap<>();
    private final Map<String, Entry<Packet, PacketSettings>> packets = new HashMap<>();

    @Override public <T> void registerItem(String id, T template, ItemSettings settings) { putUnique(items, id, new Entry<>(template, settings)); }
    @Override public <T> void registerBlock(String id, T template, BlockSettings settings) { putUnique(blocks, id, new Entry<>(template, settings)); }
    @Override public <T, C> void registerEntity(String id, T template, EntitySettings<C> settings) { putUnique(entities, id, new Entry<>(template, settings)); }
    @Override public <T, B> void registerBlockEntity(String id, T template, B[] blocks, BlockEntitySettings settings) { putUnique(blockEntities, id, new Entry<>(template, settings)); }
    @Override public <T> void registerDataGen(String id, T template, DataGenSettings settings) { putUnique(dataGens, id, new Entry<>(template, settings)); }
    @Override public void registerPacket(String id, Packet template, PacketSettings settings) { putUnique(packets, id, new Entry<>(template, settings)); }
    @Override public <T> void registerNetwork(String id, T template, NetworkSettings settings) { putUnique(networks, id, new Entry<>(template, settings)); }
    @Override public <T> void registerGui(String id, T template, GuiSettings settings) { putUnique(guis, id, new Entry<>(template, settings)); }
    @Override public <T> void registerWorld(String id, T template, WorldSettings settings) { putUnique(worlds, id, new Entry<>(template, settings)); }

    private static <V> void putUnique(Map<String, V> map, String id, V value) {
        Objects.requireNonNull(id, "registry id must not be null");
        if (id.isBlank()) throw new IllegalArgumentException("registry id must not be blank");
        if (map.putIfAbsent(id, value) != null) throw new IllegalStateException("Duplicate registry id: " + id);
    }

    @Override
    public void commit() {
        // 責務: ゲーム本体 Registry に最小限の種類だけ確実に登録する
        items.forEach((id, entry) -> Registry.register(Registries.ITEM, new Identifier(MOD_ID, id), (Item) entry.template()));
        blocks.forEach((id, entry) -> Registry.register(Registries.BLOCK, new Identifier(MOD_ID, id), (Block) entry.template()));
        entities.forEach((id, entry) -> Registry.register(Registries.ENTITY_TYPE, new Identifier(MOD_ID, id), (EntityType<?>) entry.template()));
        blockEntities.forEach((id, entry) -> Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, id), (BlockEntityType<?>) entry.template()));

        // 責務: Packet Flow(C2S/S2C) に応じて Fabric networking の受信口を張る
        packets.forEach((id, entry) -> {
            Identifier channelId = new Identifier(MOD_ID, entry.settings().channel + "/" + id);
            Packet template = entry.template();

            if (template.flow() == Packet.Flow.C2S) {
                ServerPlayNetworking.registerGlobalReceiver(channelId, (server, player, handler, buf, responseSender) -> {
                    Packet decoded = template.decode(buf);
                    server.execute(() -> decoded.handleC2S(server, player));
                });
            } else {
                ClientPlayNetworking.registerGlobalReceiver(channelId, (client, handler, buf, responseSender) -> {
                    Packet decoded = template.decode(buf);
                    client.execute(() -> decoded.handleS2C(client, client.player));
                });
            }
        });
    }
}
