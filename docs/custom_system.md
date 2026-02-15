# Custom System Guide

## Purpose
Custom systems are templates for implementing unique game/business modules without depending on loader internals.
You build the core system in shared code and connect it through DSLs and events.
This guide is written for beginners and includes **copy/paste templates** plus **tables that explain exactly what to replace**.

## How to read the templates (important)
- Values wrapped in `<...>` are placeholders. Replace them with your own values.
- String IDs (for example `"my_item"`) should usually be lowercase snake_case.
- If a row says **Required**, set that value before running.

Example:
- `Registry.item("<item_id>")` → `Registry.item("copper_hammer")`
- `.stack(<max_stack>)` → `.stack(1)`

# Registration Systems

## 1. Custom Item Generation System

```java
package com.yourname.yourmod.systems;

import com.yourname.yourmod.api.libs.Registry;

public final class CustomItemSystem {

    public static void init() {
        Object item = Registry.item("<item_id>")
                .template(<item_template>)
                .stack(<max_stack>)
                .durability(<durability>)
                .tab(<creative_tab>)
                .build();
    }
}
```

| Placeholder | Type | What to set | Result |
| --- | --- | --- | --- |
| `<item_id>` | string | Item registry ID (example: `"copper_hammer"`) | Registers the item with that ID |
| `<item_template>` | Object | Base template object | Defines base item behavior |
| `<max_stack>` | int | Max stack size | Limits stack size |
| `<durability>` | int | Durability value | Sets item durability |
| `<creative_tab>` | Object | Creative tab/category | Places item in that tab |

---

## 2. Custom Block Generation System

```java
package com.yourname.yourmod.systems;

import com.yourname.yourmod.api.libs.Registry;

public final class CustomBlockSystem {

    public static void init() {
        Object block = Registry.block("<block_id>")
                .template(<block_template>)
                .strength(<block_strength>)
                .noOcclusion(<no_occlusion>)
                .build();
    }
}
```

| Placeholder | Type | What to set | Result |
| --- | --- | --- | --- |
| `<block_id>` | string | Block registry ID | Registers the block |
| `<block_template>` | Object | Base block template | Sets base properties |
| `<block_strength>` | float | Hardness/break speed | Controls mining resistance |
| `<no_occlusion>` | boolean | `true` or `false` | `true`: non-occluding, `false`: normal occlusion |

---

## 3. Custom Entity Generation System

```java
package com.yourname.yourmod.systems;

import com.yourname.yourmod.api.libs.Registry;

public final class CustomEntitySystem {

    public static void init() {
        Object entity = Registry.entity("<entity_id>", <entity_factory>)
                .category(<entity_category>)
                .size(<entity_width>, <entity_height>)
                .build();
    }
}
```

| Placeholder | Type | What to set | Result |
| --- | --- | --- | --- |
| `<entity_id>` | string | Entity registry ID | Registers the entity |
| `<entity_factory>` | Supplier<T> | Entity creation function | Creates entity instance |
| `<entity_category>` | Object | Category/type | Assigns category |
| `<entity_width>` | float | Width | Hitbox/render size |
| `<entity_height>` | float | Height | Hitbox/render size |

---

## 4. Custom Event Registration System

```java
package com.yourname.yourmod.systems;

import com.yourname.yourmod.api.libs.Events;
import com.yourname.yourmod.api.event.EventPriority;

public final class CustomEventSystem {

    public static void init() {
        Events.playerJoin()
                .priority(<event_priority>)
                .handle(event -> {
                    <custom_logic>.accept(event);
                });
    }
}
```

| Placeholder | Type | What to set | Result |
| --- | --- | --- | --- |
| `<event_priority>` | EventPriority | Event order priority | Controls execution order |
| `<custom_logic>` | Consumer<T> | Logic to run | Runs on event trigger |

---

## 5. Custom UI/Client Processing System

```java
package com.yourname.yourmod.systems;

import com.yourname.yourmod.api.libs.Client;

public final class CustomClientSystem {

    public static void init() {
        Client.init(client -> {
            client.renders().registerAll();
            client.keybinds().registerAll();
            client.screens().registerAll();
            client.hud().registerAll();
        });
    }
}
```

| Placeholder | Type | What to set | Result |
| --- | --- | --- | --- |
| (none) | - | No replacement required | Initializes all client sub-DSLs |

---

## 6. Custom Data Generation System

```java
package com.yourname.yourmod.systems;

import com.yourname.yourmod.api.libs.datagen.DataGen;

public final class CustomDataGenSystem {

    public static void init() {
        DataGen.block("<block_id>").end();
        DataGen.item("<item_id>").lang(<item_name>).end();
        DataGen.entity("<entity_id>").lang(<entity_name>).end();
    }
}
```

| Placeholder | Type | What to set | Result |
| --- | --- | --- | --- |
| `<block_id>` | string | Block ID for data gen | Generates block data |
| `<item_id>` | string | Item ID for data gen | Generates item data |
| `<item_name>` | string | Item display name | Used for language text |
| `<entity_id>` | string | Entity ID for data gen | Generates entity data |
| `<entity_name>` | string | Entity display name | Used for language text |

---

## 7. Custom Network Packet System

```java
package com.yourname.yourmod.systems;

import com.yourname.yourmod.api.libs.packet.Packet;

public final class CustomPacketSystem {

    public static void init() {
        Packet<String> ping = Packet.<String>define("<packet_id>")
                .serverbound()
                .codec(buf -> "ping", (packet, buf) -> {})
                .handle((packet, ctx) -> <custom_logic>.accept(packet));

        ping.register();
        ping.sendToServer("hello");
    }
}
```

| Placeholder | Type | What to set | Result |
| --- | --- | --- | --- |
| `<packet_id>` | string | Packet ID | Identifies packet channel |
| `<custom_logic>` | PacketHandler | Receive logic | Runs when packet is received |

# Processing Systems

> Goal: let users build core MOD logic by replacing placeholders, like editing JSON values.

## 1. Constants and Registry IDs
```java
package com.yourname.yourmod.systems.core;

public final class XSystemConstants {
    public static final int MAX_ENERGY = <max_energy>;
    public static final int MIN_ENERGY = <min_energy>;

    public static final String ENERGY_ITEM_ID = "<energy_item_id>";
    public static final String LEVEL_ITEM_ID  = "<level_item_id>";

    private XSystemConstants() {}
}
```

| Placeholder | Type | What to set | Result |
| --- | --- | --- | --- |
| `<max_energy>` | int | Maximum allowed energy | Used by clamps/limits |
| `<min_energy>` | int | Minimum allowed energy | Usually `0` |
| `<energy_item_id>` | string | Item ID for energy-related content | Links constants to registry |
| `<level_item_id>` | string | Item ID for level-related content | Links constants to registry |

---

## 2. Player State Model
```java
package com.yourname.yourmod.systems.core;

import java.util.HashMap;
import java.util.Map;

public final class XState {
    private int energy;
    private int level;
    private boolean unlocked;
    private final Map<String, Integer> counters = new HashMap<>();

    public int getEnergy() { return energy; }
    public void setEnergy(int value) {
        energy = Math.max(XSystemConstants.MIN_ENERGY, Math.min(value, XSystemConstants.MAX_ENERGY));
    }

    public int getLevel() { return level; }
    public void setLevel(int value) { level = Math.max(0, value); }

    public boolean isUnlocked() { return unlocked; }
    public void setUnlocked(boolean value) { unlocked = value; }

    public int getCounter(String key) { return counters.getOrDefault(key, 0); }
    public void addCounter(String key, int amount) { counters.put(key, getCounter(key) + amount); }
}
```

| Placeholder | Type | What to set | Result |
| --- | --- | --- | --- |
| `"key"` in `getCounter/addCounter` | string | Counter key like `"kills"` | Tracks any custom stat |
| `amount` | int | Counter increment/decrement | Changes key value |

---

## 3. State Service (per-player access)
```java
package com.yourname.yourmod.systems.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class XService {
    private static final Map<Object, XState> STATES = new ConcurrentHashMap<>();
    private XService() {}

    public static XState byPlayer(Object player) {
        return STATES.computeIfAbsent(player, p -> new XState());
    }

    public static void clear(Object player) {
        STATES.remove(player);
    }
}
```

| Placeholder | Type | What to set | Result |
| --- | --- | --- | --- |
| `player` | Object | Player object from event/context | Retrieves/creates state |

---

## 4. Server Events (join, tick, custom)
```java
package com.yourname.yourmod.systems.core;

import com.yourname.yourmod.api.libs.Events;

public final class XEvents {
    private XEvents() {}

    public static void register() {
        Events.playerJoin().handle(event -> {
            Object player = event.player;
            XState state = XService.byPlayer(player);
            state.setEnergy(<join_energy>);
            state.setLevel(<join_level>);
            state.setUnlocked(<join_unlocked>);
        });

        Events.serverTick().handle(event -> {
            if (event.currentTick % <recovery_interval_tick> == 0) {
                for (Object player : <online_players_supplier>.get()) {
                    XService.byPlayer(player).setEnergy(XService.byPlayer(player).getEnergy() + <recovery_amount>);
                }
            }
        });

        Events.on(<custom_event_class>.class).handle(event -> {
            Object player = event.player;
            XService.byPlayer(player).addCounter("<counter_key>", <counter_add>);
        });
    }
}
```

| Placeholder | Type | What to set | Result |
| --- | --- | --- | --- |
| `<join_energy>` | int | Initial energy on join | Player default state |
| `<join_level>` | int | Initial level | Player default level |
| `<join_unlocked>` | boolean | `true` / `false` | Initial feature lock |
| `<recovery_interval_tick>` | int | Tick interval (20 = 1s) | Periodic execution timing |
| `<online_players_supplier>` | Supplier<Iterable<Object>> | Function returning online players | Tick loop target |
| `<recovery_amount>` | int | Energy recovered per interval | Auto-regeneration amount |
| `<custom_event_class>` | Class<?> | Your custom event type | Hooks your event |
| `<counter_key>` | string | Counter key name | Target counter |
| `<counter_add>` | int | Add value | Counter increment |

---

## 5. Client HUD and Screen Registration
```java
package com.yourname.yourmod.systems.client;

import com.yourname.yourmod.api.libs.Client;

public final class XClient {
    private XClient() {}

    public static void register() {
        Client.init(client -> {
            client.hud().registerAll();
            client.screens().registerAll();
            client.keybinds().registerAll();
        });
    }

    public static String hudText(Object player) {
        XState state = XService.byPlayer(player);
        return "Energy=" + state.getEnergy() + " Level=" + state.getLevel();
    }
}
```

| Placeholder | Type | What to set | Result |
| --- | --- | --- | --- |
| `player` in `hudText` | Object | Local player object | Reads state for HUD text |

---

## 6. Network Sync Template
```java
package com.yourname.yourmod.systems.net;

import com.yourname.yourmod.api.libs.packet.Packet;

public final class XSync {
    private static final Packet<String> STATE_SYNC = Packet.<String>define("<sync_packet_id>")
            .clientbound()
            .codec(buf -> "", (packet, buf) -> {})
            .handle((packet, ctx) -> <client_sync_handler>.accept(packet));

    private XSync() {}

    public static void register() {
        STATE_SYNC.register();
    }

    public static void sync(Object player, XState state) {
        <send_to_player>.accept(player, "energy=" + state.getEnergy() + ",level=" + state.getLevel());
    }
}
```

| Placeholder | Type | What to set | Result |
| --- | --- | --- | --- |
| `<sync_packet_id>` | string | Packet ID for state sync | Network channel ID |
| `<client_sync_handler>` | Consumer<String> | Client-side apply logic | Updates client state |
| `<send_to_player>` | BiConsumer<Object, String> | Packet send implementation | Sends payload to player |

---

## 7. Save/Load (persistent data template)
```java
package com.yourname.yourmod.systems.persistence;

public final class XPersistence {
    private XPersistence() {}

    public static void save(Object player, XState state) {
        <save_int>.accept(player, "energy", state.getEnergy());
        <save_int>.accept(player, "level", state.getLevel());
        <save_bool>.accept(player, "unlocked", state.isUnlocked());
    }

    public static void load(Object player, XState state) {
        state.setEnergy(<load_int>.apply(player, "energy", <default_energy>));
        state.setLevel(<load_int>.apply(player, "level", <default_level>));
        state.setUnlocked(<load_bool>.apply(player, "unlocked", <default_unlocked>));
    }
}
```

| Placeholder | Type | What to set | Result |
| --- | --- | --- | --- |
| `<save_int>` | TriConsumer<Object, String, Integer> | Save integer function | Writes integer data |
| `<save_bool>` | TriConsumer<Object, String, Boolean> | Save boolean function | Writes boolean data |
| `<load_int>` | TriFunction<Object, String, Integer, Integer> | Load integer function | Reads int with default |
| `<load_bool>` | TriFunction<Object, String, Boolean, Boolean> | Load bool function | Reads bool with default |
| `<default_energy>` | int | Fallback value | Used when no save exists |
| `<default_level>` | int | Fallback value | Used when no save exists |
| `<default_unlocked>` | boolean | Fallback value | Used when no save exists |

---

## 8. Command Template (debug / operation)
```java
package com.yourname.yourmod.systems.command;

public final class XCommands {
    private XCommands() {}

    public static void register() {
        <register_command>.accept("<command_name>", ctx -> {
            Object player = ctx.player();
            XState state = XService.byPlayer(player);
            state.setEnergy(<command_energy_set>);
            return 1;
        });
    }
}
```

| Placeholder | Type | What to set | Result |
| --- | --- | --- | --- |
| `<register_command>` | BiConsumer<String, CommandHandler> | Command registration function | Registers command |
| `<command_name>` | string | Command text (without `/`) | Command trigger |
| `<command_energy_set>` | int | New energy value | Useful for testing |

---

## 9. Single Initialization Entry Point
```java
package com.yourname.yourmod;

public final class MyModInit {
    private MyModInit() {}

    public static void init() {
        XSync.register();
        XEvents.register();
        XClient.register();
        XCommands.register();
    }
}
```

| Placeholder | Type | What to set | Result |
| --- | --- | --- | --- |
| (none) | - | Keep call order | Starts all subsystems |

---

## 10. “Minimum Build” checklist (copy before release)
- [ ] IDs replaced (`<item_id>`, `<block_id>`, `<sync_packet_id>`, etc.)
- [ ] Join defaults set (`<join_energy>`, `<join_level>`, `<join_unlocked>`)
- [ ] Tick values set (`<recovery_interval_tick>`, `<recovery_amount>`)
- [ ] Save/load functions connected
- [ ] Sync send/apply functions connected
- [ ] Init entry point calls all register methods

# Do / Don't
Do:
- Keep system logic testable and pure
- Use `Object` as the shared API boundary
- Isolate loader-specific conversions
- Replace placeholders one by one from top to bottom

Don't:
- Import Minecraft classes into shared system core
- Mix Forge/Fabric code in one shared class
- Leave `<...>` placeholders unedited
- Skip sync/persistence when state is user-visible

## Validation
```bash
./gradlew :forge:compileJava :fabric:compileJava
./gradlew clean build
```

# カスタムシステムガイド

## 目的
カスタムシステムは、ローダー内部に依存せず独自のゲーム/業務モジュールを実装するためのテンプレートです。
共通コードでシステム本体を作り、DSLやイベントで接続します。
ここでは、開発者が簡単に要素を配置できるように、**コピー＆ペースト可能なコードブロックと指定箇所表**を提供します。

# 登録系

## 1. カスタムアイテム生成システム

```java
package com.yourname.yourmod.systems;

import com.yourname.yourmod.api.libs.Registry;

public final class CustomItemSystem {

    public static void init() {
        Object item = Registry.item("custom_id")
                .template(new Object())
                .stack(stackSize)
                .durability(durabilityValue)
                .tab(tabCategory)
                .build();
    }
}
```

| 指定必須箇所(変数名)     | 変数に入る情報 | 指定した場合の挙動・処理                    |
| --------------- | ------- | ------------------------------- |
| "custom_id"     | string  | 入れたIDでアイテムが登録される。|
| stackSize       | int     | アイテムの最大スタック数 |
| durabilityValue | int     | アイテム耐久値          |
| tabCategory     | Object  | アイテムが所属するクリエイティブタブ       |

---

## 2. カスタムブロック生成システム

```java
package com.yourname.yourmod.systems;

import com.yourname.yourmod.api.libs.Registry;

public final class CustomBlockSystem {

    public static void init() {
        Object block = Registry.block("custom_block_id")
                .template(blockTemplate)
                .strength(blockStrength)
                .noOcclusion(occlusionFlag)
                .build();
    }
}
```

| 指定必須箇所(変数名)       | 変数に入る情報 | 指定した場合の挙動・処理                  |
| ----------------- | ------- | ----------------------------- |
| "custom_block_id" | string  | 入れたIDでブロックが登録される              |
| blockTemplate     | Object  | ブロックの元テンプレート                  |
| blockStrength     | float   | ブロックの硬さ・破壊時間                  |
| occlusionFlag     | boolean | trueの場合は非透過ブロック、falseなら透過ブロック |

---

## 3. カスタムエンティティ生成システム

```java
package com.yourname.yourmod.systems;

import com.yourname.yourmod.api.libs.Registry;

public final class CustomEntitySystem {

    public static void init() {
        Object entity = Registry.entity("custom_entity_id", entityFactory)
                .category(entityCategory)
                .size(entityWidth, entityHeight)
                .build();
    }
}
```

| 指定必須箇所(変数名)        | 変数に入る情報     | 指定した場合の挙動・処理       |
| ------------------ | ----------- | ------------------ |
| "custom_entity_id" | string      | 入れたIDでエンティティが登録される |
| entityFactory      | Supplier<T> | エンティティ生成関数         |
| entityCategory     | Object      | エンティティカテゴリ         |
| entityWidth        | float       | 幅                  |
| entityHeight       | float       | 高さ                 |

---

## 4. カスタムイベント登録システム

```java
package com.yourname.yourmod.systems;

import com.yourname.yourmod.api.libs.Events;
import com.yourname.yourmod.api.event.EventPriority;

public final class CustomEventSystem {

    public static void init() {
        Events.playerJoin()
                .priority(eventPriority)
                .handle(event -> {
                    // イベント処理内容
                    customLogic.accept(event);
                });
    }
}
```

| 指定必須箇所(変数名)   | 変数に入る情報       | 指定した場合の挙動・処理  |
| ------------- | ------------- | ------------- |
| eventPriority | EventPriority | イベント処理の優先度    |
| customLogic   | Consumer<T>   | 実行したいイベントロジック |

---

## 5. カスタムUI/クライアント処理システム

```java
package com.yourname.yourmod.systems;

import com.yourname.yourmod.api.libs.Client;

public final class CustomClientSystem {

    public static void init() {
        Client.init(client -> {
            client.renders().registerAll();
            client.keybinds().registerAll();
            client.screens().registerAll();
            client.hud().registerAll();
        });
    }
}
```

| 指定必須箇所(変数名) | 変数に入る情報 | 指定した場合の挙動・処理     |
| ----------- | ------- | ---------------- |
| （なし）        |         | 全てのサブDSLを初期化して登録 |

---

## 6. カスタムデータ生成システム

```java
package com.yourname.yourmod.systems;

import com.yourname.yourmod.api.libs.datagen.DataGen;

public final class CustomDataGenSystem {

    public static void init() {
        DataGen.block("custom_block_id").end();
        DataGen.item("custom_item_id").lang(itemName).end();
        DataGen.entity("custom_entity_id").lang(entityName).end();
    }
}
```

| 指定必須箇所(変数名)        | 変数に入る情報 | 指定した場合の挙動・処理   |
| ------------------ | ------- | -------------- |
| "custom_block_id"  | string  | データ生成用ブロックID   |
| "custom_item_id"   | string  | データ生成用アイテムID   |
| itemName           | string  | アイテム名          |
| "custom_entity_id" | string  | データ生成用エンティティID |
| entityName         | string  | エンティティ名        |

---

## 7. カスタムネットワークパケットシステム

```java
package com.yourname.yourmod.systems;

import com.yourname.yourmod.api.libs.packet.Packet;

public final class CustomPacketSystem {

    public static void init() {
        Packet<String> ping = Packet.<String>define("ping")
                .serverbound()
                .codec(buf -> "ping", (packet, buf) -> {})
                .handle((packet, ctx) -> customLogic.accept(packet));

        ping.register();
        ping.sendToServer("hello");
    }
}
```

| 指定必須箇所(変数名) | 変数に入る情報       | 指定した場合の挙動・処理 |
| ----------- | ------------- | ------------ |
| "ping"      | string        | パケットID       |
| customLogic | PacketHandler | パケット受信時の処理   |

# 処理系

## 1. 定数・登録ID定義

```java
package com.example.mymod.system.X;

public final class XSystemConstants {

    public static final int MAX_VALUE = maxValue; // システムで扱う最大値
    public static final int MIN_VALUE = minValue; // システムで扱う最小値

    // 登録IDの例: アイテム/ブロック/エンティティに紐づける
    public static final String ENERGY_ID = energyId;
    public static final String LEVEL_ID  = levelId;

    private XSystemConstants() {}
}
```

| 指定必須箇所(変数名) | 変数に入る情報 | 指定した場合の挙動・処理 | Note                      |
| ----------- | ------- | ------------ | ------------------------- |
| maxValue    | int     | 最大値設定        | システム全体で参照                 |
| minValue    | int     | 最小値設定        | 同上                        |
| energyId    | String  | エネルギー用ID     | Registry.item("ID") 等に紐づく |
| levelId     | String  | レベル用ID       | Registry.item("ID") 等に紐づく |

---

## 2. 状態モデル（多変数・コレクション・フラグ）

```java
package com.example.mymod.system.X;

import java.util.HashMap;
import java.util.Map;

public final class XState {

    private int energy;
    private boolean unlocked;
    private int level;
    private Map<String, Integer> counters = new HashMap<>();

    public int getEnergy() { return energy; }
    public void setEnergy(int next) { energy = Math.max(XSystemConstants.MIN_VALUE, Math.min(next, XSystemConstants.MAX_VALUE)); }
    public void addEnergy(int delta) { setEnergy(energy + delta); }

    public boolean isUnlocked() { return unlocked; }
    public void setUnlocked(boolean flag) { unlocked = flag; }

    public int getLevel() { return level; }
    public void setLevel(int lvl) { level = Math.max(0, lvl); }

    public void incrementCounter(String key) { counters.put(key, counters.getOrDefault(key, 0) + 1); }
    public int getCounter(String key) { return counters.getOrDefault(key, 0); }
}
```

| 指定必須箇所(変数名) | 変数に入る情報         | 指定した場合の挙動・処理 | Note           |
| ----------- | --------------- | ------------ | -------------- |
| energy      | int             | エネルギー値       | MAX/MINに自動制限   |
| unlocked    | boolean         | フラグ管理        | サーバ/クライアント判定可能 |
| level       | int             | レベル管理        | 0以上固定          |
| counters    | Map<String,int> | カウンター管理      | 任意IDに紐づく       |

---

## 3. サービス（状態取得・ID紐付け対応）

```java
package com.example.mymod.system.X;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class XService {

    private static final Map<Object, XState> STATE = new ConcurrentHashMap<>();
    private XService() {}

    public static XState byPlayer(Object player) {
        return STATE.computeIfAbsent(player, key -> new XState());
    }

    public static void addEnergy(Object player, int amount) {
        byPlayer(player).addEnergy(amount);
    }

    public static void unlockFeature(Object player) {
        byPlayer(player).setUnlocked(true);
    }

    public static void incrementCounter(Object player, String key) {
        byPlayer(player).incrementCounter(key);
    }
}
```

| 指定必須箇所(変数名) | 変数に入る情報 | 指定した場合の挙動・処理 | Note            |
| ----------- | ------- | ------------ | --------------- |
| player      | Object  | プレイヤーオブジェクト  | Registry に紐づく状態 |
| amount      | int     | エネルギー増減量     | MAX_VALUE を超えない |
| key         | String  | counters のキー | 任意ID紐付け可能       |

---

## 4. イベント接続（複数イベント・条件付き）

```java
package com.example.mymod.system.X;

import com.yourname.yourmod.api.libs.Events;

public final class XEvents {

    private XEvents() {}

    public static void register() {

        // プレイヤー参加時
        Events.playerJoin().handle(event -> {
            Object player = event.player;
            XState state = XService.byPlayer(player);
            state.setEnergy(initialEnergy);
            state.setLevel(startLevel);
            state.setUnlocked(startUnlocked);
        });

        // カスタムイベント例
        Events.on(CustomEvent.class).handle(event -> {
            Object player = event.player;
            XService.addEnergy(player, event.deltaEnergy);
            if(event.condition) XService.unlockFeature(player);
        });

        // 追加条件イベント例
        Events.on(LevelUpEvent.class).handle(event -> {
            Object player = event.player;
            XService.byPlayer(player).setLevel(event.newLevel);
        });
    }
}
```

| 指定必須箇所(変数名)       | 変数に入る情報  | 指定した場合の挙動・処理 | Note              |
| ----------------- | -------- | ------------ | ----------------- |
| initialEnergy     | int      | 初期値設定        | プレイヤー参加時          |
| startLevel        | int      | 初期レベル        | 0以上               |
| startUnlocked     | boolean  | 初期フラグ        | true/false        |
| CustomEvent       | Class<T> | 任意イベント型      | deltaEnergy などを含む |
| event.deltaEnergy | int      | イベントでの増減量    | -                 |
| event.condition   | boolean  | 条件に応じたアンロック  | -                 |
| LevelUpEvent      | Class<T> | レベル変更イベント    | newLevel 付き       |
| event.newLevel    | int      | 新しいレベル       | 0以上               |

---

## 5. クライアント接続（HUD/UI）

```java
package com.example.mymod.system.X;

import com.yourname.yourmod.api.libs.Client;

public final class XClient {

    private XClient() {}

    public static void register() {
        Client.init(client -> {
            client.hud().registerAll(); // HUD描画用
            client.screens().registerAll(); // UI画面
        });
    }
}
```

| 指定必須箇所(変数名) | 変数に入る情報 | 指定した場合の挙動・処理 | Note            |
| ----------- | ------- | ------------ | --------------- |
| （なし）        |         | HUD/UI初期化    | 描画内容はXStateから取得 |

---

## 6. 初期化接続

```java
package com.example.mymod;

import com.example.mymod.system.X.XEvents;
import com.example.mymod.system.X.XClient;

public final class MyModInit {

    private MyModInit() {}

    public static void init() {
        XEvents.register();
        XClient.register();
    }
}
```

| 指定必須箇所(変数名) | 変数に入る情報 | 指定した場合の挙動・処理 | Note             |
| ----------- | ------- | ------------ | ---------------- |
| （なし）        |         | MOD初期化       | イベント・クライアント登録を呼ぶ |

# 上級処理系

## 1. プレイヤー単位の状態操作系

```java
package com.example.mymod.system.X;

public final class XServiceAdvanced {

    private XServiceAdvanced() {}

    // ⚡ エネルギーを直接設定
    public static void setPlayerEnergy(Object player, int value) {
        XService.byPlayer(player).setEnergy(value);
    }

    // ➕ エネルギーを増減
    public static void addPlayerEnergy(Object player, int delta) {
        XService.byPlayer(player).addEnergy(delta);
    }

    // 🔓 特定フラグをアンロック
    public static void unlockPlayerFeature(Object player) {
        XService.byPlayer(player).setUnlocked(true);
    }

    // 🆙 レベルを設定
    public static void setPlayerLevel(Object player, int level) {
        XService.byPlayer(player).setLevel(level);
    }

    // 🧮 counters にキーでアクセスしてインクリメント
    public static void incrementPlayerCounter(Object player, String key) {
        XService.byPlayer(player).incrementCounter(key);
    }

    // 📊 counters の値を取得
    public static int getPlayerCounter(Object player, String key) {
        return XService.byPlayer(player).getCounter(key);
    }

    // ✅ プレイヤー状態を全リセット
    public static void resetPlayerState(Object player) {
        XState state = XService.byPlayer(player);
        state.setEnergy(0);
        state.setLevel(0);
        state.setUnlocked(false);
        // counters は空に
        state.counters.clear();
    }
}
```

| 指定必須箇所(変数名) | 変数に入る情報 | 挙動         | Note               |
| ----------- | ------- | ---------- | ------------------ |
| player      | Object  | 操作対象プレイヤー  | Registryに紐づくオブジェクト |
| delta       | int     | エネルギー増減量   | MAX/MINに自動制限       |
| level       | int     | 設定レベル      | 0以上                |
| key         | String  | countersキー | 任意ID紐付け可能          |

---

## 2. サーバー向け複数プレイヤー処理

```java
package com.example.mymod.system.X;

import java.util.Collection;

public final class XServerUtils {

    private XServerUtils() {}

    // 🌐 全プレイヤーにエネルギー付与
    public static void addEnergyToAll(Collection<Object> players, int amount) {
        for (Object player : players) {
            XServiceAdvanced.addPlayerEnergy(player, amount);
        }
    }

    // 🔄 全プレイヤーのレベルを一括更新
    public static void setLevelForAll(Collection<Object> players, int level) {
        for (Object player : players) {
            XServiceAdvanced.setPlayerLevel(player, level);
        }
    }

    // 🧹 全プレイヤー状態リセット
    public static void resetAllPlayers(Collection<Object> players) {
        for (Object player : players) {
            XServiceAdvanced.resetPlayerState(player);
        }
    }
}
```

| 指定必須箇所(変数名) | 変数に入る情報            | 挙動          | Note        |
| ----------- | ------------------ | ----------- | ----------- |
| players     | Collection<Object> | 操作対象プレイヤー集合 | 全体操作用       |
| amount      | int                | エネルギー付与量    | MAX/MIN制限あり |
| level       | int                | 設定レベル       | 0以上         |

---

## 3. 条件・計算ユーティリティ

```java
package com.example.mymod.system.X;

public final class XUtils {

    private XUtils() {}

    // 🔎 プレイヤーが指定レベル以上か
    public static boolean isPlayerLevelAtLeast(Object player, int level) {
        return XService.byPlayer(player).getLevel() >= level;
    }

    // ⚡ エネルギーが閾値以上か
    public static boolean isPlayerEnergyAtLeast(Object player, int value) {
        return XService.byPlayer(player).getEnergy() >= value;
    }

    // 🧩 任意キーのカウンターが閾値以上か
    public static boolean isCounterAtLeast(Object player, String key, int threshold) {
        return XService.byPlayer(player).getCounter(key) >= threshold;
    }

    // 💰 エネルギーを消費できるか確認
    public static boolean canConsumeEnergy(Object player, int amount) {
        return XService.byPlayer(player).getEnergy() >= amount;
    }

    // 🔄 消費可能なら減らす
    public static boolean consumeEnergyIfPossible(Object player, int amount) {
        XState state = XService.byPlayer(player);
        if(state.getEnergy() >= amount) {
            state.addEnergy(-amount);
            return true;
        }
        return false;
    }
}
```

| 指定必須箇所(変数名)  | 変数に入る情報 | 挙動         | Note        |
| ------------ | ------- | ---------- | ----------- |
| player       | Object  | 判定対象プレイヤー  | Registry紐付け |
| value/amount | int     | 閾値/消費量     | MAX/MIN制限適用 |
| key          | String  | countersキー | 任意ID紐付け可能   |
| threshold    | int     | 閾値         | 判定用         |

---

## 4. クライアントHUD描画テンプレート（例）

```java
package com.example.mymod.system.X;

import com.yourname.yourmod.api.libs.Client;

public final class XHud {

    private XHud() {}

    public static void register() {
        Client.init(client -> {
            client.hud().registerAll(); // HUD初期化
        });
    }

    // 💠 HUD描画用メソッド
    public static String getEnergyText(Object player) {
        int energy = XService.byPlayer(player).getEnergy();
        int level  = XService.byPlayer(player).getLevel();
        return "Energy: " + energy + " | Level: " + level;
    }
}
```

| 指定必須箇所(変数名) | 変数に入る情報 | 挙動      | Note        |
| ----------- | ------- | ------- | ----------- |
| player      | Object  | HUD描画対象 | Registry紐付け |

---

## 5. エントリポイント

```java
package com.example.mymod.system.X;

public final class XSystem {

    private XSystem() {}

    /**
     * ===== SYSTEM ENTRY POINT =====
     * このシステムの全イベント・自動進行を登録する
     */
    public static void init() {

        registerPlayerJoin();
        registerCustomEvents();
        registerAutoProgress();
    }

    // =========================================
    // 🔹 PLAYER JOIN INITIALIZATION
    // =========================================
    private static void registerPlayerJoin() {

        Events.playerJoin().handle(event -> {

            Object player = event.player;

            XState state = XManager.get(player);

            state.setEnergy(100);
            state.setLevel(1);
            state.setUnlocked(true);

            XSync.syncToClient(player);
        });
    }

    // =========================================
    // 🔹 CUSTOM EVENT REGISTRATION
    // =========================================
    private static void registerCustomEvents() {

        Events.on(CustomEvent.class).handle(event -> {

            Object player = event.player;

            if (XUtils.consumeEnergyIfPossible(player, event.deltaEnergy)) {
                XEventLogic.handleAdvancedCondition(player);
                XSync.syncToClient(player);
            }
        });
    }

    // =========================================
    // 🔹 AUTO PROGRESS SYSTEM (TICK BASED)
    // =========================================
    private static void registerAutoProgress() {

        Events.serverTick().handle(event -> {
            long tick = event.currentTick;

            XAutoProgress.onServerTick(tick);

            for (Object player : XManager.allPlayers()) {
                XSync.syncToClient(player);
            }
        });
    }
}

```

| 指定必須箇所                  | 型       | 内容      | Note       |
| ----------------------- | ------- | ------- | ---------- |
| state.setEnergy(100)    | int     | 初期エネルギー | システム固有値    |
| state.setLevel(1)       | int     | 初期レベル   | 0以上        |
| state.setUnlocked(true) | boolean | 初期フラグ   | true/false |
| CustomEvent       | Class  | 任意イベント型 | deltaEnergy必須 |
| event.deltaEnergy | int    | 消費エネルギー | マイナス可         |
| player            | Object | 対象プレイヤー | Registry紐付け   |
| event.currentTick  | long   | 現在Tick | サーバーイベント |
| XAutoProgress      | class  | 自動進行処理 | ロジック層    |
| XSync.syncToClient | method | 同期処理   | Tick毎同期  |

⸻
# 最上級系
## 1. 状態管理（拡張版・時間対応）
```Java
package com.example.mymod.system.X;

import java.util.HashMap;
import java.util.Map;

public class XState {

    private int energy;
    private int level;
    private boolean unlocked;

    private long lastUpdateTick;
    private long playTimeTicks;

    final Map<String, Integer> counters = new HashMap<>();

    // ===== ENERGY =====
    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int value) {
        this.energy = Math.max(0, value);
    }

    public void addEnergy(int delta) {
        setEnergy(this.energy + delta);
    }

    // ===== LEVEL =====
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = Math.max(0, level);
    }

    // ===== UNLOCK =====
    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean value) {
        this.unlocked = value;
    }

    // ===== COUNTERS =====
    public void incrementCounter(String key) {
        counters.put(key, getCounter(key) + 1);
    }

    public int getCounter(String key) {
        return counters.getOrDefault(key, 0);
    }

    public void setCounter(String key, int value) {
        counters.put(key, value);
    }

    // ===== TIME =====
    public void updateTick(long tick) {
        this.playTimeTicks += (tick - lastUpdateTick);
        this.lastUpdateTick = tick;
    }

    public long getPlayTimeTicks() {
        return playTimeTicks;
    }
}
```

指定必須箇所|型|内容|Note|
|---|---|---|---|
|key	|String|カウンター|識別子|ID紐付け可能|
|value	|int|設定値|0以上|
|tick	|long|現在サーバーTick	|自動進行で使用|


⸻

## 2. 複数プレイヤー管理（集中管理型）
```Java
package com.example.mymod.system.X;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class XManager {

    private static final Map<Object, XState> PLAYER_STATES = new HashMap<>();

    private XManager() {}

    public static XState get(Object player) {
        return PLAYER_STATES.computeIfAbsent(player, p -> new XState());
    }

    public static void remove(Object player) {
        PLAYER_STATES.remove(player);
    }

    public static Collection<XState> allStates() {
        return PLAYER_STATES.values();
    }

    public static Collection<Object> allPlayers() {
        return PLAYER_STATES.keySet();
    }
}
```
指定必須箇所|型|内容|Note|
|---|---|---|---|
|player	|Object	|プレイヤー|Registry紐付け|

## 3. 自動進行システム（Tick駆動）
```Java
package com.example.mymod.system.X;

public final class XAutoProgress {

    private XAutoProgress() {}

    public static void onServerTick(long currentTick) {

        for (Object player : XManager.allPlayers()) {

            XState state = XManager.get(player);
            state.updateTick(currentTick);

            // レベル自動上昇
            if(state.getPlayTimeTicks() > 12000) {
                state.setLevel(state.getLevel() + 1);
                state.setCounter("auto_levelups",
                        state.getCounter("auto_levelups") + 1);
            }

            // エネルギー自然回復
            if(currentTick % 20 == 0) {
                state.addEnergy(1);
            }
        }
    }
}
```

指定必須箇所|型|内容|Note|
|---|---|---|---|
|currentTick	|long|現在Tick|サーバーイベントから渡す|

⸻

## 4. 複雑条件イベント処理
```Java
package com.example.mymod.system.X;

public final class XEventLogic {

    private XEventLogic() {}

    public static void handleAdvancedCondition(Object player) {

        XState state = XManager.get(player);

        boolean levelOk = state.getLevel() >= 5;
        boolean energyOk = state.getEnergy() >= 50;
        boolean killCountOk = state.getCounter("kills") >= 10;
        boolean unlocked = state.isUnlocked();

        if(levelOk && energyOk && killCountOk && unlocked) {

            state.addEnergy(-50);
            state.incrementCounter("ultimate_used");

            triggerUltimateEffect(player);
        }
    }

    private static void triggerUltimateEffect(Object player) {
        // エフェクトやスキル処理
    }
}
```

|指定必須箇所|型|内容|Note|
|---|---|---|---|
|player|Object	|対象プレイヤー	|Registry紐付け|
|“kills”	|String	|カウンターキー	|任意ID|
|“ultimate_used”	|String|使用回数キー|任意ID|

⸻

## 5. エンティティ紐付け管理
```Java
package com.example.mymod.system.X;

import java.util.HashMap;
import java.util.Map;

public final class XEntityBinding {

    private static final Map<Object, Object> ENTITY_OWNER = new HashMap<>();

    private XEntityBinding() {}

    public static void bind(Object entity, Object player) {
        ENTITY_OWNER.put(entity, player);
    }

    public static Object getOwner(Object entity) {
        return ENTITY_OWNER.get(entity);
    }

    public static void unbind(Object entity) {
        ENTITY_OWNER.remove(entity);
    }
}
```
指定必須箇所|型|内容|Note|
|---|---|---|---|
|entity	|Object	|対象エンティティ|登録ID紐付け
|player	|Object	|所有プレイヤー	|Registry紐付け


⸻

## 6. ネットワーク同期（疑似Packet構造）
```Java
package com.example.mymod.system.X;

public final class XSync {

    private XSync() {}

    public static void syncToClient(Object player) {

        XState state = XManager.get(player);

        Packet packet = new Packet();
        packet.putInt("energy", state.getEnergy());
        packet.putInt("level", state.getLevel());
        packet.putBoolean("unlocked", state.isUnlocked());

        sendToPlayer(player, packet);
    }

    private static void sendToPlayer(Object player, Packet packet) {
        // 実装依存
    }
}
```

|指定必須箇所|型|内容|Note|
|---|---|---|---|
|player	|Object	|同期対象	|クライアントへ送信|
|“energy”|String	|データキー|ID固定
|packet	|Packet	|データ構造|実装依存


⸻

## 7. リアルタイムHUD（毎Tick描画）
```Java
package com.example.mymod.system.X;

public final class XHudRealtime {

    private XHudRealtime() {}

    public static String render(Object player) {

        XState state = XManager.get(player);

        StringBuilder builder = new StringBuilder();
        builder.append("Energy: ").append(state.getEnergy());
        builder.append(" | Level: ").append(state.getLevel());
        builder.append(" | Kills: ").append(state.getCounter("kills"));

        return builder.toString();
    }
}
```
|指定必須箇所|型|内容|Note|
|---|---|---|---|
|player	|Object	|描画対象	|Registry紐付け
|“kills”	|String	|カウンターキー	|任意ID

## Do / Don't
Do:
- システムロジックを純粋でテストしやすく保つ
- 共有API境界は `Object` で扱う
- ローダー依存変換を分離する

Don't:
- 共通システム本体に Minecraft 型を import する
- Forge/Fabric を同一共有クラスに混在させる
- サーバーロジックにUI前提を混ぜる

## 検証
```bash
gradlew :forge:compileJava :fabric:compileJava
gradlew clean build
```
