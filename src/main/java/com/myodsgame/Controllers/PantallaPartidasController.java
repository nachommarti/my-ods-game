package com.myodsgame.Controllers;

import com.myodsgame.Builder.PartidaAhorcadoBuilder;
import com.myodsgame.Builder.PartidaDirector;
import com.myodsgame.Builder.PartidaMixtaBuilder;
import com.myodsgame.Builder.PartidaPreguntasBuilder;
import com.myodsgame.Models.Partida;
import com.myodsgame.Models.Reto;
import com.myodsgame.ODSGame;
import com.myodsgame.Utils.EstadoJuego;
import com.myodsgame.Utils.TipoReto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
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
    private ImageView imagenUsuario;
    @FXML
    private Label puntosAlmacenados;
    int puntosJugador = EstadoJuego.getInstance().getUsuario().getEstadistica().getPuntosTotales();
    final int puntosNecesarios = 1000;
    @FXML
    private StackPane stackPane;
    String avatar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        retoAhorcado.setGraphic(new ImageView(new Image(Path.of("", "src", "main", "resources", "images", "ahorcado.png").toAbsolutePath().toString())));
        retoPregunta.setGraphic(new ImageView(new Image(Path.of("", "src", "main", "resources", "images", "retoPregunta.png").toAbsolutePath().toString())));
        retoMixto.setGraphic(new ImageView(new Image(Path.of("", "src", "main", "resources", "images", "mixto.png").toAbsolutePath().toString())));
        desplegablePerfil.getItems().add("Perfil");
        desplegablePerfil.getItems().add("Estadísticas");
        desplegablePerfil.getItems().add("Cerrar sesión");
        desplegablePerfil.setStyle("-fx-background-color: null");
        puntosAlmacenados.setText("Puntos totales: " + puntosJugador);
        avatar = EstadoJuego.getInstance().getUsuario().getAvatar();
        imagenUsuario.setImage(new Image(Path.of("", "src", "main", "resources", "images", avatar.substring(avatar.lastIndexOf("/") + 1)).toAbsolutePath().toString()));

        if (puntosJugador < puntosNecesarios)
        {
            Tooltip mensajeBloqueado = new Tooltip("Debes conseguir "  + puntosNecesarios + " puntos para poder jugar esta partida");
            mensajeBloqueado.setShowDelay(Duration.seconds(0));
            retoAhorcado.setTooltip(mensajeBloqueado);
            retoMixto.setTooltip(mensajeBloqueado);

            retoAhorcado.setGraphic(new ImageView(new Image(Path.of("", "src", "main", "resources", "images", "ahorcado sin desbloquear.png").toAbsolutePath().toString())));
            retoMixto.setGraphic(new ImageView(new Image(Path.of("", "src", "main", "resources", "images", "mixto sin desbloquear.png").toAbsolutePath().toString())));

        }

        desplegablePerfil.valueProperty().addListener((ov, p1, p2) ->
                {
                    if (desplegablePerfil != null) {
                        if (desplegablePerfil.getSelectionModel().isSelected(0)) {
                            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/com/myodsgame/perfil-view.fxml"));
                            BorderPane root = null;
                            try {
                                root = myLoader.load();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.setTitle("Perfil");
                            stage.initModality(Modality.WINDOW_MODAL);
                            stage.getIcons().add(new Image(Path.of("", "src", "main", "resources", "images", "LogoODS.png").toAbsolutePath().toString()));
                            stage.setResizable(false);
                            stage.show();

                            Stage stageOld = (Stage) desplegablePerfil.getScene().getWindow();
                            stageOld.close();
                        } else if (desplegablePerfil.getSelectionModel().isSelected(1)) {
                            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/com/myodsgame/estadisticas.fxml"));
                            BorderPane root = null;
                            try {
                                root = myLoader.load();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.setTitle("Estadísticas");
                            stage.initModality(Modality.WINDOW_MODAL);
                            stage.getIcons().add(new Image(Path.of("", "src", "main", "resources", "images", "LogoODS.png").toAbsolutePath().toString()));
                            stage.setResizable(false);
                            stage.show();

                            Stage stageOld = (Stage) desplegablePerfil.getScene().getWindow();
                            stageOld.close();
                        } else if (desplegablePerfil.getSelectionModel().isSelected(2)) {
                            Stage stage = (Stage) desplegablePerfil.getScene().getWindow();
                            stage.close();

                            try{FXMLLoader fxmlLoader = new FXMLLoader(ODSGame.class.getResource("mainScreen-view.fxml"));
                                Scene scene = new Scene(fxmlLoader.load(), 600, 415);
                                stage.setTitle("ODS Game");
                                stage.setScene(scene);
                                stage.setResizable(false);
                                stage.getIcons().add(new Image(Path.of("", "src", "main", "resources", "images", "LogoODS.png").toAbsolutePath().toString()));

                                stage.show();}
                            catch(IOException e){}

                        }
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
            if(EstadoJuego.getInstance().getPartida().isPartidaPerdida() || EstadoJuego.getInstance().getPartida().isPartidaAbandonada()) {
                //TODO:show message saying how many points the user has won during this game
                break;
            }
            loadReto("retoPregunta", "Reto Pregunta");
        }
        puntosJugador = EstadoJuego.getInstance().getUsuario().getEstadistica().getPuntosTotales();
        puntosAlmacenados.setText("Puntos totales: " + puntosJugador);
        Node source = (Node) event.getSource();
        Stage oldStage = (Stage) source.getScene().getWindow();
        oldStage.close();
    }



    @FXML
    void retoMixtoPulsado (ActionEvent event) throws IOException
    {
        if (puntosJugador >= puntosNecesarios)
        {
            PartidaDirector partidaDirector = new PartidaDirector(new PartidaMixtaBuilder());
            Partida partida = partidaDirector.BuildPartida();
            EstadoJuego.getInstance().setPartida(partida);
            for(int i = 0; i < partida.getRetos().size(); i++){
                if(EstadoJuego.getInstance().getPartida().isPartidaPerdida() || EstadoJuego.getInstance().getPartida().isPartidaAbandonada()) {
                    //TODO:show message saying how many points the user has won during this game
                    break;
                }
                Reto retoActual = EstadoJuego.getInstance().getPartida().getRetos().get(i);
                if (retoActual.getTipoReto().equals(TipoReto.PREGUNTA)) {
                    loadReto("retoPregunta", "Reto Pregunta");
                } else {
                    loadReto("retoAhorcado", "Reto Ahorcado");
                }
                if(EstadoJuego.getInstance().getPartida().getRetosFallados()[i]){
                    i--;
                }
                // TODO - Al implementar el tercer reto, cambiar esto
            }
            Node source = (Node) event.getSource();
            Stage oldStage = (Stage) source.getScene().getWindow();
            oldStage.close();
        }

    }
    @FXML
    void retoAhorcadoPulsado (ActionEvent event) throws IOException {
        if (puntosJugador >= puntosNecesarios)
        {
            PartidaDirector partidaDirector = new PartidaDirector(new PartidaAhorcadoBuilder());
            Partida partida = partidaDirector.BuildPartida();
            EstadoJuego.getInstance().setPartida(partida);
            int aux = partida.getRetos().size();
            for(int i = 0; i < aux; i++){
                if(EstadoJuego.getInstance().getPartida().isPartidaPerdida() || EstadoJuego.getInstance().getPartida().isPartidaAbandonada()) {
                    //TODO:show message saying how many points the user has won during this game
                    break;
                }
                loadReto("retoAhorcado", "Reto Ahorcado");
            }
            puntosJugador = EstadoJuego.getInstance().getUsuario().getEstadistica().getPuntosTotales();
            puntosAlmacenados.setText("Puntos totales: " + puntosJugador);
        }
    }

    private void loadReto(String reto, String titulo) throws IOException {



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
