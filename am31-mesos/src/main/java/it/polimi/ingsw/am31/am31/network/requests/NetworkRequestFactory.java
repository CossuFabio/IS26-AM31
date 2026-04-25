package it.polimi.ingsw.am31.am31.network.requests;

import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.JoinGameNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.transportLayerRequest.NewServerConnectionRequest;

public class NetworkRequestFactory {

    //TODO: IMPLEMENT OTHERS



    public static NewServerConnectionRequest createNewServerConnectionRequest(){
        return new NewServerConnectionRequest();
    }




}
