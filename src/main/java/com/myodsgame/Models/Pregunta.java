package com.myodsgame.Models;

public class Pregunta {
    private int id;
    private String enunciado;
    private String respuesta1;
    private String respuesta2;
    private String respuesta3;
    private String respuesta4;
    private int respuestaCorrecta;
    private int nivelDificultad;

    private Pregunta(Builder builder) {
        id = builder.id;
        enunciado = builder.enunciado;
        respuesta1 = builder.respuesta1;
        respuesta2 = builder.respuesta2;
        respuesta3 = builder.respuesta3;
        respuesta4 = builder.respuesta4;
        respuestaCorrecta = builder.respuestaCorrecta;
        nivelDificultad = builder.nivelDificultad;
    }

    // getters de cada atributo

    @Override
    public String toString() {
        return "Pregunta{" +
                "id=" + id +
                ", enunciado='" + enunciado + '\'' +
                ", respuesta1='" + respuesta1 + '\'' +
                ", respuesta2='" + respuesta2 + '\'' +
                ", respuesta3='" + respuesta3 + '\'' +
                ", respuesta4='" + respuesta4 + '\'' +
                ", respuestaCorrecta=" + respuestaCorrecta +
                ", nivelDificultad=" + nivelDificultad +
                '}' + "\n";
    }


    public static class Builder {
        private int id;
        private String enunciado;
        private String respuesta1;
        private String respuesta2;
        private String respuesta3;
        private String respuesta4;
        private int respuestaCorrecta;
        private int nivelDificultad;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setEnunciado(String enunciado) {
            this.enunciado = enunciado;
            return this;
        }

        public Builder setRespuesta1(String respuesta1) {
            this.respuesta1 = respuesta1;
            return this;
        }

        public Builder setRespuesta2(String respuesta2) {
            this.respuesta2 = respuesta2;
            return this;
        }

        public Builder setRespuesta3(String respuesta3) {
            this.respuesta3 = respuesta3;
            return this;
        }

        public Builder setRespuesta4(String respuesta4) {
            this.respuesta4 = respuesta4;
            return this;
        }

        public Builder setRespuestaCorrecta(int respuestaCorrecta) {
            this.respuestaCorrecta = respuestaCorrecta;
            return this;
        }

        public Builder setNivelDificultad(int nivelDificultad) {
            this.nivelDificultad = nivelDificultad;
            return this;
        }

        public Pregunta build() {
            return new Pregunta(this);
        }
    }
}

