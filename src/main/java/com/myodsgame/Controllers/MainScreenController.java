package com.myodsgame.Controllers;

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
    void fastGameButtonClicked(ActionEvent event) throws IOException {
        int randomChallenge = 1 + new Random().nextInt(4);
        String viewRoute = null;
        String challengeTitle = null;

        switch (randomChallenge){
            case 1:
                viewRoute = "/com/myodsgame/retoPregunta-view.fxml";
                challengeTitle = "Reto Pregunta";
                break;
            case 2:
                viewRoute = "/com/myodsgame/retoPregunta-view.fxml";
                challengeTitle = "Reto Sopa Letras";
                break;
            case 3:
                viewRoute = "/com/myodsgame/retoPregunta-view.fxml";
                challengeTitle = "Reto Ahorcado";
                break;
            case 4:
                viewRoute = "/com/myodsgame/retoPregunta-view.fxml";
                challengeTitle = "Reto ni idea";
                break;
        }

        FXMLLoader myLoader = new FXMLLoader(getClass().getResource(viewRoute));
        BorderPane root = myLoader.load();

        Scene scene = new Scene (root, 600, 600);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(challengeTitle);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.getIcons().add(new Image(Path.of("", "src", "main", "resources", "images", "LogoODS.png").toAbsolutePath().toString()));
        stage.show();
    }

    @FXML
    void loginButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/com/myodsgame/pantallaPartidas.fxml"));
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

    @FXML
    void logOutClicked(ActionEvent event) {}

    @FXML
    void settingsClicked(ActionEvent event) {}

}