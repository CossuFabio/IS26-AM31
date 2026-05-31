package it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException;

/**
 * Resources that can be requested across the network and whose retrieval may fail
 */
public enum InvalidResourceTypeEnum {


        CARD("Card"),
        OFFER_CARD("OfferCard"),
        ROW("Row");

        private final String displayName;

        InvalidResourceTypeEnum(String displayName) { this.displayName = displayName; }

        @Override
        public String toString() { return displayName; }


}
