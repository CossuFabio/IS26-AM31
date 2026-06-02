package it.polimi.ingsw.am31.am31.network.messages.errorMessage;



import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.network.messages.IMessageVisitor;
import it.polimi.ingsw.am31.am31.network.messages.Message;


/**
 * Server-to-client error notification. Carries an {@link ErrorCode} and a string message.
 * Since the client-side handling of the messages is typically a prompt on the screen and does not interfere with local state,
 * there is not a hierarchy of ErrorMessage, but the handling is based on the ErrorCode.
 */
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


    /**
    * Accept the message visitor to dispatch this object to the handler for error messages
     */
    @Override
    public void acceptVisit(IMessageVisitor visitor) {
       visitor.visitError(this);
    }

    @Override
    public boolean checkValidity() {
        return message!=null && errorCode != null;
    }


}
