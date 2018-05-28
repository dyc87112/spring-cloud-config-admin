package com.didispace.scca.rest.web;

import com.didispace.scca.core.domain.Project;
import com.didispace.scca.rest.dto.base.WebResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by 程序猿DD/翟永超 on 2018/4/27.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Api("Project MGT（项目管理）")
@Slf4j
@RestController
@RequestMapping("/xhr/project")
public class ProjectController extends BaseController {

    @ApiOperation("List Project / 项目列表")
    @RequestMapping(method = RequestMethod.GET)
    public WebResp<List<Project>> findAll() {
//        Pageable pageable = new PageRequest(page, size);
        log.info("=====>findALl");
        List<Project> r = projectRepo.findAll();
        return WebResp.success(r);
    }

    @ApiOperation("Create Project / 创建项目")
    @RequestMapping(method = RequestMethod.POST)
    public WebResp<Project> create(@RequestBody Project project) {
        log.info("create Project : " + project);
        return WebResp.success(projectRepo.save(project));
    }

    @ApiOperation("Delete Project / 删除环境")
    @RequestMapping(method = RequestMethod.DELETE)
    public WebResp<String> delete(@RequestParam("id") Long id) {
        Project project = projectRepo.findOne(id);

        log.info("delete Project : " + project);
        projectRepo.delete(id);

        return WebResp.success("delete project [" + project.getName() + "] success");
    }

    @ApiOperation("Update Project / 更新环境")
    @RequestMapping(method = RequestMethod.PUT)
    public WebResp<String> update(@RequestBody Project project) {
        Project u = projectRepo.findOne(project.getId());

        log.info("Update Project : " + u + " --> " + project);

        u.setName(project.getName());
        projectRepo.save(u);

        return WebResp.success("update project [" + project.getName() + "] success");
    }

}
