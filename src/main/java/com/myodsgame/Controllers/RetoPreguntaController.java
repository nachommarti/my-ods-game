package com.myodsgame.Controllers;

import com.myodsgame.DAO.PreguntaDAO;
import com.myodsgame.Models.Pregunta;
import com.myodsgame.Repository.RepositorioPregunta;
import com.myodsgame.Repository.RepositorioPreguntaImpl;
import com.myodsgame.Utils.TestData;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
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
    private Label currentQuestion;

    @FXML
    private Label currentScore;

    @FXML
    private Label timer;


    @FXML
    private Button consolidarButton;

    @FXML
    private Label estatusRespuesta;
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
    private boolean consolidated;
    private Pregunta preguntaActual;
    private String currentStyle;
    private String initialStyle;
    private Timeline timeline;
    private int timeCountdown = 15;
    private int nFallos;
    private boolean perdido = false;
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
                            if (timeCountdown <= 0) {
                                timeline.stop();
                                endTimer();
                                showMessage(respuestaCorrectaSeleccionada);
                            }
                        })
        );

        this.initialStyle = "-fx-background-color:  rgba(255, 255, 255, 0.5); -fx-background-radius: 10; -fx-border-color: black; -fx-border-radius: 10";
        this.repositorioPregunta = new RepositorioPreguntaImpl();
        this.preguntas = repositorioPregunta.getPreguntasOrdenadasPorNivelDificultad();
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
        currentQuestion.setText("Question: " + numeroPregunta + "/10");
        currentScore.setText("Score: " + obtainedPoints);
        consolidarButton.setDisable(true);
        nextQuestionButton.setDisable(true);
        this.timeCountdown = 15;
        ((Label) labelArray.getChildren().get(numeroPregunta-1)).setTextFill(Color.BLUEVIOLET);
        timeline.playFromStart();
    }

    @FXML
    void ayudaClicked(ActionEvent event) {
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

        if (respuestaSeleccionada.getText().equals(respuestaCorrecta)) {
            ((Label) labelArray.getChildren().get(numeroPregunta-1)).setTextFill(Color.GREEN);
            respuestaCorrectaSeleccionada = true;
            consolidarButton.setDisable(consolidated);
        } else {
            ((Label) labelArray.getChildren().get(numeroPregunta-1)).setTextFill(Color.RED);
            consolidarButton.setDisable(!consolidated);
            numeroPregunta--;
            nFallos++;
            if (nFallos == 2)
                lostGame();
        }

        for (Button respuesta : respuestas) {
            currentStyle = respuesta.getStyle();
            int index = currentStyle.indexOf(";");
            if (respuesta.getText().equals(respuestaCorrecta)) {
                respuesta.setStyle("-fx-background-color: rgba(184, 218, 186, 0.5)" + currentStyle.substring(index));
                respuesta.setDisable(true);
            } else if (!respuestaCorrectaSeleccionada) {
                respuestaSeleccionada.setStyle("-fx-background-color: rgba(204, 96, 56, 0.5)" + currentStyle.substring(index));
                respuestaSeleccionada.setDisable(true);
            }
            respuesta.setDisable(true);

        }

        if(numeroPregunta < 10 && !perdido)
            nextQuestionButton.setDisable(false);

        ayuda.setDisable(true);
        computePoints();
    }

    private void computePoints() {
        if (respuestaCorrectaSeleccionada) {
            obtainedPoints += addPoints(preguntaActual.getNivelDificultad());
        } else {
            obtainedPoints -= decreasePoints(preguntaActual.getNivelDificultad());
            if (obtainedPoints < 0)
                obtainedPoints = 0;
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

        ((Label) labelArray.getChildren().get(numeroPregunta-1)).setTextFill(Color.RED);
        consolidarButton.setDisable(consolidated);
        ayuda.setDisable(true);
        this.timeCountdown = 15;
    }

    private void showMessage(boolean answered) {
        if (perdido) return;
        if (answered) {
            estatusRespuesta.setText("¡CORRECTO! " + "¡Acabas de conseguir " + addPoints(preguntaActual.getNivelDificultad()) + " puntos!");
            estatusRespuesta.setTextFill(Color.GREEN);
        } else {
            estatusRespuesta.setText("¡INCORRECTO! " + "¡Acabas de perder " + addPoints(preguntaActual.getNivelDificultad()) + " puntos!");
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
}
