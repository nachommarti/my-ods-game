package com.myodsgame.Models;

import com.myodsgame.Utils.TipoReto;

import java.util.List;

public class RetoFrase extends Reto{

    private String frase;
    private String pista;
    private int intentos;

    public RetoFrase(boolean ayudaUsada, int duracion, int tiempoTicTac, int dificultad, int puntuacion,
                        TipoReto tipoReto, List<Integer> ODS, String palabra, String pista, int intentos) {
        super(ayudaUsada, duracion, tiempoTicTac, dificultad, puntuacion, tipoReto, ODS);
        this.frase = palabra;
        this.pista = pista;
        this.intentos = intentos;
    }

    public String getPalabra() {
        return frase;
    }

    public void setPalabra(String palabra) {
        this.frase = palabra;
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
