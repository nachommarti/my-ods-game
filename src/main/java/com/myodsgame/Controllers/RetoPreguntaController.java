package com.myodsgame.Controllers;

import com.myodsgame.DAO.PreguntaDAO;
import com.myodsgame.Models.Pregunta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class RetoPreguntaController implements Initializable {

    @FXML
    private Label enunciadoPregunta;

    @FXML
    private Button pista;

    @FXML
    private Button respuesta1;

    @FXML
    private Button respuesta2;

    @FXML
    private Button respuesta3;

    @FXML
    private Button respuesta4;

    private String informacionPista;

    private String respuestaCorrecta;

    @FXML
    void pistaClicked(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Pregunta> preguntas = new PreguntaDAO().getPregunta();
        Collections.shuffle(preguntas);
        Pregunta preguntaAleatoria = getRandomQuestion(preguntas);
        enunciadoPregunta.setText(preguntaAleatoria.getEnunciado());
        respuesta1.setText(preguntaAleatoria.getRespuesta1());
        respuesta2.setText(preguntaAleatoria.getRespuesta2());
        respuesta3.setText(preguntaAleatoria.getRespuesta3());
        respuesta4.setText(preguntaAleatoria.getRespuesta4());
        //informacionPista = preguntaAleatoria.getPista();

    }

    private Pregunta getRandomQuestion(List<Pregunta> preguntas){
        return preguntas.get(new Random().nextInt(preguntas.size()));
    }

}
