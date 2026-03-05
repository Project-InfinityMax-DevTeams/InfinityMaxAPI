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

    /**
 * アイテムの登録情報を内部コレクションに記録し、後で commit() によって実際の登録を行う。
 *
 * @param id        登録に使う識別子。null であってはならず、空文字も不可。モジュール内で一意である必要がある。
 * @param template  登録対象のアイテムテンプレート（実際の Item オブジェクトになることを想定）。
 * @param settings  このアイテムの設定（登録時に利用されるメタ情報を含む）。
 * @throws NullPointerException     id が null の場合
 * @throws IllegalStateException    同じ id が既に登録済みの場合
 */
@Override public <T> void registerItem(String id, T template, ItemSettings settings) { putUnique(items, id, new Entry<>(template, settings)); }
    /**
 * 指定した識別子と設定でブロック登録エントリを内部コレクションに追加する。
 *
 * @param id 登録に使う識別子（空文字・空白は不可）
 * @param template 登録するブロックのテンプレートオブジェクト
 * @param settings ブロックの設定
 * @throws IllegalStateException 同じ識別子が既に登録済みの場合
 */
@Override public <T> void registerBlock(String id, T template, BlockSettings settings) { putUnique(blocks, id, new Entry<>(template, settings)); }
    /**
 * エンティティの登録情報（テンプレートと設定）を内部に記録する。
 *
 * 記録された情報は後で commit() により実際の登録処理で使用される。
 *
 * @param id 登録識別子。モッドのネームスペースと組み合わせて最終的な識別子となるパス部分で、空白であってはなりません。
 * @param template エンティティのテンプレートオブジェクト（登録時に使用する実体）。
 * @param settings エンティティ登録に付随する設定。
 * @throws IllegalStateException 指定した `id` が既に登録済みの場合
 */
@Override public <T, C> void registerEntity(String id, T template, EntitySettings<C> settings) { putUnique(entities, id, new Entry<>(template, settings)); }
    /**
 * ブロックエンティティの登録情報を収集して内部に保持し、後で commit() によって適用されるようにする。
 *
 * @param id 登録識別子。null や空文字は許容されず、同じ識別子で重複登録はできない。
 * @param template 登録するブロックエンティティのテンプレート（型パラメータ T）。
 * @param blocks そのブロックエンティティが関連付けられるブロックの配列。
 * @param settings ブロックエンティティ登録に関する設定情報。
 */
@Override public <T, B> void registerBlockEntity(String id, T template, B[] blocks, BlockEntitySettings settings) { putUnique(blockEntities, id, new Entry<>(template, settings)); }
    /**
 * データ生成用のテンプレートと設定を登録し、後でcommit()によって適用されるよう保持する。
 *
 * @param id 登録識別子 — マップ内で一意である必要があります
 * @param template データ生成に使用するテンプレートオブジェクト
 * @param settings 当該テンプレートに対するデータ生成設定
 * @throws NullPointerException if {@code id} is null
 * @throws IllegalStateException if {@code id} が既に登録済みの場合
 */
@Override public <T> void registerDataGen(String id, T template, DataGenSettings settings) { putUnique(dataGens, id, new Entry<>(template, settings)); }
    /**
 * コミット時に適用されるパケット登録の記述を内部コレクションに追加する。
 *
 * @param id       登録識別子。空文字・nullは許容されず、一意である必要がある（同一idは上書きせずエラーとなる）。
 * @param template 送受信処理を定義した Packet 実装
 * @param settings パケット送受信に関する設定（チャネル名やフローなど）
 */
@Override public void registerPacket(String id, Packet template, PacketSettings settings) { putUnique(packets, id, new Entry<>(template, settings)); }
    /**
 * モッド内で使用するネットワーク登録情報を収集して内部に保存する。
 *
 * @param id       登録識別子。null または空白文字列は不可で、同一識別子が既に登録されていると登録を拒否する。
 * @param template ネットワーク登録に使用するテンプレートオブジェクト。
 * @param settings 登録の動作を制御する設定。
 * @throws IllegalStateException 同一の `id` が既に登録されている場合。
 */
@Override public <T> void registerNetwork(String id, T template, NetworkSettings settings) { putUnique(networks, id, new Entry<>(template, settings)); }
    /**
 * 指定したIDでGUIテンプレートとその設定を登録する。
 *
 * @param id       登録に使用する識別子。nullまたは空文字は許容されず、一意である必要がある。
 * @param template 登録するGUIのテンプレートオブジェクト
 * @param settings GUI登録に用いる設定
 * @throws IllegalStateException 指定したIDが既に登録済みの場合
 */
@Override public <T> void registerGui(String id, T template, GuiSettings settings) { putUnique(guis, id, new Entry<>(template, settings)); }
    /**
 * 登録情報としてワールド定義を内部コレクションに追加し、後でcommit()実行時に実際の登録を行う。
 *
 * @param id       登録識別子。nullまたは空文字であってはならず、既に使用されている識別子は許可されない。
 * @param template 登録するワールドのテンプレートオブジェクト。
 * @param settings ワールド登録に関する設定。
 * @throws IllegalStateException 指定した`id`が既に登録済みの場合。
 */
@Override public <T> void registerWorld(String id, T template, WorldSettings settings) { putUnique(worlds, id, new Entry<>(template, settings)); }

    /**
     * マップに指定のキーで値を追加し、IDの検証と重複チェックを行う。
     *
     * @param map   登録先のマップ
     * @param id    登録キー。nullであってはならず、空白文字のみの文字列ではいけません
     * @param value 追加する値
     * @throws NullPointerException     id が null の場合
     * @throws IllegalArgumentException id が空白のみの文字列の場合
     * @throws IllegalStateException    同じ id が既にマップに存在する場合
     */
    private static <V> void putUnique(Map<String, V> map, String id, V value) {
        Objects.requireNonNull(id, "registry id must not be null");
        if (id.isBlank()) throw new IllegalArgumentException("registry id must not be blank");
        if (map.putIfAbsent(id, value) != null) throw new IllegalStateException("Duplicate registry id: " + id);
    }

    /**
     * 登録キューに溜めた登録定義を実際のゲームレジストリとネットワーク受信口に反映する。
     *
     * アイテム、ブロック、エンティティ型、ブロックエンティティ型をゲームのレジストリに登録し、
     * 登録されたパケット定義に基づいてクライアント／サーバーの受信ハンドラをチャンネル単位で登録する。
     */
    @Override
    public void commit() {
        // 責務: ゲーム本体 Registry に最小限の種類だけ確実に登録する
        items.forEach((id, entry) -> Registry.register(Registries.ITEM, new Identifier(MOD_ID, id), (Item) entry.template()));
        blocks.forEach((id, entry) -> Registry.register(Registries.BLOCK, new Identifier(MOD_ID, id), (Block) entry.template()));
        entities.forEach((id, entry) -> Registry.register(Registries.ENTITY_TYPE, new Identifier(MOD_ID, id), (EntityType<?>) entry.template()));
        blockEntities.forEach((id, entry) -> Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, id), (BlockEntityType<?>) entry.template()));
        guis.forEach((id, entry) -> Registry.register(Registries.SCREEN_HANDLER, new Identifier(MOD_ID, id), (ScreenHandlerType<?>) entry.template()));

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
