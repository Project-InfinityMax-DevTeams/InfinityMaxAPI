package com.yuyuto.infinitymaxapi.gamelibs.physics;

/**
 * <h1>BoundaryCondition（境界条件）</h1>
 *
 * <p>
 * 物理シミュレーションでは、
 * 空間の端（壁・真空・外部空間）に特別なルールを適用する必要があります。
 * </p>
 *
 * <h2>なぜ必要？</h2>
 * <ul>
 *     <li>壁にぶつかった空気はどうなる？</li>
 *     <li>宇宙空間なら圧力は？</li>
 *     <li>外部と接続しているなら流出する？</li>
 * </ul>
 *
 * <p>
 * これを決めるのが BoundaryCondition です。
 * </p>
 *
 * <h2>使い方</h2>
 * internalState = セル内部の状態  
 * externalState = 外側の状態（隣接チャンクや仮想空間）
 *
 * それをもとに、新しい内部状態を返します。
 *
 * <h2>例</h2>
 * ・固定圧力壁 → 圧力を強制固定  
 * ・反射壁 → 流体を通さない  
 * ・真空 → 外部圧力ゼロ
 */
public interface BoundaryCondition {

    /**
     * 境界での物理状態を計算する。
     *
     * @param internalState 境界内側のセル状態
     * @param externalState 境界外側の仮想状態
     * @return 境界処理後の新しい内部状態
     */
    PhysicalState apply(PhysicalState internalState,PhysicalState externalState);
}