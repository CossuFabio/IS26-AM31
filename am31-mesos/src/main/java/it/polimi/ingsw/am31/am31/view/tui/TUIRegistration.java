package it.polimi.ingsw.am31.am31.view.tui;

import com.google.common.eventbus.Subscribe;
import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.network.requests.transportLayerRequest.NewServerConnectionRequest;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.FailedRegistrationEvent;

public class TUIRegistration implements TUIPhase {

    private final TextUserInterface TUI;
    private final ClientController controller;

    private enum TuiRegistrationStep {REGISTRATION, WAIT_REGISTRATION_RESULT}
    private volatile TuiRegistrationStep currentStep;

    public TUIRegistration(TextUserInterface TUI, ClientController controller){
        this.TUI = TUI;
        this.controller = controller;
        this.currentStep= TuiRegistrationStep.REGISTRATION;
    }

    @Override
    public void draw() {
        switch (currentStep){
            case REGISTRATION:
                System.out.println("Enter nickname:");
                break;
            case WAIT_REGISTRATION_RESULT:
                break;
        }
    }

    @Override
    public void handleInput(String input) {
        if (input == null || input.isEmpty())
            return;
        switch (currentStep) {
            case REGISTRATION:
                try {
                    currentStep = TuiRegistrationStep.WAIT_REGISTRATION_RESULT;
                    controller.sendRequest(new NewServerConnectionRequest(input));
                }
                catch(Exception e){
                    System.out.println("Error sending request");
                }
                break;
            case WAIT_REGISTRATION_RESULT:
                break;
        }
    }


    @Subscribe
    public void failedRegistration(FailedRegistrationEvent e){
        if(currentStep == TuiRegistrationStep.WAIT_REGISTRATION_RESULT){
            System.out.println("Failed registration...");
            currentStep = TuiRegistrationStep.REGISTRATION;
            TUI.printScreen();
        }
    }
}
