package com.didispace.scca.rest.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by 程序猿DD/翟永超 on 2018/4/24.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SccaRestServerForGitConfigServer {

    public static void main(String[] args) {
        SpringApplication.run(SccaRestServerForGitConfigServer.class);
    }

}
