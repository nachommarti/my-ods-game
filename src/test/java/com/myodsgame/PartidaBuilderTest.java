package com.myodsgame;

import com.myodsgame.Builder.PartidaAhorcadoBuilder;
import com.myodsgame.Builder.PartidaBuilder;
import com.myodsgame.Builder.PartidaFrasesBuilder;
import com.myodsgame.Models.*;
import com.myodsgame.Repository.Repositorio;
import com.myodsgame.Repository.RepositorioFraseImpl;
import com.myodsgame.Repository.RepositorioPalabraImpl;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PartidaBuilderTest {

    static PartidaBuilder builder;

    @BeforeAll
    public static void setupClass() {
        JavaFXInitializer.initializeToolkit();
    }

    @AfterAll
    public static void checkBuilderFields(){
        assertEquals(builder.getPartida().getSondioFallo(), PartidaBuilder.SONIDO_FALLO);
        assertEquals(builder.getPartida().getSonidoAyuda(), PartidaBuilder.SONIDO_AYUDA);
        assertEquals(builder.getPartida().getSonidoAcierto(), PartidaBuilder.SONIDO_ACIERTO);
        assertEquals(builder.getPartida().getSonidoDerrota(), PartidaBuilder.SONIDO_DERROTA);
        assertEquals(builder.getPartida().getSonidoVictoria(), PartidaBuilder.SONIDO_VICTORIA);
        assertEquals(builder.getPartida().getDiezsecs(), PartidaBuilder.SONIDO_TICTAC);
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

        builder.BuildImagenes();
        assertNotNull(builder.getPartida().getImagenFondo());
        assertTrue(builder.getPartida().getImagenFondo() instanceof Image);

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

}



