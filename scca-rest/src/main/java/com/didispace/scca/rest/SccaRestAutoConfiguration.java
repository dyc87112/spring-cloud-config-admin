package com.didispace.scca.rest;

import com.didispace.scca.rest.config.SccaErrorAttributes;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by 程序猿DD/翟永超 on 2018/6/23.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@ComponentScan
@Configuration
@PropertySource("scca-rest.properties")
public class SccaRestAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ErrorAttributes errorAttributes() {
        return new SccaErrorAttributes();
    }

}
