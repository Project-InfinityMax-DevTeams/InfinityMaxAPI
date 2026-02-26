# Physics API ガイド（やさしめ）

このドキュメントは `gamelibs/physics` の基本的な使い方をまとめたものです。  
Java に不慣れでも使えるように、最小コード例を中心に書いています。

---

## 1. 物理状態 `PhysicalState` とは

`PhysicalState` は以下をまとめた「1セル分の物理状態」です。

- 温度 `Temperature`
- 圧力 `Pressure`
- 密度 `Density`
- 内部エネルギー `Energy`
- 相（固体/液体/気体/プラズマ） `Phase`
- 質量 `Mass`

### 作成例

```java
Temperature temp = new Temperature(300.0, Temperature.KELVIN);
Pressure pressure = new Pressure(101325.0, Pressure.PASCAL);
Density density = new Density(1.2, Density.KG_PER_M3);
Energy energy = new Energy(1000.0, Energy.JOULE);
Mass mass = new Mass(1.0, Mass.KILOGRAM);

PhysicalState state = new PhysicalState(
        temp,
        pressure,
        density,
        energy,
        PhaseResolver.resolve(temp),
        mass
);
```

---

## 2. ジュール熱 `JouleHeating` の使い方

`JouleHeating` は EnergyAPI の `EnergyConnection` を使って、
接続損失（I²R）から発熱量を計算します。

### 推奨（EnergyAPI連携）

```java
EnergyNode a = new EnergyNode();
EnergyNode b = new EnergyNode();
a.setPotential(12.0);
b.setPotential(0.0);

EnergyConnection wire = new EnergyConnection(a, b, 2.0); // 2Ω

JouleHeating heating = new JouleHeating(wire, 4200.0); // 比熱[J/(kg*K)]
PhysicalState next = heating.apply(state, 0.1); // 0.1秒進める
```

### 旧形式（互換）

```java
JouleHeating heating = new JouleHeating(
        2.0,    // 抵抗[Ω]
        3.0,    // 電流[A]
        4200.0  // 比熱[J/(kg*K)]
);
PhysicalState next = heating.apply(state, 0.1);
```

---

## 3. 熱伝導 `HeatTransfer`

`HeatTransfer` はフーリエの法則ベースの簡易モデルです。

```java
HeatTransfer transfer = new HeatTransfer(
        0.6,   // 熱伝導率
        1.0,   // 面積
        0.1,   // 距離
        4200.0 // 比熱
);

PhysicalState cooled = transfer.apply(state, 0.05);
```

---

## 4. 相判定 `PhaseResolver`

材質がある場合：

```java
Material ironLike = new Material(1811.0, 3134.0, 12000.0);
Phase phase = PhaseResolver.resolve(ironLike, temp);
```

材質未指定（簡易判定）：

```java
Phase phase = PhaseResolver.resolve(temp);
```

> ⚠️ 注意: 材質未指定の場合、デフォルト材質（水相当：融点0℃、沸点100℃）で判定されます。

---

## 5. 実装の注意点

- `Energy` は SI 単位ジュール（J）です。
- `PhysicalState` は immutable（更新時は新インスタンスを返す）です。
- 時間刻み `deltaTime` は「秒」で渡してください。
