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

    private List<Button> respuestas;

    private String respuestaCorrecta;

    private boolean ayudaPulsada;

    private boolean respuestaCorrectaSeleccionada;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //List<Pregunta> preguntas = new PreguntaDAO().getPregunta();
        //Collections.shuffle(preguntas);
        //Pregunta preguntaAleatoria = getRandomQuestion(preguntas);
        Pregunta preguntaAleatoria = TestData.getPregunta();
        enunciadoPregunta.setText(preguntaAleatoria.getEnunciado());
        respuesta1.setText(preguntaAleatoria.getRespuesta1());
        respuesta2.setText(preguntaAleatoria.getRespuesta2());
        respuesta3.setText(preguntaAleatoria.getRespuesta3());
        respuesta4.setText(preguntaAleatoria.getRespuesta4());
        //informacionPista = preguntaAleatoria.getPista();
        this.respuestaCorrecta = preguntaAleatoria.getRespuestaCorrecta();
        this.respuestas = List.of(respuesta1, respuesta2, respuesta3, respuesta4);

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
            System.out.println("pito");
        }

        this.ayuda.setDisable(true);
        this.ayudaPulsada = true;
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
        if(respuestaSeleccionada.getText().equals(respuestaCorrecta)) {
            respuestaCorrectaSeleccionada = true;
        }

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

    private Pregunta getRandomQuestion(List<Pregunta> preguntas){
        return preguntas.get(new Random().nextInt(preguntas.size()));
    }

}
