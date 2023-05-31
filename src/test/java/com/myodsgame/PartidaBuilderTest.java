package com.myodsgame;

import com.myodsgame.Builder.*;
import com.myodsgame.Models.*;
import com.myodsgame.Repository.Repositorio;
import com.myodsgame.Repository.RepositorioFraseImpl;
import com.myodsgame.Repository.RepositorioPalabraImpl;
import com.myodsgame.Repository.RepositorioPreguntaImpl;
import org.junit.jupiter.api.*;

import java.util.List;

import static com.myodsgame.Builder.PartidaBuilder.*;
import static org.junit.jupiter.api.Assertions.*;

class PartidaBuilderTest {
    PartidaBuilder builder;

    @BeforeAll
    public static void setupClass() {
        JavaFXInitializer.initializeToolkit();
    }

    @AfterEach
    public  void checkAfterTest(){
        builder.BuildSonidos();
        assertTrue(builder.getPartida().getSonidoAyuda().getSource().contains(SONIDO_AYUDA));
        assertTrue(builder.getPartida().getSonidoAcierto().getSource().contains(SONIDO_ACIERTO));
        assertTrue(builder.getPartida().getSondioFallo().getSource().contains(SONIDO_FALLO));
        assertTrue(builder.getPartida().getSonidoVictoria().getSource().contains(SONIDO_VICTORIA));
        assertTrue(builder.getPartida().getSonidoDerrota().getSource().contains(SONIDO_DERROTA));
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
        assertNotNull(builder.getPartida().getRetos());

        builder.BuildMusica();
        assertNotNull(builder.getPartida().getMusica());
        assertTrue(builder.getPartida().getMusica().getSource().contains("src/main/resources/sounds/cancion_3.mp3"));

        builder.BuildImagenes();
        assertNotNull(builder.getPartida().getImagenFondo());
        assertTrue(builder.getPartida().getImagenFondo().getUrl().contains("src\\main\\resources\\images\\fondo_mixta.png"));

        assertNotNull(builder.getPartida());

    }

}



