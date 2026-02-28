package com.yuyuto.infinitymaxapi.api.datagen;

import com.yuyuto.infinitymaxapi.api.libs.datagen.BlockGen;
import com.yuyuto.infinitymaxapi.api.libs.datagen.EntityGen;
import com.yuyuto.infinitymaxapi.api.libs.datagen.ItemGen;

public interface DataGen {

    BlockGen block(String id);
    ItemGen item(String id);
    EntityGen entity(String id);
}
