package com.myodsgame.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;


public class PantallaPartidasController implements Initializable {
    @FXML
    private Button botonAtras;
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
        retoPregunta.setGraphic(new ImageView(new Image(Path.of("", "src", "main", "resources", "images", "ayuda.png").toAbsolutePath().toString())));
        retoMixto.setGraphic(new ImageView(new Image(Path.of("", "src", "main", "resources", "images", "mixto.png").toAbsolutePath().toString())));
        botonAtras.setGraphic(new ImageView(new Image(Path.of("", "src", "main", "resources", "images", "flechaAtras.png").toAbsolutePath().toString())));
        desplegablePerfil.getItems().add("Perfil");
        desplegablePerfil.getItems().add("Estadísticas");
        desplegablePerfil.getItems().add("Cerrar sesión");
        //TODO: poner que los retos se desbloqueen con puntos
        //TODO: poner la funcionalidad de dar click al desplegable
    }

    @FXML
    void botonAtrasPulsado (ActionEvent event){}
    @FXML
    void retoPreguntaPulsado (ActionEvent event){}
    @FXML
    void retoMixtoPulsado (ActionEvent event){}
    @FXML
    void retoAhorcadoPulsado (ActionEvent event){}
}
