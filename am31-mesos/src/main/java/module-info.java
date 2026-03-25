module it.polimi.ingsw.am31.am31 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    //requires it.polimi.ingsw.am31.am31;


    opens it.polimi.ingsw.am31.am31 to javafx.fxml;
    exports it.polimi.ingsw.am31.am31;
    exports it.polimi.ingsw.am31.am31.fx;
    opens it.polimi.ingsw.am31.am31.fx to javafx.fxml;
    exports it.polimi.ingsw.am31.am31.cards;
    opens it.polimi.ingsw.am31.am31.cards to javafx.fxml;
    exports it.polimi.ingsw.am31.am31.visitor;
    opens it.polimi.ingsw.am31.am31.visitor to javafx.fxml;

}