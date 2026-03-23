package it.polimi.ingsw.am31.am31.cards;

import it.polimi.ingsw.am31.am31.visitor.TribeVisitor;

public class Farmer extends CharacterCard {
    private final int discount;

    public Farmer(int era, int discount) {
        super(era);
        this.discount = discount;
    }

    @Override
    public int getSustainDiscount() {
        return discount;
    }



    @Override
    public void acceptVisit(TribeVisitor visitor) {
        visitor.visit(this);
    }
}
