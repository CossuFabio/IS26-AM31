module it.polimi.ingsw.am31.am31 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires java.rmi;
    requires java.desktop;
    requires it.polimi.ingsw.am31.am31;
    //requires it.polimi.ingsw.am31.am31;
    //requires it.polimi.ingsw.am31.am31;
    // requires it.polimi.ingsw.am31.am31;
    //requires it.polimi.ingsw.am31.am31;


    opens it.polimi.ingsw.am31.am31 to javafx.fxml;
    //exports it.polimi.ingsw.am31.am31;
    exports it.polimi.ingsw.am31.am31.fx;
    opens it.polimi.ingsw.am31.am31.fx to javafx.fxml;
    exports it.polimi.ingsw.am31.am31.modelPackage.cardsFolder;
    opens it.polimi.ingsw.am31.am31.modelPackage.cardsFolder to javafx.fxml;
    exports it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor;
    opens it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor to javafx.fxml;
    exports it.polimi.ingsw.am31.am31.modelPackage.deckFolder;
    opens it.polimi.ingsw.am31.am31.modelPackage.deckFolder to javafx.fxml;
    exports it.polimi.ingsw.am31.am31.modelPackage.boardFolder;
    opens it.polimi.ingsw.am31.am31.modelPackage.boardFolder to javafx.fxml;
    exports it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards;
    opens it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards to javafx.fxml;
    exports it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards;
    opens it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards to javafx.fxml;
    exports it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards;
    opens it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards to javafx.fxml;
    exports it.polimi.ingsw.am31.am31.modelPackage.modelUtilities;
    opens it.polimi.ingsw.am31.am31.modelPackage.modelUtilities to javafx.fxml;
    exports it.polimi.ingsw.am31.am31.modelPackage.playerFolder;
    opens it.polimi.ingsw.am31.am31.modelPackage.playerFolder to javafx.fxml;
    exports it.polimi.ingsw.am31.am31.modelPackage;
    opens it.polimi.ingsw.am31.am31.modelPackage to javafx.fxml;
    exports it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.JSONSuppliers;
    opens it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.JSONSuppliers to javafx.fxml;
    exports it.polimi.ingsw.am31.am31.network;
    exports it.polimi.ingsw.am31.am31.network.rmi.server;
    exports it.polimi.ingsw.am31.am31.network.rmi.client;
    exports it.polimi.ingsw.am31.am31.network.requests;
    opens it.polimi.ingsw.am31.am31.network.requests to javafx.fxml, com.fasterxml.jackson.databind;

    exports it.polimi.ingsw.am31.am31.network.updateMessages;
    opens it.polimi.ingsw.am31.am31.network.updateMessages to javafx.fxml, com.fasterxml.jackson.databind;
    exports it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage;
    opens it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage to com.fasterxml.jackson.databind;
    exports it.polimi.ingsw.am31.am31.network.updateMessages.playerUpdatesMessage;
    opens it.polimi.ingsw.am31.am31.network.updateMessages.playerUpdatesMessage to com.fasterxml.jackson.databind;
    exports it.polimi.ingsw.am31.am31.network.updateMessages.boardUpdates;
    opens it.polimi.ingsw.am31.am31.network.updateMessages.boardUpdates to com.fasterxml.jackson.databind;
    exports it.polimi.ingsw.am31.am31.modelPackage.observerPattern;
    opens it.polimi.ingsw.am31.am31.modelPackage.observerPattern to javafx.fxml;

}