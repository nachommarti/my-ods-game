package com.myodsgame;

import com.myodsgame.Builder.PartidaAhorcadoBuilder;
import com.myodsgame.Builder.PartidaBuilder;
import com.myodsgame.Builder.PartidaFrasesBuilder;
import com.myodsgame.Builder.PartidaPreguntasBuilder;
import com.myodsgame.Models.*;
import com.myodsgame.Repository.Repositorio;
import com.myodsgame.Repository.RepositorioFraseImpl;
import com.myodsgame.Repository.RepositorioPalabraImpl;
import com.myodsgame.Repository.RepositorioPreguntaImpl;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import org.junit.jupiter.api.AfterAll;
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
        assertTrue(builder.getPartida().getMusica() instanceof Media);
        assertTrue(builder.getPartida().getMusica().getSource().contains("src/main/resources/sounds/cancion_4.mp3"));

        builder.BuildImagenes();
        assertNotNull(builder.getPartida().getImagenFondo());
        assertTrue(builder.getPartida().getImagenFondo() instanceof Image);
        assertTrue(builder.getPartida().getImagenFondo().getUrl().contains("src\\main\\resources\\images\\fondo_frase.png"));

        assertNotNull(builder.getPartida());
        assertTrue(builder.getPartida() instanceof Partida);
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
        assertTrue(builder.getPartida().getMusica() instanceof Media);

        builder.BuildImagenes();
        assertNotNull(builder.getPartida().getImagenFondo());
        assertTrue(builder.getPartida().getImagenFondo() instanceof Image);

        assertNotNull(builder.getPartida());
        assertTrue(builder.getPartida() instanceof Partida);
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
        assertTrue(builder.getPartida().getMusica() instanceof Media);

        builder.BuildImagenes();
        assertNotNull(builder.getPartida().getImagenFondo());
        assertTrue(builder.getPartida().getImagenFondo() instanceof Image);

        assertNotNull(builder.getPartida());
        assertTrue(builder.getPartida() instanceof Partida);
    }

}



