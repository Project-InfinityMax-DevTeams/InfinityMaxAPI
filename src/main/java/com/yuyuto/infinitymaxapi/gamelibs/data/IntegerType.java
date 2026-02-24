package com.yuyuto.infinitymaxapi.gamelibs.data;

public class IntegerType implements PIMXType<Integer> {

    @Override
    public boolean isValid(Object value) {
        return value instanceof Integer;
    }

    @Override
    public Object serialize(Integer value) {
        return value; // JSONではそのまま数値
    }

    @Override
    public String getId() {
        return "integer";
    }

    @Override
    public Integer deserialize(Object raw) {
        if (raw instanceof Number number) {
            return number.intValue();
        }
        throw new PIMXException("Cannot deserialize to Integer");
    }

    @Override
    public Integer cast(Object value) {
        return (Integer) value;
    }
}