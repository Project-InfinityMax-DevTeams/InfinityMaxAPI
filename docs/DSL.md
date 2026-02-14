# DSL Reference

## Purpose
This document explains the DSL classes in the current MDK structure.
All code snippets are aligned with current API signatures.

## 1. Registry DSL
Entry point:
- `com.yourname.yourmod.api.libs.Registry`

Available methods:
- `Registry.block(String id)` -> `BlockBuilder`
- `Registry.item(String id)` -> `ItemBuilder`
- `Registry.entity(String id, Supplier<T> factory)` -> `EntityBuilder<T>`
- `Registry.blockEntity(String id, Supplier<T> factory)` -> `BlockEntityBuilder<T>`

### BlockBuilder
Methods:
- `template(Object)`
- `strength(float)`
- `noOcclusion()`
- `build()` -> `Object`

Short template:
```java
Object block = Registry.block("sample_block")
        .template(new Object())
        .strength(3.0f)
        .noOcclusion()
        .build();
```

### ItemBuilder
Methods:
- `stack(int)`
- `tab(Object)`
- `durability(int)`
- `template(Object)`
- `build()` -> `Object`

Short template:
```java
Object item = Registry.item("sample_item")
        .template(new Object())
        .stack(64)
        .durability(120)
        .build();
```

### EntityBuilder
Methods:
- `category(Object)`
- `size(float width, float height)`
- `build()` -> `T`

Short template:
```java
Object entity = Registry.entity("sample_entity", Object::new)
        .category("creature")
        .size(0.8f, 1.6f)
        .build();
```

### BlockEntityBuilder
Methods:
- `blocks(Object... blocks)`
- `build()` -> `T`

Short template:
```java
Object blockEntity = Registry.blockEntity("sample_be", Object::new)
        .blocks(block)
        .build();
```

## 2. Event DSL
Entry point:
- `com.yourname.yourmod.api.libs.Events`

Available methods:
- `Events.on(Class<T>)`
- `Events.playerJoin()`

Builder methods:
- `priority(EventPriority)`
- `async()`
- `sync()`
- `handle(Consumer<T>)`

Short template:
```java
Events.playerJoin()
        .priority(EventPriority.NORMAL)
        .handle(event -> {
            Object player = event.player;
            System.out.println("joined: " + player);
        });
```

## 3. Client DSL
Entry point:
- `com.yourname.yourmod.api.libs.Client`

Builder objects:
- `renders()` -> `RenderDSL`
- `keybinds()` -> `KeybindDSL`
- `screens()` -> `ScreenDSL`
- `hud()` -> `HudDSL`

Each sub-DSL currently exposes:
- `registerAll()`

Short template:
```java
Client.init(client -> {
    client.renders().registerAll();
    client.keybinds().registerAll();
    client.screens().registerAll();
    client.hud().registerAll();
});
```

Note:
- `Client.init(...)` already calls `builder.registerAll()` internally.

## 4. Datagen DSL
Entry point:
- `com.yourname.yourmod.api.libs.datagen.DataGen`

Available methods:
- `DataGen.block(String)` -> `BlockGen`
- `DataGen.item(String)` -> `ItemGen`
- `DataGen.entity(String)` -> `EntityGen`

Common finalize method:
- `end()`

Short template:
```java
DataGen.block("sample_block").end();
DataGen.item("sample_item").lang("Sample Item").end();
DataGen.entity("sample_entity").lang("Sample Entity").end();
```

## 5. Packet DSL
Entry point:
- `com.yourname.yourmod.api.libs.packet.Packet`

Core methods:
- `Packet.define(String id)`
- `serverbound()` / `clientbound()`
- `codec(...)`
- `handle(...)`
- `register()`
- `sendToServer(T)`
- `sendToPlayer(Object, T)`

Short template:
```java
Packet<String> ping = Packet.<String>define("ping")
        .serverbound()
        .codec(buf -> "ping", (packet, buf) -> {})
        .handle((packet, ctx) -> System.out.println(packet));

ping.register();
ping.sendToServer("hello");
```

## 6. Common event helper
`CommonEvents.onPlayerJoin(Object player)` posts `PlayerJoinEvent` to `ModEventBus`.
Use this path for shared event behavior.

---

# DSL リファレンス

## 目的
このドキュメントは、現行MDK構成のDSLクラスを説明します。
すべてのサンプルコードは現在のAPIシグネチャに合わせています。

## 1. Registry DSL
入口:
- `com.yourname.yourmod.api.libs.Registry`

利用可能メソッド:
- `Registry.block(String id)` -> `BlockBuilder`
- `Registry.item(String id)` -> `ItemBuilder`
- `Registry.entity(String id, Supplier<T> factory)` -> `EntityBuilder<T>`
- `Registry.blockEntity(String id, Supplier<T> factory)` -> `BlockEntityBuilder<T>`

### BlockBuilder
メソッド:
- `template(Object)`
- `strength(float)`
- `noOcclusion()`
- `build()` -> `Object`

ショートテンプレ:
```java
Object block = Registry.block("sample_block")
        .template(new Object())
        .strength(3.0f)
        .noOcclusion()
        .build();
```

### ItemBuilder
メソッド:
- `stack(int)`
- `tab(Object)`
- `durability(int)`
- `template(Object)`
- `build()` -> `Object`

ショートテンプレ:
```java
Object item = Registry.item("sample_item")
        .template(new Object())
        .stack(64)
        .durability(120)
        .build();
```

### EntityBuilder
メソッド:
- `category(Object)`
- `size(float width, float height)`
- `build()` -> `T`

ショートテンプレ:
```java
Object entity = Registry.entity("sample_entity", Object::new)
        .category("creature")
        .size(0.8f, 1.6f)
        .build();
```

### BlockEntityBuilder
メソッド:
- `blocks(Object... blocks)`
- `build()` -> `T`

ショートテンプレ:
```java
Object blockEntity = Registry.blockEntity("sample_be", Object::new)
        .blocks(block)
        .build();
```

## 2. Event DSL
入口:
- `com.yourname.yourmod.api.libs.Events`

利用可能メソッド:
- `Events.on(Class<T>)`
- `Events.playerJoin()`

ビルダーメソッド:
- `priority(EventPriority)`
- `async()`
- `sync()`
- `handle(Consumer<T>)`

ショートテンプレ:
```java
Events.playerJoin()
        .priority(EventPriority.NORMAL)
        .handle(event -> {
            Object player = event.player;
            System.out.println("joined: " + player);
        });
```

## 3. Client DSL
入口:
- `com.yourname.yourmod.api.libs.Client`

ビルダー:
- `renders()` -> `RenderDSL`
- `keybinds()` -> `KeybindDSL`
- `screens()` -> `ScreenDSL`
- `hud()` -> `HudDSL`

各サブDSLの現行メソッド:
- `registerAll()`

ショートテンプレ:
```java
Client.init(client -> {
    client.renders().registerAll();
    client.keybinds().registerAll();
    client.screens().registerAll();
    client.hud().registerAll();
});
```

補足:
- `Client.init(...)` 内で `builder.registerAll()` は自動実行されます。

## 4. Datagen DSL
入口:
- `com.yourname.yourmod.api.libs.datagen.DataGen`

利用可能メソッド:
- `DataGen.block(String)` -> `BlockGen`
- `DataGen.item(String)` -> `ItemGen`
- `DataGen.entity(String)` -> `EntityGen`

共通確定メソッド:
- `end()`

ショートテンプレ:
```java
DataGen.block("sample_block").end();
DataGen.item("sample_item").lang("Sample Item").end();
DataGen.entity("sample_entity").lang("Sample Entity").end();
```

## 5. Packet DSL
入口:
- `com.yourname.yourmod.api.libs.packet.Packet`

主要メソッド:
- `Packet.define(String id)`
- `serverbound()` / `clientbound()`
- `codec(...)`
- `handle(...)`
- `register()`
- `sendToServer(T)`
- `sendToPlayer(Object, T)`

ショートテンプレ:
```java
Packet<String> ping = Packet.<String>define("ping")
        .serverbound()
        .codec(buf -> "ping", (packet, buf) -> {})
        .handle((packet, ctx) -> System.out.println(packet));

ping.register();
ping.sendToServer("hello");
```

## 6. 共通イベント補助
`CommonEvents.onPlayerJoin(Object player)` は `PlayerJoinEvent` を `ModEventBus` に投稿します。
共通イベント処理はこの経路を使います。
