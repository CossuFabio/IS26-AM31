package it.polimi.ingsw.am31.am31.network.messages.updateMessages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Exposes static methods to serialize and deserialize {@link UpdateMessage} JSON strings using Jackson.
 * May return null in case of errors
 */
public class UpdateMapper {
    private static final ObjectMapper mapper = new ObjectMapper().configure(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS,false);

    /**
     * Converts an {@link UpdateMessage} to a JSON string.
     * May return null in case of serialization errors
     * @param request the update message DTO
     * @return the JSON representation of the update message
     */
    public static String serialize(UpdateMessage request){
        try{
            String result = mapper.writeValueAsString(request);
            return result;
        }catch(JsonProcessingException e){
            System.err.println(e);
        }catch(Exception e){
            System.err.println(e);
        }
        return null;
    }

    /**
     * Converts a JSON string to an {@link UpdateMessage}.
     * May return null in case of deserialization errors
     * @param request the JSON representation of the update message
     * @return the deserialized update message DTO
     */
    public static UpdateMessage deserialize(String request){
        try{
            UpdateMessage result = mapper.readValue(request, new TypeReference<UpdateMessage>() {});
            return result;
        }catch(JsonProcessingException e){
            System.err.println(e);
        }
        return null;
    }
}
