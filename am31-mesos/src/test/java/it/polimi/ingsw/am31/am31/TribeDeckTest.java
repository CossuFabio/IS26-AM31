package it.polimi.ingsw.am31.am31;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.deckFolder.TribeDeck;
import org.junit.jupiter.api.Test;

public class TribeDeckTest {

    @Test
    void shouldCreateTribeDeck(){

        int nPlayers = 5;
        try{
            TribeDeck deck = new TribeDeck(nPlayers);
            System.out.println(deck.getSize());
            Card card = deck.draw();
            while(card != null){
                System.out.println(card);
                card = deck.draw();
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

}
