package it.polimi.ingsw.am31.am31;

import it.polimi.ingsw.am31.am31.controller.BoardRows;
import it.polimi.ingsw.am31.am31.modelPackage.Game;
import it.polimi.ingsw.am31.am31.modelPackage.TurnOrder;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.Board;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.EventCard;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObserver;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


//This class can be used to act as a spy for testing.
//It receives notifications from the model and can be used to get snapshots
//of the model in particolar situations.
//If you need to change some existing methods, consider creating another TestObserver class
//If a method here is empty, and you need it, you can write it.
//Add local variables and getters/setters if needed.
//By default every update is logged.
//You can pass a LogObserverBuilder to set only desired updates.

public class DebugObserver implements GameObserver {

    private final boolean logBuildingsUpdate;
    private final boolean logScoresUpdate;
    private final boolean logTribeUpdate;
    private final boolean logRoundStatusUpdate;
    private final boolean logPlayersListUpdate;
    private final boolean logCardLineUpdate;
    private final boolean logOfferTrackUpdate;
    private final boolean logTurnOrderUpdate;
    private final boolean logGameStartUpdate;
    private final boolean logGameEndUpdate;
    private final boolean logGameEventResolveUpdate;
    private final boolean logGameCrashUpdate;
    private final boolean logPlayerBonusDraw;

    private Map<Player, Integer>  oldPP = new HashMap<>();
    private Map<Player, Integer>  oldFood = new HashMap<>();


    //Select needed
    public DebugObserver() {
        logBuildingsUpdate = false;
        logScoresUpdate = true;
        logTribeUpdate = true;
        logRoundStatusUpdate = true;
        logPlayersListUpdate = false;
        logCardLineUpdate = false;
        logOfferTrackUpdate = false;
        logTurnOrderUpdate = false;
        logGameStartUpdate = false;
        logGameEndUpdate = false;
        logGameEventResolveUpdate = true;
        logGameCrashUpdate = false;
        logPlayerBonusDraw = false;
    }




    private void printSeparator() {
        System.out.println("--------------------------");
    }

    @Override
    public void notifyRemoveMe() {
        printSeparator();
        System.out.println("[Observer : " + getIdentifier() + " removed]");
        printSeparator();
    }

    @Override
    public void onPlayerNewBuildingEvent(Player player) {
        if (!logBuildingsUpdate) return;
        printSeparator();
        System.out.println("[Building update for player " + player.getNickname() + "]");
        player.getBuildings().forEach(b -> System.out.println(b.toString()));
        printSeparator();
    }

    @Override
    public void onPlayerScoresUpdate(Player player) {
        if (!logScoresUpdate) return;
        printSeparator();

        if(!oldFood.containsKey(player) && !oldPP.containsKey(player)) {
            oldFood.put(player, player.getFood());
            oldPP.put(player, player.getPrestigePoints());
        }

        System.out.println("[Scores update for player " + player.getNickname() + "]");
        System.out.print("Food: " + player.getFood());
        System.out.println(", old value: " + oldFood.get(player));
        System.out.print("Prestige points: " + player.getPrestigePoints());
        System.out.println(", old value: " + oldPP.get(player));

        oldFood.put(player, player.getFood());
        oldPP.put(player, player.getPrestigePoints());

        printSeparator();
    }

    @Override
    public void onPlayerTribeUpdate(Player player) {
        if (!logTribeUpdate) return;
        printSeparator();
        System.out.println("[Tribe update for player " + player.getNickname() + "]");
        player.getTribe().forEach(c -> System.out.println(c.toString()));
        printSeparator();
    }

    @Override
    public void onGameRoundStatusUpdate(Game game) {
        if (!logRoundStatusUpdate) return;
        printSeparator();
        System.out.println("[Game updated round]");
        System.out.println("Round: " + game.getRoundNumber());
        System.out.println("Era: " + game.getEra());
        printSeparator();
    }

    @Override
    public void onPlayersListUpdate(Game game) {
        if (!logPlayersListUpdate) return;
        printSeparator();
        System.out.println("[Players list updated]");
        game.getPlayersList().forEach(p ->
                System.out.println("Player: " + p.getNickname() + ", color: " + p.getColor()));
        printSeparator();
    }

    @Override
    public void onCardLineUpdate(Board board, BoardRows row) {
        if (!logCardLineUpdate) return;
        printSeparator();
        System.out.println("[" + row + " line updated]");
        List<Card> line = (row == BoardRows.LOWER) ? board.getUnderLine() : board.getUpperLine();
        line.forEach(c ->
                System.out.println(c.toString()));
        printSeparator();
    }

    @Override
    public void onOfferTrackUpdate(Board board) {
        if (!logOfferTrackUpdate) return;
        printSeparator();
        System.out.println("[OfferTrack updated]");
        board.getOfferCards().forEach(card -> {
            String occupiedBy = card.isFree() ? "is free" : "is occupied by " + card.getPlayer().getNickname() +
                    "(" + card.getPlayer().getColor() + ")";
            System.out.println("OfferCard: " + card.getOfferCardId() + " " + occupiedBy);
        });
        printSeparator();
    }

    @Override
    public void onTurnOrderUpdate(TurnOrder turnOrder) {
        if (!logTurnOrderUpdate) return;
        printSeparator();
        System.out.println("[TurnOrder updated]");
        List<Player> order = turnOrder.getOrder();
        for (int i = 0; i < order.size(); i++) {
            if (order.get(i) != null) {
                System.out.println("Player in position " + i + ": " +
                        order.get(i).getNickname() + "(" + order.get(i).getColor() + ")");
            }
        }
        printSeparator();
    }

    @Override
    public void onGameStartUpdate(Game game) {
        if (!logGameStartUpdate) return;
        printSeparator();
        System.out.println("[Game started]");
        printSeparator();
    }

    @Override
    public void onGameEndUpdate(Game game) {
        if (!logGameEndUpdate) return;
        printSeparator();
        System.out.println("[Game ended]\nLeaderboard: ");
        game.getLeaderBoard().forEach(p -> {
            System.out.println(p.getNickname() + "(" + p.getColor() + ")," +
                    " PrestigePoints: " + p.getPrestigePoints() + ", Food: " + p.getFood());
        });
        printSeparator();
    }

    @Override
    public void onGameEventResolveUpdate(EventCard c) {
        if (!logGameEventResolveUpdate) return;
        printSeparator();
        System.out.println("[Event resolved: " + c.toString() + "]");
        printSeparator();
    }

    @Override
    public void onGameCrashUpdate() {
        if (!logGameCrashUpdate) return;
        printSeparator();
        System.out.println("[Game crashed]");
        printSeparator();
    }

    @Override
    public String getIdentifier() {
        return "TestIdentifier";
    }

    @Override
    public void onPlayerBonusDrawUpdate(Player player) {
        if(!logPlayerBonusDraw) return;
        printSeparator();
        System.out.println("Player " + player.getNickname() + " now has bonus draw");
        printSeparator();
    }
}
