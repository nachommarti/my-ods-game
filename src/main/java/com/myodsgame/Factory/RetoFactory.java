package com.myodsgame.Factory;

import com.myodsgame.Models.Reto;
import com.myodsgame.Models.RetoAhorcado;
import com.myodsgame.Models.RetoPregunta;
import com.myodsgame.Utils.TipoReto;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class RetoFactory {
    public static Reto crearReto(boolean ayudaUsada, int duracion, int dificultad, int puntuacion, TipoReto tipo,
                                 HashMap<String, Object> params) {
        if (tipo.equals(TipoReto.PREGUNTA))
            return new RetoPregunta(ayudaUsada, duracion, dificultad, puntuacion, tipo, (String) params.get("enunciado"),
                    (String) params.get("respuesta1"), (String) params.get("respuesta2"), (String) params.get("respuesta3"),
                    (String) params.get("respuesta4"), (String) params.get("respuestaCorrecta"));
        else if (tipo.equals(TipoReto.AHORACADO))
            return new RetoAhorcado(ayudaUsada, duracion, dificultad, puntuacion, tipo, (String) params.get("palabra"),
                    new HashSet<Character>(), (int) params.get("intentos"));
        else
            throw new IllegalArgumentException("Tipo de reto desconocido.");

    }
}