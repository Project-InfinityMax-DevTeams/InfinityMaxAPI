# 2. DSL による各要素登録

この章では、**DSL で登録できる要素**を網羅します。
MOD 制作者はここで示す型と DSL メソッドを使い、**何を追加したいかだけを書く**ことができます。

---

## 2.1 主要 DSL 型一覧

| 型                                                   | 用途                                     | 記述場所                    | 主なメソッド                                                                | 実行時動作                         | 備考                             |
| --------------------------------------------------- | -------------------------------------- | ----------------------- | --------------------------------------------------------------------- | ----------------------------- | ------------------------------ |
| `BlockBuilder`                                      | ブロックの登録                                | `content/blocks`        | `material(Material)`, `strength(float)`, `noOcclusion()`, `build()`   | Minecraft Registry にブロック登録    | デフォルト Material は STONE         |
| `ItemBuilder`                                       | アイテムの登録                                | `content/items`         | `stack(int)`, `tab(CreativeModeTab)`, `durability(int)`, `build()`    | Minecraft Registry にアイテム登録    | CreativeTab 設定可能               |
| `EntityBuilder<T>`                                  | エンティティの登録                              | `content/entities`      | `category(MobCategory)`, `size(float,float)`, `build()`               | Registry に EntityType 登録      | サイズ・カテゴリ指定可能                   |
| `BlockEntityBuilder<T>`                             | ブロックエンティティの登録                          | `content/blockentities` | `blocks(Block...)`, `build()`                                         | Registry に BlockEntityType 登録 | 必ずブロックを紐づける必要あり                |
| `EventBuilder<T extends ModEvent>`                  | イベントリスナー登録                             | `logic/events`          | `priority(EventPriority)`, `async()`, `sync()`, `handle(Consumer<T>)` | ModEventBus にリスナー登録           | async は非同期実行、sync は明示同期        |
| `ClientBuilder`                                     | Client 専用要素（Render・Screen・KeyBind・HUD） | `client/`               | `renders()`, `screens()`, `keybinds()`, `hud()`, `registerAll()`      | Client 環境でのみ実行                | サーバーでは noop                    |
| `BaseGen<T>` / `BlockGen` / `ItemGen` / `EntityGen` | データ生成 (DataGen)                        | `datagen/`              | `model()`, `loot()`, `tag()`, `lang()`, `end()`                       | JSON データ生成・登録                 | build 時ではなく、DataGen フェーズで処理される |

---

## 2.2 簡易サンプルコード

### ブロック登録

```java
public final class MyBlocks {
    public static final Block TEST_BLOCK = new BlockBuilder("test_block")
        .material(Material.STONE)
        .strength(3.0f)
        .noOcclusion()
        .build();
}
```

* `material` → ブロック素材
* `strength` → 硬さ・破壊速度
* `noOcclusion` → 通常ブロックの背面描画制御
* `build()` → 登録実行

### アイテム登録

```java
public final class MyItems {
    public static final Item TEST_ITEM = new ItemBuilder("test_item")
        .stack(64)
        .tab(CreativeModeTab.TAB_MISC)
        .durability(100)
        .build();
}
```

### エンティティ登録

```java
public final class MyEntities {
    public static final EntityType<MyEntity> TEST_ENTITY = new EntityBuilder<>("test_entity", MyEntity::new)
        .category(MobCategory.CREATURE)
        .size(0.6f, 1.8f)
        .build();
}
```

### ブロックエンティティ登録

```java
public final class MyBlockEntities {
    public static final BlockEntityType<MyBlockEntity> TEST_BE = new BlockEntityBuilder<>("test_be", MyBlockEntity::new)
        .blocks(MyBlocks.TEST_BLOCK)
        .build();
}
```

### イベント登録

```java
new EventBuilder<>(PlayerJoinEvent.class)
    .priority(EventPriority.HIGH)
    .async()
    .handle(event -> {
        System.out.println(event.getPlayer() + " joined!");
    });
```

### Client 要素登録

```java
Client.init(client -> {
    client.renders()
        .entity(MyEntities.TEST_ENTITY, MyEntityRenderer::new)
        .block(MyBlocks.TEST_BLOCK, MyBlockRenderer::new);

    client.keybinds()
        .action("open_menu")
        .keys(GLFW.GLFW_KEY_G)
        .onPress(() -> Client.screen(MyScreen::new));

    client.screens()
        .register(MyScreen.class);

    client.hud()
        .overlay((graphics, tickDelta) -> {
            graphics.drawString("Hello HUD", 10, 10);
        });

    client.registerAll(); // まとめて登録
});
```

* Client DSL は **サーバーで実行しない**
* `registerAll()` が必要
* KeyBind の `.action()` は一意 ID を指定、`.onPress()` で挙動を定義

### DataGen の例

```java
DataGen.block("my_block")
    .model(myModel)
    .loot(myLoot)
    .tag(Tags.Blocks.MINEABLE_WITH_PICKAXE)
    .end();

DataGen.item("my_item")
    .model(myModel)
    .tag(Tags.Items.MISC)
    .lang("My Item Name")
    .end();

DataGen.entity("my_entity")
    .loot(myLoot)
    .lang("My Entity")
    .end();
```

* **DataGen は DSL と同じく「宣言的」**
* 生成されるのは JSON ファイルや言語ファイル
* 書く場所は `datagen/` ディレクトリ

---

## 2.3 DSL と登録場所のまとめ（推奨）

| 要素         | DSL 型                          | 記述場所                    | 注意点                         |
| ---------- | ------------------------------ | ----------------------- | --------------------------- |
| ブロック       | BlockBuilder                   | `content/blocks`        | build() で登録                 |
| アイテム       | ItemBuilder                    | `content/items`         | CreativeTab / stack 指定可能    |
| エンティティ     | EntityBuilder                  | `content/entities`      | build() で EntityType 登録     |
| ブロックエンティティ | BlockEntityBuilder             | `content/blockentities` | blocks() 指定必須               |
| イベント       | EventBuilder                   | `logic/events`          | async / sync 指定可            |
| Client     | ClientBuilder                  | `client/`               | registerAll() 必須、サーバーで noop |
| データ生成      | BlockGen / ItemGen / EntityGen | `datagen/`              | end() で処理実行                 |

---

### 2.4 特殊注意点

1. **Client DSL**

   * 描画・UI・キー入力はサーバーでは動かない
   * `.registerAll()` を呼ばないと反映されない

2. **DataGen**

   * JSON・言語ファイルの生成
   * 実行はビルド時フェーズのみ
   * `datagen/` ディレクトリに宣言

3. **EventBuilder**

   * async は非同期で、サーバー側イベントをブロックせずに処理
   * priority は EventBus の呼び出し順

4. **BlockEntityBuilder**

   * ブロック紐付けを忘れると登録失敗
