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
public interface EnvRepo extends JpaRepository<Env, Long> {

    Env findByName(String name);

    @Query("select new Env(e.id, e.name) from Env e order by e.id asc")
    List<Env> simpleFindAll();

}
