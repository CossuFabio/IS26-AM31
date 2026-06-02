package it.polimi.ingsw.am31.am31.network.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorMessage;
import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorMessageMapper;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateMapper;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateMessage;


/**
 * Exposes static methods to serialize and deserialize {@link Message} objects to/from JSON.
 * It is able to handle bot {@link UpdateMessage} and {@link ErrorMessage} by reading the "type" field in the JSON.
 * May return null in case of errors
 */
public class MessageMapper {
    private static final ObjectMapper mapper = new ObjectMapper().configure(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS,false);

    /**
     * Converts a {@link Message} to a JSON string.
     * May return null in case of serialization errors
     * @param request the message DTO
     * @return the JSON representation of the message
     */
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


    /**
     * Converts a JSON string to a {@link Message}.
     * May return null in case of deserialization errors
     * @param jsonString the JSON representation of the message
     * @return the deserialized message DTO
     */
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
