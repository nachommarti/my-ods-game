package com.myodsgame.Models;

import java.time.LocalDate;

public class PuntuacionDiaria {
    private String usuario;
    private int puntos;
    private LocalDate fecha;

    public String getUsuario(){return usuario;}
    public int getPuntos(){return puntos;}
    public LocalDate getFecha(){return fecha;}

    public void setUsuario(String user){usuario = user;}
    public void setPuntos(int points){puntos = points;}
    public void setFecha(LocalDate date){fecha = date;}
}
