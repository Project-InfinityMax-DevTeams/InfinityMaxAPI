package com.yuyuto.infinitymaxapi.gamelibs.event;

/**
 * イベントリスナーインターフェース
 */
@FunctionalInterface
public interface InfinityEventListener<T extends InfinityEvent> {

    void handle(T event);

}