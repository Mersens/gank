package com.mersens.gank.entity;

/**
 * Created by Mersens on 2016/11/10.
 */

public class UserBean {
    private String login;
    private String avatar_url;
    private String email;
    @Override
    public String toString() {
        return "UserBean{" +
                "login='" + login + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }


}
