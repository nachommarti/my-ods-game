package com.myodsgame.Mediator;

import com.myodsgame.Models.Partida;
import com.myodsgame.Models.Reto;
import com.myodsgame.Utils.EstadoJuego;
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

    public Stage showPopUp(Partida partidaActual) {
        try
        {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/com/myodsgame/popUpReto.fxml"));
            BorderPane root = myLoader.load();
            Scene scene = new Scene (root, 357, 184);
            Stage stage = new Stage();
            stage.setScene(scene);
            if (partidaActual.getRetoActual() == 10)
            {
                stage.setTitle("¡Ganaste!");
            }
            else if (!partidaActual.getRetosFallados()[partidaActual.getRetoActual()]) {
                stage.setTitle("¡Enhorabuena!");
            } else {
                stage.setTitle("Oops!");
            }
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);
            stage.getIcons().add(new Image(Path.of("", "src", "main", "resources", "images", "LogoODS.png").toAbsolutePath().toString()));
            stage.setOnCloseRequest(e -> {
                System.exit(0);
            });
            return stage;
        }
        catch (IOException e) {System.out.println(e.getMessage());}
        return null;
    }
}
