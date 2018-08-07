package com.didispace.scca.plugin.git;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by 程序猿DD/翟永超 on 2018/5/2.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Configuration
@EnableConfigurationProperties({SccaGitProperties.class, SccaGitPlusProperties.class})
@PropertySource("scca-plugin-git.properties")
@ComponentScan
public class SccaPluginGitConfiguration {


}
