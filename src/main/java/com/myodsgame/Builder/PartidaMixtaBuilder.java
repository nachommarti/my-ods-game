package com.myodsgame.Builder;

import com.myodsgame.Models.Partida;
import com.myodsgame.Models.Reto;
import com.myodsgame.Repository.RepositorioRetoMixtoImpl;
import com.myodsgame.Services.IServices;
import com.myodsgame.Services.Services;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

import java.io.File;
import java.nio.file.Path;
import java.util.*;

public class PartidaMixtaBuilder extends PartidaBuilder {
    @Override
    public void BuildRetos() {
        List<Reto> retos = new RepositorioRetoMixtoImpl().getRetosPorNivelDificultadInicial(1);
        retos.sort(Comparator.comparingInt(Reto::getDificultad));

        int lastNivelDificultad = -1;
        List<Integer> randomIndices = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < retos.size(); i++) {
            Reto reto = retos.get(i);
            if (reto.getDificultad() != lastNivelDificultad) {
                if (randomIndices.size() > 1) {
                    Collections.shuffle(randomIndices, rand);
                    IServices.reorderRetos(retos, i - randomIndices.size(), randomIndices);
                }
                lastNivelDificultad = reto.getDificultad();
                randomIndices.clear();
            }
            randomIndices.add(i);
        }
        if (randomIndices.size() > 1) {
            Collections.shuffle(randomIndices, rand);
            IServices.reorderRetos(retos, retos.size() - randomIndices.size(), randomIndices);
        }
        partida.setRetos(retos);
    }

    @Override
    public void BuildMusica() {
        partida.setMusica(new Media(new File("src/main/resources/sounds/cancion_3.mp3").toURI().toString()));
    }

    @Override
    public void BuildImagenes() {
        super.BuildImagenes();
        partida.setImagenFondo(new Image(Path.of("", "src", "main", "resources", "images", "fondo_mixta.png")
                .toAbsolutePath().toString()));
    }

    @Override
    public Partida getPartida() {
        return partida;
    }
}
