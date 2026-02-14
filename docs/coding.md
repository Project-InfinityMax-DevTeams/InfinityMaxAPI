# Coding Guide

## Scope
This guide defines coding rules for this MDK template.
Follow these rules to keep shared code stable across Forge and Fabric.

## Core rules
1. Keep common code platform-neutral
2. Use shared DSL/API for registrations and events
3. Isolate platform conversion in loader layer
4. Verify both loaders on every change

## Folder policy
- `api/`: shared DSL/API and shared logic helpers
- `loader/Forge`: Forge-only behavior
- `loader/Fabric`: Fabric-only behavior

Do not place loader-specific imports in `api/`.

## Dependency boundary
Allowed in common code:
- Java standard library
- Template shared APIs in `com.yourname.yourmod.api.*`

Not allowed in common code:
- `net.minecraft.*`
- `net.minecraftforge.*`
- `net.fabricmc.*`

## Naming conventions
- Class: `PascalCase`
- Method/field/local: `camelCase`
- Constant: `UPPER_SNAKE_CASE`
- Package: lowercase

## Short code templates (current API)

### Block registration
```java
public static final Object TEST_BLOCK = Registry.block("test_block")
        .template(new Object())
        .strength(2.0f)
        .noOcclusion()
        .build();
```

### Item registration
```java
public static final Object TEST_ITEM = Registry.item("test_item")
        .template(new Object())
        .stack(64)
        .durability(100)
        .build();
```

### Entity registration
```java
public static final Object TEST_ENTITY = Registry.entity("test_entity", Object::new)
        .category("creature")
        .size(0.8f, 1.6f)
        .build();
```

### BlockEntity registration
```java
public static final Object TEST_BE = Registry.blockEntity("test_be", Object::new)
        .blocks(TEST_BLOCK)
        .build();
```

### Event subscription
```java
Events.playerJoin().handle(event -> {
    Object player = event.player;
    System.out.println("Join: " + player);
});
```

### Client DSL
```java
Client.init(client -> {
    client.renders().registerAll();
    client.keybinds().registerAll();
    client.screens().registerAll();
    client.hud().registerAll();
});
```

### Datagen DSL
```java
DataGen.block("test_block").end();
DataGen.item("test_item").lang("Test Item").end();
DataGen.entity("test_entity").lang("Test Entity").end();
```

## Error prevention checklist
- Common API has no direct loader imports
- Public API signatures remain platform-neutral
- New bridge requirement reflected in `LoaderExpectPlatform`
- Both loader implementations are updated
- Build passes on both loaders

## Verification commands
```bash
gradlew :forge:compileJava :fabric:compileJava
gradlew clean build
```

---

# コーディングガイド

## 対象
このガイドは本MDKテンプレートの実装ルールを定義します。
Forge と Fabric の両方で安定動作する共通コードを維持するために従ってください。

## 基本ルール
1. 共通コードはプラットフォーム非依存で保つ
2. 登録とイベントは共有DSL/APIを使う
3. プラットフォーム変換はローダー層へ隔離する
4. 変更ごとに両ローダーで検証する

## フォルダ方針
- `api/`: 共有DSL/API と共通ロジック補助
- `loader/Forge`: Forge 専用処理
- `loader/Fabric`: Fabric 専用処理

`api/` にローダー依存 import を置かないでください。

## 依存境界
共通コードで許可:
- Java標準ライブラリ
- `com.yourname.yourmod.api.*` の共有API

共通コードで禁止:
- `net.minecraft.*`
- `net.minecraftforge.*`
- `net.fabricmc.*`

## 命名規約
- クラス: `PascalCase`
- メソッド/フィールド/ローカル変数: `camelCase`
- 定数: `UPPER_SNAKE_CASE`
- パッケージ: 小文字

## ショートテンプレコード（現行API対応）

### Block登録
```java
public static final Object TEST_BLOCK = Registry.block("test_block")
        .template(new Object())
        .strength(2.0f)
        .noOcclusion()
        .build();
```

### Item登録
```java
public static final Object TEST_ITEM = Registry.item("test_item")
        .template(new Object())
        .stack(64)
        .durability(100)
        .build();
```

### Entity登録
```java
public static final Object TEST_ENTITY = Registry.entity("test_entity", Object::new)
        .category("creature")
        .size(0.8f, 1.6f)
        .build();
```

### BlockEntity登録
```java
public static final Object TEST_BE = Registry.blockEntity("test_be", Object::new)
        .blocks(TEST_BLOCK)
        .build();
```

### Event購読
```java
Events.playerJoin().handle(event -> {
    Object player = event.player;
    System.out.println("Join: " + player);
});
```

### Client DSL
```java
Client.init(client -> {
    client.renders().registerAll();
    client.keybinds().registerAll();
    client.screens().registerAll();
    client.hud().registerAll();
});
```

### Datagen DSL
```java
DataGen.block("test_block").end();
DataGen.item("test_item").lang("Test Item").end();
DataGen.entity("test_entity").lang("Test Entity").end();
```

## 事故防止チェック
- 共通APIにローダー直importがない
- 公開APIシグネチャが非依存型のまま
- 新しい橋渡し要件を `LoaderExpectPlatform` に反映
- Forge/Fabric 両実装を更新済み
- 両ローダーでビルド成功

## 検証コマンド
```bash
gradlew :forge:compileJava :fabric:compileJava
gradlew clean build
```
