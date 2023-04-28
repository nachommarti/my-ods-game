package com.myodsgame.Factory;

import com.myodsgame.Models.Reto;
import com.myodsgame.Models.RetoAhorcado;
import com.myodsgame.Models.RetoPregunta;

import java.util.List;

public class RetoFactory {
    public static Reto crearReto(boolean ayudaUsada, int duracion, int dificultad, int puntuacion, String tipo,
                                 String enunciado, String respuesta1, String respuesta2, String respuesta3,
                                 String respuesta4, String respuestaCorrecta, String palabra,
                                 List<Character> letrasUsadas, int intentos) {
        if (tipo.equals("pregunta"))
           return new RetoPregunta(ayudaUsada, duracion, dificultad, puntuacion, tipo, enunciado,
                    respuesta1, respuesta2, respuesta3, respuesta4, respuestaCorrecta);
        else if (tipo.equals("ahorcado"))
            return new RetoAhorcado(ayudaUsada, duracion, dificultad, puntuacion, tipo, palabra,
                    letrasUsadas, intentos);
        else
            throw new IllegalArgumentException("Tipo de reto desconocido.");

    }
}
