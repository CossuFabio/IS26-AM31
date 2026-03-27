package it.polimi.ingsw.am31.am31.exceptions;

public class InsufficientFoodException extends Exception {
    public InsufficientFoodException(int food, int discount, int cost) {
        super("Player's food: " + food +
                ", player's discount: " + discount + ", card cost: " + cost);
    }
}
