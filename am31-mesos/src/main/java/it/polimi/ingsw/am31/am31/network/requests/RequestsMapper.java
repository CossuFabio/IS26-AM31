package it.polimi.ingsw.am31.am31.network.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;




public class RequestsMapper {
    private static final ObjectMapper mapper = new ObjectMapper().configure(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS,false);

    public static String serialize(NetworkRequest request){
        try{
            String result = mapper.writeValueAsString(request);
            System.out.println(result);
            return result;
        }catch(JsonProcessingException e){
            System.err.println(e);
        }
        return null;
    }

    public static NetworkRequest deserialize(String request){
        try{
            System.out.println("request");
            NetworkRequest result = mapper.readValue(request, new TypeReference<NetworkRequest>() {});
            return result;
        }catch(JsonProcessingException e){
            System.out.println("Errore di deserializzazione!");
            System.err.println(e);
        }
        return null;
    }
}
