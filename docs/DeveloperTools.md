# InfinityMaxAPI Internal DSL – Developer Documentation
---
# 📦 BlockBuilder DSL

## 🔹 Usage Example (with imports)

```java
import com.yuyuto.infinitymaxapi.api.libs.internal.BlockBuilder;
import net.minecraft.world.level.block.Block;

Block myBlock = new BlockBuilder<Block>("copper_machine")
        .template(new Block(Block.Properties.of()))
        .strength(3.0f)
        .noOcclusion()
        .build();
```

---

## 🔹 Configuration Table

| Method                    | Data Passed    | Type     | Purpose                         | Required | Notes                 |
| ------------------------- | -------------- | -------- | ------------------------------- | -------- | --------------------- |
| `BlockBuilder(String id)` | Block ID       | `String` | Registry identifier             | ✅        | Used in ModRegistries |
| `template(T template)`    | Block instance | `T`      | Actual block object to register | ✅        | Cannot be null        |
| `strength(float)`         | Hardness       | `float`  | Mining strength                 | ❌        | Default = 1.0         |
| `noOcclusion()`           | Flag           | boolean  | Disables light occlusion        | ❌        | Sets true when called |
| `build()`                 | —              | `T`      | Executes registration           | —        | Calls registerBlock   |

---

## 🔹 Internal Registration Call

```
ModRegistries.registerBlock(
    id,
    template,
    strength,
    noOcclusion
)
```

---

# 📦 ItemBuilder DSL

## 🔹 Usage Example

```java
import com.yuyuto.infinitymaxapi.api.libs.internal.ItemBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;

Item myItem = new ItemBuilder<Item, CreativeModeTab>("energy_core")
        .template(new Item(new Item.Properties()))
        .stack(16)
        .durability(250)
        .tab(CreativeModeTab.TAB_MISC)
        .build();
```

---

## 🔹 Configuration Table

| Method                   | Data          | Type     | Purpose                     | Required | Notes              |
| ------------------------ | ------------- | -------- | --------------------------- | -------- | ------------------ |
| `ItemBuilder(String id)` | ID            | `String` | Registry name               | ✅        |                    |
| `template(T)`            | Item instance | `T`      | Actual item                 | ✅        |                    |
| `stack(int)`             | Stack size    | `int`    | Max stack count             | ❌        | Default = 64       |
| `durability(int)`        | Durability    | `int`    | Tool durability             | ❌        | Default = 0        |
| `tab(TAB)`               | Creative tab  | `TAB`    | Creative inventory category | ❌        |                    |
| `build()`                | —             | `T`      | Executes registration       | —        | Calls registerItem |

---

## 🔹 Internal Registration

```
ModRegistries.registerItem(id, template)
```

*Note:* stack/durability/tab are expected to influence the template configuration.

---

# 📦 EntityBuilder DSL

## 🔹 Usage Example

```java
import com.yuyuto.infinitymaxapi.api.libs.internal.EntityBuilder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

EntityType<?> myEntity = new EntityBuilder<>("energy_drone", () -> EntityType.Builder.of(...))
        .category(() -> MobCategory.MISC)
        .size(0.8f, 0.8f)
        .build();
```

---

## 🔹 Configuration Table

| Method                               | Data           | Type                | Purpose               | Required | Notes                |
| ------------------------------------ | -------------- | ------------------- | --------------------- | -------- | -------------------- |
| `EntityBuilder(String, Supplier<T>)` | ID + factory   | `String + Supplier` | Entity creation logic | ✅        |                      |
| `category(Supplier<C>)`              | Mob category   | `Supplier<C>`       | Entity classification | ⚠        | Must not be null     |
| `size(float, float)`                 | Width / Height | `float`             | Hitbox size           | ❌        | Default = 0.6 / 1.8  |
| `build()`                            | —              | `T`                 | Executes registration | —        | Calls registerEntity |

---

## 🔹 Internal Registration

```
ModRegistries.registerEntity(
    id,
    entity,
    category,
    width,
    height
)
```

---

# 📦 BlockEntityBuilder DSL

## 🔹 Usage Example

```java
import com.yuyuto.infinitymaxapi.api.libs.internal.BlockEntityBuilder;

BlockEntityType<?> machineEntity =
    new BlockEntityBuilder<>("machine_entity", MyBlockEntity::new)
        .blocks(machineBlock)
        .build();
```

---

## 🔹 Configuration Table

| Method                                    | Data          | Type                | Purpose                        | Required |
| ----------------------------------------- | ------------- | ------------------- | ------------------------------ | -------- |
| `BlockEntityBuilder(String, Supplier<T>)` | ID + factory  | `String + Supplier` | BlockEntity factory            | ✅        |
| `blocks(B...)`                            | Target blocks | Varargs             | Blocks this entity attaches to | ⚠        |
| `build()`                                 | —             | `T`                 | Executes registration          | —        |

---

## 🔹 Internal Registration

```
ModRegistries.registerBlockEntity(id, blockEntity, blocks)
```

---

# 📦 EventBuilder DSL

## 🔹 Usage Example

```java
import com.yuyuto.infinitymaxapi.api.libs.internal.EventBuilder;
import com.yuyuto.infinitymaxapi.api.event.PlayerJoinEvent;
import com.yuyuto.infinitymaxapi.api.event.EventPriority;

new EventBuilder<>(PlayerJoinEvent.class)
        .priority(EventPriority.HIGH)
        .async()
        .handle(event -> {
            System.out.println("Player joined");
        });
```

---

## 🔹 Configuration Table

| Method                    | Data          | Type          | Purpose                 | Options             |
| ------------------------- | ------------- | ------------- | ----------------------- | ------------------- |
| `EventBuilder(Class<T>)`  | Event class   | `Class<T>`    | Target event type       | Any `ModEvent`      |
| `priority(EventPriority)` | Priority      | enum          | Execution order         | LOW / NORMAL / HIGH |
| `async()`                 | Async flag    | boolean       | Runs on separate thread | async = true        |
| `sync()`                  | Sync flag     | boolean       | Main-thread execution   | async = false       |
| `handle(Consumer)`        | Handler logic | `Consumer<T>` | Event processing        | Required            |

---

## 🔹 Internal Registration

```
ModEventBus.listen(eventClass, consumer, priority, async)
```

---

# 📦 ClientBuilder DSL

## 🔹 Usage Example

```java
import com.yuyuto.infinitymaxapi.api.libs.internal.ClientBuilder;

ClientBuilder client = new ClientBuilder();

client.renders().registerAll();
client.keybinds().registerAll();
client.screens().registerAll();
client.hud().registerAll();

client.registerAll();
```

---

## 🔹 DSL Structure Overview

| Method          | Purpose                     |
| --------------- | --------------------------- |
| `renders()`     | Renderer registration DSL   |
| `keybinds()`    | Keybinding registration DSL |
| `screens()`     | GUI screen registration     |
| `hud()`         | HUD elements                |
| `registerAll()` | Final registration trigger  |

---

# 📦 PlatformDataGen

## 🔹 Usage Example

```java
import com.yuyuto.infinitymaxapi.api.platform.PlatformDataGen;

PlatformDataGen.submitBlock(id, model, loot, tags);
PlatformDataGen.submitItem(id, model, tags, lang);
PlatformDataGen.submitEntity(id, loot, lang);
```

---

## 🔹 Configuration Table

| Method         | Data Passed              | Purpose                |
| -------------- | ------------------------ | ---------------------- |
| `submitBlock`  | id / model / loot / tags | Block data generation  |
| `submitItem`   | id / model / tags / lang | Item data generation   |
| `submitEntity` | id / loot / lang         | Entity data generation |

A custom `Handler` implementation can override behavior.

---

# 🧠 Architectural Summary

| DSL                | Registers To  | Manages         |
| ------------------ | ------------- | --------------- |
| BlockBuilder       | ModRegistries | Blocks          |
| ItemBuilder        | ModRegistries | Items           |
| EntityBuilder      | ModRegistries | Entities        |
| BlockEntityBuilder | ModRegistries | BlockEntities   |
| EventBuilder       | ModEventBus   | Events          |
| ClientBuilder      | Client Layer  | Rendering / UI  |
| PlatformDataGen    | Handler       | Data generation |
---

# 開発DSLライブラリドキュメント
ここでは、MODのアドオン開発者向けにAPIの登録DSLを用いたゲームオブジェクトを実装する方法を示します。

# 📦 BlockBuilder DSL

## 🔹 使用例（import込み）

```java
import com.yuyuto.infinitymaxapi.api.libs.internal.BlockBuilder;
import net.minecraft.world.level.block.Block;

Block myBlock = new BlockBuilder<Block>("copper_machine")
        .template(new Block(Block.Properties.of()))
        .strength(3.0f)
        .noOcclusion()
        .build();
```

---

## 🔹 データ構造と流れ

| 設定メソッド                    | 入るデータ  | 型        | 何を設定するか           | 必須 | 備考                 |
| ------------------------- | ------ | -------- | ----------------- | -- | ------------------ |
| `BlockBuilder(String id)` | ブロックID | `String` | レジストリ登録ID         | ✅  | ModRegistriesに渡される |
| `template(T template)`    | ブロック本体 | 任意型T     | 実際に登録するブロックインスタンス | ✅  | null不可             |
| `strength(float)`         | 硬さ     | `float`  | 破壊硬度              | ❌  | デフォルト1.0           |
| `noOcclusion()`           | フラグ    | boolean  | 光を遮らない設定          | ❌  | 呼ぶとtrue            |
| `build()`                 | —      | T        | 登録実行              | —  | registerBlockに渡される |

---

## 🔹 内部登録されるデータ

```text
ModRegistries.registerBlock(
    id,
    template,
    strength,
    noOcclusion
)
```

---

# 📦 ItemBuilder DSL

## 🔹 使用例

```java
import com.yuyuto.infinitymaxapi.api.libs.internal.ItemBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;

Item myItem = new ItemBuilder<Item, CreativeModeTab>("energy_core")
        .template(new Item(new Item.Properties()))
        .stack(16)
        .durability(250)
        .tab(CreativeModeTab.TAB_MISC)
        .build();
```

---

## 🔹 設定項目一覧

| メソッド                     | データ     | 型      | 内容         | 必須 | 備考               |
| ------------------------ | ------- | ------ | ---------- | -- | ---------------- |
| `ItemBuilder(String id)` | ID      | String | 登録ID       | ✅  |                  |
| `template(T)`            | アイテム本体  | T      | 実際のItem    | ✅  |                  |
| `stack(int)`             | 最大スタック数 | int    | 1〜64など     | ❌  | default=64       |
| `durability(int)`        | 耐久値     | int    | ツール用       | ❌  | default=0        |
| `tab(TAB)`               | クリエタブ   | TAB    | Creative分類 | ❌  |                  |
| `build()`                | —       | T      | 登録         | —  | registerItem呼び出し |

---

## 🔹 内部登録処理

```text
ModRegistries.registerItem(id, template)
```

※ stack/durability/tab はテンプレート生成側で利用される想定

---

# 📦 EntityBuilder DSL

## 🔹 使用例

```java
import com.yuyuto.infinitymaxapi.api.libs.internal.EntityBuilder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

EntityType<?> myEntity = new EntityBuilder<>("energy_drone", () -> EntityType.Builder.of(...))
        .category(() -> MobCategory.MISC)
        .size(0.8f, 0.8f)
        .build();
```

---

## 🔹 設定項目一覧

| メソッド                                 | データ            | 型                 | 内容         | 必須 | 備考                 |
| ------------------------------------ | -------------- | ----------------- | ---------- | -- | ------------------ |
| `EntityBuilder(String, Supplier<T>)` | ID + factory   | String + Supplier | エンティティ生成処理 | ✅  |                    |
| `category(Supplier<C>)`              | MobCategory    | Supplier          | 分類         | ⚠  | null注意             |
| `size(float, float)`                 | width / height | float             | 当たり判定サイズ   | ❌  | default 0.6/1.8    |
| `build()`                            | —              | T                 | 登録         | —  | registerEntity呼び出し |

---

## 🔹 内部登録

```text
ModRegistries.registerEntity(
    id,
    entity,
    category,
    width,
    height
)
```

---

# 📦 BlockEntityBuilder DSL

## 🔹 使用例

```java
import com.yuyuto.infinitymaxapi.api.libs.internal.BlockEntityBuilder;

BlockEntityType<?> machineEntity =
    new BlockEntityBuilder<>("machine_entity", MyBlockEntity::new)
        .blocks(machineBlock)
        .build();
```

---

## 🔹 設定表

| メソッド                                      | データ          | 型                 | 内容      | 必須 |
| ----------------------------------------- | ------------ | ----------------- | ------- | -- |
| `BlockEntityBuilder(String, Supplier<T>)` | ID + factory | String + Supplier | BE生成処理  | ✅  |
| `blocks(B...)`                            | 対象ブロック       | 可変長               | 紐付けブロック | ⚠  |
| `build()`                                 | —            | T                 | 登録      | —  |

---

## 🔹 内部登録

```text
ModRegistries.registerBlockEntity(id, blockEntity, blocks)
```

---

# 📦 EventBuilder DSL

## 🔹 使用例

```java
import com.yuyuto.infinitymaxapi.api.libs.internal.EventBuilder;
import com.yuyuto.infinitymaxapi.api.event.PlayerJoinEvent;
import com.yuyuto.infinitymaxapi.api.event.EventPriority;

new EventBuilder<>(PlayerJoinEvent.class)
        .priority(EventPriority.HIGH)
        .async()
        .handle(event -> {
            System.out.println("Player joined");
        });
```

---

## 🔹 設定項目一覧

| メソッド                      | データ   | 型        | 内容       | 選択肢                 |
| ------------------------- | ----- | -------- | -------- | ------------------- |
| `EventBuilder(Class<T>)`  | イベント型 | Class    | 監視対象イベント | 任意のModEvent         |
| `priority(EventPriority)` | 優先度   | enum     | 実行順      | LOW / NORMAL / HIGH |
| `async()`                 | 非同期   | boolean  | 別スレッド実行  | async=true          |
| `sync()`                  | 同期    | boolean  | メインスレッド  | async=false         |
| `handle(Consumer)`        | 処理内容  | Consumer | 実行処理     | 必須                  |

---

## 🔹 内部登録

```text
ModEventBus.listen(eventClass, consumer, priority, async)
```

---

# 📦 ClientBuilder DSL

## 🔹 使用例

```java
import com.yuyuto.infinitymaxapi.api.libs.internal.ClientBuilder;

ClientBuilder client = new ClientBuilder();

client.renders().registerAll();
client.keybinds().registerAll();
client.screens().registerAll();
client.hud().registerAll();

client.registerAll();
```

---

## 🔹 DSL構造

| メソッド            | 役割        |
| --------------- | --------- |
| `renders()`     | レンダラ登録DSL |
| `keybinds()`    | キーバインド登録  |
| `screens()`     | GUI登録     |
| `hud()`         | HUD登録     |
| `registerAll()` | 最終登録      |

---

# 📦 PlatformDataGen

## 🔹 使用例

```java
import com.yuyuto.infinitymaxapi.api.platform.PlatformDataGen;

PlatformDataGen.submitBlock(id, model, loot, tags);
PlatformDataGen.submitItem(id, model, tags, lang);
PlatformDataGen.submitEntity(id, loot, lang);
```

---

## 🔹 登録内容一覧

| メソッド           | 入るデータ                    | 内容         |
| -------------- | ------------------------ | ---------- |
| `submitBlock`  | id / model / loot / tags | ブロック用データ生成 |
| `submitItem`   | id / model / tags / lang | アイテム用      |
| `submitEntity` | id / loot / lang         | エンティティ用    |

Handlerが差し替え可能。

---

# 🧠 全体アーキテクチャ整理

| DSL                | 登録先           | 管理対象        |
| ------------------ | ------------- | ----------- |
| BlockBuilder       | ModRegistries | ブロック        |
| ItemBuilder        | ModRegistries | アイテム        |
| EntityBuilder      | ModRegistries | エンティティ      |
| BlockEntityBuilder | ModRegistries | BlockEntity |
| EventBuilder       | ModEventBus   | イベント        |
| ClientBuilder      | Client内部      | クライアント要素    |
| PlatformDataGen    | Handler実装     | データ生成       |
