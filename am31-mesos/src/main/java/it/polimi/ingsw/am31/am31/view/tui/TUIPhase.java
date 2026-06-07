package it.polimi.ingsw.am31.am31.view.tui;
/**
 * Represents a specific phase or state of the Text User Interface.
 */
public interface TUIPhase {

    /**
     * Visualizes the current state of this phase in the terminal.
     */
    void draw();

    /**
     * Processes the user's input specific to this phase.
     *
     * @param input The text entered by the user.
     * @throws Exception if there is an issue handling the input or communicating with the server.
     */
    void handleInput(String input) throws Exception;
}
