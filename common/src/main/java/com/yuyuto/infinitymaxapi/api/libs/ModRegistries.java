package com.yuyuto.infinitymaxapi.api.libs;

import com.yuyuto.infinitymaxapi.api.libs.packet.Packet;
import com.yuyuto.infinitymaxapi.api.libs.registry.settings.*;

/**
 * ローダーごとの実登録を抽象化するインターフェース。
 *
 * <p>責務:
 * common は「何を登録したいか」をこの API に渡すだけにし、
 * 実際のローダー API 呼び出しは Fabric / Forge 側の commit() に集約する。</p>
 */
public interface ModRegistries {

    <T> void registerItem(String id, T template, ItemSettings settings);

    <T> void registerBlock(String id, T template, BlockSettings settings);

    <T, C> void registerEntity(String id, T template, EntitySettings<C> settings);

    <T, B> void registerBlockEntity(String id, T template, B[] blocks, BlockEntitySettings settings);

    <T> void registerDataGen(String id, T template, DataGenSettings settings);

    void registerPacket(String id, Packet template, PacketSettings settings);

    <T> void registerNetwork(String id, T template, NetworkSettings settings);

    <T> void registerGui(String id, T template, GuiSettings settings);

    <T> void registerWorld(String id, T template, WorldSettings settings);

    /**
     * ため込んだ登録情報(Map)をローダーの実 Registry / Network API へ反映する。
     */
    void commit();
}
