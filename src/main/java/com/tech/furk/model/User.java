package com.tech.furk.model;

public class User {

    String username;
    String senha;

    public User(String login, String senha) {
        this.username = login;
        this.senha = senha;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSenha() {
        return this.senha;
    }

}
