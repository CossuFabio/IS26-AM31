package it.polimi.ingsw.am31.am31.network.Messages;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.am31.am31.network.Messages.errorMessage.ErrorMessage;
import it.polimi.ingsw.am31.am31.network.Messages.errorMessage.ErrorMessageMapper;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMapper;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMessage;


public class MessageMapper {
    private static final ObjectMapper mapper = new ObjectMapper().configure(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS,false);

    public static String serialize(Message request){
        try{
            String result = mapper.writeValueAsString(request);
            //System.out.println(result);
            return result;
        }catch(JsonProcessingException e){
            System.err.println(e);
        }catch(Exception e){
            System.err.println(e);
        }
        return null;
    }


public static Message deserialize(String jsonString){
        try{
        //Must know if the message is an UpdateType or ErrorMessage
        JsonNode rootNode = mapper.readTree(jsonString);

        //Need the discriminator
        if(!rootNode.has("messageType")) {
            System.err.println("Missing messageType");
            return null;
        }
        String messageType = rootNode.get("messageType").asText();
        //Calls the proper mapper
        if (UpdateMessage.messageType.equals(messageType)) {
            return UpdateMapper.deserialize(jsonString);
        }
        else if (ErrorMessage.messageType.equals(messageType)) {
            return ErrorMessageMapper.deserialize(jsonString);
        }
        else {
            System.err.println("Unknown MessageType: " + messageType);
        }

    } catch(Exception e){
        System.out.println("Errore di deserializzazione!");
        e.printStackTrace();
    }
    return null;
}

}
