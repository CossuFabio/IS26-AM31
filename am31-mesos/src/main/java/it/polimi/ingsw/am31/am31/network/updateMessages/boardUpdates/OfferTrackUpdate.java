package it.polimi.ingsw.am31.am31.network.updateMessages.boardUpdates;

import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMethodsConstants;

import java.util.List;

public class OfferTrackUpdate extends UpdateMessage {

    private final List<OfferCardMessage> offerTrack;

    public OfferTrackUpdate(List<OfferCardMessage> offerTrack){
        super(UpdateMethodsConstants.BOARD_OFFERCARD_UPDATE_METHOD);
        this.offerTrack = offerTrack;
    }

    public List<OfferCardMessage> getOfferTrack(){return this.offerTrack;}

}
