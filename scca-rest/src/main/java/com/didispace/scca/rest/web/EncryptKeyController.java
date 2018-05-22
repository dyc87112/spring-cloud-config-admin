package com.didispace.scca.rest.web;

import com.didispace.scca.core.domain.EncryptKey;
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
@Api("EncryptKey MGT（加密管理）")
@Slf4j
@RestController
@RequestMapping("/encryptKey")
public class EncryptKeyController extends BaseController {

    @ApiOperation("List EncryptKey / 需加密的Key清单")
    @RequestMapping(method = RequestMethod.GET)
    public WebResp<List<EncryptKey>> findAll() {
        return WebResp.success(encryptKeyRepo.findAll());
    }

    @ApiOperation("Add EncryptKey / 新增需加密的Key")
    @RequestMapping(method = RequestMethod.POST)
    public WebResp<EncryptKey> create(@RequestBody EncryptKey encryptKey) {
        log.info("Add EncryptKey : " + encryptKey);
        return WebResp.success(encryptKeyRepo.save(encryptKey));
    }

    @ApiOperation("Delete EncryptKey / 删除需加密的Key")
    @RequestMapping(method = RequestMethod.DELETE)
    public WebResp<String> delete(@RequestParam("envId") Long id) {
        EncryptKey encryptKey = encryptKeyRepo.findOne(id);

        log.info("delete encryptKey : " + encryptKey);
        envRepo.delete(id);

        return WebResp.success("delete encryptKey [" + encryptKey.getEKey() + "] success");
    }

    @ApiOperation("Update EncryptKey / 更新需加密的Key")
    @RequestMapping(method = RequestMethod.PUT)
    public WebResp<String> update(@RequestBody EncryptKey encryptKey) {
        EncryptKey ek = encryptKeyRepo.findOne(encryptKey.getId());

        log.info("Update EncryptKey : " + ek + " --> " + encryptKey);

        ek.setEKey(encryptKey.getEKey());
        encryptKeyRepo.save(ek);

        return WebResp.success("update EncryptKey : [" + ek.getEKey() + "] --> [" + encryptKey.getEKey() + "] success");
    }

    /**
     * batch opt / 批量操作
     **/

    @Transactional
    @ApiOperation("Add EncryptKeys / 批量新增需加密的Key")
    @RequestMapping(path = "/batch", method = RequestMethod.POST)
    public WebResp<String> createBatch(@RequestBody List<EncryptKey> encryptKeys) {
        log.info("Add EncryptKeys : " + encryptKeys);

        for (EncryptKey encryptKey : encryptKeys) {
            encryptKeyRepo.save(encryptKey);
        }

        return WebResp.success("批量新增需加密的Key：" + encryptKeys.size() + "个");
    }

    @Transactional
    @ApiOperation("Delete EncryptKeys / 批量删除需加密的Key")
    @RequestMapping(path = "/batch", method = RequestMethod.DELETE)
    public WebResp<String> deleteBatch(@RequestBody List<Long> ids) {
        log.info("Delete EncryptKeys : " + ids);

        for (Long id : ids) {
            encryptKeyRepo.delete(id);
        }

        return WebResp.success("批量新增需加密的Key：" + ids.size() + "个");
    }

}
