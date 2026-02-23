package com.yuyuto.infinitymaxapi.gamelibs.data;

public class StringType implements PIMXType<String> {

    @Override
    public boolean isValid(Object value) {
        return value instanceof String;
    }

    @Override
    public Object serialize(String value) {
        return value;
    }

    @Override
    public String deserialize(Object raw) {
        if (raw instanceof String str) {
            return str;
        }
        throw new PIMXException("Cannot deserialize to String");
    }

    @Override
    public String cast(Object value) {
        return (String) value;
    }
}