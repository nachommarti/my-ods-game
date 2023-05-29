package com.myodsgame.Strategy;

import com.myodsgame.Models.Usuario;
import com.myodsgame.Repository.RepositorioUsuarioImpl;
import com.myodsgame.Utils.EstadoJuego;

public class EstrategiaLoginUsuario implements IEstrategiaLogin {
    public boolean login(String username, String password){
        RepositorioUsuarioImpl repositorioUsuario = new RepositorioUsuarioImpl();
        Usuario user = repositorioUsuario.findByUserAndPassword(username, password);

        if(user != null) {
            EstadoJuego.getInstance().setUsuario(user);
            return true;
        }
        return false;
    }
}
