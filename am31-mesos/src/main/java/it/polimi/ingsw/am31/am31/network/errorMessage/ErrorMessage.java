package it.polimi.ingsw.am31.am31.network.errorMessage;

//No hierarchy for messages since the clients only displays them, unlike updates and network request that operates on them

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
//TODO: Find a way to handle both error and update messages on SocketClient
public class ErrorMessage {

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


}
