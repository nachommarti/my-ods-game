package com.myodsgame.Builder;

import com.myodsgame.Models.*;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

import java.io.File;
import java.nio.file.Path;

public abstract class PartidaBuilder implements IPartidaBuilder{
    protected Partida partida;
    public static String SONIDO_AYUDA = "src/main/resources/sounds/pista_larga.mp3";
    public static String SONIDO_ACIERTO = "src/main/resources/sounds/Acierto.mp3";
    public static String SONIDO_FALLO = "src/main/resources/sounds/Fallo.mp3";
    public static String SONIDO_VICTORIA = "src/main/resources/sounds/Victoria.mp3";
    public static String SONIDO_DERROTA = "src/main/resources/sounds/Partida_Perdida.mp3";
    public static String SONIDO_TICTAC = "src/main/resources/sounds/10S_tick.mp3";

    public PartidaBuilder() {
        partida = new Partida();
    }

    @Override
    public void BuildSonidos() {
        partida.setSonidoAyuda(new Media(new File(SONIDO_AYUDA).toURI().toString()));
        partida.setSonidoAcierto(new Media(new File(SONIDO_ACIERTO).toURI().toString()));
        partida.setSondioFallo(new Media(new File(SONIDO_FALLO).toURI().toString()));
        partida.setSonidoVictoria(new Media(new File(SONIDO_VICTORIA).toURI().toString()));
        partida.setSonidoDerrota(new Media(new File(SONIDO_DERROTA).toURI().toString()));
        partida.setDiezsecs(new Media(new File(SONIDO_TICTAC).toURI().toString()));
    }

    @Override
    public void BuildImagenes() {
        partida.setImagenVidas(new Image(Path.of("", "src", "main", "resources", "images", "vidas.png")
                .toAbsolutePath().toString()));
    }
}
