module com.myods.my.ods.game {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.data.jpa;
    requires spring.data.commons;

    opens com.myods.my.ods.game to javafx.fxml;
    exports com.myods.my.ods.game;
    
    opens com.myods.my.ods.game.Controllers to javafx.fxml;
    exports com.myods.my.ods.game.Controllers;
    
    opens com.myods.my.ods.game.Repositories to javafx.fxml;
    exports com.myods.my.ods.game.Repositories;
}
