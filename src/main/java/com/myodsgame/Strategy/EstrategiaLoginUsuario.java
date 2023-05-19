package com.myodsgame.Strategy;

import com.myodsgame.Models.Usuario;
import com.myodsgame.Repository.RepositorioUsuario;
import com.myodsgame.Repository.RepositorioUsuarioImpl;
import com.myodsgame.Utils.EstadoJuego;

public class EstrategiaLoginUsuario implements EstrategiaLogin{
    public boolean login(String username, String password){
        RepositorioUsuario repositorioUsuario = new RepositorioUsuarioImpl();
        Usuario user = repositorioUsuario.getUsuarioPorUsernameYContraseña(username, password);

        if(user != null) {
            EstadoJuego.getInstance().setUsuario(user);
            return true;
        }
        return false;
    }
}
