package com.myodsgame.Builder;

import com.myodsgame.Models.Partida;
import com.myodsgame.Models.RetoAhorcado;
import com.myodsgame.Models.RetoPregunta;
import com.myodsgame.Repository.Repositorio;
import com.myodsgame.Repository.RepositorioPalabraImpl;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

import java.io.File;
import java.nio.file.Path;

public class PartidaAhorcadoBuilder extends PartidaBuilder{
    @Override
    public void BuildRetos() {
        Repositorio<RetoAhorcado, Integer> repositorioPalabra = new RepositorioPalabraImpl<>();
        partida.setRetos(repositorioPalabra.findByLimit(5, 4));
    }

    @Override
    public void BuildMusica() {
        partida.setMusica(new Media(new File("src/main/resources/sounds/cancion_2.mp3").toURI().toString()));
    }

    @Override
    public void BuildImagenes() {
        super.BuildImagenes();
        partida.setImagenFondo(new Image(Path.of("", "src", "main", "resources", "images", "fondo_ahorcado.png")
                .toAbsolutePath().toString()));
    }

    @Override
    public Partida getPartida() {
        return partida;
    }
}
