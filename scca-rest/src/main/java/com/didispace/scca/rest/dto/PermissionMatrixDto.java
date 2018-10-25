package com.didispace.scca.rest.dto;

import com.didispace.scca.core.domain.Env;
import com.didispace.scca.core.domain.Project;
import com.didispace.scca.rest.domain.Permission;
import lombok.Data;

import java.util.List;

/**
 * 用户权限矩阵
 */
@Data
public class PermissionMatrixDto {

    // 环境列表
    private List<Env> envs;

    // 项目列表
    private List<Project> projects;

    // 权限矩阵
    private List<Permission> permissions;

}
