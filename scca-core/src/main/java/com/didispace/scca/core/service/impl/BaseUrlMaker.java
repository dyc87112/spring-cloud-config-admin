package com.didispace.scca.core.service.impl;

import com.didispace.scca.core.config.SccaProperties;
import com.didispace.scca.core.domain.Env;
import com.didispace.scca.core.domain.EnvRepo;
import com.didispace.scca.core.service.UrlMakerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 程序猿DD/翟永超 on 2018/4/24.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Slf4j
public class BaseUrlMaker implements UrlMakerService {

    @Autowired
    protected EnvRepo envRepo;

    @Autowired
    protected SccaProperties sccaProperties;

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

    @Override
    public String configServerBaseUrl(String envName) {
        Env env = envRepo.findByName(envName);
        if (env.getRegistryAddress() == null || env.getRegistryAddress().isEmpty()) {
            log.debug("config server url : " + env.getConfigServerName());
            return env.getConfigServerName() + env.getContextPath();
        } else {
            log.error("Your env config use registry address, So you should use service discovery plugin and make sure property is not `sccs.config-server-url.enable=true`");
            throw new RuntimeException("Need use service discovery plugin");
        }
    }

    @Override
    public List<String> allConfigServerBaseUrl(String envName) {
        List<String> result = new ArrayList<>();
        result.add(configServerBaseUrl(envName));
        return result;
    }

}
