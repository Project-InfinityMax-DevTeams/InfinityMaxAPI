package com.yuyuto.infinitymaxapi.loader.forge;

import com.yuyuto.infinitymaxapi.api.event.CommonEvents;
import com.yuyuto.infinitymaxapi.loader.LoaderExpectPlatform;

public final class ForgeEventsImpl implements LoaderExpectPlatform.Events {

    @Override
    public void register() {
    }

    @Override
    public void onPlayerJoin(Object player) {
        CommonEvents.onPlayerJoin(player);
    }
}
