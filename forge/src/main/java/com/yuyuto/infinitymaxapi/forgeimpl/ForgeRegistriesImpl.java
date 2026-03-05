package com.yuyuto.infinitymaxapi.forgeimpl;

import com.yuyuto.infinitymaxapi.api.libs.ModRegistries;
import com.yuyuto.infinitymaxapi.api.libs.packet.Packet;
import com.yuyuto.infinitymaxapi.api.libs.registry.settings.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * Forge 実装の責務:
 * 1) DSL の登録要求を Map に退避
 * 2) commit() で Forge Registry / Network へ反映
 */
public final class ForgeRegistriesImpl implements ModRegistries {
    private static final String MOD_ID = "infinitymaxapi";

    private record Entry<T, S>(T template, S settings) {}
    private record PacketEnvelope(String id, Packet payload) {}

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
        // 責務: アイテム/ブロック等は既存の Forge 登録経路で扱う（この最小実装では保持のみ）。
        // 責務: channel 単位で SimpleChannel を作成し、受信時に Packet.handle* へ委譲
        Map<String, SimpleChannel> channels = new HashMap<>();
        AtomicInteger discriminator = new AtomicInteger(0);

        packets.forEach((packetId, entry) -> {
            PacketSettings settings = entry.settings();
            Packet template = entry.template();

            SimpleChannel channel = channels.computeIfAbsent(settings.channel, ch ->
                NetworkRegistry.newSimpleChannel(
                    new ResourceLocation(MOD_ID, ch),
                    () -> settings.protocolVersion,
                    settings.protocolVersion::equals,
                    settings.protocolVersion::equals
                )
            );

            channel.registerMessage(
                discriminator.getAndIncrement(),
                PacketEnvelope.class,
                (msg, buf) -> {
                    buf.writeUtf(msg.id());
                    msg.payload().encode(buf);
                },
                buf -> {
                    String receivedId = buf.readUtf();
                    Entry<Packet, PacketSettings> reg = packets.get(receivedId);
                    if (reg == null) return new PacketEnvelope(receivedId, null);
                    return new PacketEnvelope(receivedId, reg.template().decode(buf));
                },
                (msg, ctxSupplier) -> handlePacketMessage(msg, ctxSupplier, template.flow()),
                OptionalDirection.from(template.flow())
            );
        });
    }

    private static void handlePacketMessage(PacketEnvelope msg, Supplier<NetworkEvent.Context> ctxSupplier, Packet.Flow flow) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> {
            if (msg.payload() == null) return;
            if (flow == Packet.Flow.C2S) {
                ServerPlayer sender = ctx.getSender();
                msg.payload().handleC2S(sender != null ? sender.server : null, sender);
            } else {
                msg.payload().handleS2C(null, null);
            }
        });
        ctx.setPacketHandled(true);
    }

    private static class OptionalDirection {
        private static NetworkDirection from(Packet.Flow flow) {
            return flow == Packet.Flow.C2S ? NetworkDirection.PLAY_TO_SERVER : NetworkDirection.PLAY_TO_CLIENT;
        }
    }
}
