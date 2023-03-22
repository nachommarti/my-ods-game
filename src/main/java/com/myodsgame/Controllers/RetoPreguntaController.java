package com.myodsgame.Controllers;

import com.myodsgame.DAO.PreguntaDAO;
import com.myodsgame.Models.Pregunta;
import com.myodsgame.Repository.RepositorioPregunta;
import com.myodsgame.Repository.RepositorioPreguntaImpl;
import com.myodsgame.Utils.TestData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class RetoPreguntaController implements Initializable {

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

    private List<Button> respuestas;

    private String respuestaCorrecta;

    private boolean ayudaPulsada;

    private boolean respuestaCorrectaSeleccionada;

    private int numeroPregunta;

    private List<Pregunta> preguntas;
    private RepositorioPregunta repositorioPregunta;

    private final int easyQuestionPoints = 100;
    private final int mediumQuestionPoints = 200;
    private final int hardQuestionPoints = 300;
    public int obtainedPoints = 0;
    public int consolidatedPoints = 0;
    public boolean consolidated = false;
    Pregunta preguntaActual;
    public String currentStyle;
    public final String initialStyle = "-fx-background-color:  rgba(255, 255, 255, 0.5); -fx-background-radius: 10; -fx-border-color: black; -fx-border-radius: 10";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.repositorioPregunta = new RepositorioPreguntaImpl();
        this.preguntas = repositorioPregunta.getPreguntas();
        loadQuestion(preguntas);

    }

    private void loadQuestion(List<Pregunta> preguntas){
        preguntaActual = preguntas.get(numeroPregunta);
        enunciadoPregunta.setText(preguntaActual.getEnunciado());
        respuesta1.setText(preguntaActual.getRespuesta1());
        respuesta2.setText(preguntaActual.getRespuesta2());
        respuesta3.setText(preguntaActual.getRespuesta3());
        respuesta4.setText(preguntaActual.getRespuesta4());
        this.respuestaCorrecta = preguntaActual.getRespuestaCorrecta();
        this.respuestas = List.of(respuesta1, respuesta2, respuesta3, respuesta4);
        numeroPregunta++;
    }

    @FXML
    void ayudaClicked(ActionEvent event){
        int removedElements = 0;
        Set<Button> seenElement = new HashSet<>();

        while(removedElements < 2){
            Button respuesta = respuestas.get(new Random().nextInt(respuestas.size()));
            if(!respuesta.getText().equals(respuestaCorrecta) && !seenElement.contains(respuesta)){
                respuesta.setDisable(true);
                seenElement.add(respuesta);
                removedElements++;
            }
        }

        this.ayuda.setDisable(true);
        this.ayudaPulsada = true;
    }

    private void restoreState(){
        respuesta1.setDisable(false);
        respuesta2.setDisable(false);
        respuesta3.setDisable(false);
        respuesta4.setDisable(false);
        ayuda.setDisable(false);

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

    private void checkAnswers(Button respuestaSeleccionada){

        if(respuestaSeleccionada.getText().equals(respuestaCorrecta))
            respuestaCorrectaSeleccionada = true;

        for(Button respuesta : respuestas){
            currentStyle = respuesta.getStyle();
            int index = currentStyle.indexOf(";");
            if(respuesta.getText().equals(respuestaCorrecta)){
                respuesta.setStyle("-fx-background-color: rgba(184, 218, 186, 0.5)" + currentStyle.substring(index));
                respuesta.setDisable(true);
            }
            else if(!respuestaCorrectaSeleccionada){
                respuestaSeleccionada.setStyle("-fx-background-color: rgba(204, 96, 56, 0.5)" + currentStyle.substring(index));
                respuestaSeleccionada.setDisable(true);
            }
            respuesta.setDisable(true);

        }
        ayuda.setDisable(true);
        computePoints();
    }

    private void computePoints(){
        if(respuestaCorrectaSeleccionada){
                obtainedPoints += addPoints(preguntaActual.getNivelDificultad());
        }
        else{
            obtainedPoints -= decreasePoints(preguntaActual.getNivelDificultad());
            if(obtainedPoints < 0)
                obtainedPoints = 0;
        }
    }
    private Pregunta getRandomQuestion(List<Pregunta> preguntas){
        return preguntas.get(new Random().nextInt(preguntas.size()));
    }

    private int addPoints(int difficulty){
        switch (difficulty) {
            case 1:
                if(ayudaPulsada)
                    return easyQuestionPoints / 2;
                else
                    return easyQuestionPoints;

            case 2:
                if(ayudaPulsada)
                    return mediumQuestionPoints / 2;
                else
                    return mediumQuestionPoints;

            case 3:
                if(ayudaPulsada)
                    return hardQuestionPoints / 2;
                else
                    return hardQuestionPoints;

            default:
                return 0;
        }
    }

    private int decreasePoints(int difficulty){
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

    public void showMessage(boolean answered)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(answered) {
            alert.setTitle("Acertada");
            alert.setHeaderText("¡Acabas de conseguir " + addPoints(preguntaActual.getNivelDificultad()) + " puntos!");
            if (!consolidated) {
                alert.setContentText("Llevas un total de " + obtainedPoints + " puntos ¿Quieres consolidar?");
                ButtonType buttonTypeOk = new ButtonType("Vale", ButtonBar.ButtonData.OK_DONE);
                ButtonType buttonTypeCancel = new ButtonType("Seguir", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonTypeOk, buttonTypeCancel);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == buttonTypeOk) {
                    consolidatedPoints = obtainedPoints;
                    consolidated = true;
                    loadQuestion(preguntas);
                    restoreState();
                }
                else{
                    loadQuestion(preguntas);
                    restoreState();
                }
            }
            else {
                alert.setContentText("Llevas un total de " + obtainedPoints + " puntos");
                ButtonType buttonTypeCancel = new ButtonType("Seguir", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonTypeCancel);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == buttonTypeCancel) {
                    loadQuestion(preguntas);
                    restoreState();
                }
            }
        }
        else {
            alert.setTitle("Error");
            alert.setHeaderText("¡Acabas de perder " + decreasePoints(preguntaActual.getNivelDificultad()) + " puntos!");
            alert.setContentText("Llevas un total de " + obtainedPoints + " puntos");
            ButtonType buttonTypeCancel = new ButtonType("Seguir", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonTypeCancel) {
                loadQuestion(preguntas);
                restoreState();
            }
        }
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.toFront();
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();

    }
}
