package com.myodsgame.Builder;

import com.myodsgame.Models.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;

import java.io.File;
import java.nio.file.Path;

public abstract class PartidaBuilder implements IPartidaBuilder{
    protected Partida partida;

    public PartidaBuilder() {
        partida = new Partida();
    }

    @Override
    public void BuildSonidos() {
        partida.setSonidoAyuda(new Media(new File("src/main/resources/sounds/pista_larga.mp3").toURI().toString()));
        partida.setSonidoAcierto(new Media(new File("src/main/resources/sounds/Acierto.mp3").toURI().toString()));
        partida.setSondioFallo(new Media(new File("src/main/resources/sounds/Fallo.mp3").toURI().toString()));
        partida.setSonidoVictoria(new Media(new File("src/main/resources/sounds/Victoria.mp3").toURI().toString()));
        partida.setSonidoDerrota(new Media(new File("src/main/resources/sounds/Partida_Perdida.mp3").toURI().toString()));
        partida.setDiezsecs(new Media(new File("src/main/resources/sounds/10S_tick.mp3").toURI().toString()));
    }

    @Override
    public void BuildImagenes() {
        partida.setImagenVidas(new Image(Path.of("", "src", "main", "resources", "images", "vidas.png")
                .toAbsolutePath().toString()));
    }
}
