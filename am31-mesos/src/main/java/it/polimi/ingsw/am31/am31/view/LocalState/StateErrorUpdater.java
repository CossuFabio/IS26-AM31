package it.polimi.ingsw.am31.am31.view.LocalState;

import it.polimi.ingsw.am31.am31.network.Messages.errorMessage.ErrorMessage;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.ErrorHandler;
import it.polimi.ingsw.am31.am31.view.eventsHandling.IEventBus;
import it.polimi.ingsw.am31.am31.view.eventsHandling.ViewEventBus;

public class StateErrorUpdater implements ErrorHandler {

    private final LocalGameState localState;
    private final IEventBus eventBus;

    public StateErrorUpdater(LocalGameState localState, IEventBus eventBus){

        this.localState = localState;
        this.eventBus = eventBus;

    }

    @Override
    public void handleErrorMessage(ErrorMessage errorMessage){

    }


}
