package com.didispace.scca.plugin.db;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by 程序猿DD/翟永超 on 2018/5/2.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Configuration
@PropertySource("scca-plugin-config-server-db.properties")
@EntityScan("com.didispace.scca.plugin.db")
@EnableJpaRepositories(basePackages = "com.didispace.scca.plugin.db")
@ComponentScan
public class SccaPluginDbConfiguration {


}
