module it.polimi.ingsw.am31.am31 {
    requires javafx.controls;
    requires javafx.fxml;


    opens it.polimi.ingsw.am31.am31 to javafx.fxml;
    exports it.polimi.ingsw.am31.am31;
}