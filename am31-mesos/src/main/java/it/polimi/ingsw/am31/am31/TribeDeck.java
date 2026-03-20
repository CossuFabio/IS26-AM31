package it.polimi.ingsw.am31.am31;

import it.polimi.ingsw.am31.am31.cards.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TribeDeck extends Deck{

    public TribeDeck(int nplayers) {
        List<Card> list1 = new ArrayList<>();
        for (int i = 0; i < 3; i++) list1.add(new Artist(1));
        for (int i = 0; i < 2; i++) list1.add(new Farmer(1, 3));
        List<Card> list2 = new ArrayList<>();
        for (int i = 0; i < 3; i++) list2.add(new Artist(2));
        list1.add(new Farmer(2, 3));
        List<Card> list3 = new ArrayList<>();
        for (int i = 0; i < 3; i++) list3.add(new Artist(3));
        list1.add(new Farmer(3, 3));
        List<Card> list4 = new ArrayList<>();
        //TODO ADD EVENT CARDS CONSTRUCTOR
        list4.add(new SustainEventCard(3,1));
        list4.add(new RitualEventCard(1,1,1));
        if (nplayers >= 3) {
            list1.add(new Artist(1));
            list1.add(new Farmer(1, 3));

            list2.add(new Artist(2));
            list2.add(new Farmer(2, 3));


        }
        if (nplayers >= 4) {
            list1.add(new Artist(1));

            list2.add(new Farmer(2, 3));

            list3.add(new Farmer(3, 3));
        }
        if (nplayers >= 5) {
            list1.add(new Farmer(1, 3));

            list2.add(new Farmer(2, 3));

            list3.add(new Artist(3));
            list3.add(new Farmer(3, 3));

        }
//        { TEMPLATE
//            for (int i = 0; i < 3; i++) list1.add(new Artist(1));
//            for (int i = 0; i < 3; i++) list1.add(new Farmer(1, 3));
//
//            for (int i = 0; i < 3; i++) list2.add(new Artist(2));
//            for (int i = 0; i < 3; i++) list2.add(new Farmer(2, 3));
//
//            for (int i = 0; i < 3; i++) list3.add(new Artist(3));
//            for (int i = 0; i < 3; i++) list3.add(new Farmer(3, 3));
//        }
        //After making the lists, shuffle and compose in eraDeck.
        Collections.shuffle(list1);
        Collections.shuffle(list2);
        Collections.shuffle(list3);

        this.eraDeck.addAll(list1);
        this.eraDeck.addAll(list2);
        this.eraDeck.addAll(list3);
        this.eraDeck.addAll(list4);

    }
}
