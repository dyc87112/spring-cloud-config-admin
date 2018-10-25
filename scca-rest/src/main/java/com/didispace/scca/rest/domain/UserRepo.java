package com.didispace.scca.rest.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Anoyi on 2018/8/1.
 * <p>
 * Blog: https://anoyi.com/
 * Github: https://github.com/ChineseSilence
 */
public interface UserRepo extends JpaRepository<User, Long> {

    User findById(Long id);

    User findByUsername(String username);

    void deleteByUsername(String username);

    boolean existsByUsername(String username);

}
