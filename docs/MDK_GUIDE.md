# InfinityMaxMOD Template MDK Guide

## 1. What This Template Is
This template is a multi-loader Minecraft mod development kit (MDK) for building one codebase into both Forge and Fabric artifacts.

Goal:
- Keep shared logic in common code
- Keep loader-specific logic in loader implementations
- Build both outputs with one command

## 2. Quick Start
### Requirements
- JDK 17
- Gradle Wrapper (included)

### Build Everything
```bash
gradlew clean build
```

### Build Only Forge
```bash
gradlew :forge:build
```

### Build Only Fabric
```bash
gradlew :fabric:build
```

## 3. Project Layout
```text
src/main/java/com/yourname/yourmod/
  api/                # Shared API and DSL
  loader/             # Loader bridge + Forge/Fabric implementations
forge/                # Forge module settings
fabric/               # Fabric module settings
docs/                 # Documentation
```

Important split:
- Common (`api`, shared code): no `net.minecraft.*` imports
- Loader-specific (`loader/Forge`, `loader/Fabric`): platform-specific code

## 4. Architecture Rules (Very Important)
### Rule A: Keep Common Code Pure Java
Do not import Minecraft/Forge/Fabric classes directly in common API.

Bad (common):
```java
import net.minecraft.world.entity.player.Player;
```

Good (common):
```java
public void onPlayerJoin(Object player)
```

### Rule B: Use Wrapper Types in Common API
Use `Object` or your own wrapper interfaces in shared API boundaries.

### Rule C: Convert in Loader Layer
Cast or convert platform types only in Forge/Fabric implementation classes.

## 5. Build Pipeline
- Root `build` triggers both platform builds
- `forge/build.gradle` compiles shared Java + Forge sources
- `fabric/build.gradle` compiles shared Java + Fabric sources
- Platform source exclusion prevents cross-loader compile conflicts

## 6. Core APIs You Will Use
### Registry DSL
Files:
- `api/libs/Registry.java`
- `api/libs/ModItems.java`
- `api/libs/ModBlocks.java`
- `api/libs/ModEntities.java`
- `api/libs/ModBlockEntities.java`

Purpose:
- Register mod content from shared code
- Delegate actual registration to loader implementation

### Event DSL
Files:
- `api/libs/Events.java`
- `api/libs/internal/EventBuilder.java`
- `api/event/ModEventBus.java`

Purpose:
- Subscribe and fire shared mod events

### Packet DSL
Files:
- `api/libs/packet/Packet.java`
- `api/libs/packet/SimplePacket.java`
- `api/libs/packet/PacketBuffer.java`

Purpose:
- Define packet metadata in common code
- Implement transport in loader-specific network classes

### Datagen DSL
Files:
- `api/libs/datagen/*`
- `api/platform/PlatformDataGen.java`

Purpose:
- Define model/loot/tag generation requests in common
- Process per-loader through handler implementation

## 7. Lifecycle and Startup
Entrypoints:
- Forge: `loader/Forge/ForgeEntrypoint.java`
- Fabric: `loader/Fabric/FabricEntrypoint.java`

Flow:
1. Set current platform bridge (`Platform.set(...)`)
2. Register network/events
3. Run `YourMod.init()`

## 8. How to Add New Feature Safely
1. Define shared API/DSL in `api`
2. Keep all method signatures platform-neutral
3. Extend `LoaderExpectPlatform` if bridge is needed
4. Implement behavior in both Forge and Fabric classes
5. Run:
```bash
gradlew :forge:compileJava :fabric:compileJava
```
6. Run full verification:
```bash
gradlew clean build
```

## 9. Troubleshooting
### Error: "cannot find symbol Player" in common source
Cause:
- Minecraft type imported in shared code

Fix:
- Replace direct type with `Object` or wrapper interface
- Move concrete conversion to loader implementation

### Error: cross-loader compile failure
Cause:
- Forge code compiled in Fabric task (or opposite)

Fix:
- Confirm source exclusion in `forge/build.gradle` and `fabric/build.gradle`

### Error: event builder compile mismatch
Cause:
- DSL method visibility or incorrect event bus method name

Fix:
- Ensure `EventBuilder` constructor is public
- Ensure `ModEventBus.listen(...)` is used

## 10. Recommended Daily Workflow
1. Edit shared DSL/API
2. Implement Forge/Fabric bridge behavior
3. Run `:forge:compileJava` and `:fabric:compileJava`
4. Run full `clean build`
5. Update docs when public API changes

---

# InfinityMaxMOD テンプレート MDK ガイド

## 1. このテンプレートの目的
このテンプレートは、1つのコードベースから Forge と Fabric の両方をビルドするためのマルチローダー向け MDK です。

目的:
- 共通ロジックは共通コードに置く
- ローダー依存処理は実装層に分離する
- 1コマンドで両方の成果物を作る

## 2. クイックスタート
### 必要環境
- JDK 17
- Gradle Wrapper（同梱）

### 全体ビルド
```bash
gradlew clean build
```

### Forgeのみ
```bash
gradlew :forge:build
```

### Fabricのみ
```bash
gradlew :fabric:build
```

## 3. プロジェクト構成
```text
src/main/java/com/yourname/yourmod/
  api/                # 共通APIとDSL
  loader/             # ローダーブリッジ + Forge/Fabric実装
forge/                # Forgeモジュール設定
fabric/               # Fabricモジュール設定
docs/                 # ドキュメント
```

重要な分離:
- 共通層（`api` など）: `net.minecraft.*` を直接 import しない
- ローダー層（`loader/Forge`, `loader/Fabric`）: プラットフォーム固有コード

## 4. 設計ルール（重要）
### ルールA: 共通コードは純Javaで保つ
共通APIで Minecraft/Forge/Fabric の型を直接 import しないでください。

悪い例（共通層）:
```java
import net.minecraft.world.entity.player.Player;
```

良い例（共通層）:
```java
public void onPlayerJoin(Object player)
```

### ルールB: 共通API境界はラッパー型
共通API境界は `Object` か独自ラッパーインターフェースを使います。

### ルールC: 型変換はローダー層で実施
Forge/Fabric 実装クラス内だけでキャスト・変換します。

## 5. ビルドパイプライン
- ルート `build` が両プラットフォームのビルドを実行
- `forge/build.gradle` は共通Java + Forge側をコンパイル
- `fabric/build.gradle` は共通Java + Fabric側をコンパイル
- ソース除外設定でクロスローダー衝突を防止

## 6. 主要API
### Registry DSL
対象:
- `api/libs/Registry.java`
- `api/libs/ModItems.java`
- `api/libs/ModBlocks.java`
- `api/libs/ModEntities.java`
- `api/libs/ModBlockEntities.java`

役割:
- 共通層から登録を記述
- 実登録はローダー実装に委譲

### Event DSL
対象:
- `api/libs/Events.java`
- `api/libs/internal/EventBuilder.java`
- `api/event/ModEventBus.java`

役割:
- 共通イベントの購読・発火

### Packet DSL
対象:
- `api/libs/packet/Packet.java`
- `api/libs/packet/SimplePacket.java`
- `api/libs/packet/PacketBuffer.java`

役割:
- 共通層でパケット定義
- 通信処理はローダー側で実装

### Datagen DSL
対象:
- `api/libs/datagen/*`
- `api/platform/PlatformDataGen.java`

役割:
- 共通層で model/loot/tag 生成要求を定義
- 実処理はローダー側ハンドラに委譲

## 7. ライフサイクルと起動
エントリポイント:
- Forge: `loader/Forge/ForgeEntrypoint.java`
- Fabric: `loader/Fabric/FabricEntrypoint.java`

流れ:
1. `Platform.set(...)` で実行プラットフォームを設定
2. network/events を登録
3. `YourMod.init()` を実行

## 8. 新機能追加の安全手順
1. `api` に共通API/DSLを定義
2. メソッドシグネチャをプラットフォーム非依存にする
3. 必要なら `LoaderExpectPlatform` を拡張
4. Forge/Fabric 両方に実装を追加
5. 次を実行:
```bash
gradlew :forge:compileJava :fabric:compileJava
```
6. 最終確認:
```bash
gradlew clean build
```

## 9. トラブルシューティング
### 「cannot find symbol Player」が共通層で出る
原因:
- 共通層が Minecraft 型を直接 import している

対処:
- `Object` または独自ラッパーに置き換える
- 型変換はローダー実装へ移す

### ローダー間コンパイル衝突
原因:
- ForgeコードをFabricタスクが拾う（逆も同様）

対処:
- `forge/build.gradle` と `fabric/build.gradle` の除外設定を確認

### Event DSL のコンパイル不整合
原因:
- `EventBuilder` 可視性不足、または EventBus API 名の不一致

対処:
- `EventBuilder` コンストラクタを public にする
- `ModEventBus.listen(...)` を使う

## 10. 推奨ワークフロー
1. 共通DSL/APIを編集
2. Forge/Fabric ブリッジ実装を追加
3. `:forge:compileJava` と `:fabric:compileJava` を実行
4. `clean build` を実行
5. 公開API変更時は docs を更新
