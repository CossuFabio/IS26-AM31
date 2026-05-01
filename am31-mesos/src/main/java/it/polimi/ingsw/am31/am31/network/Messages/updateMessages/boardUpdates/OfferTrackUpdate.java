package it.polimi.ingsw.am31.am31.network.Messages.updateMessages.boardUpdates;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.IUpdateVisitor;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMethodsConstants;

import java.util.List;

public class OfferTrackUpdate extends UpdateMessage {

    private final List<it.polimi.ingsw.am31.am31.network.Messages.updateMessages.boardUpdates.OfferCardMessage> offerTrack;

    @JsonCreator
    public OfferTrackUpdate(@JsonProperty("offerTrack") List<OfferCardMessage> offerTrack){
        super(UpdateMethodsConstants.BOARD_OFFERCARD_UPDATE_METHOD);
        this.offerTrack = offerTrack;
    }

    public List<OfferCardMessage> getOfferTrack(){return this.offerTrack;}

    @Override
    protected boolean checkSpecificValidity() {
        return offerTrack != null && !offerTrack.contains(null) && offerTrack.stream().allMatch(oft -> oft.checkValidity());
    }

    @Override
    public void acceptVisit(IUpdateVisitor visitor){
        visitor.handleUpdateMessage(this);
    }

}
