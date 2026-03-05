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

    /**
 * アイテムの登録テンプレートと設定を内部マップに保存する。
 *
 * @param id 登録識別子。nullまたは空文字は許容されず、一意である必要がある。
 * @param template 登録するアイテムのテンプレートオブジェクト。
 * @param settings アイテム登録時に使用される設定。
 * @throws IllegalArgumentException id が null または空文字の場合。
 * @throws IllegalStateException id が既に登録済みの場合。
 */
@Override public <T> void registerItem(String id, T template, ItemSettings settings) { putUnique(items, id, new Entry<>(template, settings)); }
    /**
 * 指定されたIDでブロックの登録テンプレートと設定を内部に記録する。
 *
 * @param id 登録に使用する識別子（nullまたは空白は許容されず検査される）
 * @param template ブロックの登録テンプレート
 * @param settings ブロックの設定
 * @throws IllegalArgumentException idがnullまたは空白文字列の場合
 * @throws IllegalStateException 同じidですでに登録済みの場合
 */
@Override public <T> void registerBlock(String id, T template, BlockSettings settings) { putUnique(blocks, id, new Entry<>(template, settings)); }
    /**
 * エンティティの登録テンプレートと設定を内部の登録マップに保存する。
 *
 * @param id 登録識別子（空文字やnullは許容されない）。同じ識別子が既に存在する場合は登録を拒否する。
 * @param template 登録するエンティティのテンプレートオブジェクト
 * @param settings エンティティ登録に関連する設定
 * @throws IllegalArgumentException idがnullまたは空白の場合
 * @throws IllegalStateException 同一のidが既に登録済みの場合
 */
@Override public <T, C> void registerEntity(String id, T template, EntitySettings<C> settings) { putUnique(entities, id, new Entry<>(template, settings)); }
    /**
 * ブロックエンティティの登録テンプレートと設定を内部マップに一時保存する。
 *
 * <p>実際の登録は commit() 実行時に反映される。</p>
 *
 * @param id 登録識別子。空文字は許可されない（空文字の場合は {@link IllegalArgumentException} を投げる）。
 * @param template ブロックエンティティの登録テンプレートオブジェクト。
 * @param blocks このブロックエンティティに関連付けるブロック配列（関連ブロックを識別するために使用）。
 * @param settings ブロックエンティティの追加設定。
 * @throws IllegalArgumentException id が null または空文字の場合
 * @throws IllegalStateException 同一 id の登録が既に存在する場合
 */
@Override public <T, B> void registerBlockEntity(String id, T template, B[] blocks, BlockEntitySettings settings) { putUnique(blockEntities, id, new Entry<>(template, settings)); }
    /**
 * データ生成（data-gen）のテンプレートと設定を内部登録マップに追加する。
 *
 * @param id 登録識別子。nullでなく空白であってはいけません（空白の場合は IllegalArgumentException を投げます）。既に同一の id が存在すると IllegalStateException を投げます。
 * @param template データ生成のテンプレートオブジェクト（型は呼び出し元が決定）。
 * @param settings データ生成の振る舞いを定義する設定オブジェクト。
 * @throws IllegalArgumentException id が null または空白の場合
 * @throws IllegalStateException 同じ id が既に登録済みの場合
 */
@Override public <T> void registerDataGen(String id, T template, DataGenSettings settings) { putUnique(dataGens, id, new Entry<>(template, settings)); }
    /**
 * 指定したIDでパケットのテンプレートと設定を登録する。
 *
 * @param id       登録識別子（nullまたは空白文字は許容されず、空白の場合は例外が発生します）
 * @param template 登録するパケットのテンプレート
 * @param settings パケットの設定
 * @throws IllegalArgumentException idがnullまたは空白文字列の場合
 * @throws IllegalStateException    同一のidが既に登録されている場合
 */
@Override public void registerPacket(String id, Packet template, PacketSettings settings) { putUnique(packets, id, new Entry<>(template, settings)); }
    /**
 * ネットワーク定義を内部の登録マップに一意に格納する。
 *
 * @param id 登録に使用する識別子。null または空白の文字列は許容されない。
 * @param template 登録するネットワークのテンプレート（任意の型）。
 * @param settings ネットワークの設定情報。
 * @throws IllegalArgumentException id が null または空白の文字列の場合
 * @throws IllegalStateException 同じ id ですでに登録が存在する場合
 */
@Override public <T> void registerNetwork(String id, T template, NetworkSettings settings) { putUnique(networks, id, new Entry<>(template, settings)); }
    /**
 * 指定したIDでGUI登録テンプレートと設定を一時的に保存する。
 *
 * @param id 登録に使用する一意の識別子（nullまたは空白は不可）
 * @param template 登録するGUIのテンプレートオブジェクト
 * @param settings GUI登録に関する追加設定
 * @throws IllegalArgumentException idがnullまたは空白の場合
 * @throws IllegalStateException 同じidが既に登録済みの場合
 */
@Override public <T> void registerGui(String id, T template, GuiSettings settings) { putUnique(guis, id, new Entry<>(template, settings)); }
    /**
 * ワールドの登録テンプレートと設定を内部マップに一意に保存する。
 *
 * @param id 登録に使用する識別子（nullまたは空白は許容されない）
 * @param template 登録するワールドのテンプレート
 * @param settings ワールドの設定
 * @throws IllegalArgumentException id が null または空白文字列の場合
 * @throws IllegalStateException 同じ id が既に登録済みの場合
 */
@Override public <T> void registerWorld(String id, T template, WorldSettings settings) { putUnique(worlds, id, new Entry<>(template, settings)); }

    /**
     * 指定されたマップに一意のキーで値を挿入する。
     *
     * <p>id が null または空白の場合は受け付けず、既に同じ id が存在する場合は挿入を拒否します。</p>
     *
     * @param map   値を格納するターゲットのマップ
     * @param id    登録用の識別子（null または空白は許容されない）
     * @param value マップに挿入する値
     * @throws NullPointerException     id が null の場合
     * @throws IllegalArgumentException id が空白文字列の場合
     * @throws IllegalStateException    同じ id が既にマップに存在する場合
     */
    private static <V> void putUnique(Map<String, V> map, String id, V value) {
        Objects.requireNonNull(id, "registry id must not be null");
        if (id.isBlank()) throw new IllegalArgumentException("registry id must not be blank");
        if (map.putIfAbsent(id, value) != null) throw new IllegalStateException("Duplicate registry id: " + id);
    }

    /**
     * 保持している登録情報を反映し、登録されたパケット用のネットワークチャネルとメッセージハンドラを構成する。
     *
     * <p>内部に蓄えられたパケット登録をチャネル単位で SimpleChannel にまとめて作成し、
     * 各登録に対して一意の識別子を割り当てて PacketEnvelope のエンコード／デコードおよび
     * パケット処理の委譲を登録する。</p>
     */
    @Override
    public void commit() {
        // ---- Item ----
        items.forEach((id, entry) -> {
            Registry.register(
                ForgeRegistries.ITEMS,
                new ResourceLocation(modId, id),
                (Item) entry.template()
            );
        });

        // ---- Block ----
        blocks.forEach((id, entry) -> {
            Registry.register(
                ForgeRegistries.BLOCKS,
                new ResourceLocation(modId, id),
                (Block) entry.template()
            );
        });

        // ---- Entity ----
        entities.forEach((id, entry) -> {
            Registry.register(
                ForgeRegistries.ENTITY_TYPES,
                new ResourceLocation(modId, id),
                (EntityType<?>) entry.template()
            );
        });

        // ---- BlockEntity ----
        blockEntities.forEach((id, entry) -> {
            Registry.register(
                ForgeRegistries.BLOCK_ENTITY_TYPES,
                new ResourceLocation(modId, id),
                (BlockEntityType<?>) entry.template()
            );
        });

        // Packet / Network / World は今は触らない
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

                // encode
                (msg, buf) -> {
                    buf.writeUtf(msg.id());
                    msg.payload().encode(buf);
                },

                // decode
                buf -> {
                    String receivedId = buf.readUtf();
                    Entry<Packet, PacketSettings> reg = packets.get(receivedId);
                    if (reg == null) return new PacketEnvelope(receivedId, null);
                    return new PacketEnvelope(receivedId, reg.template().decode(buf));
                },

                // handle
                (msg, ctxSupplier) -> {
                    var ctx = ctxSupplier.get();
                    ctx.enqueueWork(() -> handlePacketMessage(msg, ctxSupplier, template.flow()));
                    ctx.setPacketHandled(true);
                },

                // ★ここが重要★
                Optional.of(
                    template.flow() == PacketFlow.C2S
                        ? NetworkDirection.PLAY_TO_SERVER
                        : NetworkDirection.PLAY_TO_CLIENT
                )
            );
        });
    }

    /**
     * 受信したパケット封筒をネットワークコンテキスト上で処理キューに登録し、処理完了としてマークする。
     *
     * <p>適切なフロー（クライアント→サーバーまたはサーバー→クライアント）に応じて
     * 封筒内の `Packet` を呼び出して処理を委譲する。ペイロードが存在しない場合は何もしない。</p>
     *
     * @param msg 受信したパケットとその識別子を含む封筒
     * @param ctxSupplier ネットワークイベントのコンテキストを供給するサプライヤ
     * @param flow パケットの送受信方向（C2S または S2C）
     */
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
        /**
         * パケットの送信フローから対応するNetWorkの方向を決定する。
         *
         * @param flow パケットの送受信フロー（例: C2S はクライアントからサーバへ、S2C はサーバからクライアントへ）
         * @return `NetworkDirection.PLAY_TO_SERVER` は `Packet.Flow.C2S` の場合、その他は `NetworkDirection.PLAY_TO_CLIENT`
         */
        private static NetworkDirection from(Packet.Flow flow) {
            return flow == Packet.Flow.C2S ? NetworkDirection.PLAY_TO_SERVER : NetworkDirection.PLAY_TO_CLIENT;
        }
    }
}
