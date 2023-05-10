package com.myodsgame.Controllers;

import com.myodsgame.Builder.PartidaDirector;
import com.myodsgame.Builder.PartidaPreguntasBuilder;
import com.myodsgame.Models.Partida;
import com.myodsgame.Models.Reto;
import com.myodsgame.Models.RetoPregunta;
import com.myodsgame.Utils.EstadoJuego;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Random;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {
    @FXML
    private Pane pane;
    @FXML
    private Button loginButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image(Path.of("", "src", "main", "resources", "images", "mundoGIF.gif").toAbsolutePath().toString());
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        pane.setBackground(background);
        loginButton.setDisable(false);
    }

    @FXML
    void loginButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/com/myodsgame/loginUsuario-view.fxml"));
        BorderPane root = myLoader.load();

        Scene scene = new Scene (root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.getIcons().add(new Image(Path.of("", "src", "main", "resources", "images", "LogoODS.png").toAbsolutePath().toString()));
        stage.show();
    }

}