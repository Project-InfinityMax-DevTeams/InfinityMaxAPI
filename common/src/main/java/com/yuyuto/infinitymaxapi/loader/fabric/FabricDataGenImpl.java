package com.yuyuto.infinitymaxapi.loader.fabric;

import com.yuyuto.infinitymaxapi.api.libs.datagen.BlockGen;
import com.yuyuto.infinitymaxapi.api.libs.datagen.DataGen;
import com.yuyuto.infinitymaxapi.api.libs.datagen.EntityGen;
import com.yuyuto.infinitymaxapi.api.libs.datagen.ItemGen;

public final class FabricDataGenImpl implements com.yuyuto.infinitymaxapi.api.datagen.DataGen {

    @Override
    public BlockGen block(String id) {
        return DataGen.block(id);
    }

    @Override
    public ItemGen item(String id) {
        return DataGen.item(id);
    }

    @Override
    public EntityGen entity(String id) {
        return DataGen.entity(id);
    }
}
