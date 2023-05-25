package com.myodsgame.Builder;

import com.myodsgame.Models.Partida;
import com.myodsgame.Repository.RepositorioFraseImpl;
import com.myodsgame.Repository.RepositorioRetos;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

import java.io.File;
import java.nio.file.Path;

public class PartidaFrasesBuilder extends PartidaBuilder {

    @Override
    public void BuildRetos() {
        RepositorioRetos repositorioFrase = new RepositorioFraseImpl();
        partida.setRetos(repositorioFrase.getRetosPorNivelDificultadInicial(1, 5, 4));
    }

    @Override
    public void BuildMusica() {
        partida.setMusica(new Media(new File("src/main/resources/sounds/cancion_3.mp3").toURI().toString()));
    }

    @Override
    public void BuildImagenes() {
        super.BuildImagenes();
       //todo add imagenes??
    }

    @Override
    public Partida getPartida() {
        return partida;
    }
}
