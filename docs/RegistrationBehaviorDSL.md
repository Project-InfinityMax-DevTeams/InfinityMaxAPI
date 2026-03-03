# InfinityMaxAPI Registration / Logic DSL ガイド

このドキュメントは、次の3点を説明します。

1. 書き方（基本構文）
2. DSLで登録・接続できるもの一覧
3. 各スコープで記述できる内容

---

## 1. 書き方（基本構文）

InfinityMaxAPI の設計は以下です。

- `registry {}`: **静的データ定義層**（ゲーム要素の設定値を宣言するだけ）
- `behavior {}`: **要素IDとLogicIDの接続層**（既存互換）
- `logic {}`: **動的実行層**（Event → LogicID トリガー定義）

> 重要: Registry は設定値のみを扱います。event 定義は `logic {}` のみで行います。

### 1-1. registry DSL（ゲーム要素登録）

```kotlin
import com.yuyuto.infinitymaxapi.api.libs.registry
import com.yuyuto.infinitymaxapi.api.libs.packet.PacketDirection 

registry {
    item("example_item", Any()) {
        stack = 64          // 数値を変えると最大スタックが変わる
        durability = 250    // 数値を変えると耐久値が変わる
        tab = "materials"  // 文字列/オブジェクトを変えると分類が変わる
    }

    block("example_block", Any()) {
        strength = 3.5f     // 数値を変えると硬さが変わる
        noOcclusion = false // true にすると非遮蔽扱い
    }

    blockEntity("example_block_entity", Any(), Any()) {
        profile = "default" // 文字列を変えると識別プロファイルが変わる
    }

    entity<Any, Any>("example_entity", Any()) {
        category = Any()    // ここを差し替えるとカテゴリが変わる
        width = 0.8f        // 当たり判定の横幅
        height = 1.95f      // 当たり判定の高さ
    }

    dataGen("example_datagen", Any()) {
        namespace = "examplemod" // 出力の識別子
        overwrite = true           // 上書き可否
    }

    packet("example_packet", Any()) {
        direction = PacketDirection.C2S
        channel = "main"          // チャネル識別子
    }

    network("example_network", Any()) {
        protocol = "1"   // プロトコル文字列
        clientSync = true // 同期要否
    }

    gui("example_screen", Any()) {
        screenId = "ui/example_screen" // GUI識別ID
        layer = 10                       // レイヤー優先度
    }

    world("example_dimension", Any()) {
        kind = "dimension" // dimension / biome / structure
        order = 100          // 生成順序調整
    }
}
```

### 1-2. behavior DSL（登録要素IDとLogicIDの接続）

```kotlin
import com.yuyuto.infinitymaxapi.api.libs.Phase
import com.yuyuto.infinitymaxapi.api.libs.behavior
import com.yuyuto.infinitymaxapi.api.libs.behavior.BehaviorConnector
import com.yuyuto.infinitymaxapi.api.libs.behavior.PacketBehaviorConnector

behavior {
    item("example_item") {
        resourceId = "textures/item/example_item"
        phase = Phase.USE
        logicId = "examplemod:item_use" // 文字列を変えると接続先LogicIDが変わる
        meta("cooldown", 40)
        connector = BehaviorConnector { ctx ->
            // Java/Kotlin ロジック本体
        }
    }

    packet<Any>("example_packet") {
        resourceId = "network/example_packet"
        phase = Phase.RECEIVE
        logicId = "examplemod:packet_receive"
        connector = PacketBehaviorConnector<Any> { ctx, payload ->
            // packet logic
        }
    }
}
```

### 1-3. logic DSL（Event定義とトリガー）

```kotlin
import com.yuyuto.infinitymaxapi.api.libs.logic
import com.yuyuto.infinitymaxapi.api.libs.Phase
import com.yuyuto.infinitymaxapi.api.event.PlayerJoinEvent

logic {
    event<PlayerJoinEvent> {
        trigger("examplemod:item_use") // この文字列で起動LogicIDを指定
        phase = Phase.INTERACT          // context phase の識別値
        priority = com.yuyuto.infinitymaxapi.api.event.EventPriority.NORMAL
        async = false
        meta("source", "player_join")
    }
}
```

---

## 2. DSLで登録・接続できるもの一覧

### registry DSL（静的データ定義）

- アイテム (`item`)
- ブロック (`block`)
- ブロックエンティティ (`blockEntity`)
- エンティティ (`entity`)
- DataGen (`dataGen`)
- パケット (`packet`)
- ネットワーク (`network`)
- GUI (`gui`)
- ワールド要素 (`world`)
  - `kind` に `dimension` / `biome` / `structure` を指定

### behavior DSL（接続定義）

- `block`
- `item`
- `entity`
- `keybind`
- `ui`
- `packet`

### logic DSL（動的実行定義）

- `event<T : ModEvent>`
  - Event発火時に `trigger("logic_id")` で LogicID を起動

---

## 3. 各スコープで記述できる内容

### registry スコープ

- `item(id, template) { ... }`
  - `stack`, `durability`, `tab`
- `block(id, template) { ... }`
  - `strength`, `noOcclusion`
- `blockEntity(id, template, vararg blocks) { ... }`
  - `profile`
- `entity(id, template) { ... }`
  - `category`, `width`, `height`
- `dataGen(id, template) { ... }`
  - `namespace`, `overwrite`
- `packet(id, template) { ... }`
  - `direction`, `channel`
- `network(id, template) { ... }`
  - `protocol`, `clientSync`
- `gui(id, template) { ... }`
  - `screenId`, `layer`
- `world(id, template) { ... }`
  - `kind`, `order`

### behavior スコープ

- 共通 (`block/item/entity/keybind/ui`)
  - `resourceId`（文字列変更で参照先変更）
  - `phase`（実行フェーズ）
  - `logicId`（実行ロジック識別子）
  - `meta(key, value)`（追加情報）
  - `connector`（ロジック本体）
- `packet<T>`
  - 共通項目 + `PacketBehaviorConnector<T>`

### logic スコープ

- `event<T : ModEvent> { ... }`
  - `trigger(logicId)`（必須）
  - `phase`
  - `priority`
  - `async`
  - `meta(key, value)`

---

## 実装のコツ

- **IDを変えるだけで差し替え可能**: `"example_item"` や `"examplemod:item_use"` を変更。
- **数値を変えるだけで調整可能**: `stack`, `durability`, `strength`, `width`, `height`, `layer`, `order`。
- **コピペ運用がしやすい構造**: まず `registry` で全要素を宣言し、その後 `behavior` と `logic` を足していく。

この手順で、要素定義（静的）と実行（動的）を分離したまま安全に拡張できます。
