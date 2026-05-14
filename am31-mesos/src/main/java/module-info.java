module it.polimi.ingsw.am31.am31 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires java.rmi;
    requires java.desktop;
    requires org.fusesource.jansi;



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
    opens it.polimi.ingsw.am31.am31.resources.resourceSuppliers to javafx.fxml;
    exports it.polimi.ingsw.am31.am31.network;
    exports it.polimi.ingsw.am31.am31.network.rmi.server;
    exports it.polimi.ingsw.am31.am31.network.rmi.client;
    exports it.polimi.ingsw.am31.am31.network.requests;
    opens it.polimi.ingsw.am31.am31.network.requests to javafx.fxml, com.fasterxml.jackson.databind;

    exports it.polimi.ingsw.am31.am31.network.Messages.updateMessages;
    opens it.polimi.ingsw.am31.am31.network.Messages.updateMessages to javafx.fxml, com.fasterxml.jackson.databind;
    exports it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage;
    opens it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage to com.fasterxml.jackson.databind;
    exports it.polimi.ingsw.am31.am31.network.Messages.updateMessages.playerUpdatesMessage;
    opens it.polimi.ingsw.am31.am31.network.Messages.updateMessages.playerUpdatesMessage to com.fasterxml.jackson.databind;
    exports it.polimi.ingsw.am31.am31.network.Messages.updateMessages.boardUpdates;
    opens it.polimi.ingsw.am31.am31.network.Messages.updateMessages.boardUpdates to com.fasterxml.jackson.databind;
    exports it.polimi.ingsw.am31.am31.network.Messages.updateMessages.serverMessages;
    opens it.polimi.ingsw.am31.am31.network.Messages.updateMessages.serverMessages to com.fasterxml.jackson.databind;
    exports it.polimi.ingsw.am31.am31.modelPackage.observerPattern;
    opens it.polimi.ingsw.am31.am31.modelPackage.observerPattern to javafx.fxml;
    exports it.polimi.ingsw.am31.am31.network.Messages.errorMessage;
    opens it.polimi.ingsw.am31.am31.network.Messages.errorMessage to com.fasterxml.jackson.databind;
    exports it.polimi.ingsw.am31.am31.network.requests.gameRequest;
    opens it.polimi.ingsw.am31.am31.network.requests.gameRequest to com.fasterxml.jackson.databind, javafx.fxml;
    exports it.polimi.ingsw.am31.am31.network.requests.lobbyRequest;
    opens it.polimi.ingsw.am31.am31.network.requests.lobbyRequest to com.fasterxml.jackson.databind, javafx.fxml;
    exports it.polimi.ingsw.am31.am31.network.requests.transportLayerRequest;
    opens it.polimi.ingsw.am31.am31.network.requests.transportLayerRequest to com.fasterxml.jackson.databind, javafx.fxml;
    exports it.polimi.ingsw.am31.am31.network.Messages;
    exports it.polimi.ingsw.am31.am31.view.gui;
    opens it.polimi.ingsw.am31.am31.view.gui to javafx.fxml;
    exports it.polimi.ingsw.am31.am31.view.tui;
    exports it.polimi.ingsw.am31.am31.view.gui.scene;
    opens it.polimi.ingsw.am31.am31.view.gui.scene to javafx.fxml;
    exports it.polimi.ingsw.am31.am31.resources.resourceSuppliers;

}