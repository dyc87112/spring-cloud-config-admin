package com.didispace.scca.rest.web;

import com.didispace.scca.core.domain.Env;
import com.didispace.scca.core.domain.Project;
import com.didispace.scca.rest.domain.Permission;
import com.didispace.scca.rest.dto.PermissionMatrixDto;
import com.didispace.scca.rest.dto.PermissionParamDto;
import com.didispace.scca.rest.dto.base.WebResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Anoyi on 2018/10/23.
 * <p>
 * Blog: https://anoyi.com/
 * Github: https://github.com/ChineseSilence
 */
@Api("Permission（权限管理）")
@Slf4j
@RestController
@RequestMapping("${scca.rest.context-path:}/permission")
@Secured("ROLE_ADMIN")
public class PermissionController extends BaseController {

    @ApiOperation("Get User Permissions / 权限查询")
    @RequestMapping(method = RequestMethod.GET)
    public WebResp<PermissionMatrixDto> getPermission(@RequestParam("userId") Long userId) {
        PermissionMatrixDto permissionMatrixDto = new PermissionMatrixDto();
        // 环境列表
        List<Env> envs = envRepo.simpleFindAll();
        permissionMatrixDto.setEnvs(envs);
        // 项目列表
        List<Project> projects = projectRepo.simpleFindAll();
        permissionMatrixDto.setProjects(projects);
        // 权限矩阵
        List<Permission> permissions = permissionService.getPermission(userId);
        permissionMatrixDto.setPermissions(permissions);
        return WebResp.success(permissionMatrixDto);
    }

    @ApiOperation("Update User Permissions / 更新权限")
    @RequestMapping(method = RequestMethod.POST)
    public WebResp<String> updatePermission(@RequestBody PermissionParamDto permissionParamDto) {
        Long userId = permissionParamDto.getUserId();
        List<Permission> permissionList = permissionParamDto.getPermissions();
        if (permissionList != null) {
            permissionList.forEach(permission -> permission.setUserId(userId));
        }
        permissionService.updatePermission(userId, permissionList);
        return WebResp.success("update permission success");
    }

}
