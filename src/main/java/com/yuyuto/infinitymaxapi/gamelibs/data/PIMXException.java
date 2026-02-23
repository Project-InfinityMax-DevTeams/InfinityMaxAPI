package com.yuyuto.infinitymaxapi.gamelibs.data;

/**
 * PIMX規格違反専用例外
 */
public class PIMXException extends RuntimeException{
    public PIMXException(String message){
        super(message);
    }
}
