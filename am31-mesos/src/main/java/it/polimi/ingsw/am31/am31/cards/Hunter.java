package it.polimi.ingsw.am31.am31.cards;

public class Hunter extends CharacterCard {
    private boolean mark;

    public void Hunter (boolean mark) { this.mark = mark; }

    @Override
    public boolean getMark() { return mark; }

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
