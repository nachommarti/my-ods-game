package com.myodsgame.Models;

import com.myodsgame.Utils.TipoReto;

import java.util.List;

public class RetoPregunta extends Reto{
    private String enunciado;
    private String respuesta1;
    private String respuesta2;
    private String respuesta3;
    private String respuesta4;
    private String respuestaCorrecta;

    public RetoPregunta(){
        super();

    }

    public RetoPregunta(int id, boolean ayudaUsada, int duracion, int tiempoTicTac, int dificultad, int puntuacion,
                        TipoReto tipoReto, List<Integer> ODS, String enunciado, String respuesta1, String respuesta2,
                        String respuesta3, String respuesta4, String respuestaCorrecta) {
        super(id, ayudaUsada, duracion, tiempoTicTac, dificultad, puntuacion, tipoReto, ODS);
        this.enunciado = enunciado;
        this.respuesta1 = respuesta1;
        this.respuesta2 = respuesta2;
        this.respuesta3 = respuesta3;
        this.respuesta4 = respuesta4;
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getRespuesta1() {
        return respuesta1;
    }

    public void setRespuesta1(String respuesta1) {
        this.respuesta1 = respuesta1;
    }

    public String getRespuesta2() {
        return respuesta2;
    }

    public void setRespuesta2(String respuesta2) {
        this.respuesta2 = respuesta2;
    }

    public String getRespuesta3() {
        return respuesta3;
    }

    public void setRespuesta3(String respuesta3) {
        this.respuesta3 = respuesta3;
    }

    public String getRespuesta4() {
        return respuesta4;
    }

    public void setRespuesta4(String respuesta4) {
        this.respuesta4 = respuesta4;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public void setRespuestaCorrecta(String respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }
}

