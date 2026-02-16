# InfinityMaxMOD-Template

## Overview
InfinityMaxMOD-Template is a multi-loader Minecraft MDK template.
One shared codebase can produce both Forge and Fabric outputs.

## Features
- Shared API/DSL structure in common code
- Loader bridge architecture (`Forge` / `Fabric`)
- Single-command full build
- Documentation set for architecture, coding, and DSL usage

## Quick Help
### Requirements
- JDK 17
- Gradle Wrapper (included)

### Build all
```bash
gradlew clean build
```

### Build only Forge
```bash
gradlew :forge:build
```

### Build only Fabric
```bash
gradlew :fabric:build
```

## Documentation
- Main index: [docs/README.md](docs/README.md)
- Full MDK guide: [docs/MDK_GUIDE.md](docs/MDK_GUIDE.md)
- DSL reference: [docs/DSL.md](docs/DSL.md)
- Coding guide: [docs/coding.md](docs/coding.md)
- About: [docs/about.md](docs/about.md)
- Custom system: [docs/custom_system.md](docs/custom_system.md)
- System runtime guide: [docs/system_runtime.md](docs/system_runtime.md)

## Support Notes
- Keep common code platform-neutral (no direct Minecraft imports)
- Put loader-specific conversions in `loader/Forge` and `loader/Fabric`
- Run both compile tasks before release

## DSL-Driven System Template
- `SystemRegistry`: central registry and lifecycle manager for all systems
- Capability-like `StateManager` / `StateContainer`
- `NetworkChannel`: platform-neutral packet channel abstraction
- `TickScheduler`: delayed/repeating tick execution
- `SaveManager`: section-based save/load aggregation
- `EventBridge`: loader events -> common event bus bridge
- `DataDrivenLoader`: DSL declaration binding to runtime systems

Key packages:
- API layer: `com.yourname.yourmod.api.system.*`
- Loader bridge layer: `com.yourname.yourmod.loader.bridge.*`

---

# InfinityMaxMOD-Template

## 概要
InfinityMaxMOD-Template は、マルチローダー対応の Minecraft MDK テンプレートです。
1つの共通コードベースから Forge と Fabric の両方を出力できます。

## 特徴
- 共通層に共有 API / DSL 構造
- ローダーブリッジ設計（`Forge` / `Fabric`）
- 1コマンドで全体ビルド
- 設計・実装・DSL のドキュメントを同梱

## クイックヘルプ
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

## ドキュメント
- メイン索引: [docs/README.md](docs/README.md)
- MDK総合ガイド: [docs/MDK_GUIDE.md](docs/MDK_GUIDE.md)
- DSLリファレンス: [docs/DSL.md](docs/DSL.md)
- コーディングガイド: [docs/coding.md](docs/coding.md)
- 概要: [docs/about.md](docs/about.md)
- カスタムシステム: [docs/custom_system.md](docs/custom_system.md)

## サポートメモ
- 共通層はプラットフォーム非依存で保つ（Minecraft直import禁止）
- ローダー固有の型変換は `loader/Forge` / `loader/Fabric` に置く
- リリース前に両方の compile タスクを実行する

## DSL駆動システムテンプレート（新規）
- `SystemRegistry`: 全システムの中心的なレジストリおよびライフサイクル管理
- 機能ベースの `StateManager` / `StateContainer`
- `NetworkChannel`: プラットフォーム非依存のパケットチャネル抽象化
- `TickScheduler`: 遅延/繰り返しティック実行
- `SaveManager`: セクションベースの保存/読み込み集約
- `EventBridge`: ローダーイベント → 共通イベントバスブリッジ
- `DataDrivenLoader`: DSL宣言とランタイムシステムのバインディング

主要パッケージ:
- API層: `com.yourname.yourmod.api.system.*`
- ローダーブリッジ層: `com.yourname.yourmod.loader.bridge.*`
