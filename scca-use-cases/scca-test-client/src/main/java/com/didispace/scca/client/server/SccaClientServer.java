package com.didispace.scca.client.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SccaClientServer {

    public static void main(String[] args) {
        SpringApplication.run(SccaClientServer.class, args);
    }

}
