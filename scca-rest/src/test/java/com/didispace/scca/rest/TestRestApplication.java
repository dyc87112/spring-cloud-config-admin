package com.didispace.scca.rest;

import com.pszymczyk.consul.ConsulProcess;
import com.pszymczyk.consul.ConsulStarterBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Created by 程序猿DD/翟永超 on 2018/4/24.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@EnableConfigServer
@EnableDiscoveryClient
@SpringBootApplication
public class TestRestApplication {

    public static void main(String[] args) {
        ConsulProcess consul = ConsulStarterBuilder.consulStarter().build().start();

        System.setProperty("spring.cloud.consul.enabled", "true");
        System.setProperty("spring.cloud.consul.host", "localhost");
        System.setProperty("spring.cloud.consul.port", String.valueOf(consul.getHttpPort()));

        SpringApplication.run(TestRestApplication.class);
    }

}
