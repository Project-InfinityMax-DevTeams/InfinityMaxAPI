package com.yuyuto.infinitymaxapi.api.event;

import java.util.function.Consumer;

public record EventListener<T extends ModEvent>(Consumer<T> consumer, EventPriority priority, boolean async) {

}