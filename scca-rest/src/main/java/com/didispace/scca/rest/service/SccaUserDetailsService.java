package com.didispace.scca.rest.service;

import com.didispace.scca.rest.constant.UserRoleEnum;
import com.didispace.scca.rest.domain.User;
import com.didispace.scca.rest.domain.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by Anoyi on 2018/8/1.
 * <p>
 * Blog: https://anoyi.com/
 * Github: https://github.com/ChineseSilence
 */
@Service
@AllArgsConstructor
public class SccaUserDetailsService implements UserDetailsService {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("username: " + username);
        }
        return createUserPrincipal(user);
    }

    private UserDetails createUserPrincipal(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(UserRoleEnum.getValue(user.getRole()))
                .build();
    }

}
