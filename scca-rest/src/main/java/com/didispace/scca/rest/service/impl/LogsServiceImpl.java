package com.didispace.scca.rest.service.impl;

import com.didispace.scca.rest.constant.UserLogTypeEnum;
import com.didispace.scca.rest.domain.UserLog;
import com.didispace.scca.rest.domain.UserLogRepo;
import com.didispace.scca.rest.service.LogsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by Anoyi on 2018/8/14.
 * <p>
 * Blog: https://anoyi.com/
 * Github: https://github.com/ChineseSilence
 */
@Service
@AllArgsConstructor
public class LogsServiceImpl implements LogsService {

    private final UserLogRepo userLogRepo;

    @Override
    public void addUserLog(UserLog userLog) {
        userLogRepo.save(userLog);
    }

    @Override
    public Page<UserLog> getLoginLogs(String username, Pageable pageable) {
        return userLogRepo.findAllByUsernameAndType(username, UserLogTypeEnum.LOGIN.getCode(), pageable);
    }

}