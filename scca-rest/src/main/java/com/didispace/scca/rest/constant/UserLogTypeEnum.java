package com.didispace.scca.rest.constant;

/**
 * Created by Anoyi on 2018/8/14.
 * <p>
 * Blog: https://anoyi.com/
 * Github: https://github.com/ChineseSilence
 */
public enum  UserLogTypeEnum {

    LOGIN(10);

    private int code;

    UserLogTypeEnum(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
