package com.example.youtubermanager;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String name;
    private String email;
    private String password;
    private int typeAccount;
    private int typeLogin;
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(int typeAccount) {
        this.typeAccount = typeAccount;
    }

    public int getTypeLogin() {
        return typeLogin;
    }

    public void setTypeLogin(int typeLogin) {
        this.typeLogin = typeLogin;
    }

    public User() {
    }

    public User(String name, String email, String password, int typeAccount, int typeLogin, String img) {

        this.name = name;
        this.email = email;
        this.password = password;
        this.typeAccount = typeAccount;
        this.typeLogin = typeLogin;
        this.img = img;
    }
    public User(int id, String name, String email, String password, int typeAccount, int typeLogin, String img) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.typeAccount = typeAccount;
        this.typeLogin = typeLogin;
        this.img = img;
    }
}
