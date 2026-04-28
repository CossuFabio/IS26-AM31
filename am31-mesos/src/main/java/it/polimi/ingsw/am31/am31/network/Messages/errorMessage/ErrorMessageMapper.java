package it.polimi.ingsw.am31.am31.network.Messages.errorMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ErrorMessageMapper {

    private static final ObjectMapper mapper = new ObjectMapper().configure(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS,false);

    public static String serialize(ErrorMessage request){
        try{
            String result = mapper.writeValueAsString(request);
            //System.out.println(result);
            return result;
        }catch(JsonProcessingException e){
            System.err.println("Serialization JSON error: " + e.getMessage());
        }catch(Exception e){
            System.err.println("Serialization error: " + e.getMessage());
        }
        return null;
    }

    public static ErrorMessage deserialize(String request){
        try{
            ErrorMessage result = mapper.readValue(request, new TypeReference<ErrorMessage>() {});
            return result;
        }catch(JsonProcessingException e){
            System.out.println("Errore di deserializzazione!");
            System.err.println(e);
        }
        return null;
    }

}
