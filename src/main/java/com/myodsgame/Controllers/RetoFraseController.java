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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
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
    @FXML
    private Button ayuda;
    @FXML
    private Label timer;

    private String frase;

    private List<Character> letrasRestantes = new ArrayList<>();
    private int timeCountdown = 40;
    private MediaPlayer mediaPlayerSonidos = null, mediaPlayerMusic = null, mediaPlayerTicTac = new MediaPlayer(new Media(new File("src/main/resources/sounds/10S_tick.mp3").toURI().toString()));

    private Timeline timeline;
    private Button botonPulsado;

    @FXML
    private Label palabraOculta;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        frase = "LA CONTAMINACION MATA";
        addSentenceLabels();
        addClickableChars();

        System.out.println("hi!");

        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        event -> {
                            timeCountdown--;
                            // update timerLabel
                            timer.setText(Integer.toString(timeCountdown));

                            if (timeCountdown <= 0) {
                                reproducirSonido(false);
                                timeline.stop();
                                mostrarFrase();
                                endTimer();

                            }

                            if (timeCountdown == 10) {
                                Media TicTac = new Media(new File("src/main/resources/sounds/10S_tick.mp3").toURI().toString());
                                mediaPlayerTicTac = new MediaPlayer(TicTac);
                                mediaPlayerTicTac.play();
                            }
                        })
        );
        timeline.playFromStart();

    }

    private void addSentenceLabels() {
        List<Label> labels = new ArrayList<>();
        for (int i = 0; i < frase.length(); i++) {
            Label currentLabel;
            Character currentChar = frase.charAt(i);
            if (Character.isWhitespace(currentChar)) {
                currentLabel = new Label("-");
                labels.add(currentLabel);
                currentLabel.setStyle("-fx-text-fill: white; -fx-border-color: white; -fx-border-width: 2;");
            } else {
                currentLabel = new Label(currentChar + "");
                labels.add(currentLabel);
                currentLabel.setStyle("-fx-text-fill: rgba(255, 255, 255, 0); -fx-border-color: white; -fx-border-width: 2;");

                currentLabel.setOnMouseClicked(e -> {
                    if (botonPulsado != null)
                        if (botonPulsado.getText().equals(currentLabel.getText())) {
                            System.out.println("MONDONGO!");
                            clickableChars.getChildren().remove(botonPulsado);
                            currentLabel.setStyle("-fx-text-fill: white; -fx-border-color: white; -fx-border-width: 2;");
                            botonPulsado = null;
                            letrasRestantes.remove(currentChar);
                            checkWin();
                        }
                });

            }
            currentLabel.setFont(new Font("Arial", 30));
            currentLabel.setPrefSize(30, 30);
            currentLabel.setAlignment(Pos.CENTER);

        }

        sentence.getChildren().addAll(labels);
    }

    private void addClickableChars() {
        List<String> letters = Arrays.asList(frase.split(""));
        Collections.shuffle(letters);
        String shuffled = letters.stream().collect(Collectors.joining()).replaceAll("\\s", "");
        List<Button> clickableCharsArray = new ArrayList<>();
        for (int i = 0; i < shuffled.length(); i++) {
            char currentChar = shuffled.charAt(i);
            Button button = new Button(currentChar + "");
            clickableCharsArray.add(button);
            System.out.println("Added button: " + currentChar);
            button.setOnAction(e -> botonPulsado = button);
            letrasRestantes.add(currentChar);
        }
        clickableChars.getChildren().addAll(clickableCharsArray);
    }



    private void checkWin() {
        if (letrasRestantes.size() == 0)
            System.out.println("V DE VICTORIA!");
    }

    @FXML
    void ayudaPulsada(ActionEvent event) {
        List<String> letters = Arrays.asList(frase.split(""));
        Collections.shuffle(letters);
        String shuffled = letters.stream().collect(Collectors.joining()).replaceAll("\\s", "");
        Character letraRandomRestante = shuffled.charAt(new Random().nextInt(shuffled.length()));

        for (Node node : sentence.getChildren()) {
            Label label = (Label) node;
            if (label.getText().equals(Character.toString(letraRandomRestante))) {
                label.setStyle("-fx-text-fill: green; -fx-border-color: white; -fx-border-width: 2;");
                letrasRestantes.remove(letraRandomRestante);
            }
        }
        List<Button> buttonsToBeRemoved = new ArrayList<>();
        for (Node node : clickableChars.getChildren()) {
            Button button = (Button) node;
            if (button.getText().equals(Character.toString(letraRandomRestante))) {
                buttonsToBeRemoved.add(button);
            }
        }
        clickableChars.getChildren().removeAll(buttonsToBeRemoved);


        ayuda.setDisable(true);
    }

    private void endTimer() {
        //retoActual.setIntentos(0);
        System.out.println("PARTIDA PERDIDA");
    }

    private void reproducirSonido(boolean acertado) {
        String path;
        if (acertado) path = "src/main/resources/sounds/Acierto.mp3";
        else path = "src/main/resources/sounds/Fallo.mp3";

        mediaPlayerSonidos = new MediaPlayer(new Media(new File(path).toURI().toString()));
        mediaPlayerSonidos.setVolume(0.2);
        mediaPlayerSonidos.play();
    }

    private void mostrarFrase() {
        for (Node node : sentence.getChildren()) {
            Label label = (Label) node;
            label.setStyle("-fx-text-fill: red; -fx-border-color: white; -fx-border-width: 2;");
        }
    }
}






