package ru.vsouth.springbootpractice.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.vsouth.springbootpractice.entity.UserInfo;
import ru.vsouth.springbootpractice.repository.UserInfoRepository;

import java.util.Optional;
@Service
public class UserInfoUserDetailsService implements UserDetailsService {
    private final UserInfoRepository userInfoRepository;

    public UserInfoUserDetailsService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public UserInfoRepository getUserInfoRepository() {
        return userInfoRepository;
    }

    @Override
    public UserInfoUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userInfoRepository.getUserByLogin(username);
        if (userInfo.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return new UserInfoUserDetails(userInfo.get());
    }
}
