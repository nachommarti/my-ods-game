package TestData;

import com.myodsgame.Models.Pregunta;

public class TestData {

    public static Pregunta getPregunta(){
        return new Pregunta.Builder()
                .setId(1)
                .setEnunciado("¿Cuál de las siguientes es la primera letra del abecedario?")
                .setRespuestaCorrecta("A")
                .setRespuesta1("B")
                .setRespuesta2("A")
                .setRespuesta3("E")
                .setRespuesta4("C")
                .build();
    }
}
