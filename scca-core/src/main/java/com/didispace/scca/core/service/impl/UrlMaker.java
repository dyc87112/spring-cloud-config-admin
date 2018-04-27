package com.didispace.scca.core.service.impl;

import com.didispace.scca.core.domain.EnvRepo;
import com.didispace.scca.core.service.UrlMakerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by 程序猿DD/翟永超 on 2018/4/24.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Slf4j
public abstract class UrlMaker implements UrlMakerService {

    @Autowired
    protected EnvRepo envRepo;

    @Override
    public String propertiesLoadUrl(String application, String envName, String label) {
        // http://config-server/{application}/{environmentName}/{label}
        StringBuffer result = new StringBuffer();
        result.append(configServerBaseUrl(envName) + "/" + application + "/" + envName);
        if (label != null) {
            result.append("/" + label);
        }
        log.debug("propertiesLoadUrl : " + result);
        return result.toString();
    }
}
