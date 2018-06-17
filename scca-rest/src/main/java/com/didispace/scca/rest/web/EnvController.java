package com.didispace.scca.rest.web;

import com.alibaba.fastjson.JSON;
import com.didispace.scca.core.domain.Env;
import com.didispace.scca.rest.dto.EnvDto;
import com.didispace.scca.rest.dto.base.WebResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public WebResp<List<EnvDto>> findAllEnv() {

        List<EnvDto> result = new ArrayList<>();
        for (Env env : envRepo.findAll()) {
            EnvDto dto = new EnvDto();
            BeanUtils.copyProperties(env, dto);
            result.add(dto);
        }

        return WebResp.success(result);
    }

    @ApiOperation("Create Env / 创建环境")
    @RequestMapping(method = RequestMethod.POST)
    public WebResp<String> createEnv(@RequestBody EnvDto env) {
        log.info("create env. env={}", JSON.toJSONString(env));

        Env saveEnv = new Env();
        BeanUtils.copyProperties(env, saveEnv);
        envRepo.save(saveEnv);

        return WebResp.success("create Env success");
    }

    @ApiOperation("Delete Env / 删除环境")
    @RequestMapping(method = RequestMethod.DELETE)
    public WebResp<String> deleteEnv(@RequestParam("id") Long id) {
        Env env = envRepo.findOne(id);

        log.info("delete env. env={}", JSON.toJSONString(env));
        envRepo.delete(id);

        return WebResp.success("delete Env success");
    }

    @ApiOperation("Update Env / 更新环境")
    @RequestMapping(method = RequestMethod.PUT)
    public WebResp<String> updateEnv(@RequestBody EnvDto env) {
        Env u = envRepo.findOne(env.getId());

        log.info("update env. u={} env={}", JSON.toJSONString(u), JSON.toJSONString(env));

        u.setName(env.getName());
        u.setConfigServerName(env.getConfigServerName());
        u.setRegistryAddress(env.getRegistryAddress());
        envRepo.save(u);

        return WebResp.success("update Env success");
    }

}
