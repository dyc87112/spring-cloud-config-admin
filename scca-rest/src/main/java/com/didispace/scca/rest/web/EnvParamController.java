package com.didispace.scca.rest.web;

import com.didispace.scca.core.domain.Env;
import com.didispace.scca.core.domain.EnvParam;
import com.didispace.scca.rest.dto.base.WebResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by 程序猿DD/翟永超 on 2018/4/27.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Api("EnvParam MGT（环境参数管理）")
@Slf4j
@RestController
@RequestMapping("/envParam")
public class EnvParamController extends BaseController {

    @ApiOperation("List EnvParam / 环境参数列表")
    @RequestMapping(method = RequestMethod.GET)
    public WebResp<List<EnvParam>> findByEnv(@RequestParam("envId") Long envId) {
        return WebResp.success(envParamRepo.findAllByEnvironment_Id(envId));
    }

    @ApiOperation("Add EnvParam / 添加一个环境参数")
    @RequestMapping(method = RequestMethod.POST)
    public WebResp<EnvParam> addEnvParam(@RequestParam("envId") Long envId, @RequestBody EnvParam envParam) {
        log.info("Add an EnvParam to {} : {} ", envId, envParam);

        Env env = envRepo.findOne(envId);
        envParam.setEnvironment(env);

        return WebResp.success(envParamRepo.save(envParam));
    }

    @ApiOperation("Update EnvParam / 更新某个环境参数")
    @RequestMapping(method = RequestMethod.PUT)
    public WebResp<String> update(@RequestBody EnvParam envParam) {
        EnvParam ep = envParamRepo.findOne(envParam.getId());

        log.info("Update EnvParam : " + ep + " --> " + envParam);

        ep.setPKey(envParam.getPKey());
        ep.setPValue(envParam.getPValue());
        ep = envParamRepo.save(ep);

        return WebResp.success("update env [" + ep + "] success");
    }

    @ApiOperation("Delete EnvParam / 删除某个环境参数")
    @RequestMapping(method = RequestMethod.DELETE)
    public WebResp<String> delete(@RequestParam("envId") Long id) {
        EnvParam envParam = envParamRepo.findOne(id);

        log.info("delete EnvParam : " + envParam);
        envParamRepo.delete(id);

        return WebResp.success("delete EnvParam [" + envParam.getPKey() + "] success");
    }

    /**
     * batch opt / 批量操作
     **/

    @Transactional
    @ApiOperation("Add EnvParams / 新增一批环境参数")
    @RequestMapping(path = "/batch", method = RequestMethod.POST)
    public WebResp<String> createBatch(@RequestParam("envId") Long envId, @RequestBody List<EnvParam> envParams) {
        Env env = envRepo.findOne(envId);
        log.info("Add EnvParams to {} : {} ", env.getName(), envParams);

        for (EnvParam envParam : envParams) {
            envParam.setEnvironment(env);
            envParamRepo.save(envParam);
        }

        return WebResp.success("批量新增环境参数：" + envParams.size() + " 个");
    }

    @Transactional
    @ApiOperation("Delete EnvParams / 删除一批环境参数")
    @RequestMapping(path = "/batch", method = RequestMethod.DELETE)
    public WebResp<String> deleteBatch(@RequestBody List<Long> envParamIds) {
        log.info("Delete EnvParams : {} ", envParamIds);

        for (Long id : envParamIds) {
            envParamRepo.delete(id);
        }

        return WebResp.success("批量删除环境参数：" + envParamIds.size() + " 个");
    }

}
