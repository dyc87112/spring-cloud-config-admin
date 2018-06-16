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

/**
 * Created by 程序猿DD/翟永超 on 2018/5/21.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Api("Config MGT（配置管理）")
@Slf4j
@RestController
@RequestMapping("/edit4service")
public class Edit4ServiceController extends BaseController {

    @ApiOperation("Get properties / 获取详细配置信息")
    @RequestMapping(value = "/properties", method = RequestMethod.GET)
    public WebResp<String> properties(@RequestParam String project,
                                      @RequestParam String profile,
                                      @RequestParam String label) {
        // 通过
        Environment environment = baseOptService.getProperties(project, profile, label);

        for (PropertySource propertySource : environment.getPropertySources()) {


        }
        return null;
    }


}
