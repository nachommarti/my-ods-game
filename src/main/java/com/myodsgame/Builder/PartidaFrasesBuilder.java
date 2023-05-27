package com.myodsgame.Builder;

import com.myodsgame.Models.Partida;
import com.myodsgame.Models.RetoFrase;
import com.myodsgame.Repository.Repositorio;
import com.myodsgame.Repository.RepositorioFraseImpl;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

import java.io.File;
import java.nio.file.Path;

public class PartidaFrasesBuilder extends PartidaBuilder {

    @Override
    public void BuildRetos() {
        Repositorio<RetoFrase, Integer> repositorioFrase = new RepositorioFraseImpl<>();
        partida.setRetos(repositorioFrase.findByLimit(5, 4));
    }

    @Override
    public void BuildMusica() {
        partida.setMusica(new Media(new File("src/main/resources/sounds/cancion_4.mp3").toURI().toString()));
    }

    @Override
    public void BuildImagenes() {
        super.BuildImagenes();
        partida.setImagenFondo(new Image(Path.of("", "src", "main", "resources", "images", "fondo_frase.png")
                .toAbsolutePath().toString()));
    }

    @Override
    public Partida getPartida() {
        return partida;
    }
}
