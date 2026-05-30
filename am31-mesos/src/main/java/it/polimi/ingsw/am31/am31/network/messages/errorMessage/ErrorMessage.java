package it.polimi.ingsw.am31.am31.network.messages.errorMessage;

//No hierarchy for messages since the clients only displays them, unlike updates and network request that operates on them

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.network.messages.IMessageVisitor;
import it.polimi.ingsw.am31.am31.network.messages.Message;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.ErrorHandler;

public class ErrorMessage extends Message {

    @JsonIgnore
    public static final String messageType = "ERROR";

    private final String message;
    private final ErrorCode errorCode;


    @JsonCreator
    public ErrorMessage(
            @JsonProperty("message") String message,
            @JsonProperty("errorCode") ErrorCode errorCode){
        super(messageType);
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getMessage(){return message; }

    public ErrorCode getErrorCode(){
        return errorCode;
    }

    @JsonIgnore
    public ErrorCategory getErrorCategory(){return errorCode.getCategory(); }

    @Override
    public void acceptVisit(IMessageVisitor visitor) {
       visitor.visitError(this);
    }

    @Override
    public boolean checkValidity() {
        return message!=null && errorCode != null;
    }

    public void acceptVisit(ErrorHandler visitor){
        
    }

}
