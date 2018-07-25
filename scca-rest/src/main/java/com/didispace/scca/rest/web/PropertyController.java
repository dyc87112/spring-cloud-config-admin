package com.didispace.scca.rest.web;

import com.didispace.scca.core.domain.Env;
import com.didispace.scca.rest.dto.base.WebResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("${scca.rest.context-path:}/property")
public class PropertyController extends BaseController {

    @ApiOperation("persistent properties / 获取持久化的配置信息")
    @RequestMapping(path = "/persistent", method = RequestMethod.GET)
    public WebResp<Properties> propertiesFromPersistent(@RequestParam("project") String project,
                                                        @RequestParam("profile") String profile,
                                                        @RequestParam("label") String label) {
        // 直接通过持久化实现，获取配置信息
        Properties properties = persistenceService.readProperties(project, profile, label);
        return WebResp.success(properties);
    }

    @ApiOperation("Save properties / 保存持久化的配置信息")
    @RequestMapping(path = "/persistent", method = RequestMethod.POST)
    public WebResp<String> saveProperties(@RequestParam("project") String project,
                                          @RequestParam("profile") String profile,
                                          @RequestParam("label") String label,
                                          @RequestBody Properties newProperties) {
        // 保存配置信息
        persistenceService.saveProperties(project, profile, label, newProperties);
        return WebResp.success("save properties success");
    }

    @ApiOperation("config server properties / 获取配置中心获取的配置信息")
    @RequestMapping(path = "/configServer", method = RequestMethod.GET)
    public WebResp<List<PropertySource>> propertiesFromConfigServer(@RequestParam("project") String project,
                                                                    @RequestParam("profile") String profile,
                                                                    @RequestParam("label") String label) {
        // 通过配置中心来获取配置信息, 参考应用获取的具体配置信息
        Environment environment = baseOptService.getProperties(project, profile, label);
        return WebResp.success(environment.getPropertySources());
    }

    @ApiOperation("Encrypt property / 加密配置")
    @RequestMapping(path = "/encrypt", method = RequestMethod.POST)
    public WebResp<String> encrypt(@RequestParam("envId") Long envId,
                                   @RequestBody String value) {
        // 指定某个环境的加密（单个值）
        Env env = envRepo.findOne(envId);

        Assert.notNull(env, "Env [" + envId + "] not exist");

        String result = baseOptService.encrypt(value, env);
        log.info("encrypt property {} -> {}", value, result);

        return WebResp.success(result, null);
    }

    @ApiOperation("Decrypt property / 解密配置")
    @RequestMapping(path = "/decrypt", method = RequestMethod.POST)
    public WebResp<String> decrypt(@RequestParam("envId") Long envId,
                                   @RequestBody String value) {
        // 指定某个环境的解密（单个值）
        Env env = envRepo.findOne(envId);

        Assert.notNull(env, "Env [" + envId + "] not exist");

        String result = baseOptService.decrypt(value, env);
        log.info("decrypt property {} -> {}", value, result);
        return WebResp.success(result, null);
    }

}
