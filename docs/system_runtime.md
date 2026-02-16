# System Runtime 実運用ガイド

このドキュメントは `api.system` テンプレートを**ブラックボックス化させない**ための運用手順です。

## 0. 起動時の実処理
`YourMod.init()` は次の順で処理します。
1. `installDefaultsIfNeeded()`
   - 何も登録されていない場合は `HeartbeatSystem` を自動登録
   - DSLソース未指定なら `InMemoryDslSource` を自動注入
2. `SystemRegistry.register(...)`
3. `SystemBootstrap.start()`
   - `DataDrivenLoader.bind()`
   - `EventBridge.registerForwarders()`
   - `initializeAll(runtime)`

## 1. どこに何を書くか

### A. システム実装 (HOW)
配置例:
- `src/main/java/com/yourname/yourmod/api/system/example/HeartbeatSystem.java`

必須:
- `id()` で一意文字列
- `initialize(SystemRuntime runtime)` で動作を実装

任意:
- `ConfigurableSystem` を実装し `configure(DslDefinition definition)` で DSL入力を受ける

### B. DSL入力 (WHAT)
- 開発中は `InMemoryDslSource` + `MapDslDefinition` が最も明確
- 本番では `DslDefinitionSource` 実装を作り、JSON/YAML等を読み込む

入力キー例 (`HeartbeatSystem`):
- `intervalTicks`: 実行周期 (int, 1以上)
- `saveSectionId`: 保存セクションID (string)
- `networkMessageId`: ネットワークID (string)
- `stateKeyId`: 状態キーID (string)

## 2. 最小の実運用コード
```java
// どこか初期化前の場所
YourMod.queueSystem(new HeartbeatSystem());

YourMod.useDslSource(new InMemoryDslSource()
        .add(new MapDslDefinition("heartbeat", Map.of(
                "intervalTicks", 20,
                "saveSectionId", "heartbeat",
                "networkMessageId", "heartbeat/ping",
                "stateKeyId", "yourmod:heartbeat_count"
        ))));

// 既存エントリポイントから呼ばれる
YourMod.init();
```

## 3. runtimeで何が使えるか
`SystemRuntime` から次を利用できます。
- `stateManager()` : owner単位状態の attach/get/detach
- `networkChannel()` : register/send
- `tickScheduler()` : schedule / scheduleRepeating
- `saveManager()` : section登録 + save/load集約
- `eventBridge()` : 共通イベント発火

## 4. デバッグしやすくする仕様
`DataDrivenLoader.bind()` は、どの systemId にどのプロパティを適用したかを標準出力に出します。

出力例:
```text
[SystemDSL] bound system='heartbeat' properties={intervalTicks=40, ...}
```

## 5. 注意点 (実運用で必ず埋める)
- `PlatformNetworkChannel.register()` は現在重複チェックのみ
- `ForgeEventsImpl.register()` / `FabricEventsImpl.register()` は空実装
- `SimpleTickScheduler.tick()` を呼ぶイベント配線が必要
- `SaveManager.saveAll/loadAll` と実ワールド保存の接続が必要

上記は「テンプレートの責務分離」を守るために未結線です。
実運用時は loader 層で結線してください。
