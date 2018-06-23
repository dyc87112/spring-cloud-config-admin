package com.didispace.scca.core.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Created by 程序猿DD/翟永超 on 2018/4/24.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@EnableConfigServer
@SpringBootApplication
public class TestCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestCoreApplication.class);
    }

}
