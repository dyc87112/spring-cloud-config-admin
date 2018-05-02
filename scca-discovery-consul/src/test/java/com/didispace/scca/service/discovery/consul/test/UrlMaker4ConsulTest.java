package com.didispace.scca.service.discovery.consul.test;

import com.didispace.scca.core.domain.Env;
import com.didispace.scca.core.domain.EnvRepo;
import com.didispace.scca.core.service.UrlMakerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlMaker4ConsulTest {

    @Autowired
    protected EnvRepo envRepo;
    @Autowired
    protected UrlMakerService urlMakerService;

    @Test
    @Rollback
    public void testConsulUrlMaker() {
        // 新增一个环境数据
        Env stage = new Env();
        stage.setName("stage");
        stage.setConfigServerName("yh-config-server");
        stage.setRegistryAddress("10.19.90.19:8500");
        envRepo.save(stage);

        // 获取stage环境的配置中心URL
        String configServerUrl = urlMakerService.configServerBaseUrl("stage");
        log.info(configServerUrl);
        assertThat(configServerUrl).isEqualTo("http://10.19.129.121:9010");

        // 获取yh-consul-admin项目、stage环境、develop分支的配置抓取链接
        String propertiesUrl = urlMakerService.propertiesLoadUrl("yh-consul-admin", "stage", "develop");
        log.info(propertiesUrl);
        assertThat(propertiesUrl).isEqualTo("http://10.19.129.121:9010/yh-consul-admin/stage/develop");
    }

}
