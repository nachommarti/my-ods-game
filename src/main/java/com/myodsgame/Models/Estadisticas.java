package com.myodsgame.Models;

public class Estadisticas {

    private int [] progresoIndividualOds = new int[17];
    private int puntosTotales;

    private int partidasJugadas;

    private int numeroAciertos;
    private int numeroFallos;
    private String usuario;

    public int[] getProgresoIndividualOds() {
        return progresoIndividualOds;
    }

    public void setProgresoIndividualOds(int[] progresoIndividualOds) {
        this.progresoIndividualOds = progresoIndividualOds;
    }

    public int getPuntosTotales() {
        return puntosTotales;
    }

    public void setPuntosTotales(int puntosTotales) {
        this.puntosTotales = puntosTotales;
    }

    public int getPartidasJugadas() {
        return partidasJugadas;
    }

    public void setPartidasJugadas(int partidasJugadas) {
        this.partidasJugadas = partidasJugadas;
    }

    public int getNumeroAciertos() {
        return numeroAciertos;
    }

    public void setNumeroAciertos(int numeroAciertos) {
        this.numeroAciertos = numeroAciertos;
    }

    public int getNumeroFallos() {
        return numeroFallos;
    }

    public void setNumeroFallos(int numeroFallos) {
        this.numeroFallos = numeroFallos;
    }

    public String getUsuario() {return usuario;}
    public void setUsuario(String user){this.usuario = user;}
}
