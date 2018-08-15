package com.didispace.scca.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by 程序猿DD/翟永超 on 2018/5/2.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Data
@ConfigurationProperties("scca.config-server")
public class SccaConfigServerProperties {

    /**
     * 访问配置中心模块的用户名
     */
    private String username;

    /**
     * 访问配置中心模块的密码
     */
    private String password;

}
