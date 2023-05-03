package com.myodsgame.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;


public class PantallaPartidasController implements Initializable {
    @FXML
    private Button retoPregunta;
    @FXML
    private Button retoAhorcado;
    @FXML
    private Button retoMixto;
    @FXML
    private ComboBox<String> desplegablePerfil;
    @FXML
    private Label puntosAlmacenados;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        retoAhorcado.setGraphic(new ImageView(new Image(Path.of("", "src", "main", "resources", "images", "ahorcado.png").toAbsolutePath().toString())));
        retoPregunta.setGraphic(new ImageView(new Image(Path.of("", "src", "main", "resources", "images", "retoPregunta.png").toAbsolutePath().toString())));
        retoMixto.setGraphic(new ImageView(new Image(Path.of("", "src", "main", "resources", "images", "mixto.png").toAbsolutePath().toString())));
        desplegablePerfil.getItems().add("Perfil");
        desplegablePerfil.getItems().add("Estadísticas");
        desplegablePerfil.getItems().add("Cerrar sesión");

        //TODO: poner que los retos se desbloqueen con puntos
        //TODO: poner la funcionalidad de dar click al desplegable
    }
    @FXML
    void retoPreguntaPulsado (ActionEvent event) throws IOException {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/com/myodsgame/retoPregunta-viex.fxml"));
        BorderPane root = myLoader.load();

        Scene scene = new Scene (root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Reto Pregunta");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.show();

        Node source = (Node) event.getSource();
        Stage oldStage = (Stage) source.getScene().getWindow();
        oldStage.close();
    }
    @FXML
    void retoMixtoPulsado (ActionEvent event)
    {

    }
    @FXML
    void retoAhorcadoPulsado (ActionEvent event) throws IOException {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/com/myodsgame/retoAhorcado-view.fxml"));
        BorderPane root = myLoader.load();

        Scene scene = new Scene (root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Reto Ahorcado");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.show();

        Node source = (Node) event.getSource();
        Stage oldStage = (Stage) source.getScene().getWindow();
        oldStage.close();
    }
}
