# Forgeの動き

1.Minecraft起動
2.Forge起動
3.ForgeがMODを探す
4.@MODクラスを見つける
5.MODのコードを実行
6.Registryイベント発生
7.Block/Item/Entityなどを登録
8.ゲーム開始
→MODは「登録情報を流すだけ」
# MODの基本構造
基本はこうなる↓
```
ExampleMOD
→ModBlocks
→ModItems
→ModEntities
```
入口
```java
@Mod("Mod_id(String)")
public class Mod_Name{
    public ExampleMod(){
        ModBlocks.register();
        ModItems.register();
        ModEntities.register();
    }
}
```