package com.myodsgame.Controllers;

import com.myodsgame.Models.Partida;
import com.myodsgame.Models.RetoPregunta;
import com.myodsgame.Utils.EstadoJuego;
import com.myodsgame.Utils.UserUtils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
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
    private Label consolidatedScore;
    @FXML
    private ImageView vidas;
    @FXML
    private Button consolidarButton;

    @FXML
    private Label estatusRespuesta;
    @FXML
    private ImageView imagenODS;
    @FXML
    private HBox labelArray;
    @FXML
    private Button botonSalir;
    @FXML
    private Button nextQuestionButton;
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
    private int timeCountdown = 30;
    private boolean perdido = false;
    private int contAbandonar = 1;
    private MediaPlayer mediaPlayerSonidos = null, mediaPlayerMusic = null, mediaPlayerTicTac = new MediaPlayer(new Media(new File("src/main/resources/sounds/10S_tick.mp3").toURI().toString()));

    private Partida partidaActual;

    private RetoPregunta retoActual;
    private boolean fallado;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        this.partidaActual = EstadoJuego.getInstance().getPartida();
        this.retoActual = (RetoPregunta) partidaActual.getRetos().get(partidaActual.getRetoActual()-1);
        this.numeroPregunta = partidaActual.getRetoActual();
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


        Rectangle clip = new Rectangle(imagenODS.getFitWidth(), imagenODS.getFitHeight());
        clip.setArcWidth(40);
        clip.setArcHeight(40);
        imagenODS.setClip(clip);
    }

    private void loadRetosState(){
        for(int i = 0; i < numeroPregunta; i++){
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
        consolidarButton.setDisable(true);
        nextQuestionButton.setDisable(true);
        this.timeCountdown = 30;
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
    }
    @FXML
    void botonSalirPulsado(ActionEvent event) {
        if(mediaPlayerTicTac.getStatus() == MediaPlayer.Status.PLAYING) mediaPlayerTicTac.stop();
        if(mediaPlayerMusic.getStatus() == MediaPlayer.Status.PLAYING) mediaPlayerMusic.stop();
        Stage stage = (Stage) botonSalir.getScene().getWindow();
        stage.close();
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

    @FXML
    void siguientePreguntaClicked(ActionEvent event) {
        if(numeroPregunta == 10){
            UserUtils.saveUserScore(EstadoJuego.getInstance().getPartida().getPuntuacion());
        }

        Stage stage = (Stage) nextQuestionButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void consolidarButtonClicked(ActionEvent event) {
        int puntosPartida = EstadoJuego.getInstance().getPartida().getPuntuacion();
        consolidarButton.setDisable(true);
        consolidated = true;
        consolidatedScore.setVisible(true);
        consolidatedScore.setText("Consolidated Score: " + puntosPartida);
        botonSalir.setDisable(false);
        UserUtils.saveUserScore(puntosPartida);
    }

    private void checkAnswers(Button respuestaSeleccionada) {
        timeline.stop();
        mediaPlayerMusic.stop();
        if(mediaPlayerTicTac.getStatus() == MediaPlayer.Status.PLAYING) mediaPlayerTicTac.stop();
        currentStyleLabel = labelArray.getStyle();
        EstadoJuego.getInstance().getPartida().setRetoActual(numeroPregunta+1);
        if (respuestaSeleccionada.getText().equals(respuestaCorrecta)) {
            ((Label) labelArray.getChildren().get(numeroPregunta-1)).setStyle("-fx-background-color: rgba(184, 218, 186, 1); " + currentStyleLabel);
            ((Label) labelArray.getChildren().get(numeroPregunta-1)).setTextFill(Color.WHITE);
            respuestaCorrectaSeleccionada = true;
            consolidarButton.setDisable(consolidated);
            reproducirSonido("src/main/resources/sounds/Acierto.mp3", 0.15);
            EstadoJuego.getInstance().getPartida().getRetosFallados()[numeroPregunta-1] = false;

        }
        else {
            ((Label) labelArray.getChildren().get(numeroPregunta-1)).setStyle("-fx-background-color: rgb(255,25,25); " + currentStyleLabel);
            ((Label) labelArray.getChildren().get(numeroPregunta-1)).setTextFill(Color.WHITE);
            EstadoJuego.getInstance().getPartida().setVidas(partidaActual.getVidas()-1);
            reproducirSonido("src/main/resources/sounds/Fallo.mp3", 0.5);
            vidas.setImage(new Image(Path.of("", "src", "main", "resources", "images", "vidaMitad.png").toAbsolutePath().toString()));
            EstadoJuego.getInstance().getPartida().getRetosFallados()[numeroPregunta-1] = true;
            if ( EstadoJuego.getInstance().getPartida().getVidas() == 0) {
                mediaPlayerSonidos.stop();
                lostGame();
                EstadoJuego.getInstance().getPartida().setPartidaPerdida(true);
                reproducirSonido("src/main/resources/sounds/Partida_Perdida.mp3", 0.5);
                vidas.setImage(new Image(Path.of("", "src", "main", "resources", "images", "vidasAgotadas.png").toAbsolutePath().toString()));
            }
            numeroPregunta--;

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

        if(numeroPregunta < 10){
            if (!perdido) nextQuestionButton.setDisable(false);
        }
        else{
            nextQuestionButton.setText("Fin de la partida!");
            botonSalir.setText("Guardar y salir");
            botonSalir.setDisable(false);
        }


        ayuda.setDisable(true);
        computePoints();
        EstadoJuego.getInstance().getPartida().setPuntuacion(EstadoJuego.getInstance().getPartida().getPuntuacion() + obtainedPoints);
        currentScore.setText("Score: " + EstadoJuego.getInstance().getPartida().getPuntuacion());
    }

    private void computePoints() {
        obtainedPoints = UserUtils.computePoints(retoActual, ayudaPulsada, respuestaCorrectaSeleccionada);
    }

    private void endTimer() {
        respuestas.forEach(respuesta -> respuesta.setDisable(true));

        if(numeroPregunta < 10)
            nextQuestionButton.setDisable(false);

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
        consolidarButton.setDisable(consolidated);
        ayuda.setDisable(true);
        this.timeCountdown = 30;
        numeroPregunta--;
    }

    private void showMessage(boolean answered) {
        if (perdido) return;
        if (answered) {
            estatusRespuesta.setText("¡CORRECTO! " + "¡Acabas de conseguir " + obtainedPoints + " puntos!");
            estatusRespuesta.setTextFill(Color.GREEN);
        } else {
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
        nextQuestionButton.setDisable(true);
        botonSalir.setDisable(false);
    }

    private void reproducirSonido(String sonidoPath, double volumen){
        mediaPlayerMusic.pause();
        Media media = new Media(new File(sonidoPath).toURI().toString());
        mediaPlayerSonidos = new MediaPlayer(media);
        mediaPlayerSonidos.setVolume(volumen);
        mediaPlayerSonidos.play();
    }

    private void reproducirMusica(){
        String[] musics = {"src/main/resources/sounds/cancion_1.mp3", "src/main/resources/sounds/cancion_2.mp3","src/main/resources/sounds/cancion_3.mp3"};
        Random random = new Random();
        int index = random.nextInt(3);
        String selected = musics[index];

        mediaPlayerMusic = new MediaPlayer(new Media(new File(selected).toURI().toString()));
        mediaPlayerMusic.setVolume(0.15);
        mediaPlayerMusic.play();

    }

}
