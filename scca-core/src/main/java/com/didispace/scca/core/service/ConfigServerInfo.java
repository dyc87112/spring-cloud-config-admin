package com.didispace.scca.core.service;

import lombok.Data;

/**
 * Created by 程序猿DD/翟永超 on 2018/7/9.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Data
public class ConfigServerInfo {

    /**
     * 配置中心操作接口的基础访问url
     */
    private String url;

    /**
     * 配置中心的加密功能状态
     */
    private String encryptStatus;

}
