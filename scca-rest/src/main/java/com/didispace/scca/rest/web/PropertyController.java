package com.didispace.scca.rest.web;

import com.didispace.scca.rest.dto.base.WebResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Properties;

/**
 * Created by 程序猿DD/翟永超 on 2018/5/21.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Api("Property MGT（配置详细管理）")
@Slf4j
@RestController
@RequestMapping("/property")
public class PropertyController extends BaseController {

    @ApiOperation("Get properties from persistent / 获取持久化的配置信息")
    @RequestMapping(path = "/persistent", method = RequestMethod.GET)
    public WebResp<Properties> propertiesFromPersistent(@RequestParam String project,
                                                        @RequestParam String profile,
                                                        @RequestParam String label) {
        // 直接通过持久化实现，获取配置信息
        Properties properties = persistenceService.readProperties(project, profile, label);
        return WebResp.success(properties);
    }

    @ApiOperation("Get properties from config server / 通过配置中心接口获取详细配置信息")
    @RequestMapping(path = "/configServer", method = RequestMethod.GET)
    public WebResp<List<PropertySource>> propertiesFromConfigServer(@RequestParam String project,
                                                                    @RequestParam String profile,
                                                                    @RequestParam String label) {
        // 通过配置中心来获取配置信息, 参考应用获取的具体配置信息
        Environment environment = baseOptService.getProperties(project, profile, label);
        return WebResp.success(environment.getPropertySources());
    }


}
