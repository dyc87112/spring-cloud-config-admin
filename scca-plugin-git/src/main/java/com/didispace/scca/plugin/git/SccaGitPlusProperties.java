package com.didispace.scca.plugin.git;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by 程序猿DD/翟永超 on 2018/4/24.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Data
@ConfigurationProperties(prefix = "scca.git")
public class SccaGitPlusProperties {

    /**
     * Git存储的配置文件名称规则，可以配置多个规则（用逗号分割），比如：
     * <p>
     * - application-{profile}.properties,application-{profile}.yml
     * - {application}-{profile}.properties,{application}-{profile}.yml
     * <p>
     * 根据实际Git中存储的规则来配置，默认采用"application-{profile}.properties,application-{profile}.yaml,application-{profile}.yml"规则
     */
    private String filePattern = "application-{profile}.properties,application-{profile}.yaml,application-{profile}.yml";

    /**
     * Git存储的文件编码，仅适用于YAML格式，默认使用UTF-8
     */
    private String fileCharset = "UTF-8";

}
