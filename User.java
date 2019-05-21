package com.example.vabolari.login_register;


public class User {
    String name, username, password;
    int income;

    public User (String name, int income, String username, String password){
        this.name = name;
        this.income = income;
        this.username = username;
        this.password = password;

    }

    public User (String username, String password){
        this.username = username;
        this.password = password;
        this.income = 0;
        this.name = "";

    }


}
