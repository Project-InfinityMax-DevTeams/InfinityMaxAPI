# About This Template

## What this MDK is
InfinityMaxMOD-Template is a multi-loader MDK.
One shared codebase can build both Forge and Fabric artifacts.
This template is designed for developers who want to maintain both Forge and Fabric from a single codebase.

## What is MDK
MDK (Mod Development Kit) is a starter development project for building Minecraft mods.

## Main design
This template is split into two layers:
- Common layer (`api` and shared logic): pure Java, platform-neutral
- Loader layer (`loader/Forge`, `loader/Fabric`): platform-specific adaptation

The common layer should not directly depend on Minecraft loader classes.
If common code imports loader classes, the build will no longer be platform-neutral and dual compilation will fail.
So, Use `Object` or wrapper interfaces at API boundaries.For the wrapper interface, refer to ``.
※The reason we're doing this is to prevent the loader's specific type from leaking into shared code by intentionally using Object at the boundary.

As a mod developer, you usually only write code inside the common layer.

## Why this design exists
Benefits:
- One gameplay/business logic codebase
- Less duplication between Forge and Fabric
- Faster maintenance when updating features

Tradeoff:
- You must keep strict boundaries between common and loader code

## Role Description for Each Step
When building with this MDK, it operates as follows:
1. Launching the loader entry point (`ForgeEntrypoint` / `FabricEntrypoint`)
   → This is the sole entry point for Forge and Fabric to recognize the MOD
   → The common code is not yet running
2. Set the running platform bridge via `Platform.set(...)`
  → Notifies the common layer about the running loader
  → Only after this does the common API finally start working
3. Register network events
  → Phase connecting Forge/Fabric's event systems to the common DSL
4. Execute `YourMod.init()`
  → Executes the code you wrote
5. Delegate common API/DSL calls to loader implementation
  → The loader implementation is called within `Registry.block()`

## Where to Store Your Code
src/main/java/com/yourname/yourmod/
⇒api/
┗ Here, you implement the conversion functionality that allows the Loader to load your code.
⇒loader/
┗This section handles differences between Forge and Fabric specifications, enabling simultaneous builds.
**DO NOT TOUCH ANYTHING LISTED HERE!**
Changing it will cause bugs and break your code.

## Mandatory Rules
1. Do not directly import `net.minecraft.*` in the common API layer
⇒ This is because, from an abstraction standpoint, the design ensures that even if you try to import it in your code, it won't be found. That's the reason.
2. Keep shared signatures as non-dependent types (`Object` or wrappers)
→ Dependent types will immediately fail compilation. If you make them dependent, they can't be shared.
3. Perform type conversions only within loader implementations
→ Since type conversion is already implemented, you don't need to implement it. All you need to do is describe your MOD's behavior, register elements, and handle logic processing. And resources, of course.
4. Always verify both loaders when making changes
→ If one works but the other breaks, that indicates an issue with MDK development. Please report such problems on [Github](https://github.com/Project-InfinityMax-DevTeams/InfinityMaxMOD-Template/issues).

## Build Commands
```bash
gradlew :forge:compileJava 
gradlew :fabric:compileJava
gradlew clean build
```
The commands above compile separately for each platform.
Specifying just forge or just fabric as shown generates a jar file.
The bottom command performs simultaneous generation and verification! In other words, it's the build command.

## Developer Workspace and Code Notes
First, replace “yourname” and “yourmod” with your actual name and MOD name.
**These are placeholder names. While functional, we strongly recommend changing them personally.**
Change not only folder names but also package declarations and import statements within `api/` and `loader/`.

Developers should handle common MOD logic, such as adding blocks, MOB AI processing, and BlockEntity registration, in the following location:
`src/main/java/com/yourname/yourmod/logics`
Replace `yourname` and `yourmod` with your name and MOD name.
Below are two examples of good and bad code practices for this location.
### Bad Example 1: Using a Minecraft-style pattern in the common layer
```java
package com.example.mymod;

import net.minecraft.world.entity.player.Player;
import com.yourname.yourmod.api.libs.Events;

public final class BadFeature {

    public static void init() {
        Events.playerJoin().handle(event -> {
            Player player = (Player) event.player;
            System.out.println(player.getName().getString());
        });
    }
}

```
Doing this will cause an error because the Minecraft type isn't loaded in the common layer.
Forge might work, but Fabric will definitely break.
Please import and use everything as `com.{yourname}.{yourmod}.api.libs.desired_system`.
If you're unsure what to use, [this guide](A) explains it.
### Bad Example 2: Writing Logic Assuming a Loader
```java
package com.example.mymod;

import com.yourname.yourmod.loader.Forge.SomeForgeOnlyClass;

public final class BadFeature2 {

    public static void init() {
        SomeForgeOnlyClass.doSomething();
    }
}
```
This is also OUT.
Because it directly depends on the loader implementation from the common code, the result is:
・Certain compilation errors in the Fabric build
・The multi-loader design collapses
This is because the dependency direction is reversed.
### Good Example 1: Pure Common Logic
```java
package com.example.mymod.feature;

import com.yourname.yourmod.api.libs.Events;

public final class GoodFeature {

    private GoodFeature() {}

    public static void init() {
        Events.playerJoin().handle(event -> {
            Object player = event.player;
            System.out.println("Player joined: " + player);
        });
    }
}
```
### Good Example 2: Common Logic + Abstract Separation
```java
package com.example.mymod.feature;

import com.yourname.yourmod.api.libs.Registry;

public final class GoodBlockFeature {

    private static final Object BLOCK = Registry.block("good_block")
            .template(new Object())
            .strength(2.0f)
            .build();

    private GoodBlockFeature() {}
}

```

## Common Mistakes
- Importing Minecraft classes into the common API
  Symptom: Missing class error during Fabric compile
  Cause: Forge types are leaking
  Fix: Move to the loader layer
- Writing Forge implementations in the Fabric side (and vice versa)
  Symptom: Types become messy, causing Gradle to fail
  Cause: Fabric code written in Forge implementation
  Fix: Strictly place Forge code in Forge's designated location
- Writing code assuming loader-specific objects exist in the common layer
  Symptom: Massive “class errors”
  Cause: Placing loader-specific code in the common layer
  Fix: Place files in Forge's location or Fabric's dedicated directory, not the common root, and write code there.
- Skipping compilation checks for both loaders
  Symptom: Potential to destroy Minecraft with unknown behavior
  Cause: Insufficient testing
  Fix: Don't slack off—do your testing!

---

# このテンプレートについて

## このMDKの役割
InfinityMaxMOD-Template はマルチローダー対応MDKです。
1つの共通コードベースから Forge と Fabric の両方をビルドできます。
このテンプレートは、共通コードからForgeとFabricの両方でMODを同時リリースしたい開発者向けに設計されています。

## MDKとはなんぞや？
MDK(Mod Development Kit)は、マイクラのMODを開発するための環境をまとめたものです。kitによって種類や書き方が様々です。

## 基本設計
このテンプレートは2層構造です。
- 共通層（`api` と共通ロジック）: 純Java、プラットフォーム非依存
- ローダー層（`loader/Forge`, `loader/Fabric`）: プラットフォーム固有の適応処理

共通レイヤーはMinecraftローダークラスに直接依存すべきではありません。
共通コードがローダークラスをインポートすると、ビルドはプラットフォーム中立性を失い、同時ビルドが失敗します。
したがって、API境界では`Object`またはラッパーインターフェースを使用してください。ラッパーインタフェースについては``を参照してください。
※どうしてこんなことをしてるかと言うと、境界で意図的にObjectを使用することで、ローダー固有の型が共有コードに漏れるのを防いでいるためです。

Mod開発者として、通常は共通レイヤー内部でのみコードを記述します。

## この設計の目的
メリット:
- ゲームロジックを1つのコードベースに集約できる
- Forge/Fabric間の重複を減らせる
- 機能更新時の保守が速い
その代わり、層の分離を厳密に管理しないといけません。

## 各ステップの役割説明
このMDKでビルドしたとき、
1. ローダーのエントリポイント起動（`ForgeEntrypoint` / `FabricEntrypoint`）
  →ここがForgeとFabricがMODを認識する唯一の窓口
  →まだ共通コードは動いてない
2. `Platform.set(...)` で実行中のプラットフォームブリッジを設定
  →実行中のローダーを共通層に知らせる役割をする
  →これをしてやっとこさ共通APIが動くことになる
3. ネットワーク・イベントを登録
  →Forge/Fabricのイベントシステムを共通DSLに接続してるフェーズ
4. `YourMod.init()` 実行
  →あなた達の書いたコードの実行
5. 共通API/DSL呼び出しをローダー実装へ委譲
  → Registry.block() の中で loader 実装が呼ばれる
というふうに動いてます。

## コードの保管場所について
src/main/java/com/yourname/yourmod/
⇒api/
┗ここでは、あなたたちのコードをLoader側に読み込ませる変換機能を実装してます。
⇒loader/
┗ここはForgeとFabricの仕様差分を吸収し、同時にビルドできるようにした部分です。
**ここに羅列したものは絶対さわらないでください！**
書いたコードがバグって壊れちゃうのでダメです。

## 必須ルール
1. 共通API層で `net.minecraft.*` を直接 import しない
⇒既に抽象化している観点から、コード上としてはimportしても見つからない設計にしています。そのためです。
2. 共有シグネチャは非依存型（`Object` かラッパー）で保つ
→依存型はすぐにコンパイル通らなくなります。依存させたら共通化できないからですね。
3. 型変換はローダー実装側だけで行う
→既に型変換は実装してるので、あなた達が実装する必要性はないです。必要なのはMODの動き・要素登録・ロジック処理記述だけです。それにリソースも。
4. 変更時は両ローダーを必ず検証する
→一方が動いても、もう一方で壊れてた！と言った場合、それはこのMDKの開発問題です。そのため、[Github](https://github.com/Project-InfinityMax-DevTeams/InfinityMaxMOD-Template/issues)にて問題を報告してください。

## ビルドの時のコマンド
```bash
gradlew :forge:compileJava 
gradlew :fabric:compileJava
gradlew clean build
```
上のコマンドはそれぞれのプラットフォームでの分離でコンパイルする時です。
上記のようにforgeだけ・fabricだけと指定してあげればjarファイルは生成されます。
そして一番下は同時生成と検証をしてくれるものです！つまりビルドコマンドです。

## 開発者の開発場所とコードの諸注意
最初に、yournameとyourmodの名前をあなたの名前とあなたのMOD名にしてください。
**この名前は仮の名前です。動きますが絶対に個人で変えておくことをおすすめします。**
フォルダー名だけではなく、api/及びloader/の中も同様にpackage文やimport文を変えてください。

開発者は、以下の場所で共通で使うMODの処理、例えばブロックの追加、MOBのAI処理、BlockEntity登録を行うようにしてください。
`src/main/java/com/yourname/yourmod/logics`
yournameとyourmodにはあなたの名前とMODの名前が入ります。
以下より、その場所で書くコードの良い例と悪い例を2つ紹介します。
### 悪い例1:共通層でMinecraft型を使う
```java
package com.example.mymod;

import net.minecraft.world.entity.player.Player;
import com.yourname.yourmod.api.libs.Events;

public final class BadFeature {

    public static void init() {
        Events.playerJoin().handle(event -> {
            Player player = (Player) event.player;
            System.out.println(player.getName().getString());
        });
    }
}
```
これをしてしまうとErrorが出てしまいます。共通層ではminecraft型が読み込まれないからです。
Forgeは通るかもですが、Fabricは確実に壊れます。
すべて`com.{yourname}.{yourmod}.api.libs.任意の使用したいシステム`でimportして使ってください。
何を使えばいいのかわからない場合は[こちら](A)でご案内してます。
### 悪い例②：ローダー前提のロジックを書く
```java
package com.example.mymod;

import com.yourname.yourmod.loader.Forge.SomeForgeOnlyClass;

public final class BadFeature2 {

    public static void init() {
        SomeForgeOnlyClass.doSomething();
    }
}
```
これもOUTです。
共通コードから loader 実装に直接依存しているので、結果として
・Fabricビルドで確実にコンパイルエラー
・マルチローダー設計が崩壊
という結末を迎えます。
依存方向が逆転しているためです。
### 良い例1:純粋共通ロジック
```java
package com.example.mymod.feature;

import com.yourname.yourmod.api.libs.Events;

public final class GoodFeature {

    private GoodFeature() {}

    public static void init() {
        Events.playerJoin().handle(event -> {
            Object player = event.player;
            System.out.println("Player joined: " + player);
        });
    }
}
```
### 良い例2:共通ロジック＋抽象分離
```java
package com.example.mymod.feature;

import com.yourname.yourmod.api.libs.Registry;

public final class GoodBlockFeature {

    private static final Object BLOCK = Registry.block("good_block")
            .template(new Object())
            .strength(2.0f)
            .build();

    private GoodBlockFeature() {}
}
```

## よくある失敗
- 共通APIに Minecraft クラスを import してしまう
  症状：Fabric compile で missing class
  原因：Forge型が漏れている
  修正：loader層に移動
- Forge実装を Fabric 側に書く（逆も同様）
  症状：型がぐちゃぐちゃになり、Gradleがストライキを起こす
  原因：Forge実装にFabricを記述したため
  修正：しっかりとForgeはForgeの場所で記述する
- 共通層でローダー専用オブジェクトがある前提で書く
  症状：「クラスエラー」が大量に出る
  原因：共通層の部分にローダー専門の場所を持ってくる
  修正：共通ルートではなく、Forgeの場所やFabricの専用ディレクトリにファイルを置いて、そこに記述する。
- 両ローダーのコンパイル確認を省略する
  症状：不明な挙動でマイクラを破壊する可能性
  原因：テスト不足
  修正：サボらないでテストやろう！
