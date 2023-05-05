package com.myodsgame.Controllers;

import com.myodsgame.Builder.PartidaAhorcadoBuilder;
import com.myodsgame.Builder.PartidaDirector;
import com.myodsgame.Builder.PartidaPreguntasBuilder;
import com.myodsgame.Models.Partida;
import com.myodsgame.Models.Usuario;
import com.myodsgame.Utils.EstadoJuego;
import com.myodsgame.Utils.TipoReto;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;


public class PantallaPartidasController implements Initializable {
    @FXML
    private BorderPane borderPane;
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
    int puntosJugador = EstadoJuego.getInstance().getUsuario().getEstadistica().getPuntosTotales();
    final int puntosNecesarios = 1000;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        retoAhorcado.setGraphic(new ImageView(new Image(Path.of("", "src", "main", "resources", "images", "ahorcado.png").toAbsolutePath().toString())));
        retoPregunta.setGraphic(new ImageView(new Image(Path.of("", "src", "main", "resources", "images", "retoPregunta.png").toAbsolutePath().toString())));
        retoMixto.setGraphic(new ImageView(new Image(Path.of("", "src", "main", "resources", "images", "mixto.png").toAbsolutePath().toString())));
        desplegablePerfil.getItems().add("Perfil");
        desplegablePerfil.getItems().add("Estadísticas");
        desplegablePerfil.getItems().add("Cerrar sesión");
        puntosAlmacenados.setText("Puntos totales: " + puntosJugador);

        if (puntosJugador < puntosNecesarios)
        {
            retoAhorcado.setDisable(true);
            retoMixto.setDisable(true);
        }

        desplegablePerfil.valueProperty().addListener((ov, p1, p2) ->
                {
                    if (desplegablePerfil.getValue() == "Perfil")
                    {
                        //hacer que lleve al perfil
                    }
                    else if (desplegablePerfil.getValue() == "Estadísticas")
                    {
                        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/com/myodsgame/estadisticas.fxml"));
                        BorderPane root = null;
                        try {
                            root = myLoader.load();
                        }
                        catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        Scene scene = new Scene (root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setTitle("Estadísticas");
                        stage.initModality(Modality.WINDOW_MODAL);
                        stage.setResizable(false);
                        stage.show();
                    }
                    else if (desplegablePerfil.getValue() == "Cerrar sesión")
                    {
                        Stage stage = (Stage) desplegablePerfil.getScene().getWindow();
                        stage.close();
                    }
                }
                );
    }
    @FXML
    void retoPreguntaPulsado (ActionEvent event) throws IOException {
        PartidaDirector partidaDirector = new PartidaDirector(new PartidaPreguntasBuilder());
        Partida partida = partidaDirector.BuildPartida();
        EstadoJuego.getInstance().setPartida(partida);
        for(int i = 0; i < partida.getRetos().size(); i++){
            loadReto("retoPregunta", "Reto Pregunta", false);
        }
        Node source = (Node) event.getSource();
        Stage oldStage = (Stage) source.getScene().getWindow();
        oldStage.close();
    }



    @FXML
    void retoMixtoPulsado (ActionEvent event)
    {

        Node source = (Node) event.getSource();
        Stage oldStage = (Stage) source.getScene().getWindow();
        oldStage.close();
    }
    @FXML
    void retoAhorcadoPulsado (ActionEvent event) throws IOException {
        PartidaDirector partidaDirector = new PartidaDirector(new PartidaAhorcadoBuilder());
        Partida partida = partidaDirector.BuildPartida();
        EstadoJuego.getInstance().setPartida(partida);
        for(int i = 0; i < partida.getRetos().size(); i++){
            loadReto("retoAhorcado", "Reto Ahorcado", false);
        }

        Node source = (Node) event.getSource();
        Stage oldStage = (Stage) source.getScene().getWindow();
        oldStage.close();
    }

    private void loadReto(String reto, String titulo, boolean mixto) throws IOException {
        if (mixto)
        {
            double aux = Math.random();
            if (aux < 0.5)
            {
                reto = "retoPregunta";
                titulo = "Reto Pregunta";
            }
            else
            {
                reto = "retoAhorcado";
                titulo = "Reto Ahorcado";
            }
        }
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/com/myodsgame/" + reto + "-view.fxml"));
        BorderPane root = myLoader.load();
        Scene scene = new Scene (root, 600, 600);
        Stage  stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(titulo);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.getIcons().add(new Image(Path.of("", "src", "main", "resources", "images", "LogoODS.png").toAbsolutePath().toString()));
        stage.setOnCloseRequest(e -> {
            System.exit(0);
        });
        stage.showAndWait();
    }
}
