package it.polimi.ingsw.am31.am31.cards;

public class Farmer extends CharacterCard {
    private int discount;

    public Farmer(int era, int discount) {
        super(era);
        this.discount = discount;
    }

    @Override
    public int getSustainDiscount() {
        return discount;
    }



//    @Override
//    public int acceptVisit() {
//        super.acceptVisit();
//        return 0;
//    }
}
