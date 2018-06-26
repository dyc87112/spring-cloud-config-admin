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
@ConfigurationProperties("scca")
public class SccaProperties {

    /**
     * 默认的label名称
     */
    private String defaultLabel = "master";

}
