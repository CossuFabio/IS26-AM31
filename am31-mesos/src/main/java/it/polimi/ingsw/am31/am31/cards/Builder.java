package it.polimi.ingsw.am31.am31.cards;

public class Builder extends CharacterCard {
    private int prestigePoints;
    private int discount;

    public Builder(int era, int prestigePoints, int discount) {
        super(era);
        this.prestigePoints = prestigePoints;
        this.discount = discount;
    }

    @Override
    public int getBuildingDiscount() {
        return discount;
    }

    @Override
    public int getPrestigePoints() {
        return prestigePoints;
    }

//    @Override
//    public int acceptVisit() {
//        super.acceptVisit();
//        return 0;
//    }
}
