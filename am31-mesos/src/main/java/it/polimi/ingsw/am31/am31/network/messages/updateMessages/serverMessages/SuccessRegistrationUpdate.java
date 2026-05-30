package it.polimi.ingsw.am31.am31.network.messages.updateMessages.serverMessages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.IUpdateVisitor;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateMethodsConstants;

public class SuccessRegistrationUpdate extends UpdateMessage {

    //Acknowledges the client that now he is registered into the server via the username provided and send it back to
    // confirm

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
