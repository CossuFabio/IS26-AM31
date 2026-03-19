package it.polimi.ingsw.am31.am31.cards;

import it.polimi.ingsw.am31.am31.visitor.TribeVisitor;

public class Inventor extends CharacterCard {
    private final IconEnum icon;

    public Inventor(int era, IconEnum icon) {
        super(era);
        this.icon = icon;
    }

    @Override
    public IconEnum getIcon() {
        return icon;
    }


    @Override
    public void acceptVisit(TribeVisitor visitor) {
        visitor.visit(this);
    }
}
