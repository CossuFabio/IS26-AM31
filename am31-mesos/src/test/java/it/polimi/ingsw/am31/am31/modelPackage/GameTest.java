package it.polimi.ingsw.am31.am31.modelPackage;

import it.polimi.ingsw.am31.am31.exceptions.gameException.lobbyException.*;
import it.polimi.ingsw.am31.am31.exceptions.gameInvariantException.IncorrectMethodCallException;
import it.polimi.ingsw.am31.am31.exceptions.gameInvariantException.InsufficientPlayersNumberException;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.CharacterCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.RitualEventCard;
import it.polimi.ingsw.am31.am31.modelPackage.modelUtilities.GameConstants;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static it.polimi.ingsw.am31.am31.testUtils.TestUtilities.createGame;
import static it.polimi.ingsw.am31.am31.testUtils.TestUtilities.createPlayer;
import static it.polimi.ingsw.am31.am31.testUtils.cards.CardTestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;
    private Player player, player1;
    @BeforeEach
    void setUp() throws Exception {
        this.game = createGame(2);
        BuildingCard bd1 = createBuilding().era(1).cost(1).prestigePointsGained(1).build();
        BuildingCard bd2 = createBuilding().era(2).cost(2).prestigePointsGained(2).build();
        CharacterCard h = createHunter().era(1).mark(true).build();
        player = createPlayer().color(Color.BLUE).name("dummy").build();
       // player = new Player("dummy", Color.BLUE);
        game.getBoard().addUpper(bd1);
        game.getBoard().addUpper(h);
        game.getBoard().addUpper(h);
        game.getBoard().addLower(bd2);
        game.getBoard().addLower(h);
        game.getBoard().addLower(h);
        game.addPlayer(player);
       // player1 = new Player("dummy2",Color.RED);
        player1 = createPlayer().color(Color.RED).name("dummy2").build();
        //the setup makes a blue player, a red player, a game with 2 players, a board with 3 cards Up and 3 under
    }

    @Test
    void TestShouldResolveEvents() throws Exception {
        game.addPlayer(player1);
        assertEquals(0,player1.getFood());
        player1.editPrestigePoints(5);
        assertEquals(5,player1.getPrestigePoints());
        player.editFood(10);
        assertEquals(10,player.getFood());
        //p has 10 food, 0pp | p1 has 0 food, 5pp
        game.resolveEvents(); //there are no events, should do nothing
        assertEquals(5,player1.getPrestigePoints());assertEquals(10,player.getFood());
        //add events and cards to players
        RitualEventCard r1 = createRitualEvent().prestigePointsMalus(1).prestigePointsBonus(2).build();
        game.getBoard().addLower(r1);
        game.resolveEvents(); //both should lose and win, gaining 1 pp
        assertEquals(6,player1.getPrestigePoints());assertEquals(1,player.getPrestigePoints());
        player1.addCard(createShaman().stars(2).build()); //now player1 will win, gain 2pp, go to 8. p loses 1, goes to 0
        game.resolveEvents(); //event is still there.
        assertEquals(8,player1.getPrestigePoints()); assertEquals(0,player.getPrestigePoints());
        game.getBoard().addLower(createSustainEvent().prestigePointsMalus(1).build()); //sustain should go last
        game.getBoard().addLower(createHuntEvent().foodBonus(1).prestigePointsBonus(1).build());
        game.getBoard().addLower(createPaintingEvent().prestigePointsMalus(1).minArtist(1).build()); //both should lose 1 pp
        player1.addCard(createHunter().era(1).mark(false).build()); //gets 1 food and 1 pp after event
        //p1 has 0 food, p has 10 food. p1 has 2 cards. should gain 1 food 1 pp (hunt), then gain 2 pp (ritual), then lose 2pp (Sus+painting)
        //p1 ends up with 0 food, 10 pp
        //p has no cards, 10 food, 0 pp. lose 2 pp (ritual + paint) and lose no food
        //p ends with 10 food, -1 pp
        game.resolveEvents();
        assertEquals(9,player1.getPrestigePoints()); assertEquals(-2,player.getPrestigePoints());
        assertEquals(0, player1.getFood()); assertEquals(10,player.getFood());

        //now lets try the last turn
        game = createGame(2);
        game.setRound(GameConstants.ROUNDS_NUMBER);
        game.getBoard().addUpper(r1);
        game.addPlayer(player);
        game.addPlayer(player1);
        game.resolveEvents(); //p1 wins 2, p loses 1
        assertEquals(11,player1.getPrestigePoints()); assertEquals(-3,player.getPrestigePoints());


    }

    @Test
    void TestShouldAddPlayer() throws GameAlreadyStartedException, TooManyPlayersException, UsernameAlreadyTakenException, PlayerColorAlreadyTakenException {
        //test exceptions
        game.setCurrentRoundPhase(RoundPhasesEnum.ACTION_PHASE);
        assertThrows(GameAlreadyStartedException.class, () -> game.addPlayer(player));
        game.setCurrentRoundPhase(RoundPhasesEnum.GAME_STARTING);
        assertThrows(UsernameAlreadyTakenException.class, () -> game.addPlayer(player));
        assertThrows(PlayerColorAlreadyTakenException.class, () -> game.addPlayer(createPlayer().color(Color.BLUE).name("dummy2").build()));
        assertEquals(1,game.getPlayersList().size());
        //check if the players size increases
        game.addPlayer(createPlayer().color(Color.RED).name("dummy3").build());
        assertEquals(2,game.getPlayersList().size());
        assertThrows(TooManyPlayersException.class, () -> game.addPlayer(createPlayer().color(Color.WHITE).name("dummy4").build()));
        
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
        //conditons for game to finish are round and round phase
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