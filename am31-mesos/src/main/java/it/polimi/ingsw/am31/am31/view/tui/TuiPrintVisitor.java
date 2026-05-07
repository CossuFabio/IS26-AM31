package it.polimi.ingsw.am31.am31.view.tui;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.*;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.EventCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.TribeVisitor;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalCardDictionary;
import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.Color.BLUE;
import static org.fusesource.jansi.Ansi.Color.YELLOW;
import static org.fusesource.jansi.Ansi.ansi;

public class TuiPrintVisitor implements TribeVisitor {


    @Override
    public void visit(Hunter hunter) {  //Hunters
        String cardId = hunter.getCardId();
        System.out.println(ansi().fgRed().a(cardId+" "+hunter).reset());
    }

    @Override
    public void visit(Shaman shaman) {  //Shaman
        String cardId = shaman.getCardId();
        System.out.println(ansi().fgCyan().a(cardId+" "+shaman).reset());

    }

    @Override
    public void visit(Farmer farmer) { //Farmers
        String cardId = farmer.getCardId();
        System.out.println(ansi().fgGreen().a(cardId+" "+farmer).reset());
    }

    @Override
    public void visit(EventCard event) { //Events
        String cardId = event.getCardId();
        System.out.println(ansi().bgBright(Ansi.Color.WHITE).fgBlack().a(cardId+" "+event).reset());
    }

    @Override
    public void visit(Inventor inventor) { //Inventors
        String id = inventor.getCardId();
        System.out.println(ansi().fg(BLUE).a(id+" "+inventor).reset());
    }

    @Override
    public void visit(Builder builder) { //Builders
        String id = builder.getCardId();
        System.out.println(ansi().fg(Ansi.Color.MAGENTA).a(id+" "+builder).reset());
    }

    @Override
    public void visit(Artist artist) { //Artists
        String id = artist.getCardId();
        System.out.println(ansi().fg(YELLOW).a(id+" "+artist).reset());
    }

    @Override
    public void visit(BuildingCard card){ //Buildings with descriptions
        String id = card.getCardId();
        String description = LocalCardDictionary.getDescription(id);
     System.out.println(ansi().bg(Ansi.Color.BLUE).fg(Ansi.Color.BLACK).a(id+" "+description).reset());

    }
}
