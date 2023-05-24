package com.myodsgame.Controllers;

import com.myodsgame.Models.RetoAhorcado;
import com.myodsgame.Services.Services;
import com.myodsgame.Utils.EstadoJuego;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class RetoFraseController implements Initializable {

    @FXML
    private HBox clickableChars;
    @FXML
    private HBox sentence;

    private String frase;

    private Button botonPulsado;

    @FXML
    private Label palabraOculta;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        frase = "LA CONTAMINACION MATA";
        addSentenceLabels();
        addClickableChars();
        System.out.println("hi!");
    }

    private void addSentenceLabels(){
        List<Label> labels = new ArrayList<>();
        for(int i = 0; i < frase.length(); i++){
            Label currentLabel = null;
            char currentChar = frase.charAt(i);
            if (Character.isWhitespace(currentChar)) {
                currentLabel = new Label("-");
                labels.add(currentLabel);
            }else{
                currentLabel = new Label(currentChar+"");
                labels.add(currentLabel);
            }
            currentLabel.setStyle("-fx-text-fill: white; -fx-border-color: white; -fx-border-width: 2;");
            currentLabel.setFont(new Font("Arial", 30));
            currentLabel.setPrefSize(30, 30);
            currentLabel.setAlignment(Pos.CENTER);
            Label finalCurrentLabel = currentLabel;
            currentLabel.setOnMouseClicked(e -> {
                if(botonPulsado != null)
                   if(botonPulsado.getText().equals(finalCurrentLabel.getText())){
                       System.out.println("MONDONGO!");
                       clickableChars.getChildren().remove(botonPulsado);
                       botonPulsado = null;
                   }

            });
        }
        sentence.getChildren().addAll(labels);
    }


    private void addClickableChars(){
        List<String> letters = Arrays.asList(frase.split(""));
        Collections.shuffle(letters);
        String shuffled = letters.stream().collect(Collectors.joining()).replaceAll("\\s", "");
        List<Button> clickableCharsArray = new ArrayList<>();
        for(int i = 0; i < shuffled.length(); i++){
            char currentChar = shuffled.charAt(i);
            Button button = new Button(currentChar+"");
            clickableCharsArray.add(button);
            System.out.println("Added button: " + currentChar);
            button.setOnAction(e -> botonPulsado = button);
        }
        clickableChars.getChildren().addAll(clickableCharsArray);

    }


}
