package com.didispace.scca.core.config;

import com.didispace.scca.core.service.BaseOptService;
import com.didispace.scca.core.service.PropertiesService;
import com.didispace.scca.core.service.UrlMakerService;
import com.didispace.scca.core.service.impl.BaseOptServiceImpl;
import com.didispace.scca.core.service.impl.BaseUrlMaker;
import com.didispace.scca.core.service.impl.PropertiesServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by 程序猿DD/翟永超 on 2018/5/2.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Configuration
@EnableConfigurationProperties(SccaProperties.class)
@EntityScan("com.didispace.scca.core.domain")
@EnableJpaRepositories(basePackages = "com.didispace.scca.core.domain")
public class SccaBaseConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public BaseOptService optService() {
        return new BaseOptServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public PropertiesService propertiesService() {
        return new PropertiesServiceImpl();
    }

    /**
     * 不使用基于服务的配置中心配置
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnMissingClass({
            "com.didispace.scca.service.discovery.consul.UrlMaker4Consul",
            "com.didispace.scca.service.discovery.eureka.UrlMaker4Eureka"
    })
    public UrlMakerService urlMakerService() {
        return new BaseUrlMaker();
    }

}
