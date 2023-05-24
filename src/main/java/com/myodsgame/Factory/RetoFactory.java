package com.myodsgame.Factory;

import com.myodsgame.Models.Reto;
import com.myodsgame.Models.RetoAhorcado;
import com.myodsgame.Models.RetoPregunta;
import com.myodsgame.Utils.TipoReto;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RetoFactory {
    // De momento se pone el map de objetos, pero si al implementar todos los retos, todos son strings, se pasa a string
    public static Reto crearReto(boolean ayudaUsada, int duracion, int tiempoTicTac, int dificultad, int puntuacion,
                                 List<Integer> ODS, TipoReto tipo, HashMap<String, Object> params) {
        if (tipo.equals(TipoReto.PREGUNTA))
            return new RetoPregunta(ayudaUsada, duracion, tiempoTicTac, dificultad, puntuacion, tipo, ODS,
                    (String) params.get("enunciado"), (String) params.get("respuesta1"),
                    (String) params.get("respuesta2"), (String) params.get("respuesta3"),
                    (String) params.get("respuesta4"), (String) params.get("respuesta_correcta"));
        else if (tipo.equals(TipoReto.AHORACADO))
        {
            switch (dificultad)
            {
                case 1:
                    return new RetoAhorcado(ayudaUsada, duracion, tiempoTicTac, dificultad, puntuacion, tipo, ODS,
                            (String) params.get("palabra"), (String) params.get("pista"),10);

                case 2:
                    return new RetoAhorcado(ayudaUsada, duracion, tiempoTicTac, dificultad, puntuacion, tipo, ODS,
                            (String) params.get("palabra"), (String) params.get("pista"),7);

                case 3:
                    return new RetoAhorcado(ayudaUsada, duracion, tiempoTicTac, dificultad, puntuacion, tipo, ODS,
                            (String) params.get("palabra"), (String) params.get("pista"),5);

                default:
                    return null;
            }
        }
        else if(tipo.equals(TipoReto.FRASE)){
            switch (dificultad)
            {
                case 1:
                    return new RetoAhorcado(ayudaUsada, duracion, tiempoTicTac, dificultad, puntuacion, tipo, ODS,
                            (String) params.get("frase"), (String) params.get("pista"),12);

                case 2:
                    return new RetoAhorcado(ayudaUsada, duracion, tiempoTicTac, dificultad, puntuacion, tipo, ODS,
                            (String) params.get("frase"), (String) params.get("pista"),9);

                case 3:
                    return new RetoAhorcado(ayudaUsada, duracion, tiempoTicTac, dificultad, puntuacion, tipo, ODS,
                            (String) params.get("frase"), (String) params.get("pista"),6);

                default:
                    return null;
            }
        }

        else
            throw new IllegalArgumentException("Tipo de reto desconocido.");

    }
}