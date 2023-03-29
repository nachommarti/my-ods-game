package com.myodsgame;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

public class ODSGame extends Application {

    private MediaPlayer mediaPlayer;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ODSGame.class.getResource("mainScreen-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 450);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setResizable(false);

        File lobby_sound = new File("src/main/resources/sounds/Lobby.mp3");
        Media media = new Media(lobby_sound.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

        scene.getWindow().focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    mediaPlayer.play();
                } else {
                    mediaPlayer.pause();
                }
            }
        });


        stage.show();
    }

    public static void main(String[] args) {
        //System.out.println(new PreguntaDAO().getPregunta());
        launch();
    }
}