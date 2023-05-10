package com.myodsgame.Models;

import java.util.Date;

public class Usuario {
    private String username;
    private String email;
    private String password;
    private Date birthdate;
    private Estadisticas estadisticas;
    private String avatar;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Estadisticas getEstadistica() {
        return estadisticas;
    }

    public void setEstadistica(Estadisticas estadistica) {
        this.estadisticas = estadistica;
    }

    public String getAvatar() { return avatar; }

    public void setAvatar(String avatar) { this.avatar = avatar; }
}
