package com.myodsgame.Models;

import java.time.LocalDate;

public class PuntuacionDiariaPK {
    String usuario;
    LocalDate fecha;

    public PuntuacionDiariaPK(String usuario, LocalDate fecha) {
        this.usuario = usuario;
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
