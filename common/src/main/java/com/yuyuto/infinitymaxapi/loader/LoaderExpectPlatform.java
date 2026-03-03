package com.yuyuto.infinitymaxapi.loader;

public interface LoaderExpectPlatform {

    String id(String path);
    boolean isClient();

    Registries registries();
    Network network();
    Events events();

    interface Registries {
        <T> void item(String name, T item);
        /**
 * 指定された名前でブロックを登録します。
 *
 * @param <T>      ブロックを表す型
 * @param name     登録するブロックの識別名（path またはレジストリ名）
 * @param block    登録するブロックのインスタンス
 * @param strength ブロックの強度（破壊に要する耐久や硬さを表す値）
 * @param noOcclusion 隣接ブロックによる遮蔽を無効にする場合は `true`（視界や光の遮蔽を抑えるフラグ）
 */
<T> void block(String name, T block, float strength, boolean noOcclusion);
        /**
 * 指定した名前でエンティティタイプを登録する。
 *
 * @param name エンティティの登録名（識別用の文字列）
 * @param entityType 登録するエンティティタイプのインスタンス
 * @param category エンティティのカテゴリ（分類）
 * @param width エンティティの幅（ブロック単位）
 * @param height エンティティの高さ（ブロック単位）
 */
<T, C> void entity(String name, T entityType, C category, float width, float height);
        /**
 * 指定した名前でブロックエンティティ型を登録し、その型を関連付けるブロック群を指定します。
 *
 * @param name 登録に使用する一意の識別名
 * @param blockEntityType 登録するブロックエンティティの型インスタンス
 * @param blocks そのブロックエンティティが関連付けられるブロック（可変長引数）
 */
<T, B> void blockEntity(String name, T blockEntityType, B... blocks);

        /**
 * データ生成（DataGen）用の登録フックを提供する。
 *
 * デフォルト実装は何もしません。必要に応じてオーバーライドしてデータ生成定義を登録してください。
 *
 * @param name 登録に使用する識別名
 * @param dataGenDefinition データ生成の定義オブジェクト（プラットフォーム固有の型を受け取ります）
 */
        default <T> void dataGen(String name, T dataGenDefinition) {}

        /**
 * 名前で指定した GUI 定義をプラットフォームに登録する登録ポイントを提供する。
 *
 * プラットフォーム側で必要に応じてオーバーライドして GUI を登録するためのフックです。
 *
 * @param name 登録キーとなる識別名（例: モードの名前やパス）
 * @param guiDefinition 登録する GUI の定義オブジェクト（型はプラットフォームごとに解釈される）
 */
        default <T> void gui(String name, T guiDefinition) {}

        /**
 * ワールド要素（ディメンション、バイオーム、構造物）を登録するためのフックを提供する。
 *
 * プラットフォーム実装は必要に応じてこのメソッドをオーバーライドして指定された名前でワールド要素を登録する。
 *
 * @param name 登録に使用する識別名
 * @param worldDefinition 登録するワールド要素の定義オブジェクト（型はプラットフォームや用途に依存）
 */
        default <T> void world(String name, T worldDefinition) {}

        /**
 * 名前付きのネットワーク定義を登録する。
 *
 * 実装側はこの呼び出しを利用してネットワークチャンネルやハンドラなどの初期化を行う。デフォルト実装は何もしません。
 *
 * @param name 登録に使う一意の名前（識別子）
 * @param networkDefinition プラットフォーム固有のネットワーク定義オブジェクト（型は実装に依存）
 */
        default <T> void network(String name, T networkDefinition) {}

        /**
 * ネットワークパケットを登録するための登録フックを提供する。
 *
 * プラットフォーム固有の実装がこのメソッドをオーバーライドしてパケットの登録処理を行う。デフォルト実装は何もしません。
 *
 * @param name 登録に使用する識別名（モジュール内で一意となること）
 * @param packetDefinition 登録するパケットの定義または構成オブジェクト
 */
        default <T> void packet(String name, T packetDefinition) {}
    }

    interface Network {
        void register();
        <T> void sendToServer(T packet);
        <T> void sendToPlayer(T player, T packet);
    }

    interface Events {
        void register();
        <T> void onPlayerJoin(T player);
    }
}
