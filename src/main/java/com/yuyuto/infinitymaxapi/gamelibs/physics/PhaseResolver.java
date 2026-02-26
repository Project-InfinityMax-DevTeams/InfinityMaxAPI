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

    private PhaseResolver() {}

    public static Phase resolve(Material material,
                                Temperature temperature) {

        return material.resolvePhase(
                temperature.getSI()
        );
    }

    /**
     * 既存コード互換向け。
     * 材質未指定時はデフォルト材質（簡易的に水相当）で判定する。
     */
    public static Phase resolve(Temperature temperature) {
        return resolve(DEFAULT_MATERIAL, temperature);
    }
}
