package com.myodsgame.Controllers;

import com.myodsgame.Models.Partida;
import com.myodsgame.Models.RetoPregunta;
import com.myodsgame.Services.IServices;
import com.myodsgame.Services.Services;
import com.myodsgame.Utils.EstadoJuego;
import com.myodsgame.Utils.UserUtils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.*;

public class RetoPreguntaController implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private Label enunciadoPregunta;

    @FXML
    private Button ayuda;

    @FXML
    private Button respuesta1;

    @FXML
    private Button respuesta2;

    @FXML
    private Button respuesta3;

    @FXML
    private Button respuesta4;

    @FXML
    private Label currentScore;

    @FXML
    private Label timer;
    @FXML
    private Button abandonarBoton;
    @FXML
    private ImageView vidas;

    @FXML
    private Label estatusRespuesta;
    @FXML
    private ImageView imagenODS;
    @FXML
    private HBox labelArray;
    private List<Button> respuestas;
    private String respuestaCorrecta;
    private boolean ayudaPulsada;
    private boolean respuestaCorrectaSeleccionada;
    private int numeroPregunta;
    public int obtainedPoints = 0;
    private boolean consolidated;
    private String currentStyleButton;
    private String currentStyleLabel;
    private String initialStyle;
    private Timeline timeline;
    private int timeCountdown;
    private boolean perdido = false;
    private int contAbandonar = 1;
    private MediaPlayer mediaPlayerSonidos = null, mediaPlayerMusic = null, mediaPlayerTicTac = new MediaPlayer(new Media(new File("src/main/resources/sounds/10S_tick.mp3").toURI().toString()));

    private Partida partidaActual;

    private RetoPregunta retoActual;
    private boolean fallado;

    private IServices servicios;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        servicios = new Services();
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        this.partidaActual = EstadoJuego.getInstance().getPartida();
        this.numeroPregunta = partidaActual.getRetoActual();

        if(numeroPregunta > 4 && numeroPregunta <= 7){
            while(partidaActual.getRetos().get(numeroPregunta-1).getDificultad() != 2)
                numeroPregunta++;
        }

        if(numeroPregunta > 7 && numeroPregunta <= 10){
            while(partidaActual.getRetos().get(numeroPregunta-1).getDificultad() != 3)
                numeroPregunta++;
        }


        this.retoActual = (RetoPregunta) partidaActual.getRetos().get(numeroPregunta-1);

        // KeyFrame event handler
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        event -> {
                            timeCountdown--;
                            // update timerLabel
                            timer.setText(Integer.toString(timeCountdown));
                            if (timeCountdown == retoActual.getTiempoTicTac()){
                                mediaPlayerTicTac.play();
                                mediaPlayerMusic.setVolume(0.05);
                            }
                            if (timeCountdown <= 0) {
                                timeline.stop();
                                endTimer();
                                showMessage(respuestaCorrectaSeleccionada);
                                mediaPlayerMusic.stop();
                                reproducirSonido("src/main/resources/sounds/Fallo.mp3", 0.5);
                            }
                        })
        );
        reproducirMusica();
        this.initialStyle = "-fx-background-color:  rgba(255, 255, 255, 0.5); -fx-background-radius: 10; -fx-border-color: black; -fx-border-radius: 10";
        ayuda.setGraphic(new ImageView(new Image(Path.of("", "src", "main", "resources", "images", "ayuda.png").toAbsolutePath().toString())));

        loadRetosState();
        loadQuestion(retoActual);

        currentScore.setText("Score:" + EstadoJuego.getInstance().getPartida().getPuntuacion());

        if(EstadoJuego.getInstance().getPartida().getPuntuacion() >= retoActual.getPuntuacion()/2)
            ayuda.setDisable(false);
        else
            ayuda.setDisable(true);

        abandonarBoton.setVisible(partidaActual.isConsolidado());

        for(int i = 1; i <= partidaActual.getRetos().size(); i++){
            System.out.println("Desde PREGUNTA - Reto numero " + i + " es de tipo " + partidaActual.getRetos().get(i-1).getTipoReto());
        }
        System.out.println("Desde reto PREGUNTA - reto actual: " + partidaActual.getRetoActual());
        System.out.println("Desde reto PREGUNTA - tipo reto actual: " + retoActual.getTipoReto());



        Rectangle clip = new Rectangle(imagenODS.getFitWidth(), imagenODS.getFitHeight());
        clip.setArcWidth(40);
        clip.setArcHeight(40);
        imagenODS.setClip(clip);
    }

    private void loadRetosState(){
        for(int i = 0; i < partidaActual.getRetoActual(); i++){
            ((Label) labelArray.getChildren().get(i))
                    .setStyle(partidaActual.getRetosFallados()[i] ? "-fx-background-color: rgb(255,25,25); " : "-fx-background-color: rgba(184, 218, 186, 1)");
        }
    }


    private void loadQuestion(RetoPregunta retoPreguntaActual) {
        enunciadoPregunta.setText(retoPreguntaActual.getEnunciado());
        respuesta1.setText(retoPreguntaActual.getRespuesta1());
        respuesta2.setText(retoPreguntaActual.getRespuesta2());
        respuesta3.setText(retoPreguntaActual.getRespuesta3());
        respuesta4.setText(retoPreguntaActual.getRespuesta4());
        this.respuestaCorrecta = retoPreguntaActual.getRespuestaCorrecta();
        this.respuestas = List.of(respuesta1, respuesta2, respuesta3, respuesta4);
        timeCountdown = retoPreguntaActual.getDuracion();
        if (retoPreguntaActual.getODS().size() == 1) imagenODS.setImage(new Image(Path.of("", "src", "main", "resources", "images", "ODS_"+retoPreguntaActual.getODS().get(0)+".jpg").toAbsolutePath().toString()));
        else imagenODS.setImage(new Image(Path.of("", "src", "main", "resources", "images", "ODS_0.jpg").toAbsolutePath().toString()));
        ((Label) labelArray.getChildren().get(numeroPregunta - 1)).setStyle("-fx-background-color: rgb(202,184,218)");
        timeline.playFromStart();

        if (EstadoJuego.getInstance().getPartida().getVidas() == 1) {
            vidas.setImage(new Image(Path.of("", "src", "main", "resources", "images", "vidaMitad.png").toAbsolutePath().toString()));
        }
        else if (EstadoJuego.getInstance().getPartida().getVidas() == 0)
        {
            vidas.setImage(new Image(Path.of("", "src", "main", "resources", "images", "vidasAgotadas.png").toAbsolutePath().toString()));
        }
        //String odsString = "ODS_" + retoPreguntaActual.getOds() + ".jpg";
        //imagenODS.setImage(new Image(Path.of("", "src", "main", "resources", "images", odsString).toAbsolutePath().toString()));
    }

    @FXML

    void ayudaClicked(ActionEvent event) {
        reproducirSonido("src/main/resources/sounds/pista_larga.mp3", 0.5);
        mediaPlayerMusic.play();
        int removedElements = 0;
        Set<Button> seenElement = new HashSet<>();

        while (removedElements < 2) {
            Button respuesta = respuestas.get(new Random().nextInt(respuestas.size()));
            if (!respuesta.getText().equals(respuestaCorrecta) && !seenElement.contains(respuesta)) {
                respuesta.setDisable(true);
                seenElement.add(respuesta);
                removedElements++;
            }
        }
        this.ayuda.setDisable(true);
        this.ayudaPulsada = true;

        int puntos =  EstadoJuego.getInstance().getPartida().getPuntuacion() - retoActual.getPuntuacion()/2;
        EstadoJuego.getInstance().getPartida().setPuntuacion(puntos);
        currentScore.setText("Score: " + puntos);
        ayuda.setDisable(true);
        ayudaPulsada = true;

    }
    @FXML
    void abandonarBotonPulsado(ActionEvent event) {
       if(mediaPlayerTicTac.getStatus() == MediaPlayer.Status.PLAYING) mediaPlayerTicTac.stop();
        if(mediaPlayerMusic.getStatus() == MediaPlayer.Status.PLAYING) mediaPlayerMusic.stop();
        Stage stageOld = (Stage) abandonarBoton.getScene().getWindow();
        stageOld.close();
        EstadoJuego.getInstance().getPartida().setPartidaAbandonada(true);

        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/com/myodsgame/pantallaPartidas.fxml"));
        BorderPane root = null;
        try {
            root = myLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Menú Principal");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.getIcons().add(new Image(Path.of("", "src", "main", "resources", "images", "LogoODS.png").toAbsolutePath().toString()));
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void respuesta1Clicked(ActionEvent event) {
        Button respuestaSeleccionada = (Button) event.getSource();
        checkAnswers(respuestaSeleccionada);
        showMessage(respuestaCorrectaSeleccionada);
    }

    @FXML
    void respuesta2Clicked(ActionEvent event) {
        Button respuestaSeleccionada = (Button) event.getSource();
        checkAnswers(respuestaSeleccionada);
        showMessage(respuestaCorrectaSeleccionada);
    }

    @FXML
    void respuesta3Clicked(ActionEvent event) {
        Button respuestaSeleccionada = (Button) event.getSource();
        checkAnswers(respuestaSeleccionada);
        showMessage(respuestaCorrectaSeleccionada);
    }

    @FXML
    void respuesta4Clicked(ActionEvent event) {
        Button respuestaSeleccionada = (Button) event.getSource();
        checkAnswers(respuestaSeleccionada);
        showMessage(respuestaCorrectaSeleccionada);
    }

    //@FXML
    //void siguientePreguntaClicked(ActionEvent event) {
    //    if(numeroPregunta == 10){
    //        UserUtils.saveUserScore(EstadoJuego.getInstance().getPartida().getPuntuacion());
    //    }
//
    //    Stage stage = (Stage) nextQuestionButton.getScene().getWindow();
    //    stage.close();
    //}
//
    //@FXML
    //void consolidarButtonClicked(ActionEvent event) {
    //    int puntosPartida = EstadoJuego.getInstance().getPartida().getPuntuacion();
    //    consolidarButton.setDisable(true);
    //    consolidated = true;
    //    consolidatedScore.setVisible(true);
    //    consolidatedScore.setText("Consolidated Score: " + puntosPartida);
    //    botonSalir.setDisable(false);
    //    UserUtils.saveUserScore(puntosPartida);
    //}

    private void checkAnswers(Button respuestaSeleccionada) {
        timeline.stop();
        if(mediaPlayerMusic != null){mediaPlayerMusic.stop();}
        if(mediaPlayerTicTac != null){mediaPlayerTicTac.stop();}
        currentStyleLabel = labelArray.getStyle();

        if (respuestaSeleccionada.getText().equals(respuestaCorrecta)) {
            ((Label) labelArray.getChildren().get(numeroPregunta-1)).setStyle("-fx-background-color: rgba(184, 218, 186, 1); " + currentStyleLabel);
            ((Label) labelArray.getChildren().get(numeroPregunta-1)).setTextFill(Color.WHITE);
            respuestaCorrectaSeleccionada = true;
            reproducirSonido("src/main/resources/sounds/Acierto.mp3", 0.15);
            UserUtils.saveStats(true, retoActual.getODS());
            EstadoJuego.getInstance().getPartida().getRetosFallados()[numeroPregunta-1] = false;
        }
        else {
            EstadoJuego.getInstance().getPartida().setRetoFallado(true);
            ((Label) labelArray.getChildren().get(numeroPregunta-1)).setStyle("-fx-background-color: rgb(255,25,25); " + currentStyleLabel);
            ((Label) labelArray.getChildren().get(numeroPregunta-1)).setTextFill(Color.WHITE);
            EstadoJuego.getInstance().getPartida().setVidas(partidaActual.getVidas()-1);
            reproducirSonido("src/main/resources/sounds/Fallo.mp3", 0.5);
            UserUtils.saveStats(false, retoActual.getODS());
            vidas.setImage(new Image(Path.of("", "src", "main", "resources", "images", "vidaMitad.png").toAbsolutePath().toString()));
            EstadoJuego.getInstance().getPartida().getRetosFallados()[numeroPregunta-1] = true;
            EstadoJuego.getInstance().getPartida().getRetos().remove(numeroPregunta - 1);
            if (EstadoJuego.getInstance().getPartida().getVidas() == 0) {
                mediaPlayerSonidos.stop();
                lostGame();
                EstadoJuego.getInstance().getPartida().setPartidaPerdida(true);
                reproducirSonido("src/main/resources/sounds/Partida_Perdida.mp3", 0.5);
                vidas.setImage(new Image(Path.of("", "src", "main", "resources", "images", "vidasAgotadas.png").toAbsolutePath().toString()));
            }
        }

        for (Button respuesta : respuestas) {
            currentStyleButton = respuesta.getStyle();
            int index = currentStyleButton.indexOf(";");
            if (respuesta.getText().equals(respuestaCorrecta)) {
                respuesta.setStyle("-fx-background-color: rgba(184, 218, 186, 1)" + currentStyleButton.substring(index));
                respuesta.setDisable(true);
            } else if (!respuestaCorrectaSeleccionada) {
                respuestaSeleccionada.setStyle("-fx-background-color: rgba(204, 96, 56, 1)" + currentStyleButton.substring(index));
                respuestaSeleccionada.setDisable(true);
            }
            respuesta.setDisable(true);

        }

        ayuda.setDisable(true);
        computePoints();
        EstadoJuego.getInstance().getPartida().setPuntuacion(EstadoJuego.getInstance().getPartida().getPuntuacion() + obtainedPoints);
        currentScore.setText("Score: " + EstadoJuego.getInstance().getPartida().getPuntuacion());
        showPopUp();



    }

    private void computePoints() {
        obtainedPoints = servicios.computePoints(retoActual, ayudaPulsada, respuestaCorrectaSeleccionada);
    }

    private void endTimer() {
        respuestas.forEach(respuesta -> respuesta.setDisable(true));

        EstadoJuego.getInstance().getPartida().setVidas(partidaActual.getVidas()-1);
        if (EstadoJuego.getInstance().getPartida().getVidas() == 1) {
            vidas.setImage(new Image(Path.of("", "src", "main", "resources", "images", "vidaMitad.png").toAbsolutePath().toString()));
        }
        if (EstadoJuego.getInstance().getPartida().getVidas() == 2) {
            mediaPlayerSonidos.stop();
            vidas.setImage(new Image(Path.of("", "src", "main", "resources", "images", "vidasAgotadas.png").toAbsolutePath().toString()));
            lostGame();
            reproducirSonido("src/main/resources/sounds/Partida_Perdida.mp3", 0.5);

        }

        ((Label) labelArray.getChildren().get(numeroPregunta-1)).setTextFill(Color.RED);
        ayuda.setDisable(true);
        this.timeCountdown = 30;
        numeroPregunta--;
    }

    private void showMessage(boolean answered) {
        if (perdido) return;
        if (answered) {
            estatusRespuesta.setText("¡CORRECTO! " + "¡Acabas de conseguir " + obtainedPoints + " puntos!");
            estatusRespuesta.setTextFill(Color.GREEN);
        }
        else {
            estatusRespuesta.setText("¡INCORRECTO! " + "¡Acabas de perder " + obtainedPoints + " puntos!");
            estatusRespuesta.setTextFill(Color.RED);
        }
    }

    private void lostGame() {
        perdido = true;
        estatusRespuesta.setText("¡INCORRECTO! " + "Has perdido");
        estatusRespuesta.setTextFill(Color.RED);
        respuesta1.setDisable(true);
        respuesta2.setDisable(true);
        respuesta3.setDisable(true);
        respuesta4.setDisable(true);
        ayuda.setDisable(true);
        UserUtils.aumentarPartidasJugadas();
    }

    private void reproducirSonido(String sonidoPath, double volumen){
        mediaPlayerMusic.pause();
        Media media = new Media(new File(sonidoPath).toURI().toString());
        mediaPlayerSonidos = new MediaPlayer(media);
        mediaPlayerSonidos.setVolume(volumen);
        mediaPlayerSonidos.play();
    }

    private void reproducirMusica(){
        String[] musics = {"src/main/resources/sounds/cancion_1.mp3", "src/main/resources/sounds/cancion_2.mp3"};
        Random random = new Random();
        int index = random.nextInt(2);
        String selected = musics[index];

        mediaPlayerMusic = new MediaPlayer(new Media(new File(selected).toURI().toString()));
        mediaPlayerMusic.setVolume(0.15);
        mediaPlayerMusic.play();

    }

    private void showPopUp() {
        try
        {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/com/myodsgame/popUpReto.fxml"));
            BorderPane root = myLoader.load();
            Scene scene = new Scene (root, 357, 184);
            Stage  stage = new Stage();
            stage.setScene(scene);
            if (numeroPregunta == 10)
            {
                stage.setTitle("¡Ganaste!");
            }
            else if (!partidaActual.getRetosFallados()[numeroPregunta]) {
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
            stage.show();
        }
        catch (IOException e) {System.out.println(e.getMessage());}
    }

}
