package it.polimi.ingsw.am31.am31.network.Messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConcreteGenericMessage extends Message{

    @JsonIgnore
    public static final String messageType = "GENERIC_MESSAGE";

    private final String content;

    public ConcreteGenericMessage(@JsonProperty("content") String content){
        super(messageType);
        this.content = content;
    }

    @Override
    public void acceptVisit(IMessageVisitor visitor) {

    }

    public String getContent(){return getContent();}

    @Override
    public boolean checkValidity() {
        return content!=null;
    }



}
