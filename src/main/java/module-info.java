module com.myodsgame.myodsgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.media;
    requires java.desktop;


    opens com.myodsgame to javafx.fxml;
    exports com.myodsgame;
    exports com.myodsgame.Utils;
    opens com.myodsgame.Utils to javafx.fxml;
    exports com.myodsgame.Controllers;
    opens com.myodsgame.Controllers to javafx.fxml;
}