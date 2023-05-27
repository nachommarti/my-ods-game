package com.myodsgame.Builder;

import com.myodsgame.Models.Partida;
import com.myodsgame.Models.RetoPregunta;
import com.myodsgame.Repository.Repositorio;
import com.myodsgame.Repository.RepositorioPreguntaImpl;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

import java.io.File;
import java.nio.file.Path;

public class PartidaPreguntasBuilder extends PartidaBuilder{
    @Override
    public void BuildRetos() {
        Repositorio<RetoPregunta, Integer> repositorioPregunta = new RepositorioPreguntaImpl<>();
        partida.setRetos(repositorioPregunta.findByLimit(5, 4));
    }

    @Override
    public void BuildMusica() {
        partida.setMusica(new Media(new File("src/main/resources/sounds/cancion_1.mp3").toURI().toString()));
    }

    @Override
    public void BuildImagenes() {
        super.BuildImagenes();
        partida.setImagenFondo(new Image(Path.of("", "src", "main", "resources", "images", "fondo_preguntas.png")
                .toAbsolutePath().toString()));
    }

    @Override
    public Partida getPartida() {
        return partida;
    }
}
