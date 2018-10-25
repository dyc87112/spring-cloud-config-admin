package com.didispace.scca.rest.dto;

import com.didispace.scca.rest.domain.Permission;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PermissionParamDto {

    /**
     * 用户 ID
     */
    private Long userId;

    /**
     * 权限列表
     */
    List<Permission> permissions;

}
