package com.myodsgame.Mediator;

import com.myodsgame.Models.Reto;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.util.HashMap;
import java.util.Optional;

public class MediadorFrase extends Mediador{
    @Override
    public Optional<ButtonType> ayudaClicked(Reto retoActual, Label currentScore, Button ayuda) {
        super.ayudaClicked(retoActual, currentScore, ayuda);
        return result;
    }
}