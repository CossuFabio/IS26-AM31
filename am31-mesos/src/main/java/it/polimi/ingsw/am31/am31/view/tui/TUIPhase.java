package it.polimi.ingsw.am31.am31.view.tui;

public interface TUIPhase {
    void draw();

    void handleInput(String input) throws Exception;
}
