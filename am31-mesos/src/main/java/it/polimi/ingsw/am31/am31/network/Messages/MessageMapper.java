package it.polimi.ingsw.am31.am31.network.Messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageMapper {
    private static final ObjectMapper mapper = new ObjectMapper().configure(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS,false);

    public static Message deserialize(String request){
        try{
            Message result = mapper.readValue(request, new TypeReference<Message>() {});
            return result;
        }catch(JsonProcessingException e){
            System.out.println("Errore di deserializzazione!");
            System.err.println(e);
        }
        return null;
    }
}
