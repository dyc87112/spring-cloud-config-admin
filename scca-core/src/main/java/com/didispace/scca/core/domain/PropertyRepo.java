package com.didispace.scca.core.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by 程序猿DD/翟永超 on 2018/4/24.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
public interface PropertyRepo extends JpaRepository<Property, Long> {

    int deleteAllByEnv(Env env);

    int deleteAllByProject(Project project);

    int deleteAllByProjectAnAndEnv(Project project, Env env);

    int deleteAllByLabel(Label label);

    List<Property> findByEnv_IdAndProject_IdAndLabel_Id(Long envId, Long projectId, Long labelId);

    List<Property> findByEnvAndAndProjectAndLabel(Env env, Project project, Label label);

    int deleteAllByEnv_IdAndProject_IdAndLabel_Id(Long envId, Long projectId, Long labelId);

    int deleteAllByEnvAndAndProjectAndLabel(Env env, Project project, Label label);

    List<Property> findByLabel_Id(Long labelId);

}
