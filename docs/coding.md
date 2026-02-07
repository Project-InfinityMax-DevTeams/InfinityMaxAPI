# DSL 全対応表

このドキュメントは、本 MDK に実装されている **すべての DSL（Domain Specific Language）** を
「何を指定する DSL か」「どこに書くか」「何が起きるか」「どう書くか」
を **1 行も迷わせない** ことを目的としています。

---

## 0. DSL 記述ルール共通原則

* **DSL = 宣言**

  * 処理を書く場所ではない
  * 「何を登録するか」だけを書く
* **`build()` / `handle()` / `end()` を呼んだ瞬間に登録確定**
* **記述場所を間違えると動かない**
* Client DSL は **Client 環境限定**

## 1. Block DSL（BlockBuilder）

### 概要

| 項目       | 内容                                 |
| -------- | ---------------------------------- |
| DSL 型    | `BlockBuilder`                     |
| 登録対象     | Block                              |
| 登録先      | Minecraft Block Registry           |
| 記述ディレクトリ | `src/main/java/**/content/blocks/` |
| 登録確定     | `build()`                          |

### 対応コマンド表

| メソッド                 | 型          | 意味      | デフォルト            |
| -------------------- | ---------- | ------- | ---------------- |
| `material(Material)` | `Material` | ブロック素材  | `Material.STONE` |
| `strength(float)`    | `float`    | 硬さ・破壊速度 | Minecraft 標準     |
| `noOcclusion()`      | -          | 遮蔽しない   | false            |
| `build()`            | -          | 登録確定    | -                |

### 記述例

```java
package com.example.mymod.content.blocks;

import com.yourname.yourmod.api.libs.internal.BlockBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;

public final class ModBlocks {

    public static final Block TEST_BLOCK = new BlockBuilder("test_block")
        .material(Material.STONE)
        .strength(3.0f)
        .build();

    public static final Block GLASS_BLOCK = new BlockBuilder("glass_block")
        .material(Material.GLASS)
        .noOcclusion()
        .build();
}
```

## 2. Item DSL（ItemBuilder）

### 概要

| 項目       | 内容                                |
| -------- | --------------------------------- |
| DSL 型    | `ItemBuilder`                     |
| 登録対象     | Item                              |
| 記述ディレクトリ | `src/main/java/**/content/items/` |
| 登録確定     | `build()`                         |

### 対応コマンド表

| メソッド                   | 型           | 意味        | デフォルト |
| ---------------------- | ----------- | --------- | ----- |
| `stack(int)`           | int         | 最大スタック数   | 64    |
| `tab(CreativeModeTab)` | CreativeTab | クリエイティブタブ | null  |
| `durability(int)`      | int         | 耐久値       | 無し    |
| `build()`              | -           | 登録確定      | -     |

### 記述例

```java
package com.example.mymod.content.items;

import com.yourname.yourmod.api.libs.internal.ItemBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;

public final class ModItems {

    public static final Item TEST_ITEM = new ItemBuilder("test_item")
        .stack(64)
        .tab(CreativeModeTab.TAB_MISC)
        .build();

    public static final Item TOOL_ITEM = new ItemBuilder("tool_item")
        .durability(250)
        .build();
}
```

## 3. Entity DSL（EntityBuilder）

### 概要

| 項目       | 内容                                   |
| -------- | ------------------------------------ |
| DSL 型    | `EntityBuilder<T>`                   |
| 登録対象     | EntityType                           |
| 記述ディレクトリ | `src/main/java/**/content/entities/` |
| 登録確定     | `build()`                            |

### 対応コマンド表

| メソッド                    | 型            | 意味       | デフォルト     |
| ----------------------- | ------------ | -------- | --------- |
| `category(MobCategory)` | MobCategory  | エンティティ分類 | `MISC`    |
| `size(float,float)`     | width,height | 当たり判定    | 0.6 / 1.8 |
| `build()`               | -            | 登録確定     | -         |

### 記述例

```java
package com.example.mymod.content.entities;

import com.yourname.yourmod.api.libs.internal.EntityBuilder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public final class ModEntities {

    public static final EntityType<TestEntity> TEST_ENTITY =
        new EntityBuilder<>("test_entity", TestEntity::new)
            .category(MobCategory.CREATURE)
            .size(0.8f, 1.6f)
            .build();

    public static final EntityType<FlyingEntity> FLYING_ENTITY =
        new EntityBuilder<>("flying_entity", FlyingEntity::new)
            .category(MobCategory.MONSTER)
            .build();
}
```

## 4. BlockEntity DSL（BlockEntityBuilder）

### 概要

| 項目       | 内容                                        |
| -------- | ----------------------------------------- |
| DSL 型    | `BlockEntityBuilder<T>`                   |
| 必須       | 対応 Block                                  |
| 記述ディレクトリ | `src/main/java/**/content/blockentities/` |
| 登録確定     | `build()`                                 |

### 対応コマンド表

| メソッド               | 型       | 意味      |
| ------------------ | ------- | ------- |
| `blocks(Block...)` | Block[] | 紐づくブロック |
| `build()`          | -       | 登録確定    |

### 記述例

```java
package com.example.mymod.content.blockentities;

import com.yourname.yourmod.api.libs.internal.BlockEntityBuilder;
import net.minecraft.world.level.block.entity.BlockEntityType;

public final class ModBlockEntities {

    public static final BlockEntityType<TestBlockEntity> TEST_BE =
        new BlockEntityBuilder<>("test_be", TestBlockEntity::new)
            .blocks(ModBlocks.TEST_BLOCK)
            .build();
}
```

## 5. Event DSL（EventBuilder）

### 概要

| 項目       | 内容                                 |
| -------- | ---------------------------------- |
| DSL 型    | `EventBuilder<T extends ModEvent>` |
| 実行場所     | EventBus                           |
| 記述ディレクトリ | `src/main/java/**/logic/events/`   |
| 登録確定     | `handle()`                         |

### 対応コマンド表

| メソッド                      | 意味    | デフォルト  |
| ------------------------- | ----- | ------ |
| `priority(EventPriority)` | 優先度   | NORMAL |
| `async()`                 | 非同期実行 | false  |
| `sync()`                  | 同期実行  | true   |
| `handle(Consumer<T>)`     | 処理登録  | -      |

### 記述例

```java
package com.example.mymod.logic.events;

import com.yourname.yourmod.api.libs.internal.EventBuilder;
import com.yourname.yourmod.api.event.PlayerJoinEvent;
import com.yourname.yourmod.api.event.EventPriority;

public final class ModEvents {

    public static void register() {
        new EventBuilder<>(PlayerJoinEvent.class)
            .priority(EventPriority.HIGH)
            .handle(event -> {
                System.out.println("Player joined: " + event.getPlayer());
            });
    }
}
```

## 6. Client DSL（ClientBuilder）

### 概要（※特殊）

| 項目       | 内容                         |
| -------- | -------------------------- |
| DSL 型    | `ClientBuilder`            |
| 実行条件     | Client 環境のみ                |
| 記述ディレクトリ | `src/main/java/**/client/` |
| 登録確定     | `registerAll()`            |

### Client DSL 構成

| DSL          | 役割          |
| ------------ | ----------- |
| `renders()`  | Renderer 登録 |
| `keybinds()` | KeyBind     |
| `screens()`  | Screen      |
| `hud()`      | HUD Overlay |

### 記述例（完全形）

```java
package com.example.mymod.client;

import com.yourname.yourmod.api.client.Client;

public final class ModClient {

    public static void init() {
        Client.init(client -> {

            client.renders()
                .entity(ModEntities.TEST_ENTITY, TestEntityRenderer::new);

            client.keybinds()
                .action("open_menu")
                .keys(GLFW.GLFW_KEY_G)
                .onPress(() -> Client.screen(TestScreen::new));

            client.screens()
                .register(TestScreen.class);

            client.hud()
                .overlay((graphics, delta) -> {
                    graphics.drawString("Hello HUD", 10, 10);
                });

            client.registerAll();
        });
    }
}
```

## 7. DataGen DSL（BlockGen / ItemGen / EntityGen）

### 概要

| 項目       | 内容                               |
| -------- | -------------------------------- |
| DSL 型    | `BlockGen / ItemGen / EntityGen` |
| 実行タイミング  | DataGen フェーズ                     |
| 記述ディレクトリ | `src/main/java/**/datagen/`      |
| 登録確定     | `end()`                          |

### 対応表（共通）

| メソッド      | 意味      |
| --------- | ------- |
| `model()` | モデル定義   |
| `loot()`  | ルートテーブル |
| `tag()`   | タグ      |
| `lang()`  | 表示名     |
| `end()`   | 出力確定    |

### 記述例

```java
package com.example.mymod.datagen;

import com.yourname.yourmod.api.libs.datagen.DataGen;

public final class ModDataGen {

    public static void register() {

        DataGen.block("test_block")
            .model(BlockModels.CUBE_ALL)
            .end();

        DataGen.item("test_item")
            .lang("Test Item")
            .end();
    }
}
```

## 最終確認チェックリスト

* [ ] DSL は正しいディレクトリにあるか
* [ ] `build()` / `handle()` / `end()` を呼んでいるか
* [ ] Client DSL に `registerAll()` があるか
* [ ] DataGen を通常ロジックに混ぜていないか
