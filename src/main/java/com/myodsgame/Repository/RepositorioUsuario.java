package com.myodsgame.Repository;

import com.myodsgame.Models.Usuario;

public interface RepositorioUsuario {

    Usuario getUsuarioPorUsernameYContraseña(String username, String password);
    boolean checkIfUserExists(String username);

    void saveUsuario(Usuario user);

    void updateUsuarioEstadisticas(Usuario user);
}
