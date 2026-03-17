package it.polimi.ingsw.am31.am31.visitor;

import it.polimi.ingsw.am31.am31.cards.Hunter;

public interface TribeVisitor {

    public int visitHunter(Hunter hunter){
        return 1;
    }
    public void visitFarmer();

    public void visitShaman();

    public void visitBuilder();

    public void visitArtist();

    public void visitInventor();
}
