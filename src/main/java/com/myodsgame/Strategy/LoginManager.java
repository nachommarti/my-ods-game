package com.myodsgame.Strategy;

public class LoginManager {
    private IEstrategiaLogin estrategia;

    public void setEstrategia(IEstrategiaLogin estrategia){
        this.estrategia = estrategia;
    }

    public boolean login(String username, String password){
        return estrategia.login(username, password);
    }
}
