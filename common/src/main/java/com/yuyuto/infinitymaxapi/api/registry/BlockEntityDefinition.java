package com.yuyuto.infinitymaxapi.api.registry;

import java.util.List;

public record BlockEntityDefinition(
        String id,
        List<BehaviorDefinition> behaviors,
        Supplier<BlockEntityRendererFactory<?>> renderer
) {}