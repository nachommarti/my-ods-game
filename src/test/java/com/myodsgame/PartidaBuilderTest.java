package com.myodsgame;

import com.myodsgame.Builder.*;
import com.myodsgame.Models.*;
import com.myodsgame.Repository.Repositorio;
import com.myodsgame.Repository.RepositorioFraseImpl;
import com.myodsgame.Repository.RepositorioPalabraImpl;
import com.myodsgame.Repository.RepositorioPreguntaImpl;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PartidaBuilderTest {

    PartidaBuilder builder;

    @BeforeAll
    public static void setupClass() {
        JavaFXInitializer.initializeToolkit();
    }

    @Test
    public void testPartidaFrasesBuilder() {
        builder = new PartidaFrasesBuilder();
        Repositorio<RetoFrase, Integer> repositorioFrase = new RepositorioFraseImpl<>();
        List<RetoFrase> retos = repositorioFrase.findByLimit(5, 4);

        builder.BuildRetos();
        assertNotNull(builder.getPartida().getRetos());
        assertEquals(retos.size(), builder.getPartida().getRetos().size());

        builder.BuildMusica();
        assertNotNull(builder.getPartida().getMusica());
        assertTrue(builder.getPartida().getMusica().getSource().contains("src/main/resources/sounds/cancion_4.mp3"));

        builder.BuildImagenes();
        assertNotNull(builder.getPartida().getImagenFondo());
        assertTrue(builder.getPartida().getImagenFondo().getUrl().contains("src\\main\\resources\\images\\fondo_frase.png"));

        assertNotNull(builder.getPartida());
    }

    @Test
    public void testPartidaAhorcadoBuilder() {
        builder = new PartidaAhorcadoBuilder();
        Repositorio<RetoAhorcado, Integer> repositorioPalabra = new RepositorioPalabraImpl<>();
        List<RetoAhorcado> retos = repositorioPalabra.findByLimit(5, 4);

        builder.BuildRetos();
        assertNotNull(builder.getPartida().getRetos());
        assertEquals(retos.size(), builder.getPartida().getRetos().size());

        builder.BuildMusica();
        assertNotNull(builder.getPartida().getMusica());
        assertTrue(builder.getPartida().getMusica().getSource().contains("src/main/resources/sounds/cancion_2.mp3"));

        builder.BuildImagenes();
        assertNotNull(builder.getPartida().getImagenFondo());
        assertTrue(builder.getPartida().getImagenFondo().getUrl().contains("src\\main\\resources\\images\\fondo_ahorcado.png"));

        assertNotNull(builder.getPartida());
    }

    @Test
    public void testPartidaPreguntasBuilder() {
        builder = new PartidaPreguntasBuilder();
        Repositorio<RetoPregunta, Integer> repositorioPregunta = new RepositorioPreguntaImpl<>();
        List<RetoPregunta> retos = repositorioPregunta.findByLimit(5, 4);

        builder.BuildRetos();
        assertNotNull(builder.getPartida().getRetos());
        assertEquals(retos.size(), builder.getPartida().getRetos().size());

        builder.BuildMusica();
        assertNotNull(builder.getPartida().getMusica());
        assertTrue(builder.getPartida().getMusica().getSource().contains("src/main/resources/sounds/cancion_1.mp3"));

        builder.BuildImagenes();
        assertNotNull(builder.getPartida().getImagenFondo());
        assertTrue(builder.getPartida().getImagenFondo().getUrl().contains("src\\main\\resources\\images\\fondo_preguntas.png"));

        assertNotNull(builder.getPartida());
    }

    @Test
    public void testPartidaMixtaBuilder() {
        builder = new PartidaMixtaBuilder();
        builder.BuildRetos();

        List<? extends Reto> retos =  builder.getPartida().getRetos();
        Assertions.assertNotNull(retos);
        Assertions.assertEquals(builder.getPartida().getRetos().size(), retos.size());
        builder.BuildMusica();

        Media musica = builder.getPartida().getMusica();
        Assertions.assertNotNull(musica);

        builder.BuildImagenes();

        Image imagenFondo = builder.getPartida().getImagenFondo();
        Assertions.assertNotNull(imagenFondo);

        Partida partida = builder.getPartida();

        Assertions.assertNotNull(partida);
        Assertions.assertNotNull(partida.getRetos());
        Assertions.assertNotNull(partida.getMusica());
        Assertions.assertNotNull(partida.getImagenFondo());
    }

}



