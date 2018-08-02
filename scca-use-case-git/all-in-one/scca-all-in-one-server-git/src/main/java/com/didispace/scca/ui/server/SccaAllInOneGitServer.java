package com.didispace.scca.ui.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@EnableDiscoveryClient
@SpringBootApplication
public class SccaAllInOneGitServer {

    public static void main(String[] args) {
        SpringApplication.run(SccaAllInOneGitServer.class, args);
    }

}
