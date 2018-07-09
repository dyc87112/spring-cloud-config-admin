package com.didispace.scca.rest.web;

import com.didispace.scca.core.domain.Env;
import com.didispace.scca.rest.dto.DashboardEnvDto;
import com.didispace.scca.rest.dto.base.WebResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Dashboard各元素获取
 * <p>
 * Created by 程序猿DD/翟永超 on 2018/7/09.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Api("Dashboard信息")
@Slf4j
@RestController
@RequestMapping("${scca.rest.context-path:}/dashboard")
public class DashboardController extends BaseController {

    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation("Env Summary / 环境总览信息")
    @RequestMapping(path = "/envSummary", method = RequestMethod.GET)
    public WebResp<List<DashboardEnvDto>> envSummaryInfo() {
        List<Env> envList = envRepo.findAll();

        List<DashboardEnvDto> result = new ArrayList<>();
        // 环境名、配置中心状态（访问地址、是否健康、加密是否可用）、对接项目数，环境参数数量
        for (Env env : envList) {
            DashboardEnvDto dto = new DashboardEnvDto();
            dto.setId(env.getId());
            dto.setName(env.getName()); // 环境名
            dto.setProjects(env.getProjects().size()); // 已对接的项目数
            dto.setParams(env.getEnvParams().size()); // 已配置的环境参数数量
            dto.setConfigServers(baseOptService.configServerInfo(env)); // 获取配置中心的信息：访问地址、是否健康、加密是否可用
            result.add(dto);
        }

        return WebResp.success(result);

    }

}
