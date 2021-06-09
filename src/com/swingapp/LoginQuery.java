package com.swingapp;

public class LoginQuery {

    private String username;
    private String password;

    public LoginQuery(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }

}
