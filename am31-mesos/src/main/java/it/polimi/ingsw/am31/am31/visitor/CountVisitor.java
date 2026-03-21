package it.polimi.ingsw.am31.am31.visitor;

import it.polimi.ingsw.am31.am31.cards.*;

public class CountVisitor implements TribeVisitor{

    private int Hcount;
    private int Fcount;
    private int Scount;
    private int Bcount;
    private int Acount;
    private int Icount;
    private int Event;

    public CountVisitor() {
        Hcount = 0;
        Fcount =0;
        Scount = 0;
        Bcount = 0;
        Acount = 0;
        Icount = 0;
        Event = 0;
    }


    public void visit(Hunter hunter) {
        this.Hcount++;
    }


    public void visit(Farmer farmer) {
        this.Fcount++;
    }

    public void visit(Shaman shaman) {
        this.Scount++;
    }

    public void visit(Builder builder) {
        this.Bcount++;
    }


    public void visit(Artist artist) {
        this.Acount++;
    }


    public void visit(Inventor inventor) {
        this.Icount++;
    }

    public void visit(EventCard event) {
        this.Event++;
    }

    public int getHunters(){return Hcount; }
    public int getFarmers(){return Fcount; }
    public int getShamans(){return Scount; }
    public int getBuilders(){return Bcount; }
    public int getInventors(){return Icount; }
    public int getArtists(){return Acount;}
    public int getEvent(){return Event;}
}
