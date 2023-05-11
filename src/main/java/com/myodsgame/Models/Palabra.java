package com.myodsgame.Models;

public class Palabra {

    private String palabra;
    private int nivelDificultad;

    public Palabra(String palabra, int nivelDificultad) {
        this.palabra = palabra;
        this.nivelDificultad = nivelDificultad;
    }

    public String getPalabra() {
        return palabra;
    }

    public int getNivelDificultad() {
        return nivelDificultad;
    }
}
