package com.myodsgame.Utils;

import com.myodsgame.Models.Estadisticas;
import com.myodsgame.Models.Reto;
import com.myodsgame.Models.Usuario;
import com.myodsgame.Repository.RepositorioUsuario;
import com.myodsgame.Repository.RepositorioUsuarioImpl;

import java.util.List;

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

    public static boolean checkUserExists(String username){
        RepositorioUsuario repositorioUsuario = new RepositorioUsuarioImpl();
        return repositorioUsuario.checkIfUserExists(username);
    }

    public static boolean checkUsername(String username) {
        return username.matches(USERNAME_REGEX);
    }

    public static boolean checkAndSetUser(String username, String password){
        RepositorioUsuario repositorioUsuario = new RepositorioUsuarioImpl();
        Usuario user = repositorioUsuario.getUsuarioPorUsernameYContraseña(username, password);

        if(user != null) {
            EstadoJuego.getInstance().setUsuario(user);
            return true;
        }

        return false;

    }


    public static void saveUserScore(int score){
        Usuario user = EstadoJuego.getInstance().getUsuario();
        Estadisticas estadisticas = user.getEstadistica();
        estadisticas.setPuntosTotales(estadisticas.getPuntosTotales() + score);
        user.setEstadistica(estadisticas);
        RepositorioUsuario repositorioUsuario = new RepositorioUsuarioImpl();
        repositorioUsuario.updateUsuarioEstadisticas(user);
    }

    public static void saveStats(boolean correcto, List<Integer> ODS){
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
        RepositorioUsuario repositorioUsuario = new RepositorioUsuarioImpl();
        repositorioUsuario.updateUsuarioEstadisticas(user);
    }

    public static void aumentarPartidasJugadas(){
        Usuario user = EstadoJuego.getInstance().getUsuario();
        Estadisticas estadisticas = user.getEstadistica();
        estadisticas.setPartidasJugadas(estadisticas.getPartidasJugadas() + 1);
        user.setEstadistica(estadisticas);
        RepositorioUsuario repositorioUsuario = new RepositorioUsuarioImpl();
        repositorioUsuario.updateUsuarioEstadisticas(user);
    }


    public static boolean isEmail(String input){
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return input.matches(emailRegex);
    }
}
