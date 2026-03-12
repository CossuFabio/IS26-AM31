package it.polimi.ingsw.am31.am31.cards;

public class Inventor extends CharacterCard {
    private final IconEnum icon;

    public Inventor(IconEnum icon) { this.icon = icon; }

    @Override
    public IconEnum getIcon() {
        return icon;
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
