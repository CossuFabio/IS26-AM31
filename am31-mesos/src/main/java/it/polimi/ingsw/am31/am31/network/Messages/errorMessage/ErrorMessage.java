package it.polimi.ingsw.am31.am31.network.Messages.errorMessage;

//No hierarchy for messages since the clients only displays them, unlike updates and network request that operates on them

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.network.Messages.IMessageVisitor;
import it.polimi.ingsw.am31.am31.network.Messages.Message;
import it.polimi.ingsw.am31.am31.network.Messages.MessageVisitor;

public class ErrorMessage extends Message {

    private final ErrorCategory category;
    private final String message;

    @JsonCreator
    public ErrorMessage(
            @JsonProperty("message") String message,
            @JsonProperty("category") ErrorCategory category){
        this.message = message;
        this.category = category;
    }



    public String getMessage(){return message; }
    public ErrorCategory getCategory(){return category; }

    @Override
    public void acceptVisit(IMessageVisitor visitor) {
       visitor.visitError(this);
    }
}
