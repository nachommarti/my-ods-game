package com.myodsgame.Mediator;

import com.myodsgame.Models.Reto;
import com.myodsgame.Utils.EstadoJuego;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.HashMap;
import java.util.Optional;

public abstract class Mediador {
    Optional<ButtonType> result;
    public Optional<ButtonType> ayudaClicked(Reto retoActual, Label currentScore, Button ayuda) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Usar pista");
        alert.setHeaderText("Canjear pista por " + EstadoJuego.getInstance().getPartida().getRetos().get(EstadoJuego.getInstance().getPartida().getRetoActual()).getPuntuacion() / 2 + " puntos");
        alert.setContentText("¿Deseas gastarte esos puntos en canjear esta pista?");
        ButtonType buttonType = new ButtonType("Cancelar");
        alert.getButtonTypes().add(buttonType);
        result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            int puntos =  EstadoJuego.getInstance().getPartida().getPuntuacion() - retoActual.getPuntuacion()/2;
            EstadoJuego.getInstance().getPartida().setPuntuacion(puntos);
            currentScore.setText("Score: " + puntos);
            ayuda.setDisable(true);
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
}
