package com.yuyuto.infinitymaxapi.gamelibs.data;

/**
 * PIMXのOwner識別子。
 * Scopeに応じた識別情報を保持する。
 *
 * @param identifier UUIDやDimID+Posなど
 */
public record PIMXOwner(PIMXScope scope, String identifier) {
}
