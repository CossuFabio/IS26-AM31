package it.polimi.ingsw.am31.am31.modelPackage;

import it.polimi.ingsw.am31.am31.exceptions.gameException.lobbyException.*;
import it.polimi.ingsw.am31.am31.exceptions.gameInvariantException.IncorrectMethodCallException;
import it.polimi.ingsw.am31.am31.exceptions.gameInvariantException.InsufficientPlayersNumberException;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.Board;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.OfferCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.CharacterCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.Hunter;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.EventCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;
import it.polimi.ingsw.am31.am31.modelPackage.modelUtilities.GameConstants;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.GameResources;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.JSONSuppliers.JsonBuildingCardsSupplier;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.JSONSuppliers.JsonOfferSupplier;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.JSONSuppliers.JsonTribeCardsSupplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import static java.util.Comparator.comparingInt;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;
    private Player player, player1;
    @BeforeEach
    void setUp() throws Exception {
        game = new Game(2, new GameResources(new JsonTribeCardsSupplier(), new JsonBuildingCardsSupplier(), new
                JsonOfferSupplier()));
        BuildingCard bd1 = new BuildingCard("dummy",  1, 1, 1, null);
        BuildingCard bd2 = new BuildingCard("dummy2", 2, 2, 2, null);
        CharacterCard h = new Hunter("h1", 1, 2, true);
        player = new Player("dummy", Color.BLUE);
        game.getBoard().addUpper(bd1);
        game.getBoard().addUpper(h);
        game.getBoard().addUpper(h);
        game.getBoard().addLower(bd2);
        game.getBoard().addLower(h);
        game.getBoard().addLower(h);
        game.addPlayer(player);
        player1 = new Player("dummy2", Color.RED);
    }

    @Test
    void TestShouldResolveEvents() throws IOException {
    }

    @Test
    void TestShouldAddPlayer() throws GameAlreadyStartedException, TooManyPlayersException, UsernameAlreadyTakenException, PlayerColorAlreadyTakenException {
        //test exceptions
        game.setCurrentRoundPhase(RoundPhasesEnum.ACTION_PHASE);
        assertThrows(GameAlreadyStartedException.class, () -> game.addPlayer(player));
        game.setCurrentRoundPhase(RoundPhasesEnum.GAME_STARTING);
        assertThrows(UsernameAlreadyTakenException.class, () -> game.addPlayer(player));
        assertThrows(PlayerColorAlreadyTakenException.class, () -> game.addPlayer(new Player ("dummy2",Color.BLUE)));
        assertEquals(1,game.getPlayersList().size());
        //check if the players size increases
        game.addPlayer(new Player("dummy3",Color.RED));
        assertEquals(2,game.getPlayersList().size());
        assertThrows(TooManyPlayersException.class, () -> game.addPlayer(new Player("dummy4",Color.WHITE)));
        
    }

    @Test
    void TestShouldRemovePlayer() {
        assertEquals(game.getPlayersList().size(),1);
        assertEquals(game.getPlayersList().getFirst(),player);
        game.removePlayer(player);
        assertEquals(game.getPlayersList().size(),0);
    }

    @Test
    void TestShouldGameStart() throws Exception {
        //testing exceptions
        game.setCurrentRoundPhase(RoundPhasesEnum.ACTION_PHASE);
        assertThrows(IncorrectMethodCallException.class, game::gameStart);
        game.setCurrentRoundPhase(RoundPhasesEnum.GAME_STARTING);
        assertThrows(InsufficientPlayersNumberException.class, game::gameStart);
        game.addPlayer(player1);
        game.gameStart();
        //check if the number of cards is right
        assertEquals(3+game.getNumPlayers()+GameConstants.LOWER_LINE_EXTRA_CARDS,game.getBoard().getUnderLine().size());
        assertEquals( 3+GameConstants.getEraOneBuildings(2)+game.getNumPlayers()+GameConstants.UPPER_LINE_EXTRA_CARDS,game.getBoard().getUpperLine().size());
        assertEquals(RoundPhasesEnum.TOTEM_PLACING, game.getCurrentRoundPhase());
    }

    @Test
    void TestShouldGameEnd() throws Exception {
        //gameEnd before game is finished should throw
        assertThrows(IncorrectMethodCallException.class, game::gameEnd);

        player.editPrestigePoints(10);
        player1.editPrestigePoints(5);
        game.addPlayer(player1);
        game.setCurrentRoundPhase(RoundPhasesEnum.END_TURN);
        game.setRound(GameConstants.ROUNDS_NUMBER);

        //player has 10pp, player1 has 5pp → player wins alone
        assertTrue(game.isPlayerWinner(player));
        assertFalse(game.isPlayerWinner(player1));
        assertEquals(player, game.getLeaderBoard().getFirst());

        //tie on pp, broken by food: player(10,5) vs player1(10,0) → player still wins
        player.editFood(5);
        player1.editPrestigePoints(5);
        assertEquals(player.getPrestigePoints(), player1.getPrestigePoints());
        assertEquals(player, game.getLeaderBoard().getFirst());
        assertTrue(game.isPlayerWinner(player));
        assertFalse(game.isPlayerWinner(player1));

        //full tie on pp + food → both winners, leaderboard size 2
        player1.editFood(5);
        List<Player> result = game.getLeaderBoard();
        assertEquals(player.getFood(), player1.getFood());
        assertEquals(2, result.size());
        assertTrue(game.isPlayerWinner(player));
        assertTrue(game.isPlayerWinner(player1));
    }

    @Test
    void TestShouldResetGame() {
    }

    @Test
    void TestShouldStartRound() {
    }

    @Test
    void TestShouldEndRound() {
    }

    @Test
    void TestShouldChangeEra() throws IOException, InvalidPlayersNumberException {
        //pre: setUp put bd1(era=1) in upperBLine, bd2(era=2) in underBLine
        assertEquals(2, game.getBoard().getUnderBLine().getFirst().getEra());
        assertEquals(1, game.getBoard().getUpperBLine().getFirst().getEra());

        //changeEra: discards underBLine (era=1 buildings), moves upperBLine down,
        //then refills upper from deck with new-era cards (if any)
        game.changeEra();

        //bd1(era=1) should now be the (only) under building; old bd2 was discarded
        assertEquals(1, game.getBoard().getUnderBLine().getFirst().getEra());
        assertEquals(1, game.getBoard().getUnderBLine().size());
    }
    @Test
    void TestShouldPlayerChoice() {
    }

    @Test
    void TestShouldPlayerDrawFromUpper() {
    }

    @Test
    void TestShouldPlayerDrawFromTop() {
    }

    @Test
    void TestShouldGetTurnOrder() {
    }

    @Test
    void TestShouldGetBoard() {
    }

    @Test
    void TestsetObserverHandler() {
    }

    @Test
    void TestaddPlayer() {
    }

    @Test
    void TestremovePlayer() {
    }

    @Test
    void TestendRound() {
    }

    @Test
    void TestchangeEra() {
    }

    @Test
    void TesttotemChoiceAction() {
    }

    @Test
    void TestplayerDrawFromUpper() {
    }

    @Test
    void TestplayerDrawFromLower() {
    }

    @Test
    void TestisGameFinished() {
        game.setCurrentRoundPhase(RoundPhasesEnum.ACTION_PHASE);
        assertFalse(game.isGameFinished());
        game.setCurrentRoundPhase(RoundPhasesEnum.END_TURN);
        assertFalse(game.isGameFinished());
        //conditons for game finisha are round and round phase
        game.setRound(GameConstants.ROUNDS_NUMBER);
        assertTrue(game.isGameFinished());
    }

    @Test
    void TestisTotemPlacingPhaseFinished() {
    }

    @Test
    void TestisDrawPhaseFinished() {
    }

    @Test
    void TestisGameInStartingPhase() {
    }

    @Test
    void TestisBonusDrawPhaseFinished() {
    }

    @Test
    void TesthasCurrentPlayerFinishedDrawing() {
    }

    @Test
    void TestsetUpPlayerActing() {
    }

    @Test
    void TestsetUpDrawingPhase() {
    }

    @Test
    void TestsetCurrentRoundPhase() {
    }

    @Test
    void TestsetNextPlayerDrawing() {
    }

    @Test
    void TestsetUpBonusDrawingPhase() {
    }

    @Test
    void TestsetUpTotemPlacingPhase() {
    }

    @Test
    void TestplayerSkipUpper() {
    }

    @Test
    void TestplayerSkipLower() {
    }
}