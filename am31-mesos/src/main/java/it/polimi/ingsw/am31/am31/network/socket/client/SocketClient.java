package it.polimi.ingsw.am31.am31.network.socket.client;

import it.polimi.ingsw.am31.am31.network.VirtualServer;
import it.polimi.ingsw.am31.am31.network.VirtualView;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.DisconnectNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.transportLayerRequest.NewServerConnectionRequest;
import it.polimi.ingsw.am31.am31.network.requests.RequestsMapper;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMapper;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMessage;

import java.io.*;
import java.net.Socket;

public class SocketClient implements VirtualServer, VirtualViewSocket {
    private final String identifier;
    private final Socket socket;
    private final PrintWriter output;
    private final BufferedReader input;

    public SocketClient(String ip, int port, String identifier) throws Exception{
        this.identifier = identifier;
        this.socket = new Socket(ip, port);
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.output = new PrintWriter(socket.getOutputStream());
        NetworkRequest newSocketReq = new NewServerConnectionRequest();
        sendRequest(newSocketReq);
        startClientSocket();
        System.out.println("Connesso al server");
    }

    private void startClientSocket(){

        new Thread(()->{
            String jsonUpdateMsg;
            try{
                while ((jsonUpdateMsg = input.readLine()) != null){
                    UpdateMessage updateMessage = UpdateMapper.deserialize(jsonUpdateMsg);
                    if(updateMessage != null && updateMessage.getUpdateType() != null){
                        System.out.println(updateMessage.getUpdateType());
                    }
                }
            }catch(Exception e){
                System.err.println(e.getMessage());
            }


        }).start();
    }

    @Override
    public void sendRequest(NetworkRequest request) throws Exception {
        request.setPlayerID(identifier);
        String jsonReq = RequestsMapper.serialize(request);
        output.println(jsonReq);
        output.flush();
    }

    @Override
    public void disconnect() throws Exception {
        sendRequest(new DisconnectNetworkRequest());
        output.close();
        input.close();
        socket.close();
    }



}
