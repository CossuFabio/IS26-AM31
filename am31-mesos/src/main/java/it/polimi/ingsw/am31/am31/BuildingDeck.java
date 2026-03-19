package it.polimi.ingsw.am31.am31;

import it.polimi.ingsw.am31.am31.cards.BuildingCard;
import it.polimi.ingsw.am31.am31.cards.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BuildingDeck extends Deck {
    public BuildingDeck(int nplayers) {

        List<Card> list1 = new ArrayList<>();
        list1.add(new BuildingCard(null, 2, 2));
        List<Card> list2 = new ArrayList<>();
        list2.add(new BuildingCard(null, 2, 2));
        List<Card> list3 = new ArrayList<>();
        list3.add(new BuildingCard(null, 2, 2));

        //lists are shuffled, then we add cards based on nplayers
        Collections.shuffle(list1);
        Collections.shuffle(list2);
        Collections.shuffle(list3);
        if (nplayers == 2) {
            this.eraDeck.add(list1.getFirst());
            this.eraDeck.add(list2.getFirst());
            list2.removeFirst();
            this.eraDeck.add(list2.getFirst());
            this.eraDeck.add(list3.getFirst());
            list3.removeFirst();
            this.eraDeck.add(list3.getFirst());
            list3.removeFirst();
            this.eraDeck.add(list3.getFirst());
        }
        if (nplayers == 3) {
            this.eraDeck.add(list1.getFirst());
            list1.removeFirst();
            this.eraDeck.add(list1.getFirst());
            this.eraDeck.add(list2.getFirst());
            list2.removeFirst();
            this.eraDeck.add(list2.getFirst());
            this.eraDeck.add(list3.getFirst());
            list3.removeFirst();
            this.eraDeck.add(list3.getFirst());
            list3.removeFirst();
            this.eraDeck.add(list3.getFirst());
            list3.removeFirst();
            this.eraDeck.add(list3.getFirst());
        }
        if (nplayers == 4) {
            this.eraDeck.add(list1.getFirst());
            list1.removeFirst();
            this.eraDeck.add(list1.getFirst());
            this.eraDeck.add(list2.getFirst());
            list2.removeFirst();
            this.eraDeck.add(list2.getFirst());
            list2.removeFirst();
            this.eraDeck.add(list2.getFirst());
            this.eraDeck.add(list3.getFirst());
            list3.removeFirst();
            this.eraDeck.add(list3.getFirst());
            list3.removeFirst();
            this.eraDeck.add(list3.getFirst());
            list3.removeFirst();
            this.eraDeck.add(list3.getFirst());
        }
        if (nplayers == 5) {
            this.eraDeck.add(list1.getFirst());
            list1.removeFirst();
            this.eraDeck.add(list1.getFirst());
            this.eraDeck.add(list2.getFirst());
            list2.removeFirst();
            this.eraDeck.add(list2.getFirst());
            list2.removeFirst();
            this.eraDeck.add(list2.getFirst());
            this.eraDeck.add(list3.getFirst());
            list3.removeFirst();
            this.eraDeck.add(list3.getFirst());
            list3.removeFirst();
            this.eraDeck.add(list3.getFirst());
            list3.removeFirst();
            this.eraDeck.add(list3.getFirst());
            list3.removeFirst();
            this.eraDeck.add(list3.getFirst());
        }
    }

}
