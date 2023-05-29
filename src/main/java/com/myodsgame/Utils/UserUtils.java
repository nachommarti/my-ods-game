package com.myodsgame.Utils;

import com.myodsgame.Models.Estadisticas;
import com.myodsgame.Models.Usuario;
import com.myodsgame.Repository.Repositorio;
import com.myodsgame.Repository.RepositorioEstadisticasImpl;
import com.myodsgame.Repository.RepositorioUsuarioImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserUtils {

    public static String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    public static String EMAIL_REGEX = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
    public static String USERNAME_REGEX ="^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";

    public static boolean checkPassword(String password){
        return password.matches(PASSWORD_REGEX);
    }

    public static boolean checkEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }
    public static RepositorioUsuarioImpl repositorioUsuario = new RepositorioUsuarioImpl();
    public static Repositorio<Estadisticas, String> repositorioEstadisticas = new RepositorioEstadisticasImpl();

    public static boolean checkUserExists(String username){
        return repositorioUsuario.findById(username) != null;
    }

    public static boolean checkUsername(String username) {
        return username.matches(USERNAME_REGEX);
    }

    public static void saveUserScore(int score){
        Usuario user = EstadoJuego.getInstance().getUsuario();
        Estadisticas estadisticas = user.getEstadistica();
        estadisticas.setPuntosTotales(estadisticas.getPuntosTotales() + score);
        user.setEstadistica(estadisticas);
        repositorioEstadisticas.update(estadisticas, user.getUsername());
    }

    public static void saveStats(boolean correcto, List<Integer> ODS, int idReto, String tipo){
        Usuario user = EstadoJuego.getInstance().getUsuario();
        Estadisticas estadisticas = user.getEstadistica();

        if(correcto) {
            estadisticas.setNumeroAciertos(estadisticas.getNumeroAciertos() + 1);
            if(ODS.size() > 1 || ODS.get(0) > 0){
                int[] aciertosODS = estadisticas.getAciertos_individual_ods();

                for(int i = 0; i < ODS.size(); i++) {
                    aciertosODS[ODS.get(i) - 1]++;
                }

                estadisticas.setAciertos_individual_ods(aciertosODS);
            }

            if(tipo.equals("pregunta")){ //tipo Pregunta
                Set<Integer> preguntasAcertadas = estadisticas.getPreguntasAcertadas();
                if(!preguntasAcertadas.contains(idReto)){
                    preguntasAcertadas.add(idReto);
                    estadisticas.setPreguntasAcertadas(preguntasAcertadas);
                }
            }
            else if (tipo.equals("ahorcado")) { //tipo ahorcado
                Set<Integer> palabrasAcertadas = estadisticas.getPalabrasAcertadas();
                if(!palabrasAcertadas.contains(idReto)){
                    palabrasAcertadas.add(idReto);
                    estadisticas.setPalabrasAcertadas(palabrasAcertadas);
                }
            }
            else if (tipo.equals("frase")) { //tipo frase
                Set<Integer> frasesAcertadas = estadisticas.getFrasesAcertadas();
                if(!frasesAcertadas.contains(idReto)){
                    frasesAcertadas.add(idReto);
                    estadisticas.setFrasesAcertadas(frasesAcertadas);
                }
            }

        }
        else{
            estadisticas.setNumeroFallos(estadisticas.getNumeroFallos() + 1);
            if(ODS.size() > 1 || ODS.get(0) > 0) {
                int[] fallosODS = estadisticas.getFallos_individual_ods();

                for(int i = 0; i < ODS.size(); i++) {
                    fallosODS[ODS.get(i) - 1]++;
                }

                estadisticas.setFallos_individual_ods(fallosODS);
            }
        }

        user.setEstadistica(estadisticas);
        repositorioEstadisticas.update(estadisticas, user.getUsername());
    }



    public static void aumentarPartidasJugadas(){
        Usuario user = EstadoJuego.getInstance().getUsuario();
        Estadisticas estadisticas = user.getEstadistica();
        estadisticas.setPartidasJugadas(estadisticas.getPartidasJugadas() + 1);
        user.setEstadistica(estadisticas);
        repositorioEstadisticas.update(estadisticas, user.getUsername());
    }

    public static void loadLevel(){
        Usuario user = EstadoJuego.getInstance().getUsuario();
        Estadisticas estadisticas = user.getEstadistica();
        int nivel = estadisticas.getPuntosTotales() / (int) 5000;
        if (nivel > 10)
        {
            estadisticas.setNivel(10);
        }
        else if (nivel == 0)
        {
            estadisticas.setNivel(1);
        }
        else
        {
            estadisticas.setNivel(nivel);
        }
        user.setEstadistica(estadisticas);
        repositorioEstadisticas.update(estadisticas, user.getUsername());
    }


    public static boolean isEmail(String input){
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return input.matches(emailRegex);
    }
}
