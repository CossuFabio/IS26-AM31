package it.polimi.ingsw.am31.am31.view.tui;


import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.view.eventsHandling.Subscribe;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.FailedRegistrationEvent;
import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * This class manages the registration phase of the Text User Interface.
 * It handles the initial welcome message and the process of registering a player's nickname.
 */
public class TUIRegistration implements TUIPhase {

    private final TextUserInterface TUI;
    private final ClientController controller;

    /**
     * Internal enumeration representing the sub-states of the registration phase.
     */
    private enum TuiRegistrationStep {REGISTRATION, WAIT_REGISTRATION_RESULT}
    private volatile TuiRegistrationStep currentStep;

    /**
     * Constructs a new TUIRegistration phase.
     *
     * @param TUI        The main TUI controller.
     * @param controller The network controller used to send registration requests.
     */
    public TUIRegistration(TextUserInterface TUI, ClientController controller){
        this.TUI = TUI;
        this.controller = controller;
        this.currentStep= TuiRegistrationStep.REGISTRATION;
    }

    /**
     * Renders the registration screen to the terminal.
     * Displays a welcome message and a prompt for the nickname during the REGISTRATION step,
     * or a waiting message during the WAIT_REGISTRATION_RESULT step.
     */
    @Override
    public void draw() {
        switch (currentStep){
            case REGISTRATION: {
                String space = String.format("%16s", " ");
                System.out.println("|" + space + "WELCOME TO MESOS" + space + "|");
                System.out.println("--------------------------------------------------");
                System.out.println("\n[ Thousands of years ago, a new era was beginning for humankind. ]\n" +
                        "[ The nomadic hunter-gatherers who had laboriously earned their place on Earth organized into small groups, \ndifferentiating social roles, building the first settlements, and initiating a great revolution. ]\n" +
                        "[ Scientists call this period Mesolithic, and this game talks about those people. ]\n" +
                        "[ Step into the role of a tribal leader, carefully choose the tasks to entrust to the people joining your tribe,\n" +
                        "construct specialized buildings, and prepare wisely for the events you will face, guiding your tribe to victory! ]\n");
                System.out.println(ansi().fg(Ansi.Color.YELLOW).a("\nPlease register your name first!").reset());
                System.out.println("\n\nEnter nickname:");
                break;
            }
            case WAIT_REGISTRATION_RESULT: {
                System.out.println("\nWaiting for server response");
                break;
            }
        }
    }

    /**
     * Handles the user's nickname input.
     * Sends a connection request to the server and transitions the state to wait for a response.
     *
     * @param input The nickname entered by the user.
     */
    @Override
    public void handleInput(String input) {
        if (input == null || input.isBlank())
            return;
        switch (currentStep) {
            case REGISTRATION: {
                try {
                    currentStep = TuiRegistrationStep.WAIT_REGISTRATION_RESULT;
                    controller.requestServerConnection(input);
                } catch (Exception e) {
                    currentStep = TuiRegistrationStep.REGISTRATION;
                    System.out.println("\nError sending request");
                    TUI.printScreen();
                }
                break;
            }
            case WAIT_REGISTRATION_RESULT: {
                break;
            }
        }
    }


    /**
     * Event subscriber that handles a failed registration attempt (e.g., username already taken).
     *
     * @param e The event containing details about the failed registration.
     */
    @Subscribe
    public void failedRegistration(FailedRegistrationEvent e){
        if(currentStep == TuiRegistrationStep.WAIT_REGISTRATION_RESULT){
            System.out.println("\nFailed registration: username already in use");
            currentStep = TuiRegistrationStep.REGISTRATION;
            TUI.printScreen();
        }
    }
}
