# Logicの書き方

ここではLogicの書き方について説明します。
InfinityMaxでは、APIが統一したIDを使用して、依存MODの全ロジックをIDとPayload(実行情報)を引き渡すだけで使用できるようになります。
例えば、MOD_Aが`Explode Logic`を実装し、これを他のAPI依存MODも使用したいとなった場合、他の依存MODはこのように書いて呼び出すことができます。
```java
LogicManager.run("explode", context);
```
他にも、DSLでゲーム要素(ブロック・アイテム・エンティティなど)に紐づける際にはこのように書くことができます。
```kotlin notebook
behavior("break", "explode")
```
DSLはKotlinで記述するため、Behavior登録時にはこの記述を行ってください。
この書き方で、Definitionは以下のように解釈します。
```java
event = break;
logicId = explode;
```
これでBehaviorManagerは以下の構文で動作します。
```java
LogicManager.run("explode");
```