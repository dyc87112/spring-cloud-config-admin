package com.didispace.scca.plugin.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 程序猿DD/翟永超 on 2018/8/2.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Slf4j
@RestController
@RequestMapping("${spring.cloud.config.server.prefix:}")
public class PluginCoreController {

    // TODO 配置中心提供的公共维护操作，1.2.0补充


}
