package ru.vsouth.springbootpractice.entity;

import org.springframework.stereotype.Component;
import ru.vsouth.springbootpractice.dto.Location;
@Component
public class UserInfo {
    private Integer id;
    private String login;
    private String password;
    private Location location;

    public UserInfo(String login, String password, Location location) {
        this.login = login;
        this.password = password;
        this.location = location;
    }

    public UserInfo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
