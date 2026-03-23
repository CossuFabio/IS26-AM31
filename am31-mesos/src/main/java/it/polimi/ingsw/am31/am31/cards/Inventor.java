package it.polimi.ingsw.am31.am31.cards;

import it.polimi.ingsw.am31.am31.visitor.TribeVisitor;

public class Inventor extends CharacterCard {
    private IconEnum icon;

    public Inventor(int era, IconEnum icon) {
        super(era);
        this.icon = icon;
    }

    public void setIcon (IconEnum icon) {
        this.icon = icon;
    }

    public Inventor() {}


    @Override
    public IconEnum getIcon() {
        return icon;
    }


    @Override
    public void acceptVisit(TribeVisitor visitor) {
        visitor.visit(this);
    }
}
