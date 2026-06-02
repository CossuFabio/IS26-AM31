package it.polimi.ingsw.am31.am31.network.messages.updateMessages.serverMessages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.IUpdateVisitor;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateMethodsConstants;

/**
 * Notifies the client that username registration was successful and sends back the accepted username
 */
public class SuccessRegistrationUpdate extends UpdateMessage {

    private final String username;

    @JsonCreator
    public SuccessRegistrationUpdate(@JsonProperty("username") String username){
        super(UpdateMethodsConstants.USERNAME_ACCEPTED_METHOD);
        this.username = username;
    }

    public String getUsername(){return username;}

    @Override
    protected boolean checkSpecificValidity() {
        return username != null;
    }

    @Override
    public void acceptVisit(IUpdateVisitor visitor){
        visitor.handleUpdateMessage(this);
    }

}
