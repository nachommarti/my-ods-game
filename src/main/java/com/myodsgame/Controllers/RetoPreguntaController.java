package com.myodsgame.Controllers;

import com.myodsgame.DAO.PreguntaDAO;
import com.myodsgame.Models.Pregunta;
import com.myodsgame.Utils.TestData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

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

    @FXML
    private Label resultadoRespuesta;

    @FXML
    private Button nextQuestionButton;

    private List<Button> respuestas;

    private String respuestaCorrecta;

    private boolean ayudaPulsada;

    private boolean respuestaCorrectaSeleccionada;

    private int numeroPregunta;

    private List<Pregunta> preguntas;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.preguntas = new PreguntaDAO().getPregunta();
        loadQuestion(preguntas);

    }

    private void loadQuestion(List<Pregunta> preguntas){
        nextQuestionButton.setVisible(false);
        Pregunta preguntaActual = preguntas.get(numeroPregunta);
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

    @FXML
    void nextQuestionButtonClicked(ActionEvent event) {
        loadQuestion(preguntas);
        restoreState();
    }

    private void restoreState(){
        respuesta1.setDisable(false);
        respuesta2.setDisable(false);
        respuesta3.setDisable(false);
        respuesta4.setDisable(false);
        ayuda.setDisable(false);

        respuesta1.setTextFill(Color.BLACK);
        respuesta2.setTextFill(Color.BLACK);
        respuesta3.setTextFill(Color.BLACK);
        respuesta4.setTextFill(Color.BLACK);

        resultadoRespuesta.setText("");
        respuestaCorrectaSeleccionada = false;
        ayudaPulsada = false;
    }

    @FXML
    void respuesta1Clicked(ActionEvent event) {
        Button respuestaSeleccionada = (Button) event.getSource();
        checkAnswers(respuestaSeleccionada);
    }

    @FXML
    void respuesta2Clicked(ActionEvent event) {
        Button respuestaSeleccionada = (Button) event.getSource();
        checkAnswers(respuestaSeleccionada);
    }

    @FXML
    void respuesta3Clicked(ActionEvent event) {
        Button respuestaSeleccionada = (Button) event.getSource();
        checkAnswers(respuestaSeleccionada);
    }

    @FXML
    void respuesta4Clicked(ActionEvent event) {
        Button respuestaSeleccionada = (Button) event.getSource();
        checkAnswers(respuestaSeleccionada);
    }

    private void checkAnswers(Button respuestaSeleccionada){
        nextQuestionButton.setVisible(true);

        if(respuestaSeleccionada.getText().equals(respuestaCorrecta))
            respuestaCorrectaSeleccionada = true;

        fillAnswerResult();

        for(Button respuesta : respuestas){
            if(respuesta.getText().equals(respuestaCorrecta)){
                respuesta.setTextFill(Color.GREEN);
                respuesta.setDisable(true);
            }else{
                respuesta.setTextFill(Color.RED);
                respuesta.setDisable(true);
            }

        }
        ayuda.setDisable(true);
        computePoints();
    }

    private void computePoints(){
        if(respuestaCorrectaSeleccionada){
            if(ayudaPulsada){
                //solo sumamos la mitad de puntos
            }else{
                //sumamos todos los puntos
            }
        }else{
            //restamos puntos
        }
    }

    private void fillAnswerResult(){
        resultadoRespuesta.setText(respuestaCorrectaSeleccionada ? "¡RESPUESTA CORRECTA!" : "¡RESPUESTA INCORRECTA!");
        resultadoRespuesta.setTextFill(respuestaCorrectaSeleccionada ? Color.GREEN : Color.RED);
    }

    private Pregunta getRandomQuestion(List<Pregunta> preguntas){
        return preguntas.get(new Random().nextInt(preguntas.size()));
    }

}
