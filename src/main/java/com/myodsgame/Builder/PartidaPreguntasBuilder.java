package com.myodsgame.Builder;

import com.myodsgame.Factory.RetoPreguntaFactory;
import com.myodsgame.Models.Partida;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;

import java.io.File;
import java.nio.file.Path;

public class PartidaPreguntasBuilder extends PartidaBuilder{
    @Override
    public void BuildRetos() {
        retos = new RetoPreguntaFactory().crearRetos();
    }

    @Override
    public void BuildMusica() {
        musica = new Media(new File("src/main/resources/sounds/cancion_1.mp3").toURI().toString());
    }

    @Override
    public void BuildImagenes() {
        super.BuildImagenes();
        imagenFondo = new Image(Path.of("", "src", "main", "resources", "images", "fondo_preguntas.png")
                .toAbsolutePath().toString());
    }

    @Override
    public Partida getPartida() {
        return new Partida(puntuacion, vidas, retoActual, consolidado, ayudasRestantes, retos, musica, sonidoAyuda,
                sonidoAcierto, sondioFallo, sonidoVictoria, sonidoDerrota, diezsecs, imagenVidas, imagenFondo);
    }
}
