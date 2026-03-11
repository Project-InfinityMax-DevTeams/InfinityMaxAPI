# Logicの書き方

ここではLogicの書き方について説明します。
InfinityMaxでは、APIが統一したIDを使用して、依存MODの全ロジックをIDとPayload(実行情報)を引き渡すだけで使用できるようになります。
例えば、MOD_Aが`Explode Logic`を実装し、これを他のAPI依存MODも使用したいとなった場合、他の依存MODはこのように書いて呼び出すことができます。
```java
LogicManager.run("explode", context);
```
DSLでゲーム要素(ブロック・アイテム・エンティティなど)に紐づける際には**実行関数そのもの**を記述してください。
```kotlin notebook
on.Phase(INTERACT){
    logic(Explotion()),
    meta("redius",5)
}
```
