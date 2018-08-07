package com.didispace.scca.rest.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anoyi on 2018/8/1.
 * <p>
 * Blog: https://anoyi.com/
 * Github: https://github.com/ChineseSilence
 */
public enum UserRoleEnum {

    ADMIN(1, "ADMIN"),
    DEVELOPER(2, "DEVELOPER");

    private Integer key;

    private String value;

    private static Map<Integer, String> enumMap = new HashMap<>();

    static {
        for (UserRoleEnum userRoleEnum : UserRoleEnum.values()) {
            enumMap.put(userRoleEnum.key, userRoleEnum.value);
        }
    }

    UserRoleEnum(Integer key, String value){
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static String getValue(Integer key){
        String value = enumMap.get(key);
        return value != null ? value : "";
    }

}
