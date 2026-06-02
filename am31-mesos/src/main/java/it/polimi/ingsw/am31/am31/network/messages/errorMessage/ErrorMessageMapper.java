package it.polimi.ingsw.am31.am31.network.messages.errorMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Exposes static methods to serialize and deserialize {@link ErrorMessage} JSON strings using Jackson.
 * May return null in case of errors
 */
public class ErrorMessageMapper {

    private static final ObjectMapper mapper = new ObjectMapper().configure(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS,false);

    /**
     * Converts an {@link ErrorMessage} to a JSON string.
     * May return null in case of serialization errors
     * @param request the error message DTO
     * @return the JSON representation of the error message
     */
    public static String serialize(ErrorMessage request){
        try{
            String result = mapper.writeValueAsString(request);
            return result;
        }catch(JsonProcessingException e){
            System.err.println("Serialization JSON error: " + e.getMessage());
        }catch(Exception e){
            System.err.println("Serialization error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Converts a JSON string to an {@link ErrorMessage}.
     * May return null in case of deserialization errors
     * @param request the JSON representation of the error message
     * @return the deserialized error message DTO
     */
    public static ErrorMessage deserialize(String request){
        try{
            ErrorMessage result = mapper.readValue(request, new TypeReference<ErrorMessage>() {});
            return result;
        }catch(JsonProcessingException e){
            System.err.println(e);
        }
        return null;
    }

}
