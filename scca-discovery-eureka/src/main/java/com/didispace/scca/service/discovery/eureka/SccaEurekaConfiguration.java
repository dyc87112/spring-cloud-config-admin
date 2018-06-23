package com.didispace.scca.service.discovery.eureka;

import com.didispace.scca.core.service.UrlMakerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by 程序猿DD/翟永超 on 2018/5/2.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Configuration
public class SccaEurekaConfiguration {

    @Bean
    public UrlMakerService urlMakerService() {
        return new UrlMaker4Eureka();
    }

}
