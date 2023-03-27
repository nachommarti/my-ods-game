package com.myodsgame.Models;

public class Palabra {
    private String palabra;
    private int nivelDificultad;
    private int id;

    // Constructor privado para usar solo el patrón Builder
    private Palabra(Builder builder) {
        this.palabra = builder.palabra;
        this.nivelDificultad = builder.nivelDificultad;
        this.id = builder.id;
    }

    // Getters para obtener los valores de los atributos
    public String getPalabra() {
        return palabra;
    }

    public int getNivelDificultad() {
        return nivelDificultad;
    }

    public int getIdPalabra() {
        return id;
    }

    // Clase Builder para crear objetos de Palabra
    public static class Builder {
        private String palabra;
        private int nivelDificultad;
        private int id;

        public Builder setPalabra(String palabra) {
            this.palabra = palabra;
            return this;
        }

        public Builder setNivelDificultad(int nivelDificultad) {
            this.nivelDificultad = nivelDificultad;
            return this;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Palabra build() {
            return new Palabra(this);
        }
    }
}
