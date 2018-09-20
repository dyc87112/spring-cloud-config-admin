package com.didispace.scca.rest.service;

import com.didispace.scca.rest.constant.UserRoleEnum;
import com.didispace.scca.rest.domain.User;
import com.didispace.scca.rest.domain.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anoyi on 2018/8/1.
 * <p>
 * Blog: https://anoyi.com/
 * Github: https://github.com/ChineseSilence
 */
@Service
@AllArgsConstructor
public class SccaUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("username: " + username);
        }
        return createUserPrincipal(user);
    }

    private UserDetails createUserPrincipal(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        String role = UserRoleEnum.getValue(user.getRole());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return new  org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
//        return org.springframework.security.core.userdetails.User.builder()
//                .username(user.getUsername())
//                .password(user.getPassword())
//                .roles(UserRoleEnum.getValue(user.getRole()))
//                .build();
    }

}
