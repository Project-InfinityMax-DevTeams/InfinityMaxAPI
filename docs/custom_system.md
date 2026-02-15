# Custom System Guide

## Goal
A custom system is your own gameplay/business module that is independent from loader internals.
Build systems in common code, then connect them through DSL/events.

Examples:
- Energy system
- Skill system
- Quest system
- Progression system

## Design policy
1. Keep system core in pure Java
2. Keep storage/state structure explicit
3. Connect system to lifecycle/events through shared DSL
4. Use loader layer only for platform-specific access

## Recommended structure
```text
src/main/java/com/example/mymod/
  system/
    energy/
      EnergySystem.java
      EnergyState.java
      EnergyService.java
      EnergyEvents.java
      EnergyClient.java
```

## Step 1: Define core constants
```java
package com.example.mymod.system.energy;

public final class EnergySystem {

    public static final int MAX_ENERGY = 100000;

    private EnergySystem() {}
}
```

## Step 2: Define state model
```java
package com.example.mymod.system.energy;

public final class EnergyState {

    private int value;

    public int get() {
        return value;
    }

    public void set(int next) {
        value = Math.max(0, Math.min(next, EnergySystem.MAX_ENERGY));
    }

    public void add(int delta) {
        set(value + delta);
    }
}
```

## Step 3: Define service logic
```java
package com.example.mymod.system.energy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class EnergyService {

    private static final Map<Object, EnergyState> STATE = new ConcurrentHashMap<>();

    private EnergyService() {}

    public static EnergyState byPlayer(Object player) {
        return STATE.computeIfAbsent(player, key -> new EnergyState());
    }
}
```

## Step 4: Bind system to shared events
```java
package com.example.mymod.system.energy;

import com.yourname.yourmod.api.libs.Events;

public final class EnergyEvents {

    private EnergyEvents() {}

    public static void register() {
        Events.playerJoin().handle(event -> {
            EnergyState state = EnergyService.byPlayer(event.player);
            state.set(100);
        });
    }
}
```

## Step 5: Optional client hook
```java
package com.example.mymod.system.energy;

import com.yourname.yourmod.api.libs.Client;

public final class EnergyClient {

    private EnergyClient() {}

    public static void register() {
        Client.init(client -> {
            client.hud().registerAll();
        });
    }
}
```

## Step 6: Wire into mod init
```java
package com.example.mymod;

import com.example.mymod.system.energy.EnergyEvents;

public final class MyModInit {

    private MyModInit() {}

    public static void init() {
        EnergyEvents.register();
    }
}
```

## Short template for new systems
```java
public final class XSystem {
    public static void init() {
        Events.playerJoin().handle(event -> {
            Object player = event.player;
            // initialize state here
        });
    }
}
```

## Do / Don't
Do:
- Keep system logic testable and pure
- Use `Object` boundary for shared API
- Keep loader-specific conversion isolated

Don't:
- Import Minecraft classes into shared system core
- Mix Forge/Fabric code in one shared class
- Put UI/render assumptions into server-side logic

## Validation
```bash
gradlew :forge:compileJava :fabric:compileJava
gradlew clean build
```

---

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

## 7. 新規システム用ショートテンプレ（最小構成）

```java
public final class XSystem {
    public static void init() {
        Events.playerJoin().handle(event -> {
            Object player = event.player;
            // 初期化・サービス呼び出し・フラグ設定など
        });
    }
}
```

| 指定必須箇所(変数名) | 変数に入る情報 | 指定した場合の挙動・処理 | Note |
| ----------- | ------- | ------------ | ---- |
| （なし）        |         | イベント接続のみ     | 最小構成 |

# XSystem 上級処理テンプレート集

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

## 5. ショートテンプレ（上級版）

```java
public final class XSystemAdvanced {
    public static void init() {

        // プレイヤー参加時初期化
        Events.playerJoin().handle(event -> {
            Object player = event.player;
            XServiceAdvanced.setPlayerEnergy(player, 100);
            XServiceAdvanced.setPlayerLevel(player, 1);
            XServiceAdvanced.unlockPlayerFeature(player);
        });

        // カスタムイベント接続
        Events.on(CustomEvent.class).handle(event -> {
            Object player = event.player;
            if(XUtils.canConsumeEnergy(player, event.deltaEnergy)) {
                XUtils.consumeEnergyIfPossible(player, event.deltaEnergy);
            }
        });

    }
}
```

| 指定必須箇所(変数名)       | 変数に入る情報  | 挙動      | Note             |
| ----------------- | -------- | ------- | ---------------- |
| CustomEvent       | Class<T> | 任意イベント型 | deltaEnergyなどを含む |
| event.deltaEnergy | int      | 消費量     | MAX/MIN制限適用      |
| player            | Object   | 対象プレイヤー | Registry紐付け      |


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
