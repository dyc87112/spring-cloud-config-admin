package com.didispace.scca.service.discovery.consul;

import com.didispace.scca.core.domain.Env;
import com.didispace.scca.core.service.impl.BaseUrlMaker;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.health.model.HealthService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by 程序猿DD/翟永超 on 2018/4/24.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Slf4j
public class UrlMaker4Consul extends BaseUrlMaker {

    @Override
    public String configServerBaseUrl(String envName) {
        Env env = envRepo.findByName(envName);

        ConsulClient consulClient = new ConsulClient(env.getRegistryAddress());
        Response<List<HealthService>> response = consulClient.getHealthServices(env.getConfigServerName(), true, null);
        List<HealthService> configServerList = response.getValue();
        HealthService healthService = configServerList.get(0);

        String ip = healthService.getService().getAddress();
        String port = healthService.getService().getPort().toString();
        return "http://" + ip + ":" + port;
    }

}
