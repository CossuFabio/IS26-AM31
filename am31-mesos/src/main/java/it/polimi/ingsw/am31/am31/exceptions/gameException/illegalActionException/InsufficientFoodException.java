package it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException;

import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorCode;

public class InsufficientFoodException extends InvalidPickException {
    public InsufficientFoodException(int food, int discount, int cost) {
        super("Player's food: " + food +
                ", player's discount: " + discount + ", card cost: " + cost, ErrorCode.INSUFFICIENT_FOOD);
    }
}
