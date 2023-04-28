package com.myodsgame.Builder;

import com.myodsgame.Models.Partida;

public class PartidaDirector {
    private IPartidaBuilder builder;
    public PartidaDirector(IPartidaBuilder builder) {
        this.builder = builder;
    }
    public Partida BuildPartida() {
        builder.BuildRetos();
        builder.BuildMusica();
        builder.BuildSonidos();
        builder.BuildImagenes();
        return builder.getPartida();
    }
}
