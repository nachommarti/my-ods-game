package com.myodsgame.Controllers;

import com.myodsgame.Models.Pregunta;
import com.myodsgame.Repository.RepositorioPregunta;
import com.myodsgame.Repository.RepositorioPreguntaImpl;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
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
    private ImageView ayudaImagen;

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
    private ImageView vidas;
    @FXML
    private Button consolidarButton;

    @FXML
    private Label estatusRespuesta;
    @FXML
    private Label questionProgress;
    @FXML
    private HBox labelArray;

    @FXML
    private Button nextQuestionButton;
    private List<Pregunta> preguntas;
    private List<Button> respuestas;
    private String respuestaCorrecta;
    private boolean ayudaPulsada;
    private boolean respuestaCorrectaSeleccionada;
    private int numeroPregunta;
    private int indicePregunta;
    private RepositorioPregunta repositorioPregunta;
    private final int easyQuestionPoints = 100;
    private final int mediumQuestionPoints = 200;
    private final int hardQuestionPoints = 300;
    public int obtainedPoints = 0;
    public int consolidatedPoints = 0;
    public int decreasedPoints = 0;
    private boolean consolidated;
    private Pregunta preguntaActual;
    private String currentStyleButton;
    private String currentStyleLabel;
    private String initialStyle;
    private Timeline timeline;
    private int timeCountdown = 30;
    private int nFallos;
    private boolean perdido = false;
    private MediaPlayer mediaPlayerSonidos = null, mediaPlayerMusic = null, mediaPlayerTicTac = new MediaPlayer(new Media(new File("src/main/resources/sounds/10S_tick.mp3").toURI().toString()));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        // KeyFrame event handler
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        event -> {
                            timeCountdown--;
                            // update timerLabel
                            timer.setText(Integer.toString(timeCountdown));
                            if (timeCountdown == 10){
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
        this.repositorioPregunta = new RepositorioPreguntaImpl();
        this.preguntas = repositorioPregunta.getPreguntasOrdenadasPorNivelDificultad();
        ayuda.setGraphic(new ImageView(new Image(Path.of("", "src", "main", "resources", "images", "ayuda.png").toAbsolutePath().toString())));
        loadQuestion(preguntas);

    }


    private void loadQuestion(List<Pregunta> preguntas) {
        preguntaActual = preguntas.get(indicePregunta);
        enunciadoPregunta.setText(preguntaActual.getEnunciado());
        respuesta1.setText(preguntaActual.getRespuesta1());
        respuesta2.setText(preguntaActual.getRespuesta2());
        respuesta3.setText(preguntaActual.getRespuesta3());
        respuesta4.setText(preguntaActual.getRespuesta4());
        this.respuestaCorrecta = preguntaActual.getRespuestaCorrecta();
        this.respuestas = List.of(respuesta1, respuesta2, respuesta3, respuesta4);
        numeroPregunta++;
        consolidarButton.setDisable(true);
        nextQuestionButton.setDisable(true);
        this.timeCountdown = 30;
        ((Label) labelArray.getChildren().get(numeroPregunta-1)).setTextFill(Color.BLUEVIOLET);
        timeline.playFromStart();
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

    private void restoreState() {
        respuesta1.setDisable(false);
        respuesta2.setDisable(false);
        respuesta3.setDisable(false);
        respuesta4.setDisable(false);
        ayuda.setDisable(false);
        estatusRespuesta.setText("");

        respuesta1.setStyle(initialStyle);
        respuesta2.setStyle(initialStyle);
        respuesta3.setStyle(initialStyle);
        respuesta4.setStyle(initialStyle);

        respuestaCorrectaSeleccionada = false;
        ayudaPulsada = false;
        reproducirMusica();
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
        if(numeroPregunta < 4)
            nextQuestion(1);
        else if(numeroPregunta < 7)
            nextQuestion(2);
        else
            nextQuestion(3);

        mediaPlayerSonidos.stop();
        restoreState();
        timeline.playFromStart();
    }

    private void nextQuestion(int dificultad) {
        for(int i = indicePregunta; i < preguntas.size(); i++, indicePregunta++){
            if(preguntas.get(i).getNivelDificultad() == dificultad) {
                indicePregunta++;
                loadQuestion(preguntas);
                return;
            }
        }
    }

    @FXML
    void consolidarButtonClicked(ActionEvent event) {
        consolidarButton.setDisable(true);
        consolidatedPoints = obtainedPoints;
        consolidated = true;
    }

    private void checkAnswers(Button respuestaSeleccionada) {
        timeline.stop();
        mediaPlayerMusic.stop();
        if(mediaPlayerTicTac.getStatus() == MediaPlayer.Status.PLAYING) mediaPlayerTicTac.stop();
        currentStyleLabel = labelArray.getStyle();
        if (respuestaSeleccionada.getText().equals(respuestaCorrecta)) {
            ((Label) labelArray.getChildren().get(numeroPregunta-1)).setStyle("-fx-background-color: rgba(184, 218, 186, 1); " + currentStyleLabel);
            ((Label) labelArray.getChildren().get(numeroPregunta-1)).setTextFill(Color.WHITE);
            respuestaCorrectaSeleccionada = true;
            consolidarButton.setDisable(consolidated);
            reproducirSonido("src/main/resources/sounds/Acierto.mp3", 0.15);

        } else {
            ((Label) labelArray.getChildren().get(numeroPregunta-1)).setStyle("-fx-background-color: rgb(255,25,25); " + currentStyleLabel);
            ((Label) labelArray.getChildren().get(numeroPregunta-1)).setTextFill(Color.WHITE);
            consolidarButton.setDisable(!consolidated);
            nFallos++;
            reproducirSonido("src/main/resources/sounds/Fallo.mp3", 0.5);
            vidas.setImage(new Image(Path.of("", "src", "main", "resources", "images", "vidaMitad.png").toAbsolutePath().toString()));

            if (nFallos == 2) {
                mediaPlayerSonidos.stop();
                lostGame();
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

        if(numeroPregunta < 10 && !perdido)
            nextQuestionButton.setDisable(false);

        ayuda.setDisable(true);
        computePoints();
        currentScore.setText("Score: " + obtainedPoints);
    }

    private void computePoints() {
        if (respuestaCorrectaSeleccionada) {
            obtainedPoints += addPoints(preguntaActual.getNivelDificultad());
        } else {
            decreasedPoints = decreasePoints(preguntaActual.getNivelDificultad());
            // obtainedPoints -= decreasePoints(preguntaActual.getNivelDificultad());
            if (decreasedPoints > obtainedPoints)
                decreasedPoints = obtainedPoints;
                obtainedPoints = 0;
            //if (obtainedPoints < 0)
                //obtainedPoints = 0;
        }
    }

    private int addPoints(int difficulty) {
        switch (difficulty) {
            case 1:
                if (ayudaPulsada)
                    return easyQuestionPoints / 2;
                else
                    return easyQuestionPoints;
            case 2:
                if (ayudaPulsada)
                    return mediumQuestionPoints / 2;
                else
                    return mediumQuestionPoints;
            case 3:
                if (ayudaPulsada)
                    return hardQuestionPoints / 2;
                else
                    return hardQuestionPoints;
            default:
                return 0;
        }
    }

    private int decreasePoints(int difficulty) {
        switch (difficulty) {
            case 1:
                return easyQuestionPoints * 2;
            case 2:
                return mediumQuestionPoints * 2;
            case 3:
                return hardQuestionPoints * 2;

            default:
                return 0;
        }
    }

    private void endTimer() {
        respuestas.forEach(respuesta -> respuesta.setDisable(true));

        if(numeroPregunta < 10)
            nextQuestionButton.setDisable(false);

        nFallos++;
        if (nFallos == 1) {
            vidas.setImage(new Image(Path.of("", "src", "main", "resources", "images", "vidaMitad.png").toAbsolutePath().toString()));
        }
        if (nFallos == 2) {
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
            estatusRespuesta.setText("¡CORRECTO! " + "¡Acabas de conseguir " + addPoints(preguntaActual.getNivelDificultad()) + " puntos!");
            estatusRespuesta.setTextFill(Color.GREEN);
        } else {
            estatusRespuesta.setText("¡INCORRECTO! " + "¡Acabas de perder " + decreasedPoints + " puntos!");
            estatusRespuesta.setTextFill(Color.RED);
        }
    }

    private void lostGame() {
        perdido = true;
        estatusRespuesta.setText("¡INCORRECTO! " + "Has perdido");
        estatusRespuesta.setTextFill(Color.RED);
        ((Label) labelArray.getChildren().get(numeroPregunta-1)).setTextFill(Color.RED);
        respuesta1.setDisable(true);
        respuesta2.setDisable(true);
        respuesta3.setDisable(true);
        respuesta4.setDisable(true);
        ayuda.setDisable(true);
        nextQuestionButton.setDisable(true);
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
