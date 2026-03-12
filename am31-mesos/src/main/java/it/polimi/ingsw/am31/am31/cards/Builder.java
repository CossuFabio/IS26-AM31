package it.polimi.ingsw.am31.am31.cards;

public class Builder extends CharacterCard {
    private int prestigePoints;
    private int discount;

    public void Builder(int prestigePoints, int discount) {
        this.prestigePoints = prestigePoints;
        this.discount = discount;
    }
    @Override
    public IconEnum getIcon() {
        return super.getIcon();
    }

    @Override
    public int getBuildingDiscount() {
        return discount;
    }

    @Override
    public int getPrestigePoints() {
        return prestigePoints;
    }

    @Override
    public int getSustainDiscount() {
        return super.getSustainDiscount();
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
    @Override
    public boolean getMark() { return super.getMark();}
    @Override
    public int getStars() {return super.getStars();}
}
