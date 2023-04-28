package com.myodsgame.Models;

import java.util.List;

public class RetoAhorcado extends Reto{
    private String palabra;
    private List<Character> letrasUsadas;
    private int intentos;

    public RetoAhorcado(boolean ayudaUsada, int duracion, int dificultad, int puntuacion, String tipo,
                        String palabra, List<Character> letrasUsadas, int intentos) {
        super(ayudaUsada, duracion, dificultad, puntuacion, tipo);
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

    public List<Character> getLetrasUsadas() {
        return letrasUsadas;
    }

    public void setLetrasUsadas(List<Character> letrasUsadas) {
        this.letrasUsadas = letrasUsadas;
    }

    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }
}
