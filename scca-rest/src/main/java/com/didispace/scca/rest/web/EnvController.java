package com.didispace.scca.rest.web;

import com.alibaba.fastjson.JSON;
import com.didispace.scca.core.domain.Env;
import com.didispace.scca.core.domain.Project;
import com.didispace.scca.rest.dto.EnvDto;
import com.didispace.scca.rest.dto.base.WebResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping("${scca.rest.context-path:}/env")
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

    @ApiOperation("Env Detail / 环境详情")
    @RequestMapping(path = "/detail", method = RequestMethod.GET)
    public WebResp<EnvDto> findEnvDetail(@RequestParam("id") Long id) {

        Env env = envRepo.findOne(id);
        EnvDto dto = new EnvDto();
        BeanUtils.copyProperties(env, dto);

        return WebResp.success(dto);
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

    /**
     * 删除某个环境，同时级联删除该环境下面的所有配置, 包括：
     * <p>
     * 1. scca内部实体：通过hibernate实体CascadeType配置实现，具体见 {@link Env}实体
     * 2. 实际持久化内容：根据不同的存储方式调用不同的删除逻辑
     *
     * @param id
     * @return
     */
    @Transactional
    @ApiOperation("Delete Env / 删除环境")
    @RequestMapping(method = RequestMethod.DELETE)
    public WebResp<String> deleteEnv(@RequestParam("id") Long id) {
        Env env = envRepo.findOne(id);
        log.info("delete env. env={}", JSON.toJSONString(env));

        // 删除实际持久化内容
        persistenceService.deletePropertiesByEnv(env);

        // 删除这个env与project的关联
        for (Project project : env.getProjects()) {
            project.getEnvs().remove(env);
            projectRepo.save(project);
        }

        // 删除逻辑实体
        envRepo.delete(id);

        return WebResp.success("delete Env success");
    }

    @Transactional
    @ApiOperation("Update Env / 更新环境")
    @RequestMapping(method = RequestMethod.PUT)
    public WebResp<String> updateEnv(@RequestBody EnvDto env) {
        Env u = envRepo.findOne(env.getId());

        log.info("update env. u={} env={}", JSON.toJSONString(u), JSON.toJSONString(env));

        if (!u.getName().equals(env.getName())) {
            // 环境名称有修改，如果不是db存储，其他的存储还要更新持久化内容
            persistenceService.updateProfileName(u.getName(), env.getName());
        }

        u.setName(env.getName());
        u.setConfigServerName(env.getConfigServerName());
        u.setRegistryAddress(env.getRegistryAddress());
        u.setContextPath(env.getContextPath());
        envRepo.save(u);

        return WebResp.success("update Env success");
    }

}
