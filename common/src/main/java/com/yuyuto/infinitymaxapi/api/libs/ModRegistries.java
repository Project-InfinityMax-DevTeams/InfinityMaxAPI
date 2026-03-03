package com.yuyuto.infinitymaxapi.api.libs;

import com.yuyuto.infinitymaxapi.api.libs.registry.settings.*;

/**
 * ローダーごとの実登録を抽象化するインターフェース。
 *
 * <p>common はこのインターフェースのみを参照し、Forge/Fabric 実装は
 * それぞれのローダー側クラスで行う。</p>
 */
public interface ModRegistries {

    <T> void registerItem(String id, T template, ItemSettings settings);

    <T> void registerBlock(String id, T template, BlockSettings settings);

    <T, C> void registerEntity(String id, T template, EntitySettings<C> settings);

    <T, B> void registerBlockEntity(String id, T template, B[] blocks, BlockEntitySettings settings);

    <T> void registerDataGen(String id, T template, DataGenSettings settings);

    <T> void registerPacket(String id, T template, PacketSettings settings);

    <T> void registerNetwork(String id, T template, NetworkSettings settings);

    <T> void registerGui(String id, T template, GuiSettings settings);

    <T> void registerWorld(String id, T template, WorldSettings settings);
}
