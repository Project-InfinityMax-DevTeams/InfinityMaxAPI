# How to write Registry

ここでは、APIを使用したMDKでのゲーム要素の登録の仕方を記述します。
事前にIntelliJ IDEAなどに、InfinityMaxのMDKをセットアップしておいてください。

登録にはKotlinDSLを使用します。
## ブロックの登録方法
ブロックを登録するにはこのように記述します。
```kotlin
registry{
    
    block("bomb", BlockTemplate("minecraft:tnt")){
        
        on(INTERACT){
            logic("explode")
            meta("redius", 5)
        }
        
        on(TICK){
            logic("smoke")
        }
    }
}
```
上記での構文の意味は以下の表の通りです。
|スコープ|意味|型|詳細|
|------|----|--|---|
|registry|登録ブロック|なし|この間の中ではゲーム要素を登録するということ。|
|block|blockの登録|String ID,BlockTemplate|Blockを登録することをAPIに教えるための識別スコープ。IDがないとErrorを吐き出します。Templateは、ブロックの登録に必要な情報を既存のMinecraftに存在するブロックからコピーします。このため、TemplateにはMinecraftのIDを指定してください。|
|hardness|Blockの固さ|float|DataGenに記載する「ブロックの固さ」を記述します。デフォルトは`1f`です。Templateを指定した場合、この数値が優先されます。|
|resistance|Blockが持つ爆発耐性|float|Blockが爆発を受けた際に耐える値です。デフォルトは`1f`です。Templateを指定した場合、この数値が優先されます。|
|on(Phase)|ロジック定義ブロック|Phase|Blockが持つロジックを定義します。Phaseごとに様々なロジックを持つことができます。ここでいうロジックは、MDK利用者が作ったロジックです。|
|logic("LogicID")|ロジックを定義|logicId|MDK使用者が制作したロジックを定義します。logicIdはLogicクラスで定義したものです。|
|meta("type", value)|Logicの引数|"type", value|Logicが必要とする引数を指定します。この引数は、meta関数を下に増やしていくことで、必要数の引数を渡すことができます。|