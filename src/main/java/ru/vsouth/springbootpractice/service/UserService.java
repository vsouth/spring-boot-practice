package ru.vsouth.springbootpractice.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vsouth.springbootpractice.entity.UserInfo;
import ru.vsouth.springbootpractice.repository.UserInfoRepository;

import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final UserInfoRepository userInfoRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserInfoRepository userInfoRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserInfo addUser(UserInfo userInfo) {
        String encodedPassword = passwordEncoder.encode(userInfo.getPassword());
        userInfo.setPassword(encodedPassword);
        return userInfoRepository.addUser(userInfo);
    }

    public UserInfo findUserById(Integer id) {
        return userInfoRepository.getUserById(id);
    }

    public Map<Integer, UserInfo> getUsers() {
        return userInfoRepository.getUsers();
    }

    public Optional<UserInfo> getUserByLogin(String login) {
        return userInfoRepository.getUserByLogin(login);
    }
}
