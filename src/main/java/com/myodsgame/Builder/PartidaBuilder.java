package com.myodsgame.Builder;

import com.myodsgame.Models.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;

import java.io.File;
import java.nio.file.Path;

public abstract class PartidaBuilder implements IPartidaBuilder{
    protected int puntuacion;
    protected int vidas;
    protected int retoActual;
    protected boolean consolidado;
    protected int ayudasRestantes;
    protected Reto[] retos;
    protected Media musica, sonidoAyuda, sonidoAcierto, sondioFallo, sonidoVictoria, sonidoDerrota, diezsecs;
    protected Image imagenVidas, imagenFondo;

    public PartidaBuilder() {
        puntuacion = 0;
        vidas = 2;
        retoActual = 1;
        consolidado = false;
        ayudasRestantes = 3;
    }

    @Override
    public void BuildSonidos() {
        sonidoAyuda = new Media(new File("src/main/resources/sounds/pista_larga.mp3").toURI().toString());
        sonidoAcierto = new Media(new File("src/main/resources/sounds/Acierto.mp3").toURI().toString());
        sondioFallo = new Media(new File("src/main/resources/sounds/Fallo.mp3").toURI().toString());
        sonidoVictoria = new Media(new File("src/main/resources/sounds/Victoria.mp3").toURI().toString());
        sonidoDerrota = new Media(new File("src/main/resources/sounds/Partida_Perdida.mp3").toURI().toString());
        diezsecs = new Media(new File("src/main/resources/sounds/10S_tick.mp3").toURI().toString());
    }

    @Override
    public void BuildImagenes() {
        imagenVidas = new Image(Path.of("", "src", "main", "resources", "images", "vidas.png")
                .toAbsolutePath().toString());
    }
}
