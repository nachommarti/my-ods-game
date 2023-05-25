package com.myodsgame.Mediator;

import com.myodsgame.Models.Reto;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

public class MediadorAhorcado extends Mediador{
    @Override
    public Optional<ButtonType> ayudaClicked(Reto retoActual, Label currentScore, Button ayuda) {
        super.ayudaClicked(retoActual, currentScore, ayuda);
        /*if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean notFoundChar = true;
            while (notFoundChar) {
                char randomChar = palabra.charAt(new Random().nextInt(palabra.length()));
                System.out.println("Random char is: " + randomChar);
                if (!palabraMostrada.getText().contains("" + randomChar)) {

                    for (Node node : botones1.getChildren()) {
                        Button button = (Button) node;
                        if (button.getText().equals("" + randomChar)) {
                            button.setDisable(true);
                            loadChar(randomChar);
                            checkWin();
                            notFoundChar = false;
                            break;

                        }

                    }

                    for (Node node : botones2.getChildren()) {
                        Button button = (Button) node;
                        if (button.getText().equals("" + randomChar)) {
                            button.setDisable(true);
                            loadChar(randomChar);
                            checkWin();
                            notFoundChar = false;
                            break;
                        }
                    }

                    for (Node node : botones3.getChildren()) {
                        Button button = (Button) node;
                        if (button.getText().equals("" + randomChar)) {
                            button.setDisable(true);
                            loadChar(randomChar);
                            checkWin();
                            notFoundChar = false;
                            break;
                        }
                    }
                }
            }
        }
         */
        return result;
    }
}
