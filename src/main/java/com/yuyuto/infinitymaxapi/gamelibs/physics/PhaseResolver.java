package com.yuyuto.infinitymaxapi.gamelibs.physics;

/**
 * <h2>PhaseResolver</h2>
 *
 * 物質に基づいて相を決定する。
 */
public final class PhaseResolver {
     /**
      * デフォルト材質（水相当）。
      * 融点: 273.15 K (0°C), 沸点: 373.15 K (100°C)
      * 標準気圧下での水を想定した簡易判定用。
      */
    private static final Material DEFAULT_MATERIAL =
            new Material(273.15, 373.15, 10_000.0);

    /**
     * ユーティリティクラスのインスタンス化を防ぐための非公開コンストラクタ。
     *
     * このクラスは全て静的メソッドで提供されるため、外部からのインスタンス生成を許可しません。
     */
    private PhaseResolver() {}

    /**
     * 与えられた物質特性と温度からその物質の相（Phase）を決定する。
     *
     * @param material 相の判定に用いる物質の特性（融点・沸点などを含むMaterial）
     * @param temperature 判定に使用する温度（Temperature）
     * @return 判定された相を表すPhase
     */
    public static Phase resolve(Material material,Temperature temperature) {
        return material.resolvePhase(temperature.getSI());
    }
}
