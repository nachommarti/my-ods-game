package com.myodsgame.Controllers;

import com.myodsgame.Mediator.Mediador;
import com.myodsgame.Models.Partida;
import com.myodsgame.Models.RetoAhorcado;
import com.myodsgame.Utils.EstadoJuego;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

public class RetoAhorcadoController implements Initializable {

    @FXML
    private ImageView vidas;
    @FXML
    private Button ayudaButton;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label timer;
    @FXML
    private Label frasePista;
    @FXML
    private ImageView imagenODS;
    @FXML
    private ImageView imagenAhorcado;
    @FXML
    private Label estatusRespuesta;
    @FXML
    private Label partidaScore;
    @FXML
    private HBox botones1;
    @FXML
    private HBox botones2;
    @FXML
    private HBox botones3;
    @FXML
    private Label palabraMostrada;
    @FXML
    private HBox labelArray;
    @FXML
    private Label puntosPorAcertar;
    @FXML
    private Button botonAbandonar;

    private Partida partidaActual;
    private RetoAhorcado retoActual;
    private String palabra;
    private int obtainedPoints;
    private Timeline timeline;
    private int timeCountdown;

    private MediaPlayer mediaPlayerMusic;
    private final MediaPlayer mediaPlayerTicTac = new MediaPlayer(new Media(new File("src/main/resources/sounds/10S_tick.mp3").toURI().toString()));
    private Mediador mediador;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BackgroundImage fill = new BackgroundImage(EstadoJuego.getInstance().getPartida().getImagenFondo(), null,
                null,
                null,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true));
        Background background = new Background(fill);
        borderPane.setBackground(background);
        mediador = new Mediador();
        timeline = new Timeline();
        reproducirMusica();
        timeline.setCycleCount(Timeline.INDEFINITE);
        partidaActual = EstadoJuego.getInstance().getPartida();

        if(partidaActual.getRetoActual() > 4 && partidaActual.getRetoActual() <= 7){
            while(partidaActual.getRetos().get(partidaActual.getRetoQueHayQueMirarEnElArray()).getDificultad() != 2) {
                System.out.println("wenos dias joselu");
                System.out.println("reto del array actual: " + partidaActual.getRetoQueHayQueMirarEnElArray());
                EstadoJuego.getInstance().getPartida().setRetoQueHayQueMirarEnElArray(partidaActual.getRetoQueHayQueMirarEnElArray() + 1);
                System.out.println("reto actualizado: " + EstadoJuego.getInstance().getPartida().getRetoQueHayQueMirarEnElArray());
            }
        }

        if(partidaActual.getRetoActual() > 7 && partidaActual.getRetoActual() <= 10){
            while(partidaActual.getRetos().get(partidaActual.getRetoQueHayQueMirarEnElArray()).getDificultad() != 3)
                EstadoJuego.getInstance().getPartida().setRetoQueHayQueMirarEnElArray(partidaActual.getRetoQueHayQueMirarEnElArray()+1);
        }

        if (EstadoJuego.getInstance().getPartida().getAyudasRestantes() <= 0)
        {
            ayudaButton.setDisable(true);
        }

        for(int i = 1; i <= partidaActual.getRetos().size(); i++){
            System.out.println("Desde AHORCADO - Reto numero " + i + " es de tipo " + partidaActual.getRetos().get(i-1).getTipoReto());
        }

        System.out.println("Desde reto AHORCADO - reto actual: " + partidaActual.getRetoActual());
        System.out.println("Desde reto AHORCADO - tipo reto actual: " + partidaActual.getRetos().get(partidaActual.getRetoActual()-1).getTipoReto());


        retoActual = (RetoAhorcado) EstadoJuego.getInstance().getPartida().getRetos().get(EstadoJuego.getInstance().getPartida().getRetoQueHayQueMirarEnElArray());

        timeCountdown = retoActual.getDuracion();
        palabra = retoActual.getPalabra().toUpperCase();

        // KeyFrame event handler
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        event -> {
                            timeCountdown--;
                            // update timerLabel
                            timer.setText(Integer.toString(timeCountdown));

                            if (timeCountdown <= 0) {
                                palabraMostrada.setText(palabra);
                                endTimer();
                            }

                            if (timeCountdown == retoActual.getTiempoTicTac()){
                                mediaPlayerTicTac.play();
                                mediaPlayerMusic.setVolume(0.05);
                            }
                        })
        );


        ayudaButton.setGraphic(new ImageView(new Image(Path.of("", "src", "main", "resources", "images", "ayuda.png").toAbsolutePath().toString())));
        setKeyBoardListeners(botones1);
        setKeyBoardListeners(botones2);
        setKeyBoardListeners(botones3);

        if (retoActual.getODS().size() == 1) imagenODS.setImage(new Image(Path.of("", "src", "main", "resources", "images", "ODS_"+retoActual.getODS().get(0)+".jpg").toAbsolutePath().toString()));
        else imagenODS.setImage(new Image(Path.of("", "src", "main", "resources", "images", "ODS_0.jpg").toAbsolutePath().toString()));

        frasePista.setText(retoActual.getPista());
        imagenAhorcado.setImage(new Image(Path.of("", "src", "main", "resources", "images", "ahorcado" + retoActual.getIntentos() + ".png").toAbsolutePath().toString()));

        loadRetosState();
        loadPalabra();

        vidas.setImage(EstadoJuego.getInstance().getPartida().getImagenVidas());

        ayudaButton.setDisable(EstadoJuego.getInstance().getPartida().getPuntuacion() < retoActual.getPuntuacion() / 2);

        timeline.playFromStart();

        botonAbandonar.setVisible(partidaActual.isConsolidado());

        Rectangle clip = new Rectangle(imagenODS.getFitWidth(), imagenODS.getFitHeight());
        clip.setArcWidth(40);
        clip.setArcHeight(40);
        imagenODS.setClip(clip);
        puntosPorAcertar.setText("Puntos por acertar: " + retoActual.getDificultad()*100);
    }

    private void loadRetosState(){
        for(int i = 0; i < partidaActual.getRetoActual(); i++){
            ((Label) labelArray.getChildren().get(i))
                    .setStyle(partidaActual.getRetosFallados()[i] ? "-fx-background-color: rgb(255,25,25); " : "-fx-background-color: rgba(184, 218, 186, 1)");
        }
    }

    private void setKeyBoardListeners(HBox botones){

        for(Node node : botones.getChildren()){
            Button button = (Button) node;
            button.setOnAction(eventHandler);
            button.setOnKeyPressed(keyEventHandler);
        }
    }

    EventHandler<ActionEvent> eventHandler = event -> {
        // Handle the button click
       Button pressedButton = (Button) event.getSource();
       char selectedChar = pressedButton.getText().charAt(0);
       Background background = new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY));
       if(palabra.contains(String.valueOf(selectedChar))) {
           background = new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY));
           pressedButton.setBackground(background);
           loadChar(selectedChar);
           checkWin();
       }
       else {
           pressedButton.setBackground(background);
           retoActual.setIntentos(retoActual.getIntentos()-1);

           checkLose();
           imagenAhorcado.setImage(new Image(Path.of("", "src", "main", "resources", "images", "ahorcado" + retoActual.getIntentos() +".png").toAbsolutePath().toString()));
       }
       pressedButton.setDisable(true);

    };

    EventHandler<KeyEvent> keyEventHandler = event -> {
        char selectedChar = Character.toUpperCase(event.getText().charAt(0));
        Button pressedButton = getButtonForPressedKey(selectedChar, botones1, botones2, botones3);
        Background background = new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY));
        if(Character.toUpperCase(selectedChar) == pressedButton.getText().charAt(0)) {
            if (palabra.contains(String.valueOf(selectedChar))) {
                background = new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY));
                pressedButton.setBackground(background);
                loadChar(selectedChar);
                checkWin();
            } else {
                pressedButton.setBackground(background);
                retoActual.setIntentos(retoActual.getIntentos() - 1);

                checkLose();
                imagenAhorcado.setImage(new Image(Path.of("", "src", "main", "resources", "images", "ahorcado" + retoActual.getIntentos() + ".png").toAbsolutePath().toString()));
            }
            pressedButton.setDisable(true);
        }
    };

    private Button getButtonForPressedKey(char pressedChar, HBox ... hboxes) {
        for(HBox hbox : hboxes) {
            for (javafx.scene.Node node : hbox.getChildren()) {
                if (node instanceof Button) {
                    Button button = (Button) node;
                    if (Character.toUpperCase(pressedChar) == button.getText().charAt(0)) {
                        return button;
                    }
                }
            }
        }
        return null;
    }

    private void endTimer(){
        retoActual.setIntentos(0);
        imagenAhorcado.setImage(new Image(Path.of("", "src", "main", "resources", "images", "ahorcado" + retoActual.getIntentos() +".png").toAbsolutePath().toString()));
        checkLose();
    }

    private void disableKeyboard(){
        for(Node node : botones1.getChildren()) {
            Button button = (Button) node;
            button.setDisable(true);
        }
        for(Node node : botones2.getChildren()){
            Button button = (Button) node;
            button.setDisable(true);
        }
        for(Node node : botones3.getChildren()){
            Button button = (Button) node;
            button.setDisable(true);
        }
    }

    private void checkWin() {
        if (palabraMostrada.getText().equals(palabra)) {
            reproducirSonido("src/main/resources/sounds/Acierto.mp3", 0.15);
            disableKeyboard();
            obtainedPoints = mediador.checkWin(mediaPlayerTicTac, mediaPlayerMusic, retoActual, partidaActual, ayudaButton);
            showMessage("HAS GANADO " + obtainedPoints + " PUNTOS", true);
            timeline.stop();
            showPopUp();
        }
    }

    private void checkLose(){
        if(retoActual.getIntentos() == 0){
            disableKeyboard();
            timeline.stop();
            ((Label) labelArray.getChildren().get(partidaActual.getRetoActual() - 1)).setStyle("-fx-background-color: rgba(204, 96, 56, 1)");
            int[] array = mediador.checkLose(mediaPlayerTicTac, mediaPlayerMusic, retoActual, partidaActual, ayudaButton);
            obtainedPoints = array[0];
            if(array[1] == 0){
                showMessage("HAS PERDIDO LA PARTIDA Y " + obtainedPoints + " PUNTOS!", false);
                reproducirSonido("src/main/resources/sounds/Partida_Perdida.mp3", 0.5);
                EstadoJuego.getInstance().getPartida().setImagenVidas(new Image(Path.of("", "src", "main", "resources", "images", "vidasAgotadas.png").toAbsolutePath().toString()));
            } else {
                showMessage("HAS PERDIDO " + obtainedPoints + " PUNTOS!", false);
                reproducirSonido("src/main/resources/sounds/Fallo.mp3", 0.5);
                EstadoJuego.getInstance().getPartida().setImagenVidas(new Image(Path.of("", "src", "main", "resources", "images", "vidaMitad.png").toAbsolutePath().toString()));
            }
            vidas.setImage(EstadoJuego.getInstance().getPartida().getImagenVidas());
            showPopUp();
        }
    }

    private void showMessage(String message, boolean win){
        estatusRespuesta.setText(message);
        estatusRespuesta.setTextFill(win ? Color.GREEN : Color.RED);
        partidaScore.setText("Score: " + EstadoJuego.getInstance().getPartida().getPuntuacion());
    }
    
    private void loadPalabra(){
        ((Label) labelArray.getChildren().get(partidaActual.getRetoActual() - 1)).setStyle("-fx-background-color: rgb(202,184,218)");

        for(int i = 0; i < palabra.length(); i++)
            palabraMostrada.setText(palabraMostrada.getText() + "_");

        partidaScore.setText("Score: " + EstadoJuego.getInstance().getPartida().getPuntuacion());


    }

    private void loadChar(char selectedChar){
        for(int i = 0; i < palabra.length(); i++) {
            if(palabra.charAt(i) == selectedChar) {
                StringBuilder sb = new StringBuilder(palabraMostrada.getText());
                sb.setCharAt(i, selectedChar);
                palabraMostrada.setText(sb.toString());
            }
        }
    }

    @FXML
    void ayudaButtonClicked(ActionEvent event) {
        Optional<ButtonType> result = mediador.ayudaClicked(retoActual, partidaScore, ayudaButton);
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean notFoundChar = true;
            while (notFoundChar) {
                char randomChar = palabra.charAt(new Random().nextInt(palabra.length()));
                System.out.println("Random char is: " + randomChar);
                if (!palabraMostrada.getText().contains(String.valueOf(randomChar))) {
                    notFoundChar = isNotFoundChar(notFoundChar, randomChar, botones1);
                    notFoundChar = isNotFoundChar(notFoundChar, randomChar, botones2);
                    notFoundChar = isNotFoundChar(notFoundChar, randomChar, botones3);
                }
            }
            reproducirSonido("src/main/resources/sounds/pista_larga.mp3", 0.5);
            mediaPlayerMusic.play();
        }
    }

    @FXML
    void botonAbandonarPulsado(ActionEvent event) {
        mediador.botonAbandonarPulsado(mediaPlayerTicTac, mediaPlayerMusic, botonAbandonar, timeline);
    }

    private void showPopUp() {
        Stage stage = mediador.showPopUp(partidaActual);
        stage.show();
    }

    private void reproducirMusica(){
        mediaPlayerMusic = mediador.musicaSetter();
        mediaPlayerMusic.play();
    }

    private void reproducirSonido(String sonidoPath, double volumen){
        mediaPlayerMusic.pause();
        MediaPlayer mediaPlayerSonidos = mediador.sonidoSetter(sonidoPath, volumen);
        mediaPlayerSonidos.play();
    }

    private boolean isNotFoundChar(boolean notFoundChar, char randomChar, HBox botones) {
        for (Node node : botones.getChildren()) {
            Button button = (Button) node;
            if (button.getText().equals(String.valueOf(randomChar))) {
                button.setDisable(true);
                loadChar(randomChar);
                checkWin();
                notFoundChar = false;
                break;
            }
        }
        return notFoundChar;
    }
}
