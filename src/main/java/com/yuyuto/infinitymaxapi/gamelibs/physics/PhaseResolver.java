package com.yuyuto.infinitymaxapi.gamelibs.physics;

/**
 * <h2>PhaseResolver</h2>
 *
 * 物質に基づいて相を決定する。
 */
public final class PhaseResolver {

    private PhaseResolver() {}

    public static Phase resolve(Material material,
                                Temperature temperature) {

        return material.resolvePhase(
                temperature.getSI()
        );
    }
}