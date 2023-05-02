package com.myodsgame.Factory;

import com.myodsgame.Models.Reto;
import com.myodsgame.Models.RetoAhorcado;
import com.myodsgame.Models.RetoPregunta;

import java.util.List;

public interface RetoFactory {
    public List<? extends Reto> crearRetos();
}
