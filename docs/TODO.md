# DataGen TODO（致命的欠陥一覧）

前提: 「MODに必要な全 JSON を自動生成する」ことを目標にした場合の、現行実装の欠陥。

## 1. 実 Handler が実質未実装
- `PlatformDataGen.Handler` の Forge 実装 (`ForgeDataGenImpl`) が空メソッドのため、JSON が1つも出力されない。
- Fabric 側は `PlatformDataGen.Handler` 実装自体が存在せず、common から submit された情報の終端がない。

## 2. block 系 JSON の生成欠落
- `assets/<modid>/models/block/*.json` 生成経路がない。
- `assets/<modid>/blockstates/*.json` 生成経路がない。
- `assets/<modid>/models/item/<block_item>.json`（block item model）生成経路がない。
- `data/<modid>/loot_tables/blocks/*.json` 生成経路がない。
- `data/<namespace>/tags/blocks/*.json` 生成経路がない。

## 3. item 系 JSON の生成欠落
- `assets/<modid>/models/item/*.json` 生成経路がない。
- `data/<namespace>/tags/items/*.json` 生成経路がない。
- `assets/<modid>/lang/*.json` の item 翻訳キー出力経路がない。

## 4. entity 系 JSON の生成欠落
- `data/<modid>/loot_tables/entities/*.json` 生成経路がない。
- `assets/<modid>/lang/*.json` の entity 翻訳キー出力経路がない。
- （スポーン関連を Datagen 管理する場合）`data/<modid>/tags/entity_types/*.json` 生成経路がない。

## 5. レシピ・進捗・テーブル類の生成経路がない
- `data/<modid>/recipes/*.json` 生成 API がない。
- `data/<modid>/advancements/*.json` 生成 API がない。
- `data/<modid>/loot_tables/chests/*.json` 等の汎用 loot table 生成 API がない。

## 6. data pack / assets pack の成立条件を満たせない
- `pack.mcmeta` の生成管理がない（生成先・責務が未定義）。
- 参照整合性検証（blockstate -> model、lang key、tag 参照先）の検証工程がない。

## 7. 20+ 要素運用で破綻する運用上の欠落
- 生成対象の一覧管理（何を生成し、何を生成しないか）がなく、欠落検知できない。
- 同一ID重複や上書き検知がなく、大量要素で JSON の衝突を防げない。
- 出力先パス規約（assets/data, namespace）の一元管理がない。
