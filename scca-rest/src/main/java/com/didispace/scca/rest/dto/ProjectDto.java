package com.didispace.scca.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目
 * <p>
 * Created by 程序猿DD/翟永超 on 2018/5/21.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Data
@NoArgsConstructor
public class ProjectDto {

    private Long id;

    /**
     * 项目名
     */
    private String name;

    /**
     * 部署的环境列表
     */
    private List<EnvDto> envs = new ArrayList<>();

    /**
     * 配置便签列表（灰度用）
     */
    private List<LabelDto> labels = new ArrayList<>();

}
