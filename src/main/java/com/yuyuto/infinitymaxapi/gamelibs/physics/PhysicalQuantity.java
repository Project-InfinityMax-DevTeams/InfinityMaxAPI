package com.yuyuto.infinitymaxapi.gamelibs.physics;

/**
 * <h2>PhysicalQuantity</h2>
 * 
 * すべての物理量の基底クラス。
 * 
 * SI単位で値を保持し、次元（Dimension）情報を付与する。
 *
 * 小学生向け：
 * 「長さや重さや温度など、数字にラベルをつけたもの」
 */
public abstract class PhysicalQuantity {

    /** SI単位で保持される値 */
    protected final double siValue;

    /** この物理量の次元（M,L,T…） */
    protected final Dimension dimension;

    /**
     * 単位付きコンストラクタ
     * @param value ユーザー指定の値
     * @param unit ユニット情報
     */
    protected PhysicalQuantity(double value, Unit unit) {
        this.siValue = unit.toSI(value); // SI単位に変換
        this.dimension = unit.getDimension();
    }

    /**
     * SI単位直接指定コンストラクタ
     * @param siValue SI単位値
     * @param dimension 次元情報
     */
    protected PhysicalQuantity(double siValue, Dimension dimension) {
        this.siValue = siValue;
        this.dimension = dimension;
    }

    /** SI単位の値を取得 */
    public double getSI() {
        return siValue;
    }

    /** 次元情報を取得 */
    public Dimension getDimension() {
        return dimension;
    }

    /**
     * 次元の一致チェック
     * @param other 比較対象
     * @throws IllegalArgumentException 次元が違う場合
     */
    protected void assertSameDimension(PhysicalQuantity other) {
        if (!this.dimension.equals(other.dimension)) {
            throw new IllegalArgumentException("Dimension mismatch");
        }
    }
}