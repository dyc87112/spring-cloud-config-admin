package com.didispace.scca.core.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by 程序猿DD/翟永超 on 2018/4/24.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
public interface LabelRepo extends JpaRepository<Label, Long> {

    List<Label> findAllByProject_Id(long id);

    Label findFirstByNameAndProject(String name, Project project);

}
