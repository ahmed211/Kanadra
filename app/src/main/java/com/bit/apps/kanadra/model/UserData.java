package com.bit.apps.kanadra.model;

public class UserData {
    String name, password, email;

    public void setName(String name) {
        this.name = name;
    }

    public void setPass(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {

        return name;
    }

    public String getPass() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public UserData(String name, String password, String email) {

        this.name = name;
        this.password = password;
        this.email = email;
    }
}
