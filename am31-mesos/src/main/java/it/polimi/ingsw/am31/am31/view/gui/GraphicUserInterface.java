package it.polimi.ingsw.am31.am31.view.gui;
import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.view.localState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.View;
import it.polimi.ingsw.am31.am31.view.eventsHandling.IEventBus;
import javafx.application.Application;

/**
 * Graphical user interface implementation of {@link View}.
 * Launches the JavaFX application after setting up the required dependencies.
 */
public class GraphicUserInterface implements View{
    private final ClientController controller;
    private final LocalGameState localGameState;
    private final IEventBus eventBus;

    /**
     * Creates a new GraphicUserInterface with the required dependencies.
     * @param controller the client controller for server communication
     * @param localGameState the shared local game state
     * @param eventBus the event bus for inter-component communication
     */
    public GraphicUserInterface(ClientController controller, LocalGameState localGameState, IEventBus eventBus) {

        this.controller = controller;
        this.localGameState = localGameState;
        this.eventBus = eventBus;

    }

    /**
     * Starts the GUI by initializing GUIView and launching the JavaFX application.
     *
     * @throws Exception if the JavaFX application fails to launch
     */
    @Override
    public void startView() throws Exception {
        GUIView.setController(controller);
        GUIView.setLocalGameState(localGameState);
        GUIView.setEventBus(eventBus);
        Application.launch(GUIView.class);
    }






}
