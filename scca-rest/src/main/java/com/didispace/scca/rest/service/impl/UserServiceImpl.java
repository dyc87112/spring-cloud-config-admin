package com.didispace.scca.rest.service.impl;

import com.didispace.scca.rest.domain.User;
import com.didispace.scca.rest.domain.UserRepo;
import com.didispace.scca.rest.dto.UserDto;
import com.didispace.scca.rest.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Anoyi on 2018/8/1.
 * <p>
 * Blog: https://anoyi.com/
 * Github: https://github.com/ChineseSilence
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void createUser(User user) {
        checkUserParams(user);
        user.setId(null);
        saveUser(user);
    }

    @Override
    public void updateUser(User user) {
        checkUserExists(user.getUsername());
        checkUserParams(user);
        saveUser(user);
    }

    @Override
    public void deleteUserByUsername(String username) {
        userRepo.deleteByUsername(username);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        User user = userRepo.findUserByUsername(username);
        return toDto(user);
    }

    @Override
    public Page<UserDto> getUsers(Pageable pageable) {
        Page<User> all = userRepo.findAll(pageable);
        List<User> content = all.getContent();
        List<UserDto> userDtoList = new ArrayList<>(content.size());
        content.forEach(user -> userDtoList.add(toDto(user)));
        return new PageImpl<>(userDtoList);
    }

    private void saveUser(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user);
    }

    private UserDto toDto(User user){
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setNickname(user.getNickname());
        userDto.setRole(user.getRole());
        return userDto;
    }

    /**
     * 校验用户名、密码、昵称是否符合规则
     */
    private void checkUserParams(User user){
        String username = user.getUsername();
        if (!Pattern.matches("\\w{0,19}$", username)){
            throw new RuntimeException("用户名长度必须为 1-20 位，且只能包含字母、数字和下划线");
        }
        String password = user.getPassword();
        if (!Pattern.matches("\\w{5,17}$", password)){
            throw new RuntimeException("密码长度必须为 6-18 位，且只能包含字母、数字和下划线");
        }
        int nicknameLength = user.getNickname().length();
        if (nicknameLength < 1 || nicknameLength > 10){
            throw new RuntimeException("昵称长度必须为 1-10 位");
        }
    }

    /**
     * 校验用户是否存在
     */
    private void checkUserExists(String username){
        if (!userRepo.existsByUsername(username)){
            throw new RuntimeException("用户不存在：" + username);
        }
    }

}