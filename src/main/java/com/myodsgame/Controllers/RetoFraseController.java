package com.myodsgame.Controllers;

import com.myodsgame.Models.RetoAhorcado;
import com.myodsgame.Services.Services;
import com.myodsgame.Utils.EstadoJuego;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
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
            Label currentLabel = new Label();
            char currentChar = frase.charAt(i);
            if (Character.isWhitespace(currentChar)) {
                currentLabel = new Label("-");
                labels.add(currentLabel);
            }else{
                currentLabel = new Label(currentChar+"");
                labels.add(currentLabel);
            }
            System.out.println("Adding label: " + currentLabel.getText());
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
        }
        clickableChars.getChildren().addAll(clickableCharsArray);

    }


}
