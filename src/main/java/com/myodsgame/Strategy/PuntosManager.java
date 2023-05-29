package com.myodsgame.Strategy;

public class PuntosManager {

    private IEstrategiaPuntos puntosStrategy;
    public void SetPuntosStrategy(IEstrategiaPuntos puntosStrategy){
        this.puntosStrategy = puntosStrategy;
    }

    public int SetPuntosStrategy(int dificultad){
        if(dificultad == 1){SetPuntosStrategy(new EstrategiaPuntosFacil());}
        else if (dificultad == 2){SetPuntosStrategy(new EstrategiaPuntosMedio());}
        else if (dificultad == 3){SetPuntosStrategy(new EstrategiaPuntosDificil());}

        return puntosStrategy.EstablecerPuntos();
    }
}
