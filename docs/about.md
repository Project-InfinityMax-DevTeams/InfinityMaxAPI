# About This Template

## What this MDK is
InfinityMaxMOD-Template is a multi-loader MDK.
One shared codebase can build both Forge and Fabric artifacts.

## Main design
This template is split into two layers:
- Common layer (`api` and shared logic): pure Java, platform-neutral
- Loader layer (`loader/Forge`, `loader/Fabric`): platform-specific adaptation

The common layer should not directly depend on Minecraft loader classes.
Use `Object` or wrapper interfaces at API boundaries.

## Why this design exists
Benefits:
- One gameplay/business logic codebase
- Less duplication between Forge and Fabric
- Faster maintenance when updating features

Tradeoff:
- You must keep strict boundaries between common and loader code

## High-level flow
1. Loader entrypoint starts (`ForgeEntrypoint` or `FabricEntrypoint`)
2. Runtime platform bridge is set with `Platform.set(...)`
3. Network/events are registered
4. `YourMod.init()` runs
5. Shared API/DSL calls route into loader implementations

## Project map
```text
src/main/java/com/yourname/yourmod/
  api/
    event/
    libs/
    lifecycle/
    platform/
    util/
  loader/
    LoaderExpectPlatform.java
    Platform.java
    Forge/
    Fabric/
```

## Non-negotiable rules
1. No direct `net.minecraft.*` imports in common API layer
2. Keep shared signatures platform-neutral (`Object` or wrappers)
3. Cast/convert platform types only in loader implementations
4. Validate both loaders on every change

## Build commands
```bash
gradlew :forge:compileJava :fabric:compileJava
gradlew clean build
```

## Minimal short template (current structure)
```java
package com.example.mymod;

import com.yourname.yourmod.api.libs.Events;
import com.yourname.yourmod.api.libs.Registry;

public final class MyFeature {

    private static final Object TEST_BLOCK = Registry.block("test_block")
            .template(new Object())
            .strength(3.0f)
            .build();

    private MyFeature() {}

    public static void init() {
        Events.playerJoin().handle(event -> {
            Object player = event.player;
            System.out.println("Player joined: " + player);
        });
    }
}
```

## Typical mistakes to avoid
- Importing Minecraft classes in common API files
- Writing Forge logic in Fabric classes (or opposite)
- Assuming loader-specific objects are available in common code
- Skipping dual-loader compile checks

---

# このテンプレートについて

## このMDKの役割
InfinityMaxMOD-Template はマルチローダー対応MDKです。
1つの共通コードベースから Forge と Fabric の両方をビルドできます。

## 基本設計
このテンプレートは2層構造です。
- 共通層（`api` と共通ロジック）: 純Java、プラットフォーム非依存
- ローダー層（`loader/Forge`, `loader/Fabric`）: プラットフォーム固有の適応処理

共通層は Minecraft ローダー固有クラスに直接依存しません。
API境界では `Object` かラッパーを使います。

## この設計の目的
メリット:
- ゲームロジックを1つのコードベースに集約できる
- Forge/Fabric間の重複を減らせる
- 機能更新時の保守が速い

トレードオフ:
- 共通層とローダー層の境界管理が必須

## 全体の流れ
1. ローダーのエントリポイント起動（`ForgeEntrypoint` / `FabricEntrypoint`）
2. `Platform.set(...)` で実行中のプラットフォームブリッジを設定
3. ネットワーク・イベントを登録
4. `YourMod.init()` 実行
5. 共通API/DSL呼び出しをローダー実装へ委譲

## プロジェクト構成
```text
src/main/java/com/yourname/yourmod/
  api/
    event/
    libs/
    lifecycle/
    platform/
    util/
  loader/
    LoaderExpectPlatform.java
    Platform.java
    Forge/
    Fabric/
```

## 必須ルール
1. 共通API層で `net.minecraft.*` を直接 import しない
2. 共有シグネチャは非依存型（`Object` かラッパー）で保つ
3. 型変換はローダー実装側だけで行う
4. 変更時は両ローダーを必ず検証する

## ビルドコマンド
```bash
gradlew :forge:compileJava :fabric:compileJava
gradlew clean build
```

## 最小ショートテンプレ（現行構成対応）
```java
package com.example.mymod;

import com.yourname.yourmod.api.libs.Events;
import com.yourname.yourmod.api.libs.Registry;

public final class MyFeature {

    private static final Object TEST_BLOCK = Registry.block("test_block")
            .template(new Object())
            .strength(3.0f)
            .build();

    private MyFeature() {}

    public static void init() {
        Events.playerJoin().handle(event -> {
            Object player = event.player;
            System.out.println("Player joined: " + player);
        });
    }
}
```

## よくある失敗
- 共通APIに Minecraft クラスを import してしまう
- Forge実装を Fabric 側に書く（逆も同様）
- 共通層でローダー専用オブジェクトがある前提で書く
- 両ローダーのコンパイル確認を省略する
