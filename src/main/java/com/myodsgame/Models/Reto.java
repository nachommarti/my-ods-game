package com.myodsgame.Models;

import com.myodsgame.Utils.TipoReto;

import java.util.List;

public abstract class Reto {
    private int id;
    private boolean ayudaUsada;
    private int duracion;
    private int tiempoTicTac;
    private int dificultad;
    private int puntuacion;
    private TipoReto tipoReto;
    private List<Integer> ODS;

    protected Reto(int id, boolean ayudaUsada, int duracion, int tiempoTicTac, int dificultad, int puntuacion, TipoReto tipoReto,
                List<Integer> ODS) {
        this.id = id;
        this.ayudaUsada = ayudaUsada;
        this.duracion = duracion;
        this.tiempoTicTac = tiempoTicTac;
        this.dificultad = dificultad;
        this.puntuacion = puntuacion;
        this.tipoReto = tipoReto;
        this.ODS = ODS;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

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

    public int getTiempoTicTac() {
        return tiempoTicTac;
    }

    public void setTiempoTicTac(int tiempoTicTac) {
        this.tiempoTicTac = tiempoTicTac;
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

    public TipoReto getTipoReto() {
        return tipoReto;
    }

    public void setTipoReto(TipoReto tipoReto) {
        this.tipoReto = tipoReto;
    }

    public List<Integer> getODS() {
        return ODS;
    }

    public void setODS(List<Integer> ODS) {
        this.ODS = ODS;
    }
}
