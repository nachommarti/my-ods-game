package com.myodsgame.Controllers;

import com.myodsgame.Models.Partida;
import com.myodsgame.Utils.EstadoJuego;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public class WindowShower {
    public Optional<ButtonType> showAyuda() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Usar pista");
        alert.setHeaderText("Canjear pista por " + EstadoJuego.getInstance().getPartida().getRetos().get(EstadoJuego.getInstance().getPartida().getRetoActual()).getPuntuacion() / 2 + " puntos");
        alert.setContentText("¿Deseas gastarte esos puntos en canjear esta pista?");
        ButtonType buttonType = new ButtonType("Cancelar");
        alert.getButtonTypes().add(buttonType);
        return alert.showAndWait();
    }
    public Stage showPopUp(Partida partidaActual) throws IOException {
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
}
