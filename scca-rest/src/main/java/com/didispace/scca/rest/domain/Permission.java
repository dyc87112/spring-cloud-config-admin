package com.didispace.scca.rest.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 权限
 * Created by Anoyi on 2018/10/24.
 * <p>
 * Blog: https://anoyi.com/
 * Github: https://github.com/ChineseSilence
 */
@Data
@Entity
@NoArgsConstructor
public class Permission {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 用户 ID
     */
    private Long userId;

    /**
     * 环境 ID
     */
    private Long envId;

    /**
     * 项目 ID
     */
    private Long projectId;

}