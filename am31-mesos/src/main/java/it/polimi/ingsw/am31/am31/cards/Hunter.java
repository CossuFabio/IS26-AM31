package it.polimi.ingsw.am31.am31.cards;

import it.polimi.ingsw.am31.am31.Player;

public class Hunter extends CharacterCard {
    private boolean mark;

    public Hunter(int era, boolean mark) {
        super(era);
        this.mark = mark;
    }

    @Override
    public boolean getMark() { return mark; }

    @Override
    public void onPick(Player player) {
        //TO-DO: implement this
        // IDEA:
        // if(!mark) return;
        // HunterVisitor visitor = new HunterVisitor();
        // int counter = player.getTribe().sum((accum, Card) -> visitor.visitHunter(card))
        // player.setFood(player.getFood() + counter);
    }


//    @Override
//    public int acceptVisit() {
//        super.acceptVisit();
//        return 0;
//    }
}
