package com.yourname.yourmod.loader.fabric;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public final class FabricRegistriesImpl {

    public static <T> T register(Registry<T> registry, String id, T object) {
        return Registry.register(registry, new ResourceLocation("yourmodid", id), object);
    }
}