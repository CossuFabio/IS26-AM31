package it.polimi.ingsw.am31.am31.view.tui;

import it.polimi.ingsw.am31.am31.modelPackage.modelUtilities.GameConstants;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.view.eventsHandling.Subscribe;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.FailedJoinLobby;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.InvalidColorPickEvent;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.PlayersInLobbyChangedEvent;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.ShowLobbyEvent;
import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;
/**
 * This class manages the Lobby phase of the Text User Interface.
 * It handles the user interactions for creating a game, joining an existing game,
 * viewing available lobbies, and reading the game rules.
 */
public class TUILobby implements TUIPhase {

    private final TextUserInterface TUI;
    private final ClientController controller;
    private volatile boolean creatingGame;
    private volatile Color color;
    private volatile int gameId;

    /**
     * Internal enumeration representing the sub-states of the lobby phase.
     */
    private enum TuiLobbyStep {
        START,
        RULES_EXPLANATION,
        CREATING_GAME,
        SELECT_COLOR,
        JOINING_GAME,
        WAITING_GAMESTART
    }

    private volatile TuiLobbyStep currentStep;

    private volatile int nPlayers;
    /**
     * Constructs a new TUILobby phase.
     *
     * @param TUI        The main TUI controller.
     * @param controller The network controller used to send requests to the server.
     */
    public TUILobby(TextUserInterface TUI, ClientController controller) {
        this.TUI = TUI;
        this.controller = controller;
        this.currentStep = TuiLobbyStep.START;
    }

    @Override
    /**
     * Renders the current state of the lobby to the terminal.
     * The output changes based on the {@code currentStep}.
     */
    public void draw() {
        switch (currentStep) {
            case START: {
                String spaces = String.format("%18s", " ");
                System.out.println("|"+spaces+"MESOS  LOBBY"+spaces+"|");
                System.out.println("--------------------------------------------------");
                System.out.println("\nPlease type:");
                System.out.println("["+ansi().fg(Ansi.Color.GREEN).a("1").reset() +" - To create a game]\n["+ansi().fg(Ansi.Color.GREEN).a("2").reset() +" - To show the current lobbies]\n["+ansi().fg(Ansi.Color.GREEN).a("3").reset()+" - To join a lobby]\n["+ansi().fg(Ansi.Color.GREEN).a("4").reset()+" - To read the game's rules]\n");
                break;
            }
            case RULES_EXPLANATION: {
                System.out.println(ansi().fg(Ansi.Color.GREEN).a("[OVERVIEW]").reset());
                System.out.println("[ In MESOS, you are the leader of an ancient tribe, and as such, your role is to nurture the growth \nof your tribe by adding new members, ensuring its sustenance, constructing buildings, and addressing the unfolding events. ]");
                System.out.println("\n[ In each round, you must place your Totem pawn on the Offer track. Then, following the pawns’ order on the track (from left to right), \nyou will acquire Character or Building cards and add them to your tribe. Each card has specific effects \nand may provide Prestige Points (PP) during or at the end of the game. ]");
                System.out.println("\n[ The winner at the end of the game is the one who has managed to bring the most prestige to their tribe. ]\n");
                System.out.println(ansi().fg(Ansi.Color.GREEN).a("\n[GAME ELEMENTS]").reset());
                System.out.println(ansi().fg(Ansi.Color.YELLOW).a("\n[OFFER TILES]\n").reset() +
                        "[ Each Offer tile shows the action you must perform during Action Phase (usually the number \nof cards you can take and the row from where you can take them) and a letter indicating \nthe tile’s position on the Offer track. You can choose the tile you want to place your totem \nin by typing its assigned letter when asked. ]\n");
                System.out.println(ansi().fg(Ansi.Color.YELLOW).a("[CHARACTER CARDS]\n").reset() + "[ There are 6 types of Character cards: \nINVENTORS - At the end of the game, Inventors provide a number of Prestige Points \nequal to the number of Inventors in your tribe multiplied by the number of different Invention icons you have.\n There are 10 different Invention icons.\nGATHERERS - During the Sustenance Event they provide a discount of 3 Food tokens on the total you would have to pay.\nSHAMANS - Shamans can show from 1 to 3 stars. During the Shamanic Ritual Event, having the majority of these provides Prestige Points.\n" +
                        "BUILDERS - During the game, each Builder reduces the Food cost of every Building card you take by the amount indicated in top right corner.\nAt the end of the game, each Builder provides the Prestige Points indicated in its card.\n" +
                        "ARTISTS - During the Cave Paintings Event, you can gain or lose Prestige Points based on the number of\nArtists in your tribe. At the end of the game, you gain 10 Prestige Points for every 2 Artists in your tribe.\n" +
                        "HUNTERS - Whenever you add a Hunter with a mark to your tribe, immediately take 1 Food token for each Hunter in your tribe\n(with or without a mark). ]");
                System.out.println(ansi().fg(Ansi.Color.YELLOW).a("\n[EVENT CARDS]\n").reset() + "[ There are 4 types of Event cards:\n" + "SUSTENANCE - Pay 1 Food token for each Character card in your tribe (Building cards do not count). \nIf, after paying all the Food you have, you couldn’t feed all your Characters, you lose the amount of Prestige Points indicated \non the Event card for each Character card you couldn’t feed. If there are multiple Events to resolve, Sustenance will be resolved last.\n" +
                        "HUNT - Take 1 Food token and gain the amount of Prestige Points indicated on the Event card for each Hunter in your tribe.\n" +
                        "SHAMANIC RITUAL - The player with the most icons in their tribe gains the Prestige Points indicated \non the Event card. The player with the fewest icons in their tribe loses the indicated Prestige Points. \nIn case of a tie, all tied players gain or lose the indicated Prestige Points.\n" +
                        "CAVE PAINTINGS - To gain Prestige Points with this event, you must have a minimum number of Artists, \nas indicated by the Event card. If you don’t have the minimum number of Artists indicated by the Event card, \nyou lose the amount of Prestige Points indicated. If, on the other hand, you have the right number of Artists, you gain \nthe indicated amount of Prestige Points for each of your Artists. ]\n");
                System.out.println(ansi().fg(Ansi.Color.YELLOW).a("[BUILDING CARDS]\n").reset() + "[ They cost food to add to your tribe and have different effects. ]\n");
                System.out.println(ansi().fg(Ansi.Color.GREEN).a("\n[HOW TO PLAY]").reset());
                System.out.println("A game of MESOS unfolds over 10 rounds until the Tribe cards deck is depleted. \nIn each round, you need to carry out the following two phases, in order:\n");
                System.out.println(ansi().fg(Ansi.Color.YELLOW).a("[TOTEM PLACING PHASE]\n").reset() +
                        "[ Following the order of the Totem pawns on the Turn Order tile, from top to bottom, place \nyour Totem on an available Offer tile (i.e., a tile where there is no other Totem already). ]\n");
                System.out.println(ansi().fg(Ansi.Color.YELLOW).a("[ACTION PHASE]\n").reset() +
                        "[ In turn, starting from the player with the leftmost Totem pawn on the Offer track and proceeding to the right, \nresolve the action indicated on the Offer tile where you placed your Totem.");
                System.out.println("After resolving the action on the Offer tile, your Totem pawn will return \nto the Turn Order tile, in the first available space starting from the top. ]");
                System.out.println(ansi().fg(Ansi.Color.YELLOW).a("\n[END OF THE ROUND]\n").reset() + "[ After all players have finished their turns, the following steps will be performed in order: ");
                System.out.println("1 - If there are Event cards in the bottom row, they will be resolved. \nIf Sustenance is among the Events, it’ll be resolved last. In the rare case the lower line contains \ntwo events of the same type during the same round, they’ll be resolved following the Era order.");
                System.out.println("2 - Character and Event cards in the lower line will be discarded. \nIf there are any Building cards, however, they remain in place.");
                System.out.println("3 - All remaining Character and Event cards will be moved from the top row to the bottom row. If there are any \nBuilding cards, however, they remain in place.");
                System.out.println("4 - A number of cards equal to the number of players +4 will be drawn from the Tribe \ndeck and placed above the Offer track to create a new upper line. ]\n");
                System.out.println(ansi().fg(Ansi.Color.YELLOW).a("[BEGINNING OF A NEW ERA]\n").reset() + "[ As soon as a Tribe card from the next Era will be revealed the new Era will begin.\n" +
                        "The following steps will be immediately performed in order:\n" + "1 - All Building cards present in the bottom row will be discarded. This happens only at the beginning of Era III.\n" +
                        "2 - Any Building cards present in the top row will be moved to the bottom row. This happens at the beginning of Era II and III.\n" +
                        "3 - The Building cards from the just-started Era will be placed in the top row. This happens at the beginning of Era II and III.\n" +
                        "Afterward, proceed as usual with a new round. ]\n");
                System.out.println(ansi().fg(Ansi.Color.YELLOW).a("[END OF GAME]\n").reset() + "[ The game ends at the end of the 10th round when the Tribe deck is depleted, and it \nis no longer possible to restore the top row. At the end of this round, all visible Events will be resolved, including those in the\n top row. Afterwards, add the following Prestige Points (PP) to the total Prestige Points gained during the game:\n " +
                        "PP from Builders in your tribe, 10 PP for every 2 Artists in your tribe, PP equal to the number of Inventors in your \ntribe multiplied by the number of different Invention icons on the respective cards, PP from Buildings in your tribe.\n" +
                        "The player who, after this count, has the most Prestige Points wins the game.\n" +
                        "In case of a tie, the tied player with the most Food wins. In case of further tie, the victory is shared. ]\n");

                System.out.println(TUIConfig.GO_BACK_STRING + "\n");
                break;
            }
            case CREATING_GAME: {
                System.out.println("\nEnter the number of players (2-5) " + TUIConfig.GO_BACK_STRING + "\n");
                break;
            }
            case SELECT_COLOR: {
                System.out.println("\nChoose your totem's color (white, black, red, yellow, blue) " + TUIConfig.GO_BACK_STRING + "\n");
                break;
            }
            case JOINING_GAME: {
                System.out.println("\nChoose a gameId " + TUIConfig.GO_BACK_STRING + "\n");
                break;
            }
            case WAITING_GAMESTART: {
                System.out.println("\nWaiting for the game to Start...\n");
                break;
            }
        }
    }

    @Override
    /**
     * Handles user input based on the current step of the lobby process.
     *
     * @param input The raw string input from the user.
     * @throws Exception If there is an error during input processing or network requests.
     */
    public void handleInput(String input) throws Exception {
        if (input == null || input.isBlank())
            return;
        switch (currentStep) {
            case START: {
                switch (input) {
                    case "1": {
                        creatingGame = true;
                        currentStep = TuiLobbyStep.CREATING_GAME;
                        TUI.printScreen();
                        break;
                    }
                    case "2": {
                        controller.requestShowLobbies();
                        break;
                    }
                    case "3": {
                        creatingGame = false;
                        currentStep = TuiLobbyStep.JOINING_GAME;
                        TUI.printScreen();
                        break;
                    }
                    case "4": {
                        currentStep = TuiLobbyStep.RULES_EXPLANATION;
                        TUI.printScreen();
                        break;
                    }
                    default: {
                        System.out.println("Invalid input!");
                        TUI.printScreen();
                        break;
                    }
                }
                break;
            }
            case CREATING_GAME: {

                if (input.equals(TUIConfig.GO_BACK_VALUE)) {
                    currentStep = TuiLobbyStep.START;
                    TUI.printScreen();
                    break;
                }

                try {
                    nPlayers = Integer.parseInt(input);
                } catch (Exception e) {
                    //sets invalid
                    nPlayers = 0;
                }

                if (nPlayers < GameConstants.MIN_PLAYERS || nPlayers > GameConstants.MAX_PLAYERS) {
                    System.out.println("\nInvalid number!\n");
                    TUI.printScreen();
                    break;
                }
                currentStep = TuiLobbyStep.SELECT_COLOR;
                TUI.printScreen();
                break;

            }
            case JOINING_GAME: {

                if (input.equals(TUIConfig.GO_BACK_VALUE)) {
                    currentStep = TuiLobbyStep.START;
                    TUI.printScreen();
                    break;
                }

                try {
                    gameId = Integer.parseInt(input);
                } catch (Exception e) {
                    System.out.println("\nInvalid input\n");
                    TUI.printScreen();
                    break;
                }

                currentStep = TuiLobbyStep.SELECT_COLOR;
                TUI.printScreen();
                break;
            }
            case SELECT_COLOR: {

                if (input.equals(TUIConfig.GO_BACK_VALUE)) {
                    currentStep = creatingGame ? TuiLobbyStep.CREATING_GAME : TuiLobbyStep.JOINING_GAME;
                    TUI.printScreen();
                    break;
                }


                try {
                    color = Color.valueOf(input.toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("\nInvalid color\n");
                    TUI.printScreen();
                    break;
                }
                currentStep = TuiLobbyStep.WAITING_GAMESTART;
                if (creatingGame) {
                    controller.requestNewGame(nPlayers, color);
                } else {
                    controller.requestJoinGame(color, gameId);
                }

                TUI.printScreen();
                break;
            }
            case RULES_EXPLANATION: {
                if (input.equals(TUIConfig.GO_BACK_VALUE)) {
                    currentStep = TuiLobbyStep.START;
                    TUI.printScreen();
                    break;
                }
                else {
                    System.out.println("\nInvalid input\n");
                    TUI.printScreen();
                    break;
                }
            }
            case WAITING_GAMESTART: {
                break;
            }

        }
    }


    @Subscribe
    /**
     * Event subscriber that displays the list of available lobbies received from the server.
     *
     * @param e The event containing the list of available lobbies.
     */
    public void printLobbies(ShowLobbyEvent e) {
        if(e.getLobbies().isEmpty()){
            System.out.println("\nNo lobbies available!\n");
            TUI.printScreen();
            return;
        }
        System.out.println("\nLobbies available:\n");
        e.getLobbies().forEach(
                l -> {
                    System.out.println("Lobby: " + l.getId() + ", Free slots: " + l.getFreeSlots() +
                            ", Game for " + l.getnPlayers() + " players.");
                    System.out.print("Available colors:");
                    l.getAvailableColors().forEach( c->{
                        System.out.print(" "+c);
                    });
                    System.out.println("\n");
                });
        TUI.printScreen();
    }

    @Subscribe
    /**
     * Event subscriber that handles the case where the user selected an unavailable color.
     *
     * @param e The event indicating an invalid color selection.
     */
    public void invalidColorSelected(InvalidColorPickEvent e) {
        if (currentStep == TuiLobbyStep.WAITING_GAMESTART) {
            currentStep = TuiLobbyStep.SELECT_COLOR;
            System.out.println("Invalid color pick");
            TUI.printScreen();
        }

    }

    @Subscribe
    /**
     * Event subscriber that updates the UI when players join or leave the current lobby.
     *
     * @param e The event containing the updated list of players in the lobby.
     */
    public void playersChanged(PlayersInLobbyChangedEvent e) {
        if (currentStep == TuiLobbyStep.WAITING_GAMESTART) {
            System.out.println("Players in lobby changed. New list:\n");
            e.getPlayers().forEach(p -> System.out.println(ansi().fg(Ansi.Color.YELLOW).a("Nickname: ").reset() + p.getNickname() + ", color: " + p.getColor()+"\n"));
            TUI.printScreen();
        }
    }

    @Subscribe
    /**
     * Event subscriber that handles failures when attempting to join a lobby.
     *
     * @param e The event containing the failure message.
     */
    public void unableToJoin(FailedJoinLobby e) {
        if (currentStep == TuiLobbyStep.WAITING_GAMESTART) {
            System.out.println("Unable to enter Lobby. " + e.getMessage());
            currentStep = TuiLobbyStep.START;
            TUI.printScreen();
        }
    }

}