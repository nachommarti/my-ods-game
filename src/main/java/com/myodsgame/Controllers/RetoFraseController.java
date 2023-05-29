package com.myodsgame.Controllers;

import com.myodsgame.Mediator.Mediador;
import com.myodsgame.Models.Partida;
import com.myodsgame.Models.RetoFrase;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class RetoFraseController implements Initializable {

    @FXML
    private ImageView imagenODS;
    @FXML
    private HBox clickableChars;
    @FXML
    private BorderPane borderPane;
    @FXML
    private HBox sentence;
    @FXML
    private Button ayuda;
    @FXML
    private Label timer;
    @FXML
    private ImageView vidas;
    @FXML
    private Label frasePista;
    @FXML
    private Label puntos;
    @FXML
    private Label puntosPorAcertar;
    @FXML
    private Button botonAbandonar;
    @FXML
    private HBox labelArray;

    private String frase;
    private final List<Character> letrasRestantes = new ArrayList<>();
    private int timeCountdown;
    private MediaPlayer mediaPlayerMusic;
    private final MediaPlayer mediaPlayerTicTac = new MediaPlayer(new Media(new File("src/main/resources/sounds/10S_tick.mp3").toURI().toString()));
    private Timeline timeline;
    private Button botonPulsado;
    private Partida partidaActual;
    private Mediador mediador;
    private RetoFrase retoActual;
    private int obtainedPoints; // NO BORRAR


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mediador = new Mediador();
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        partidaActual = EstadoJuego.getInstance().getPartida();
        ayuda.setGraphic(new ImageView(new Image(Path.of("", "src", "main", "resources", "images", "ayuda.png").toAbsolutePath().toString())));

        if (partidaActual.getRetoActual() > 4 && partidaActual.getRetoActual() <= 7) {
            while (partidaActual.getRetos().get(partidaActual.getRetoQueHayQueMirarEnElArray()).getDificultad() != 2) {
                EstadoJuego.getInstance().getPartida().setRetoQueHayQueMirarEnElArray(partidaActual.getRetoQueHayQueMirarEnElArray() + 1);
            }
        }

        if (partidaActual.getRetoActual() > 7 && partidaActual.getRetoActual() <= 10) {
            while (partidaActual.getRetos().get(partidaActual.getRetoQueHayQueMirarEnElArray()).getDificultad() != 3)
                EstadoJuego.getInstance().getPartida().setRetoQueHayQueMirarEnElArray(partidaActual.getRetoQueHayQueMirarEnElArray() + 1);
        }

        if (EstadoJuego.getInstance().getPartida().getAyudasRestantes() <= 0) {
            ayuda.setDisable(true);
        }

        BackgroundImage fill = new BackgroundImage(EstadoJuego.getInstance().getPartida().getImagenFondo(), null,
                null,
                null,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true));
        Background background = new Background(fill);
        borderPane.setBackground(background);

        retoActual = (RetoFrase) EstadoJuego.getInstance().getPartida().getRetos().get(EstadoJuego.getInstance().getPartida().getRetoQueHayQueMirarEnElArray());

        if (retoActual.getODS().size() == 1) imagenODS.setImage(new Image(Path.of("", "src", "main", "resources", "images", "ODS_"+retoActual.getODS().get(0)+".jpg").toAbsolutePath().toString()));
        else imagenODS.setImage(new Image(Path.of("", "src", "main", "resources", "images", "ODS_0.jpg").toAbsolutePath().toString()));

        Rectangle clip = new Rectangle(imagenODS.getFitWidth(), imagenODS.getFitHeight());
        clip.setArcWidth(40);
        clip.setArcHeight(40);
        imagenODS.setClip(clip);

        frase = retoActual.getFrase().toUpperCase();
        frasePista.setText(retoActual.getPista());
        puntosPorAcertar.setText("Puntos por acertar: " + retoActual.getDificultad() * 100);
        puntos.setText("Score: " + EstadoJuego.getInstance().getPartida().getPuntuacion());

        addSentenceLabels();
        addClickableChars();

        timeCountdown = retoActual.getDuracion();

        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        event -> {
                            timeCountdown--;
                            // update timerLabel
                            timer.setText(Integer.toString(timeCountdown));

                            if (timeCountdown <= 0) {
                                mostrarFrase();
                                endTimer();
                            }

                            if (timeCountdown == retoActual.getTiempoTicTac()) {
                                mediaPlayerTicTac.play();
                                mediaPlayerMusic.setVolume(0.05);
                            }
                        })
        );
        reproducirMusica();

        vidas.setImage(EstadoJuego.getInstance().getPartida().getImagenVidas());

        timeline.playFromStart();

        ayuda.setDisable(EstadoJuego.getInstance().getPartida().getPuntuacion() < retoActual.getPuntuacion() / 2);

        botonAbandonar.setVisible(partidaActual.isConsolidado());
        loadRetosState();
        ((Label) labelArray.getChildren().get(partidaActual.getRetoActual() - 1)).setStyle("-fx-background-color: rgb(202,184,218)");
        loadFraseChars();


    }

    private void loadFraseChars() {
        List<String> letters = Arrays.asList(frase.split(""));
        Collections.shuffle(letters);
        StringBuilder shuffled = new StringBuilder(letters.stream().collect(Collectors.joining()).replaceAll("\\s", ""));
        boolean [] rellenados = new boolean[sentence.getChildren().size()];
        int letrasParaPoner = getPercentageSizeOfString(shuffled);
        System.out.println("Letras para poner: " + letrasParaPoner);

        for(int i = 0; i < letrasParaPoner; i++) {

            Character letraRandomRestante = shuffled.charAt(new Random().nextInt(shuffled.length()));
            int contadorRellenados = 0;
            for (Node node : sentence.getChildren()) {
                Label label = (Label) node;
                if (label.getText().equals(Character.toString(letraRandomRestante)) && !rellenados[contadorRellenados]) {
                    System.out.println("Explorando label : " + label.getText());
                    label.setStyle("-fx-background-color: grey; -fx-text-fill: white; -fx-border-color: white; -fx-border-width: 2;");
                    System.out.print("LETRA ELIMINADA: " + letraRandomRestante + " ");
                    letrasRestantes.remove(letraRandomRestante);
                    rellenados[contadorRellenados] = true;
                    break;
                }else System.out.println("saltando...");
                contadorRellenados++;
            }
            Button buttonToBeRemoved = null;
            for (Node node : clickableChars.getChildren()) {
                Button button = (Button) node;
                if (button.getText().equals(Character.toString(letraRandomRestante))) {
                    buttonToBeRemoved = button;
                }
            }
            clickableChars.getChildren().remove(buttonToBeRemoved);

            for(int j = 0; j < shuffled.length(); j++){
                if(shuffled.charAt(j) == letraRandomRestante){
                    shuffled.deleteCharAt(j);
                    break;
                }
            }
        }
    }

    private int getPercentageSizeOfString(StringBuilder sb){
        int length = sb.length();
        int percentageSize;

        if(retoActual.getDificultad() == 1){
            percentageSize = (int) Math.round(length * 0.3);
        }else if(retoActual.getDificultad() == 2){
            percentageSize = (int) Math.round(length * 0.2);
        }else{
            percentageSize = (int) Math.round(length * 0.1);
        }

        return percentageSize;
    }

    private void loadRetosState() {
        for (int i = 0; i < partidaActual.getRetoActual(); i++) {
            ((Label) labelArray.getChildren().get(i))
                    .setStyle(partidaActual.getRetosFallados()[i] ? "-fx-background-color: rgb(255,25,25); " : "-fx-background-color: rgba(184, 218, 186, 1)");
        }
    }

    private void addSentenceLabels() {
        List<Label> labels = new ArrayList<>();
        for (int i = 0; i < frase.length(); i++) {
            Label currentLabel;
            Character currentChar = frase.charAt(i);
            if (Character.isWhitespace(currentChar)) {
                currentLabel = new Label("");
                labels.add(currentLabel);
                currentLabel.setVisible(false);
                currentLabel.setStyle("-fx-background-color: grey; -fx-text-fill: white; -fx-border-color: white; -fx-border-width: 2;");
            } else {
                currentLabel = new Label(String.valueOf(currentChar));
                labels.add(currentLabel);
                currentLabel.setStyle("-fx-text-fill: rgba(255, 255, 255, 0); -fx-border-color: white; -fx-border-width: 2;");

                currentLabel.setOnMouseClicked(e -> {
                    if (botonPulsado != null)
                        if (botonPulsado.getText().equals(currentLabel.getText())) {
                            clickableChars.getChildren().remove(botonPulsado);
                            currentLabel.setStyle("-fx-background-color: grey; -fx-text-fill: white; -fx-border-color: white; -fx-border-width: 2;");
                            botonPulsado = null;
                            letrasRestantes.remove(currentChar);
                            checkWin();
                        } else {
                            botonPulsado.setStyle("-fx-background-color: red;");
                            Stage stage = (Stage) botonPulsado.getScene().getWindow();
                            stage.requestFocus();
                            botonPulsado = null;
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
            Button button = new Button(String.valueOf(currentChar));
            clickableCharsArray.add(button);
            button.setOnAction(e -> {
                botonPulsado = button;
                button.setStyle("-fx-background-color: white;");
            });
            letrasRestantes.add(currentChar);
        }
        clickableChars.getChildren().addAll(clickableCharsArray);
    }


    private void checkWin() {
        if (letrasRestantes.size() == 0) {
            reproducirSonido("src/main/resources/sounds/Acierto.mp3", 0.15);
            disableClickableChars();
            obtainedPoints = mediador.checkWin(mediaPlayerTicTac, mediaPlayerMusic, retoActual, partidaActual, ayuda);
            timeline.stop();
            puntos.setText("Score: " + EstadoJuego.getInstance().getPartida().getPuntuacion());
            showPopUp();
        }
    }

    private void showPopUp() {
        Stage stage = mediador.showPopUp(partidaActual);
        stage.show();
    }

    @FXML
    void ayudaPulsada(ActionEvent event) {
        Optional<ButtonType> result = mediador.ayudaClicked(retoActual, puntos, ayuda);
        if (result.isPresent() && result.get() == ButtonType.OK) {
            List<String> letters = Arrays.asList(frase.split(""));
            Collections.shuffle(letters);
            String shuffled = letters.stream().collect(Collectors.joining()).replaceAll("\\s", "");
            Character letraRandomRestante = shuffled.charAt(new Random().nextInt(shuffled.length()));

            for (Node node : sentence.getChildren()) {
                Label label = (Label) node;
                if (label.getText().equals(Character.toString(letraRandomRestante))) {
                    label.setStyle("-fx-background-color: lightgreen; -fx-text-fill: white; -fx-border-color: white; -fx-border-width: 2;");
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

            reproducirSonido("src/main/resources/sounds/pista_larga.mp3", 0.5);
            mediaPlayerMusic.play();
        }
    }

    private void endTimer() {
        disableClickableChars();
        timeline.stop();
        ((Label) labelArray.getChildren().get(partidaActual.getRetoActual() - 1)).setStyle("-fx-background-color: rgba(204, 96, 56, 1)");
        int[] array = mediador.checkLose(mediaPlayerTicTac, mediaPlayerMusic, retoActual, partidaActual, ayuda);
        obtainedPoints = array[0];
        if (array[1] == 0) {
            reproducirSonido("src/main/resources/sounds/Partida_Perdida.mp3", 0.5);
            EstadoJuego.getInstance().getPartida().setImagenVidas(new Image(Path.of("", "src", "main", "resources", "images", "vidasAgotadas.png").toAbsolutePath().toString()));
        } else {
            reproducirSonido("src/main/resources/sounds/Fallo.mp3", 0.5);
            EstadoJuego.getInstance().getPartida().setImagenVidas(new Image(Path.of("", "src", "main", "resources", "images", "vidaMitad.png").toAbsolutePath().toString()));
        }
        vidas.setImage(EstadoJuego.getInstance().getPartida().getImagenVidas());
        puntos.setText("Score: " + EstadoJuego.getInstance().getPartida().getPuntuacion());
        showPopUp();
    }

    private void reproducirMusica() {
        mediaPlayerMusic = mediador.musicaSetter();
        mediaPlayerMusic.play();
    }

    private void reproducirSonido(String sonidoPath, double volumen) {
        mediaPlayerMusic.pause();
        MediaPlayer mediaPlayerSonidos = mediador.sonidoSetter(sonidoPath, volumen);
        mediaPlayerSonidos.play();
    }

    private void mostrarFrase() {
        for (Node node : sentence.getChildren()) {
            Label label = (Label) node;
            label.setStyle("-fx-background-color:red; -fx-text-fill: white; -fx-border-color: white; -fx-border-width: 2;");
        }
    }

    private void disableClickableChars() {
        for (Node node : clickableChars.getChildren()) {
            Button button = (Button) node;
            button.setDisable(true);
        }
    }

    @FXML
    private void botonAbandonarPulsado(ActionEvent event) {
        mediador.botonAbandonarPulsado(mediaPlayerTicTac, mediaPlayerMusic, botonAbandonar, timeline);
    }
}






