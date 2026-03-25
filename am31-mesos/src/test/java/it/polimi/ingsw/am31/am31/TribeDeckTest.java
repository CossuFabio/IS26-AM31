package it.polimi.ingsw.am31.am31;

import it.polimi.ingsw.am31.am31.cards.Card;
import org.junit.jupiter.api.Test;

public class TribeDeckTest {

    @Test
    void shouldCreateTribeDeck(){

        int nPlayers =4;
        try{
            TribeDeck deck = new TribeDeck(nPlayers);
            System.out.println(deck.getSize());
            Card card = deck.draw();
            while(card != null){
                System.out.println(card.getEra());
                card = deck.draw();
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

}
