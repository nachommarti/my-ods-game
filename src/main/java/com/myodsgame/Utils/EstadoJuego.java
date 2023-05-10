package com.myodsgame.Utils;

import com.myodsgame.Models.*;

public class EstadoJuego {
    Usuario usuario;
    Partida partida;
    String urlAvatarRegistro;
    private EstadoJuego() {};
    private static EstadoJuego estado = null;
    public static EstadoJuego getInstance() {
        if (estado == null) {
            estado = new EstadoJuego();
        }
        return estado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public String getUrlAvatarRegistro() {
        return urlAvatarRegistro;
    }

    public void setUrlAvatarRegistro(String urlAvatarRegistro) {
        this.urlAvatarRegistro = urlAvatarRegistro;
    }
}
