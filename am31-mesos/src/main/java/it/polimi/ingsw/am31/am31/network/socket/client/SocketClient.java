package it.polimi.ingsw.am31.am31.network.socket.client;

import it.polimi.ingsw.am31.am31.network.ClientConfig;
import it.polimi.ingsw.am31.am31.network.MessageDispatcher;
import it.polimi.ingsw.am31.am31.network.Messages.Message;
import it.polimi.ingsw.am31.am31.network.VirtualServer;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.RequestsMapper;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.DisconnectNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.transportLayerRequest.NewServerConnectionRequest;
import it.polimi.ingsw.am31.am31.network.Messages.MessageMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient implements VirtualServer, VirtualViewSocket {

    private String identifier = ClientConfig.UNREGISTERED_CLIENT_ID;
    private final Socket socket;
    private final PrintWriter output;
    private final BufferedReader input;
    private final MessageDispatcher messageDispatcher;
    private boolean usernameSet = false;

    public SocketClient(String ip, int port,  MessageDispatcher dispatcher) throws Exception{

        this.socket = new Socket(ip, port);
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.output = new PrintWriter(socket.getOutputStream());
        this.messageDispatcher = dispatcher;

        startClientSocket();
        System.out.println("Connesso al server");

    }

    private void startClientSocket(){

        new Thread(()->{
            String jsonMsg;
            try{
                while ((jsonMsg = input.readLine()) != null){

                    Message message = MessageMapper.deserialize(jsonMsg);
                    if(message == null || !message.checkValidity()) continue;
                    messageDispatcher.submit(message);

                }
            }catch(Exception e){
                System.err.println(e.getMessage());
            }


        }).start();
    }

    @Override
    public void sendRequest(NetworkRequest request){
        try{
            request.setPlayerID(identifier);
            String jsonReq = RequestsMapper.serialize(request);
            output.println(jsonReq);
            output.flush();
        } catch (Exception e) {
            System.out.println("Error sending request");
        }
    }

    @Override
    public void disconnect(){
        try{
            sendRequest(new DisconnectNetworkRequest());
            messageDispatcher.shutdown();
            output.close();
            input.close();
            socket.close();
        }catch(Exception e){
            System.out.println("Error closing connection");
        }
    }

    @Override
    public void setIdentifier(String identifier){
        if(!usernameSet){
            this.identifier = identifier;
            usernameSet = true;
        }
    }

}
