package com.myodsgame.Models;

public class Pregunta {
    private String enunciado;
    private String respuesta1;
    private String respuesta2;
    private String respuesta3;
    private String respuesta4;
    private String respuestaCorrecta;

    private int nivelDificultad;

    public Pregunta(String enunciado, String respuesta1, String respuesta2, String respuesta3,
                    String respuesta4, String respuestaCorrecta, int nivelDificultad) {
        this.enunciado = enunciado;
        this.respuesta1 = respuesta1;
        this.respuesta2 = respuesta2;
        this.respuesta3 = respuesta3;
        this.respuesta4 = respuesta4;
        this.respuestaCorrecta = respuestaCorrecta;
        this.nivelDificultad = nivelDificultad;
    }

    public int getNivelDificultad() {
        return nivelDificultad;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public String getRespuesta1() {
        return respuesta1;
    }

    public String getRespuesta2() {
        return respuesta2;
    }

    public String getRespuesta3() {
        return respuesta3;
    }

    public String getRespuesta4() {
        return respuesta4;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }
}
