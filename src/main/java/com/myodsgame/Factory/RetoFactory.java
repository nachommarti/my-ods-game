package com.myodsgame.Factory;

import com.myodsgame.Models.Reto;
import com.myodsgame.Models.RetoAhorcado;
import com.myodsgame.Models.RetoFrase;
import com.myodsgame.Models.RetoPregunta;
import com.myodsgame.Utils.TipoReto;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RetoFactory {
    // De momento se pone el map de objetos, pero si al implementar todos los retos, todos son strings, se pasa a string
    public static Reto crearReto(int id, boolean ayudaUsada, int duracion, int tiempoTicTac, int dificultad, int puntuacion,
                                 List<Integer> ODS, TipoReto tipo, HashMap<String, String> params) {
        if (tipo.equals(TipoReto.PREGUNTA)) {
            return new RetoPregunta(id, ayudaUsada, duracion, tiempoTicTac, dificultad, puntuacion, tipo, ODS,
                     params.get("enunciado"),  params.get("respuesta1"),
                     params.get("respuesta2"),  params.get("respuesta3"),
                     params.get("respuesta4"),  params.get("respuesta_correcta"));
        } else if (tipo.equals(TipoReto.AHORACADO)) {
            switch (dificultad)
            {
                case 1:
                    return new RetoAhorcado(id, ayudaUsada, duracion, tiempoTicTac, dificultad, puntuacion, tipo, ODS,
                             params.get("palabra"),  params.get("pista"),10);

                case 2:
                    return new RetoAhorcado(id, ayudaUsada, duracion, tiempoTicTac, dificultad, puntuacion, tipo, ODS,
                             params.get("palabra"),  params.get("pista"),7);

                case 3:
                    return new RetoAhorcado(id, ayudaUsada, duracion, tiempoTicTac, dificultad, puntuacion, tipo, ODS,
                             params.get("palabra"),  params.get("pista"),5);

                default:
                    return null;
            }
        } else if(tipo.equals(TipoReto.FRASE)) {
            switch (dificultad)
            {
                case 1:
                    return new RetoFrase(id, ayudaUsada, duracion, tiempoTicTac, dificultad, puntuacion, tipo, ODS,
                             params.get("frase"),  params.get("pista"),12);

                case 2:
                    return new RetoFrase(id, ayudaUsada, duracion, tiempoTicTac, dificultad, puntuacion, tipo, ODS,
                             params.get("frase"),  params.get("pista"),9);

                case 3:
                    return new RetoFrase(id, ayudaUsada, duracion, tiempoTicTac, dificultad, puntuacion, tipo, ODS,
                             params.get("frase"),  params.get("pista"),6);

                default:
                    return null;
            }
        } else
            throw new IllegalArgumentException("Tipo de reto desconocido.");
    }
}