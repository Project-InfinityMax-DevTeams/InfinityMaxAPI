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


    /**
    * ネットワークパケットの登録情報を追加する。
    *
    * 指定した識別子とテンプレートとなる `Packet`、および `PacketSettings` で示される設定を用いて
    * パケットの登録を記録する。記録された登録は即時にはローダー側に反映されず、`commit()` の呼び出し時に
    * 実際のロード処理（Fabric/Forge 等）へ適用される。
    *
    * @param id 登録に使用する一意の識別子。通常は "modid:packet_name" の形式を想定する。
    * @param template 登録するパケットのテンプレートまたはプロトタイプとなる `Packet` インスタンス。
    * @param settings パケットの配信方向やハンドラ設定など、登録に必要な追加設定。
    */
    void registerPacket(String id, Packet template, PacketSettings settings);

    /**
    * ネットワークメッセージまたはハンドラを指定の識別子と設定で登録する。
    *
    * @param id       登録に使用する一意の識別子（名前空間付き文字列を想定）
    * @param template 登録するメッセージまたはハンドラのテンプレートオブジェクト
    * @param settings ネットワークチャネル、同期方法、ハンドリング挙動などの登録設定
    */ 
    <T> void registerNetwork(String id, T template, NetworkSettings settings);

    /**
    * GUI 要素を指定の識別子と設定で登録する。
    *
    * 登録は内部に蓄積され、commit() 実行時にローダー固有の登録処理へ反映される。
    *
    * @param id       登録に使用する識別子
    * @param template GUI のテンプレートまたはファクトリを表すオブジェクト
    * @param settings GUI の動作や表示に関する設定
    */
    <T> void registerGui(String id, T template, GuiSettings settings);

    /**
    * 指定した識別子でワールド関連の要素を登録する。
    *
    * 登録情報は内部に蓄積され、commit() の呼び出し時にローダー固有の登録処理が実行されます。
    *
    * @param id        登録に使用する名前付き識別子（例: `"modid:resource"`）
    * @param template  登録対象を表すテンプレートまたはインスタンス
    * @param settings  ワールド関連の設定（生成・スポーン・配置などの挙動を指定する設定）
    */
    <T> void registerWorld(String id, T template, WorldSettings settings);

    /**
 * ため込まれた登録情報をローダーの実際の Registry と Network API に反映します。
 *
 * <p>呼び出すことで、これまで登録されたエントリを実際のローダー固有の登録処理へ適用します。</p>
 */
    void commit();
}
