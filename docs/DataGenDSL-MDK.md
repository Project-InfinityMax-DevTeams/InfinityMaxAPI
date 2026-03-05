# DataGen Kotlin DSL (MDK連携ガイド)

このドキュメントは、`common` にある DataGen Kotlin DSL を **MDK 側で実行する**ための最小構成を示します。  
`common` は定義専用であり、実行は Forge/Fabric 各ローダー側で行ってください。

## 1. Kotlin DSLで定義を書く（MDK共通）

```kotlin
import com.yuyuto.infinitymaxapi.api.libs.datagen.WorldGeneratorCategory
import com.yuyuto.infinitymaxapi.api.libs.datagen.dataGen
import com.yuyuto.infinitymaxapi.api.libs.datagen.runtime.DataGenDefinitionProvider

object ExampleDataGenProvider : DataGenDefinitionProvider {
    override fun provide() = dataGen {
        model("examplemod:block/sample", parent = "minecraft:block/cube_all") {
            texture("all", "examplemod:block/sample")
        }

        language("en_us") {
            entry("item.examplemod.sample", "Sample")
        }

        sound("examplemod.sample", subtitle = "subtitle.examplemod.sample") {
            clip("examplemod:sample", volume = 1.0, pitch = 1.0)
        }

        recipe("examplemod:sample", type = "minecraft:crafting_shaped") {
            ingredient("minecraft:stone", key = "S")
            result("examplemod:sample")
        }

        lootTable("examplemod:blocks/sample", type = "minecraft:block") {
            pool {
                entry(type = "minecraft:item", name = "examplemod:sample")
            }
        }

        tag("examplemod:samples", registry = "item") {
            value("examplemod:sample")
        }

        advancement("examplemod:root") {
            display("Root", "Example root", "minecraft:book")
            criterion("tick", "minecraft:tick")
        }

        globalLootModifier("examplemod:add_bonus", type = "examplemod:add_item") {
            condition("minecraft:match_tool")
            custom("item", "examplemod:bonus")
        }

        datapackRegistryObject("worldgen/configured_feature", "examplemod:sample_feature") {
            custom("feature", "minecraft:ore")
        }

        worldGenerator("examplemod:sample_placed", WorldGeneratorCategory.PLACED_FEATURE) {
            dependency("examplemod:sample_feature")
        }
    }
}
```

MDK初期化時に `DataGenBridge.setProvider(...)` を一度呼び、上記プロバイダを登録します。

---

## 2. Forge側実行

### 2-1. EntryPoint側（Forge）

`DataGenBridge.run(new ForgeDslDataGenExecutor())` を DataGen専用起動時に呼びます。  
このAPIリポジトリでは `-Dinfinitymaxapi.runDatagen=true` をトリガーに呼び出す実装例を入れています。

### 2-2. MDK Forge `build.gradle` 例（DataGenタスク）

```gradle
tasks.register("runForgeDataGen", JavaExec) {
    group = "datagen"
    description = "Run Forge DataGen using InfinityMaxAPI DSL definitions"

    classpath = sourceSets.main.runtimeClasspath
    mainClass = "net.minecraftforge.userdev.LaunchTesting"

    jvmArgs "-Dinfinitymaxapi.runDatagen=true"
    args "--mod", "examplemod", "--all", "--output", file("src/generated/resources").absolutePath
}
```

---

## 3. Fabric側実行

### 3-1. EntryPoint側（Fabric）

`DataGenBridge.run(new FabricDslDataGenExecutor())` を DataGen専用起動時に呼びます。  
このAPIリポジトリでは同じく `-Dinfinitymaxapi.runDatagen=true` をフラグにしています。

### 3-2. MDK Fabric `build.gradle` 例（DataGenタスク）

```gradle
tasks.register("runFabricDataGen") {
    group = "datagen"
    description = "Run Fabric DataGen using InfinityMaxAPI DSL definitions"
    dependsOn "runDatagen"
}
```

Fabric Loomでは通常 `runDatagen` が提供されるため、上記はエイリアスとして利用できます。

---

## 4. 注意点

- `common` 側では `net.minecraft` / `net.minecraftforge` / `net.fabricmc` を使用しません。
- `common` は **定義のみ**。実行は必ず MDK 側ローダー実装で行ってください。
- `SoundGeneratorDsl.kt` / `WorldGeneratorDsl.kt` を分離配置し、データ種別ごとの責務を明示しています。
