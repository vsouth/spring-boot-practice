package ru.vsouth.springbootpractice.repository;

import org.springframework.stereotype.Repository;
import ru.vsouth.springbootpractice.entity.UserInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserInfoRepository {
    private final HashMap<Integer, UserInfo> users = new HashMap<>();

    public Map<Integer, UserInfo> getUsers() {
        return users;
    }

    public UserInfo addUser(UserInfo userInfo) {
        if (existsByLogin(userInfo.getLogin())) {
            return null;
        }
        Integer nextId = getNextId();
        users.put(nextId, userInfo);
        userInfo.setId(nextId);

        return userInfo;
    }

    private Integer getNextId() {
        return users.size();
    }

    public UserInfo getUserById(Integer id) {
        return users.get(id);
    }

    public Optional<UserInfo> getUserByLogin(String login) {
        return users.values().stream()
                .filter(o -> o.getLogin().equals(login))
                .findFirst();
    }

    public boolean existsByLogin(String login) {
        return getUserByLogin(login).isPresent();
    }
}
