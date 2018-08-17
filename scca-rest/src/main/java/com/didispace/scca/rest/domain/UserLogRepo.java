package com.didispace.scca.rest.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Anoyi on 2018/8/14.
 * <p>
 * Blog: https://anoyi.com/
 * Github: https://github.com/ChineseSilence
 */
public interface UserLogRepo extends JpaRepository<UserLog, Long> {

    Page<UserLog> findAllByUsernameAndType(String username, int type, Pageable pageable);

}
