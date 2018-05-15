package com.didispace.scca.service.discovery.consul;

import com.didispace.scca.core.config.SccaBaseConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by 程序猿DD/翟永超 on 2018/5/15.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({SccaConsulConfiguration.class, SccaBaseConfiguration.class})
public @interface EnableSccaDiscoveryConsul {
}
