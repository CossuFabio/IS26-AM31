package it.polimi.ingsw.am31.am31.cards;

public class Farmer extends CharacterCard {
    private int discount;

    public void Farmer(int discount) {
        this.discount = discount;
    }

    @Override
    public int getSustainDiscount() {
        return discount;
    }

    @Override
    public void onPick() {
        super.onPick();
    }

    @Override
    public int acceptVisit() {
        super.acceptVisit();
        return 0;
    }
}
