package com.myodsgame.Strategy;

import com.myodsgame.Models.Usuario;
import com.myodsgame.Repository.RepositorioUsuario;
import com.myodsgame.Repository.RepositorioUsuarioImpl;
import com.myodsgame.Utils.EstadoJuego;

public class EstrategiaLoginEmail implements EstrategiaLogin{
    public boolean login(String email, String password){
        RepositorioUsuario repositorioUsuario = new RepositorioUsuarioImpl();
        Usuario user = repositorioUsuario.getUsuarioPorEmailYContraseña(email, password);

        if(user != null) {
            EstadoJuego.getInstance().setUsuario(user);
            return true;
        }
        return false;
    }
}
