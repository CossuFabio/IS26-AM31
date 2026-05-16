package it.polimi.ingsw.am31.am31.view.tui;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.Hunter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static it.polimi.ingsw.am31.am31.testUtils.cards.CardTestUtils.createHunter;
import static it.polimi.ingsw.am31.am31.view.tui.TUIConfig.printDetailedCard;
import static it.polimi.ingsw.am31.am31.view.tui.TUIConfig.printDetailedCardLine;
import static org.junit.jupiter.api.Assertions.*;

class TUIConfigTest {

    @Test
    void TestShouldPrintDetailedHunterCard() {
        Card h = createHunter().era(3).mark(true).minPlayers(2).build();
        Card h1 = createHunter().era(2).mark(false).minPlayers(2).build();
        printDetailedCard(h);
        printDetailedCard(h1);
    }
    @Test
    void TestShouldPrintDetailedCardLine() {
        Card h = createHunter().era(3).mark(true).minPlayers(2).build();
        Card h1 = createHunter().era(2).mark(false).minPlayers(2).build();
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(h); cards.add(h1);
        printDetailedCardLine(cards);
    }

}