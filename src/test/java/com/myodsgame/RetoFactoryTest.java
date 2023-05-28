package com.myodsgame;

import com.myodsgame.Factory.RetoFactory;
import com.myodsgame.Models.Reto;
import com.myodsgame.Models.RetoAhorcado;
import com.myodsgame.Models.RetoFrase;
import com.myodsgame.Models.RetoPregunta;
import com.myodsgame.Utils.TipoReto;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RetoFactoryTest {
    int id = 1;
    boolean ayudaUsada = false;
    int duracion = 10;
    int tiempoTicTac = 60;
    int dificultad = 2;
    int puntuacion = 100;
    List<Integer> ODS = Arrays.asList(1, 2, 3);
    HashMap<String, String> params = new HashMap<>();

    @Test
    public void testCrearRetoPregunta() {
        params.put("enunciado", "¿Cuál es la capital de Francia?");
        params.put("respuesta1", "Berlín");
        params.put("respuesta2", "París");
        params.put("respuesta3", "Madrid");
        params.put("respuesta4", "Roma");
        params.put("respuesta_correcta", "París");

        Reto reto = RetoFactory.crearReto(id, ayudaUsada, duracion, tiempoTicTac, dificultad, puntuacion, ODS, TipoReto.PREGUNTA, params);
        assertTrue(reto instanceof RetoPregunta);
        params.clear();
    }

    @Test
    public void testCrearRetoAhorcado() {
        params.put("palabra", "París");
        params.put("pista", "Es la capital de un país");

        Reto reto = RetoFactory.crearReto(id, ayudaUsada, duracion, tiempoTicTac, dificultad, puntuacion, ODS, TipoReto.AHORACADO, params);
        assertTrue(reto instanceof RetoAhorcado);
        params.clear();
    }

    @Test
    public void testCrearRetoFrase() {
        params.put("frase", "El zorro marrón rápido salta sobre el perro perezoso");
        params.put("pista", "Es un pangrama");

        Reto reto = RetoFactory.crearReto(id, ayudaUsada, duracion, tiempoTicTac, dificultad, puntuacion, ODS, TipoReto.FRASE, params);
        assertTrue(reto instanceof RetoFrase);
        params.clear();
    }

    @Test
    public void testCrearRetoUnknownTipoReto() {
        params.put("frase", "El zorro marrón rápido salta sobre el perro perezoso");
        params.put("pista", "Es un pangrama");

        final TipoReto unknownTipoReto = TipoReto.valueOf("DESCONOCIDO");
        assertThrows(IllegalArgumentException.class, () -> {
            RetoFactory.crearReto(id, ayudaUsada, duracion, tiempoTicTac, dificultad, puntuacion, ODS, unknownTipoReto, params);
        });
        params.clear();
    }
}
