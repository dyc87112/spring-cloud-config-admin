package com.didispace.scca.service.persistence.git.test;

import com.didispace.easyutils.file.OrderedProperties;
import com.didispace.scca.core.service.PersistenceService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Properties;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class GitPersistenceTest {

    @Autowired
    private PersistenceService persistenceService;

    @Test
    public void testPersistenceService() {
        String name = "http://gitlab.yonghuivip.com/config-repo/yh-consul-admin.git/application-stage.properties";
        String profile = "stage";
        String label = "develop";

        // 前端可以按顺序传上来，然后转换成这个OrderedProperties，就可以调用下面的写入并提交到Git的实现了
        Properties source = new OrderedProperties();

        source.put("spring.redis.host", "localhost");
        source.put("spring.redis.port", "6379");
        source.put("spring.redis.pool.max-wait", "1000");
        source.put("spring.redis.pool.min-idle", "8");
        source.put("spring.redis.pool.max-idle", "100");
        source.put("spring.redis.pool.max-active", "1000");

        source.put("xxx", "oooooooo");
        source.put("aaa", "mmmmmmmm");

        persistenceService.updateProperties(name, profile, label, source);
    }

}
