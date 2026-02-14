# Custom System Guide

## Goal
A custom system is your own gameplay/business module that is independent from loader internals.
Build systems in common code, then connect them through DSL/events.

Examples:
- Energy system
- Skill system
- Quest system
- Progression system

## Design policy
1. Keep system core in pure Java
2. Keep storage/state structure explicit
3. Connect system to lifecycle/events through shared DSL
4. Use loader layer only for platform-specific access

## Recommended structure
```text
src/main/java/com/example/mymod/
  system/
    energy/
      EnergySystem.java
      EnergyState.java
      EnergyService.java
      EnergyEvents.java
      EnergyClient.java
```

## Step 1: Define core constants
```java
package com.example.mymod.system.energy;

public final class EnergySystem {

    public static final int MAX_ENERGY = 100000;

    private EnergySystem() {}
}
```

## Step 2: Define state model
```java
package com.example.mymod.system.energy;

public final class EnergyState {

    private int value;

    public int get() {
        return value;
    }

    public void set(int next) {
        value = Math.max(0, Math.min(next, EnergySystem.MAX_ENERGY));
    }

    public void add(int delta) {
        set(value + delta);
    }
}
```

## Step 3: Define service logic
```java
package com.example.mymod.system.energy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class EnergyService {

    private static final Map<Object, EnergyState> STATE = new ConcurrentHashMap<>();

    private EnergyService() {}

    public static EnergyState byPlayer(Object player) {
        return STATE.computeIfAbsent(player, key -> new EnergyState());
    }
}
```

## Step 4: Bind system to shared events
```java
package com.example.mymod.system.energy;

import com.yourname.yourmod.api.libs.Events;

public final class EnergyEvents {

    private EnergyEvents() {}

    public static void register() {
        Events.playerJoin().handle(event -> {
            EnergyState state = EnergyService.byPlayer(event.player);
            state.set(100);
        });
    }
}
```

## Step 5: Optional client hook
```java
package com.example.mymod.system.energy;

import com.yourname.yourmod.api.libs.Client;

public final class EnergyClient {

    private EnergyClient() {}

    public static void register() {
        Client.init(client -> {
            client.hud().registerAll();
        });
    }
}
```

## Step 6: Wire into mod init
```java
package com.example.mymod;

import com.example.mymod.system.energy.EnergyEvents;

public final class MyModInit {

    private MyModInit() {}

    public static void init() {
        EnergyEvents.register();
    }
}
```

## Short template for new systems
```java
public final class XSystem {
    public static void init() {
        Events.playerJoin().handle(event -> {
            Object player = event.player;
            // initialize state here
        });
    }
}
```

## Do / Don't
Do:
- Keep system logic testable and pure
- Use `Object` boundary for shared API
- Keep loader-specific conversion isolated

Don't:
- Import Minecraft classes into shared system core
- Mix Forge/Fabric code in one shared class
- Put UI/render assumptions into server-side logic

## Validation
```bash
gradlew :forge:compileJava :fabric:compileJava
gradlew clean build
```

---

# カスタムシステムガイド

## 目的
カスタムシステムは、ローダー内部に依存しない独自のゲーム/業務モジュールです。
共通コードでシステム本体を作り、DSL/イベントで接続します。

例:
- Energy システム
- Skill システム
- Quest システム
- Progression システム

## 設計方針
1. システム本体は純Javaで作る
2. 保存データと状態構造を明確にする
3. ライフサイクル/イベント接続は共有DSL経由にする
4. プラットフォーム固有処理はローダー層に限定する

## 推奨構成
```text
src/main/java/com/example/mymod/
  system/
    energy/
      EnergySystem.java
      EnergyState.java
      EnergyService.java
      EnergyEvents.java
      EnergyClient.java
```

## 手順1: 定数定義
```java
package com.example.mymod.system.energy;

public final class EnergySystem {

    public static final int MAX_ENERGY = 100000;

    private EnergySystem() {}
}
```

## 手順2: 状態モデル定義
```java
package com.example.mymod.system.energy;

public final class EnergyState {

    private int value;

    public int get() {
        return value;
    }

    public void set(int next) {
        value = Math.max(0, Math.min(next, EnergySystem.MAX_ENERGY));
    }

    public void add(int delta) {
        set(value + delta);
    }
}
```

## 手順3: サービスロジック定義
```java
package com.example.mymod.system.energy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class EnergyService {

    private static final Map<Object, EnergyState> STATE = new ConcurrentHashMap<>();

    private EnergyService() {}

    public static EnergyState byPlayer(Object player) {
        return STATE.computeIfAbsent(player, key -> new EnergyState());
    }
}
```

## 手順4: 共有イベントへ接続
```java
package com.example.mymod.system.energy;

import com.yourname.yourmod.api.libs.Events;

public final class EnergyEvents {

    private EnergyEvents() {}

    public static void register() {
        Events.playerJoin().handle(event -> {
            EnergyState state = EnergyService.byPlayer(event.player);
            state.set(100);
        });
    }
}
```

## 手順5: 必要ならクライアント接続
```java
package com.example.mymod.system.energy;

import com.yourname.yourmod.api.libs.Client;

public final class EnergyClient {

    private EnergyClient() {}

    public static void register() {
        Client.init(client -> {
            client.hud().registerAll();
        });
    }
}
```

## 手順6: 初期化へ接続
```java
package com.example.mymod;

import com.example.mymod.system.energy.EnergyEvents;

public final class MyModInit {

    private MyModInit() {}

    public static void init() {
        EnergyEvents.register();
    }
}
```

## 新規システム用ショートテンプレ
```java
public final class XSystem {
    public static void init() {
        Events.playerJoin().handle(event -> {
            Object player = event.player;
            // ここで初期化
        });
    }
}
```

## Do / Don't
Do:
- システムロジックを純粋でテストしやすく保つ
- 共有API境界は `Object` で扱う
- ローダー依存変換を分離する

Don't:
- 共通システム本体に Minecraft 型を import する
- Forge/Fabric を同一共有クラスに混在させる
- サーバーロジックにUI前提を混ぜる

## 検証
```bash
gradlew :forge:compileJava :fabric:compileJava
gradlew clean build
```
