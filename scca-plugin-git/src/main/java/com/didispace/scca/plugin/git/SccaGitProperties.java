package com.didispace.scca.plugin.git;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by 程序猿DD/翟永超 on 2018/4/24.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Data
@ConfigurationProperties
public class SccaGitProperties {

    /**
     * Git登录的用户名
     */
    @Value("${spring.cloud.config.server.git.username:}")
    private String username;

    /**
     * Git登录的密码
     */
    @Value("${spring.cloud.config.server.git.password:}")
    private String password;

    /**
     * Git配置仓库的，与config-server中配置的Git仓库对应
     * <p>
     * 比如：https://github.com/dyc87112/{application}.git
     */
    @Value("${spring.cloud.config.server.git.uri:}")
    private String repoUri;

    /**
     * Git配置仓库下的相对路径，与config-server中配置的Git仓库下的相对路径对应
     */
    @Value("${spring.cloud.config.server.git.search-paths:}")
    private String searchPaths;

}
