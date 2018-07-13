package com.didispace.scca.service.discovery.consul;

import com.didispace.scca.core.domain.Env;
import com.didispace.scca.core.service.impl.BaseUrlMaker;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.health.model.HealthService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
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

        if (env.getRegistryAddress() == null || env.getRegistryAddress().isEmpty()) {
            // 如果没有配置注册中心，直接取服务名字段（配置中心访问地址）
            return super.configServerBaseUrl(envName);
        }

        ConsulClient consulClient = new ConsulClient(env.getRegistryAddress());
        Response<List<HealthService>> response = consulClient.getHealthServices(env.getConfigServerName(), false, null);
        List<HealthService> configServerList = response.getValue();

        if (configServerList.size() == 0) {
            throw new RuntimeException("No instances : " + env.getConfigServerName());
        }

        HealthService healthService = configServerList.get(0);

        String ip = healthService.getService().getAddress();
        String port = healthService.getService().getPort().toString();
        return "http://" + ip + ":" + port + env.getContextPath();
    }

    @Override
    public List<String> allConfigServerBaseUrl(String envName) {
        List<String> result = new ArrayList<>();

        Env env = envRepo.findByName(envName);

        if (env.getRegistryAddress() == null || env.getRegistryAddress().isEmpty()) {
            // 如果没有配置注册中心，直接取服务名字段（配置中心访问地址）
            result.add(env.getConfigServerName() + env.getContextPath());
            return result;
        }

        ConsulClient consulClient = new ConsulClient(env.getRegistryAddress());
        Response<List<HealthService>> response = consulClient.getHealthServices(env.getConfigServerName(), false, null);
        List<HealthService> configServerList = response.getValue();

        for (HealthService healthService : configServerList) {
            String ip = healthService.getService().getAddress();
            String port = healthService.getService().getPort().toString();
            result.add("http://" + ip + ":" + port + env.getContextPath());
        }
        return result;
    }

}
