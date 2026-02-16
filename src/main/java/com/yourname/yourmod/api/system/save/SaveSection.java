package com.yourname.yourmod.api.system.save;

/**
 * 型安全なセーブセクション。
 */
public interface SaveSection<T> {

    T save();

    void load(T data);

    Class<T> type();
}