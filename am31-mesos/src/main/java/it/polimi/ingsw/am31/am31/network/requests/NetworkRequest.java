package it.polimi.ingsw.am31.am31.network.requests;

public class NetworkRequest {

    private String type;
    protected NetworkRequest(String type){this.type = type;}

    public String getType(){return type;}

}
