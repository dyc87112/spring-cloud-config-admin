package com.didispace.scca.rest.runner;

import com.didispace.scca.rest.constant.UserRoleEnum;
import com.didispace.scca.rest.domain.User;
import com.didispace.scca.rest.domain.UserRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by Anoyi on 2018/8/1.
 * <p>
 * Blog: https://anoyi.com/
 * Github: https://github.com/ChineseSilence
 */
@Slf4j
@Component
@AllArgsConstructor
public class InitAdminAccountRunner implements CommandLineRunner {

    private static final String DEFAULT_ACCOUNT_USERNAME= "admin";

    private static final String DEFAULT_ACCOUNT_PASSWORD= "spring4all";

    private static final String DEFAULT_ACCOUNT_NICKNAME= "超级管理员";

    private final UserRepo userRepo;

    @Override
    public void run(String... strings) throws Exception {
        User defaultAdmin = userRepo.findByUsername(DEFAULT_ACCOUNT_USERNAME);
        if (defaultAdmin == null){
            defaultAdmin = new User();
            defaultAdmin.setUsername(DEFAULT_ACCOUNT_USERNAME);
            defaultAdmin.setNickname(DEFAULT_ACCOUNT_NICKNAME);
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(DEFAULT_ACCOUNT_PASSWORD);
            defaultAdmin.setPassword(encodedPassword);
            defaultAdmin.setRole(UserRoleEnum.ADMIN.getKey());
            userRepo.save(defaultAdmin);
            log.info("----------------------------------------------------------------");
            log.info("Init default admin account, username: {} , password: {}", DEFAULT_ACCOUNT_USERNAME, DEFAULT_ACCOUNT_PASSWORD);
            log.info("----------------------------------------------------------------");
        }
    }

}
