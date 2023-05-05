package com.myodsgame.Models;

import com.myodsgame.Utils.TipoReto;

import java.util.List;
import java.util.Set;

public class RetoAhorcado extends Reto{
    private String palabra;
    private String pista;
    private int intentos;

    public RetoAhorcado(boolean ayudaUsada, int duracion, int tiempoTicTac, int dificultad, int puntuacion,
                        TipoReto tipoReto, String palabra, String pista, int intentos) {
        super(ayudaUsada, duracion, tiempoTicTac, dificultad, puntuacion, tipoReto);
        this.palabra = palabra;
        this.pista = pista;
        this.intentos = intentos;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public String getPista() {
        return pista;
    }

    public void setPista(String pista) {
        this.pista = pista;
    }

    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }
}
