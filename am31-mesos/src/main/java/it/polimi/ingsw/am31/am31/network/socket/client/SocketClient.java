package it.polimi.ingsw.am31.am31.network.socket.client;

import it.polimi.ingsw.am31.am31.network.Messages.Message;
import it.polimi.ingsw.am31.am31.network.Messages.MessageVisitor;
import it.polimi.ingsw.am31.am31.network.VirtualServer;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.RequestsMapper;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.DisconnectNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.transportLayerRequest.NewServerConnectionRequest;
import it.polimi.ingsw.am31.am31.network.Messages.MessageMapper;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.LocalState.StateUpdater;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient implements VirtualServer, VirtualViewSocket {
    private final String identifier;
    private final Socket socket;
    private final PrintWriter output;
    private final BufferedReader input;
    private LocalGameState gameState;
    private StateUpdater stateUpdater;


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
            String jsonMsg;
            try{
                while ((jsonMsg = input.readLine()) != null){
                    Message message = MessageMapper.deserialize(jsonMsg);
                    MessageVisitor visitor = new MessageVisitor();
                    message.acceptVisit(visitor, stateUpdater);
//                    if(updateMessage != null && updateMessage.checkValidity()){
//                        //TODO: DISPATCH THE UPDATE
//                        //SOSEW: Remove this
//                        System.out.println(updateMessage.getUpdateType());
//                    }
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
            output.close();
            input.close();
            socket.close();
        }catch(Exception e){
            System.out.println("Error closing connection");
        }
    }

    public void setGameState(LocalGameState gameState){
        this.gameState = gameState;
        this.stateUpdater = new StateUpdater(gameState);
    }

}
