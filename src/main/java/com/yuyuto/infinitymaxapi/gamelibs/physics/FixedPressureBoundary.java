package com.yuyuto.infinitymaxapi.gamelibs.physics;

/**
 * <h2>FixedPressureBoundary（固定圧力境界条件）</h2>
 *
 * <p>
 * 流体シミュレーションなどで使われる「境界条件」の一種です。
 * </p>
 *
 * <h3>境界条件とは？</h3>
 *
 * 箱の中に空気があるとします。
 *
 * 箱の壁のところでは
 * ・圧力はどうなる？
 * ・温度はどうなる？
 *
 * という「ルール」が必要です。
 *
 * それを決めるのが境界条件です。
 *
 * <h3>このクラスの役割</h3>
 *
 * 壁の圧力を常に一定値に固定します。
 *
 * 例:
 * ・大気圧（101325 Pa）
 * ・外部とつながっている容器
 *
 * <h3>物理的意味</h3>
 *
 * 圧力 P は
 *
 *     P = F / A
 *
 * （面積あたりの力）
 *
 * この境界では、その値を強制的に指定します。
 *
 * 内部状態の他の値（温度や密度）は維持されます。
 */
public final class FixedPressureBoundary implements BoundaryCondition {

    /** 固定する圧力値 */
    private final Pressure fixedPressure;

    /**
     * @param fixedPressure 境界に適用する圧力
     */
    public FixedPressureBoundary(Pressure fixedPressure) {
        this.fixedPressure = fixedPressure;
    }

    /**
     * 境界条件を適用する。
     *
     * @param internal 内部セルの物理状態
     * @param external 外部セルの物理状態（未使用）
     *
     * @return 境界適用後の新しい状態
     */
    @Override
    public PhysicalState apply(PhysicalState internal,
                               PhysicalState external) {

        /*
         * 重要：
         * 圧力のみを固定する。
         * 他の物理量は内部値を維持する。
         *
         * これは「開放端」や「大気接続」モデルに近い。
         */

        return new PhysicalState(
                internal.getTemperature(),
                fixedPressure,              // ← 圧力だけ固定
                internal.getDensity(),
                internal.getInternalEnergy(),
                internal.getPhase(),
                internal.getMass()
        );
    }
}