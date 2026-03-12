package it.polimi.ingsw.am31.am31.cards;

public class Artist extends CharacterCard {
    public Artist(int era) {
        super(era);
    }

    @Override
    public IconEnum getIcon() {
        return super.getIcon();
    }

    @Override
    public int getBuildingDiscount() {
        return super.getBuildingDiscount();
    }

    @Override
    public int getPrestigePoints() {
        return super.getPrestigePoints();
    }

    @Override
    public int getSustainDiscount() {
        return super.getSustainDiscount();
    }
    @Override
    public boolean getMark() { return super.getMark();}
    @Override
    public int getStars() {return super.getStars();}

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
