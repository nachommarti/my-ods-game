package com.myodsgame.Strategy;

public class LoginManager {
    private EstrategiaLogin estrategia;

    public void setEstrategia(EstrategiaLogin estrategia){
        this.estrategia = estrategia;
    }

    public boolean login(String username, String password){
        return estrategia.login(username, password);
    }
}
