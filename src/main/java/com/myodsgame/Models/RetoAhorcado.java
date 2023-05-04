package com.myodsgame.Models;

import com.myodsgame.Utils.TipoReto;

import java.util.List;
import java.util.Set;

public class RetoAhorcado extends Reto{
    private String palabra;
    private Set<Character> letrasUsadas;
    private int intentos;

    public RetoAhorcado(boolean ayudaUsada, int duracion, int tiempoTicTac, int dificultad, int puntuacion,
                        TipoReto tipoReto, String palabra, Set<Character> letrasUsadas, int intentos) {
        super(ayudaUsada, duracion, tiempoTicTac, dificultad, puntuacion, tipoReto);
        this.palabra = palabra;
        this.letrasUsadas = letrasUsadas;
        this.intentos = intentos;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public Set<Character> getLetrasUsadas() {
        return letrasUsadas;
    }

    public void setLetrasUsadas(Set<Character> letrasUsadas) {
        this.letrasUsadas = letrasUsadas;
    }

    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }
}
