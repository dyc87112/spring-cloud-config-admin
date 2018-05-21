package com.didispace.scca.rest.web;

import com.didispace.scca.core.domain.Env;
import com.didispace.scca.rest.dto.base.WebResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by 程序猿DD/翟永超 on 2018/4/27.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Api("Env MGT（环境管理）")
@Slf4j
@RestController
@RequestMapping("/env")
public class EnvController extends BaseController {

    @ApiOperation("List Env / 环境列表")
    @RequestMapping(method = RequestMethod.GET)
    public WebResp<List<Env>> findAll() {
        return WebResp.success(envRepo.findAll());
    }

    @ApiOperation("Create Env / 创建环境")
    @RequestMapping(method = RequestMethod.POST)
    public WebResp<Env> create(@RequestBody Env env) {
        log.info("create env : " + env);
        return WebResp.success(envRepo.save(env));
    }

    @ApiOperation("Delete Env / 删除环境")
    @RequestMapping(method = RequestMethod.DELETE)
    public WebResp<String> delete(@RequestParam Long id) {
        Env env = envRepo.findOne(id);

        log.info("delete env : " + env);
        envRepo.delete(id);

        return WebResp.success("delete env [" + env.getName() + "] success");
    }

    @ApiOperation("Update Env / 更新环境")
    @RequestMapping(method = RequestMethod.PUT)
    public WebResp<String> update(@RequestBody Env env) {
        Env u = envRepo.findOne(env.getId());

        log.info("Update Env : " + u + " --> " + env);

        u.setName(env.getName());
        u.setConfigServerName(env.getConfigServerName());
        u.setRegistryAddress(env.getRegistryAddress());
        envRepo.save(u);

        return WebResp.success("update env [" + env.getName() + "] success");
    }

}
