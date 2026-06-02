package it.polimi.ingsw.am31.am31.network.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Exposes static method used to serialize and deserialize JSON strings of {@link NetworkRequest} with the Jackson library.
 */
public class RequestsMapper {
    private static final ObjectMapper mapper = new ObjectMapper().configure(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS,false);

    /**
     * Converts a {@link NetworkRequest} to a JSON String.
     * May return null in case of errors with the serialization.
     * @param request network DTO
     * @return the JSON representation of the request
     */
    public static String serialize(NetworkRequest request){
        try{
            String result = mapper.writeValueAsString(request);
            return result;
        }catch(JsonProcessingException e){
            System.err.println(e);
        }
        return null;
    }

    /**
     * Converts JSON String to a {@link NetworkRequest} .
     * May return null in case of errors with the deserialization.
     * @param request JSON version of the received DTO
     * @return the Object version of the received DTO
     */
    public static NetworkRequest deserialize(String request){
        try{
            NetworkRequest result = mapper.readValue(request, new TypeReference<NetworkRequest>() {});
            return result;
        }catch(JsonProcessingException e){
            System.err.println(e);
        }
        return null;
    }
}
