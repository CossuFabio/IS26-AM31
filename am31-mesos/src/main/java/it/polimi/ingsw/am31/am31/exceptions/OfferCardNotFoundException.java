package it.polimi.ingsw.am31.am31.exceptions;

public class OfferCardNotFoundException extends RuntimeException {
    public OfferCardNotFoundException() {
        super("Offer card not found!");
    }
}
