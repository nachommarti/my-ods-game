package com.myodsgame;

import com.myodsgame.Builder.PartidaAhorcadoBuilder;
import com.myodsgame.Builder.PartidaFrasesBuilder;
import com.myodsgame.Builder.PartidaPreguntasBuilder;
import com.myodsgame.Models.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.nio.file.Path;

public class PartidaBuilderTest {

    @Test
    public void testPartidaPreguntasBuilder() {
        PartidaPreguntasBuilder builder = new PartidaPreguntasBuilder();
        builder.BuildRetos();
        builder.BuildMusica();
        builder.BuildImagenes();

        Partida partida = builder.getPartida();

        Assertions.assertEquals(9, partida.getRetos().size(), "Should contain the expected number of RetoPregunta");
        Assertions.assertEquals("src/main/resources/sounds/cancion_1.mp3", partida.getMusica().getSource(), "Should contain the expected music path");
        Assertions.assertEquals(Path.of("", "src", "main", "resources", "images", "fondo_preguntas.png").toAbsolutePath().toString(), partida.getImagenFondo().getUrl(), "Should contain the expected background image path");
    }

    @Test
    public void testPartidaAhorcadoBuilder() {
        PartidaAhorcadoBuilder builder = new PartidaAhorcadoBuilder();
        builder.BuildRetos();
        builder.BuildMusica();
        builder.BuildImagenes();

        Partida partida = builder.getPartida();

        Assertions.assertEquals(9, partida.getRetos().size(), "Should contain the expected number of RetoAhorcado");
        Assertions.assertEquals("src/main/resources/sounds/cancion_2.mp3", partida.getMusica().getSource(), "Should contain the expected music path");
        Assertions.assertEquals(Path.of("", "src", "main", "resources", "images", "fondo_ahorcado.png").toAbsolutePath().toString(), partida.getImagenFondo().getUrl(), "Should contain the expected background image path");
    }

    @Test
    public void testPartidaFrasesBuilder() {
        PartidaFrasesBuilder builder = new PartidaFrasesBuilder();
        builder.BuildRetos();
        builder.BuildMusica();
        builder.BuildImagenes();

        Partida partida = builder.getPartida();

        Assertions.assertEquals(9, partida.getRetos().size(), "Should contain the expected number of RetoFrase");
        Assertions.assertEquals("src/main/resources/sounds/cancion_4.mp3", partida.getMusica().getSource(), "Should contain the expected music path");
        Assertions.assertEquals(Path.of("", "src", "main", "resources", "images", "fondo_frase.png").toAbsolutePath().toString(), partida.getImagenFondo().getUrl(), "Should contain the expected background image path");
    }
}



