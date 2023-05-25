package com.myodsgame.Builder;

import com.myodsgame.Models.Partida;
import com.myodsgame.Models.Reto;
import com.myodsgame.Repository.RepositorioFraseImpl;
import com.myodsgame.Repository.RepositorioPalabraImpl;
import com.myodsgame.Repository.RepositorioPreguntaImpl;
import com.myodsgame.Repository.RepositorioRetos;
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
        IServices servicios = new Services();
        RepositorioRetos repositorioPreguntas = new RepositorioPreguntaImpl();
        RepositorioRetos repositorioPalabras = new RepositorioPalabraImpl();
        RepositorioRetos repositorioFrases = new RepositorioFraseImpl();

        Random random = new Random();
        int preguntasFacil = random.nextInt(6);
        int palabrasFacil = random.nextInt(6 - preguntasFacil);
        int frasesFacil = 5 - preguntasFacil - palabrasFacil;

        int preguntasResto = random.nextInt(5);
        int palabrasResto = random.nextInt(5 - preguntasResto);
        int frasesResto = 4 - preguntasResto;
        List<Reto> retos = repositorioPreguntas.getRetosPorNivelDificultadInicial(1, preguntasFacil, preguntasResto);
        retos.addAll(repositorioPalabras.getRetosPorNivelDificultadInicial(1, palabrasFacil, palabrasResto));
        retos.addAll(repositorioFrases.getRetosPorNivelDificultadInicial(1, frasesFacil, frasesResto));
        retos.sort(Comparator.comparingInt(Reto::getDificultad));

        int lastNivelDificultad = -1;
        List<Integer> randomIndices = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < retos.size(); i++) {
            Reto reto = retos.get(i);
            if (reto.getDificultad() != lastNivelDificultad) {
                if (randomIndices.size() > 1) {
                    Collections.shuffle(randomIndices, rand);
                    servicios.reorderRetos(retos, i - randomIndices.size(), randomIndices);
                }
                lastNivelDificultad = reto.getDificultad();
                randomIndices.clear();
            }
            randomIndices.add(i);
        }
        if (randomIndices.size() > 1) {
            Collections.shuffle(randomIndices, rand);
            servicios.reorderRetos(retos, retos.size() - randomIndices.size(), randomIndices);
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
