package it.polimi.ingsw.am31.am31.network.updateMessages.boardUpdates;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMethodsConstants;

import java.util.List;

public class OfferTrackUpdate extends UpdateMessage {

    private final List<OfferCardMessage> offerTrack;

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
}
