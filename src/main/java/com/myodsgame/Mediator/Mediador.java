package com.myodsgame.Mediator;

import com.myodsgame.Controllers.WindowShower;
import com.myodsgame.Models.Partida;
import com.myodsgame.Models.Reto;
import com.myodsgame.Services.Services;
import com.myodsgame.Utils.EstadoJuego;
import com.myodsgame.Utils.UserUtils;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Optional;

public abstract class Mediador {
    WindowShower windowShower = new WindowShower();
    Services servicios = new Services();
    public Optional<ButtonType> ayudaClicked(Reto retoActual, Label currentScore, Button ayuda) {
        Optional<ButtonType> result = windowShower.showAyuda();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            int puntos =  EstadoJuego.getInstance().getPartida().getPuntuacion() - retoActual.getPuntuacion()/2;
            EstadoJuego.getInstance().getPartida().setPuntuacion(puntos);
            currentScore.setText("Score: " + puntos);
            ayuda.setDisable(true);
            EstadoJuego.getInstance().getPartida().setAyudasRestantes(EstadoJuego.getInstance().getPartida().getAyudasRestantes() - 1);
        }
        return result;
    }

    public MediaPlayer musicaSetter() {
        Media musica = EstadoJuego.getInstance().getPartida().getMusica();
        MediaPlayer mediaPlayerMusic = new MediaPlayer(musica);
        mediaPlayerMusic.setVolume(0.15);
        return mediaPlayerMusic;
    }
    public MediaPlayer sonidoSetter(String sonidoPath, double volumen) {
        MediaPlayer mediaPlayerSonidos = new MediaPlayer(new Media(new File(sonidoPath).toURI().toString()));
        mediaPlayerSonidos.setVolume(volumen);
        return mediaPlayerSonidos;
    }

    public Stage showPopUp(Partida partidaActual) {
        Stage stage = null;
        try {
            stage = windowShower.showPopUp(partidaActual);
        }
        catch (IOException e) {System.out.println(e.getMessage());}
        return stage;
    }

    public void botonAbandonarPulsado(MediaPlayer mediaPlayerTicTac, MediaPlayer mediaPlayerMusic, Button abandonarBoton, Timeline timeline) {
        UserUtils.saveUserScore(EstadoJuego.getInstance().getPartida().getPuntuacionConsolidada());
        servicios.guardarPuntosDiarios(EstadoJuego.getInstance().getPartida().getPuntuacion());
        if(mediaPlayerTicTac != null) mediaPlayerTicTac.stop();
        if(mediaPlayerMusic != null) mediaPlayerMusic.stop();
        Stage stageOld = (Stage) abandonarBoton.getScene().getWindow();
        stageOld.close();
        EstadoJuego.getInstance().getPartida().setPartidaAbandonada(true);
        timeline.stop();
    }

    public void checkLose() {

    }
}
