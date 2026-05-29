package it.polimi.ingsw.am31.am31.network.messages;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    public abstract boolean checkValidity();

}
