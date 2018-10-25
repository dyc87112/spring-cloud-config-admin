package com.didispace.scca.core.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by 程序猿DD/翟永超 on 2018/4/24.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
public interface ProjectRepo extends JpaRepository<Project, Long> {

    Project findByName(String name);

    @Query("select new Project(p.id, p.name) from Project p order by p.id asc")
    List<Project> simpleFindAll();

}
