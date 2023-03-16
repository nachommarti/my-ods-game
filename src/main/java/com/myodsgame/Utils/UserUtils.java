package com.myodsgame.Utils;

public class UserUtils {

    public static String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    public static String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$";
    public static String USERNAME_REGEX ="^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";

    public static boolean checkPassword(String password){
        return password.matches(PASSWORD_REGEX);
    }

    public static boolean checkEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }

    public static boolean checkUsername(String username) {
        return username.matches(USERNAME_REGEX);
    }
}
