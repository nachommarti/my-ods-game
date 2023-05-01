package com.myodsgame.Models;

public abstract class Reto {
    private boolean ayudaUsada;
    private int duracion;
    private int dificultad;
    private int puntuacion;
    private String tipo;

    public Reto(boolean ayudaUsada, int duracion, int dificultad, int puntuacion, String tipo) {
        this.ayudaUsada = ayudaUsada;
        this.duracion = duracion;
        this.dificultad = dificultad;
        this.puntuacion = puntuacion;
        this.tipo = tipo;
    }


    public boolean isAyudaUsada() {
        return ayudaUsada;
    }

    public void setAyudaUsada(boolean ayudaUsada) {
        this.ayudaUsada = ayudaUsada;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public int getDificultad() {
        return dificultad;
    }

    public void setDificultad(int dificultad) {
        this.dificultad = dificultad;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
