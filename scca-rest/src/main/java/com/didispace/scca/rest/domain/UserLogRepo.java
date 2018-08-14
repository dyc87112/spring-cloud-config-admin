package com.didispace.scca.rest.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Anoyi on 2018/8/14.
 * <p>
 * Blog: https://anoyi.com/
 * Github: https://github.com/ChineseSilence
 */
public interface UserLogRepo extends JpaRepository<UserLog, Long> {

    List<UserLog> findTop10ByUsernameAndTypeOrderByIdDesc(String username, int type);

}
