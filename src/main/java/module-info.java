module com.myodsgame.myodsgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.myodsgame.myodsgame to javafx.fxml;
    exports com.myodsgame.myodsgame;
    exports com.myodsgame.myodsgame.Util;
    opens com.myodsgame.myodsgame.Util to javafx.fxml;
    exports com.myodsgame.myodsgame.Controllers;
    opens com.myodsgame.myodsgame.Controllers to javafx.fxml;
}