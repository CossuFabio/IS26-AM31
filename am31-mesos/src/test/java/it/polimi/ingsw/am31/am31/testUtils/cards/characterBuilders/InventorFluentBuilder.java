package it.polimi.ingsw.am31.am31.testUtils.cards.characterBuilders;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.IconEnum;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.Inventor;
import it.polimi.ingsw.am31.am31.modelPackage.modelUtilities.GameConstants;
import it.polimi.ingsw.am31.am31.testUtils.FluentBuilder;

public class InventorFluentBuilder implements FluentBuilder<Inventor> {

    private String cardId;
    private int era;
    private int minPlayers;
    private IconEnum icon;

    public InventorFluentBuilder(){
        reset();
    }

    public InventorFluentBuilder cardId(String cardId) {
        this.cardId = cardId;
        return this;
    }

    public InventorFluentBuilder era(int era) {
        this.era = era;
        return this;
    }

    public InventorFluentBuilder minPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
        return this;
    }

    public InventorFluentBuilder icon(IconEnum icon) {
        this.icon = icon;
        return this;
    }

    @Override
    public void reset() {
        this.cardId = "i1";
        this.era = 1;
        this.minPlayers = GameConstants.MIN_PLAYERS;
        this.icon = IconEnum.EMPTY;
    }

    @Override
    public Inventor build() {
        Inventor toReturn = new Inventor(cardId, era, minPlayers, icon);
        reset();
        return toReturn;
    }


}
