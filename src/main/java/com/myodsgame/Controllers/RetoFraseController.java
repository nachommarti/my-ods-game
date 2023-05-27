package com.myodsgame.Controllers;

import com.myodsgame.Mediator.Mediador;
import com.myodsgame.Mediator.MediadorFrase;
import com.myodsgame.Models.Partida;
import com.myodsgame.Models.RetoFrase;
import com.myodsgame.Services.Services;
import com.myodsgame.Utils.EstadoJuego;
import com.myodsgame.Utils.UserUtils;
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

    private List<Character> letrasRestantes = new ArrayList<>();
    private int timeCountdown = 50;
    private MediaPlayer mediaPlayerSonidos = null, mediaPlayerMusic = null, mediaPlayerTicTac = new MediaPlayer(new Media(new File("src/main/resources/sounds/10S_tick.mp3").toURI().toString()));

    private Timeline timeline;
    private Button botonPulsado;

    private Partida partidaActual;
    private Services servicios;
    private Mediador mediador;
    private RetoFrase retoActual;
    private int obtainedPoints;
    private boolean ayudaUsada;

    @FXML
    private Label palabraOculta;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        servicios = new Services();
        mediador = new MediadorFrase();
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

        frase = retoActual.getFrase().toUpperCase();
        frasePista.setText(retoActual.getPista());
        puntosPorAcertar.setText("Puntos por acertar: " + retoActual.getDificultad() * 100);
        puntos.setText("Score: " + EstadoJuego.getInstance().getPartida().getPuntuacion());

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
                                reproducirSonido("src/main/resources/sounds/Fallo.mp3", 0.5);
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
        reproducirMusica();

        if (EstadoJuego.getInstance().getPartida().getVidas() == 1) {
            vidas.setImage(new Image(Path.of("", "src", "main", "resources", "images", "vidaMitad.png").toAbsolutePath().toString()));
        } else if (EstadoJuego.getInstance().getPartida().getVidas() == 0) {
            vidas.setImage(new Image(Path.of("", "src", "main", "resources", "images", "vidasAgotadas.png").toAbsolutePath().toString()));
        }

        timeline.playFromStart();

        if (EstadoJuego.getInstance().getPartida().getPuntuacion() >= retoActual.getPuntuacion() / 2)
            ayuda.setDisable(false);
        else
            ayuda.setDisable(true);

        botonAbandonar.setVisible(partidaActual.isConsolidado());
        loadRetosState();
        ((Label) labelArray.getChildren().get(partidaActual.getRetoActual() - 1)).setStyle("-fx-background-color: rgb(202,184,218)");


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
                currentLabel = new Label(currentChar + "");
                labels.add(currentLabel);
                currentLabel.setStyle("-fx-text-fill: rgba(255, 255, 255, 0); -fx-border-color: white; -fx-border-width: 2;");

                currentLabel.setOnMouseClicked(e -> {
                    if (botonPulsado != null)
                        if (botonPulsado.getText().equals(currentLabel.getText())) {
                            System.out.println("MONDONGO!");
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
            Button button = new Button(currentChar + "");
            clickableCharsArray.add(button);
            System.out.println("Added button: " + currentChar);
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
            if (mediaPlayerMusic != null) {
                mediaPlayerMusic.stop();
            }
            if (mediaPlayerTicTac != null) {
                mediaPlayerTicTac.stop();
            }
            reproducirSonido("src/main/resources/sounds/Acierto.mp3", 0.15);
            EstadoJuego.getInstance().getPartida().getRetosFallados()[partidaActual.getRetoActual() - 1] = false;
            obtainedPoints = servicios.computePoints(retoActual, ayudaUsada, true);
            int puntosPartida = EstadoJuego.getInstance().getPartida().getPuntuacion();
            EstadoJuego.getInstance().getPartida().setPuntuacion(puntosPartida + obtainedPoints);
            UserUtils.saveStats(true, retoActual.getODS());
            ayuda.setDisable(true);
            timeline.stop();
            puntos.setText("Score: " + EstadoJuego.getInstance().getPartida().getPuntuacion());
            disableClickableChars();
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

            ayudaUsada = true;
            reproducirSonido("src/main/resources/sounds/pista_larga.mp3", 0.5);
            mediaPlayerMusic.play();
        }
    }

    private void endTimer() {
        //retoActual.setIntentos(0);
        System.out.println("PARTIDA PERDIDA");
        if (mediaPlayerMusic != null) {
            mediaPlayerMusic.stop();
        }
        if (mediaPlayerTicTac != null) {
            mediaPlayerTicTac.stop();
        }
        timeline.stop();
        reproducirSonido("src/main/resources/sounds/Fallo.mp3", 0.5);
        //botonSalir.setDisable(false);
        UserUtils.saveStats(false, retoActual.getODS());
        obtainedPoints = servicios.computePoints(retoActual, ayudaUsada, false);
        int puntosPartida = EstadoJuego.getInstance().getPartida().getPuntuacion();

        EstadoJuego.getInstance().getPartida().getRetosFallados()[partidaActual.getRetoActual() - 1] = true;
        int vidasPartida = EstadoJuego.getInstance().getPartida().getVidas() - 1;
        if (vidasPartida == 0) {
            reproducirSonido("src/main/resources/sounds/Partida_Perdida.mp3", 0.5);
            EstadoJuego.getInstance().getPartida().setPartidaPerdida(true);
            UserUtils.aumentarPartidasJugadas();
        }
        EstadoJuego.getInstance().getPartida().setVidas(vidasPartida);
        if (EstadoJuego.getInstance().getPartida().getVidas() == 1) {
            vidas.setImage(new Image(Path.of("", "src", "main", "resources", "images", "vidaMitad.png").toAbsolutePath().toString()));
        } else if (EstadoJuego.getInstance().getPartida().getVidas() == 0) {
            vidas.setImage(new Image(Path.of("", "src", "main", "resources", "images", "vidasAgotadas.png").toAbsolutePath().toString()));
        }
        ayuda.setDisable(true);
        if (EstadoJuego.getInstance().getPartida().isConsolidado()) {
            EstadoJuego.getInstance().getPartida().setPuntuacionConsolidada(EstadoJuego.getInstance().getPartida().getPuntuacionConsolidada()
                    + obtainedPoints);
        }

        showPopUp();
        EstadoJuego.getInstance().getPartida().setPuntuacion(puntosPartida + obtainedPoints);
        disableClickableChars();
    }

    private void reproducirMusica() {
        mediaPlayerMusic = mediador.musicaSetter();
        mediaPlayerMusic.play();
    }

    private void reproducirSonido(String sonidoPath, double volumen) {
        mediaPlayerMusic.pause();
        mediaPlayerSonidos = mediador.sonidoSetter(sonidoPath, volumen);
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






