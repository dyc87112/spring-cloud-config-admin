package com.didispace.scca.service.persistence.git;

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
public class SccaGitProperties {

    /**
     * Git登录的用户名
     */
    private String username;

    /**
     * Git登录的密码
     */
    private String password;

    /**
     * Git配置仓库的，与config-server中配置的Git仓库对应
     *
     * 比如：https://github.com/dyc87112/{application}.git
     */
    private String repoUri;

    /**
     * Git配置仓库下的相对路径，与config-server中配置的Git仓库下的相对路径对应
     */
    private String basePath = "";

    /**
     * Git存储的配置文件名称规则，比如：
     *
     * - application-{profile}.properties
     * - application-{profile}.yml
     * - {application}-{profile}.properties
     *
     * 根据实际Git中存储的规则来配置，默认采用"application-{profile}.properties"规则
     *
     */
    private String filePattern = "application-{profile}.properties";

}
