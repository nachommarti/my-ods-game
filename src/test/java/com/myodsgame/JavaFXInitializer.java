package com.myodsgame;
import javafx.application.Application;
import javafx.stage.Stage;

public class JavaFXInitializer extends Application {
    private static boolean launched = false;

    @Override
    public void start(Stage primaryStage) {
    }

    public static synchronized void initializeToolkit() {
        if (!launched) {
            new Thread(() -> Application.launch(JavaFXInitializer.class)).start();
            launched = true;
        }
    }
}

