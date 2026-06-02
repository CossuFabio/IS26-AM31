package it.polimi.ingsw.am31.am31.network.messages;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Base class for all server-to-client messages; uses the visitor pattern via {@link IMessageVisitor}
 * to route the message to the correct handler without knowing its concrete type
 */
public abstract class Message {

    @JsonIgnore
    public final String messageType;

    protected Message(String messageType){
        this.messageType = messageType;
    }

    public abstract void acceptVisit(IMessageVisitor visitor);


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String getMessageType(){
        return messageType;
    }

    /**
     * @return true if the integrity check is passed
     */
    public abstract boolean checkValidity();

}
