package com.yuyuto.infinitymaxapi.api.libs.internal;

public final class ClientBuilder {

    public RenderDSL renders() { return new RenderDSL(); }
    public KeybindDSL keybinds() { return new KeybindDSL(); }
    public ScreenDSL screens() { return new ScreenDSL(); }
    public HudDSL hud() { return new HudDSL(); }

    public void registerAll() {
    }

    public static final class RenderDSL {
        public void registerAll() {}
    }

    public static final class KeybindDSL {
        public void registerAll() {}
    }

    public static final class ScreenDSL {
        public void registerAll() {}
    }

    public static final class HudDSL {
        public void registerAll() {}
    }
}
