package it.polimi.ingsw.am31.am31.cards;

import it.polimi.ingsw.am31.am31.Player;
import it.polimi.ingsw.am31.am31.visitor.CountVisitor;
import it.polimi.ingsw.am31.am31.visitor.TribeVisitor;

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
        if(!this.mark) return;
        CountVisitor visitor = new CountVisitor();
        player.getTribe().forEach(card -> card.acceptVisit(visitor));
        player.editFood(visitor.getHunters());
    }
//  TODO ADD TESTING

    @Override
    public void acceptVisit(TribeVisitor visitor) {
        visitor.visit(this);
   }
}
