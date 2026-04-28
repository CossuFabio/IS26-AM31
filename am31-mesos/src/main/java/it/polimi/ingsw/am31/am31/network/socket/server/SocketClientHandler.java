package it.polimi.ingsw.am31.am31.network.socket.server;

import it.polimi.ingsw.am31.am31.network.Server;
import it.polimi.ingsw.am31.am31.network.VirtualView;
import it.polimi.ingsw.am31.am31.network.errorMessage.ErrorMessage;
import it.polimi.ingsw.am31.am31.network.errorMessage.ErrorMessageMapper;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.RequestMethodsConstants;
import it.polimi.ingsw.am31.am31.network.requests.RequestsMapper;
import it.polimi.ingsw.am31.am31.network.requests.transportLayerRequest.NewServerConnectionRequest;
import it.polimi.ingsw.am31.am31.network.socket.client.SocketClient;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMapper;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMessage;

import java.io.*;
import java.net.Socket;

public class SocketClientHandler implements VirtualView {

    private final Server mainServer;
    private final Socket socket;
    private final BufferedReader input;
    private final PrintWriter output;
    private volatile long lastTimeSeen;



    public SocketClientHandler(Server mainServer, Socket socket) throws IOException {
        this.mainServer = mainServer;
        this.socket = socket;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.lastTimeSeen = System.currentTimeMillis();
    }


    public void runVirtualView(){
        String jsonReq;
        try{
            while((jsonReq = input.readLine()) != null){
                System.out.println(jsonReq);
                NetworkRequest req = RequestsMapper.deserialize(jsonReq);
                //No need to check if it is new connection.
                mainServer.handleNetworkRequest(req, this);
            }
        }catch(IOException e){
            System.err.println("Input error");
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        finally{
            closeConnection();
        }
    }


    private void closeConnection(){
        try{
            if (input != null) input.close();
            if (output != null) output.close();
            if (socket != null && !socket.isClosed()) socket.close();
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }


    @Override
    public void receiveUpdate(UpdateMessage data) throws Exception {
        this.output.println(UpdateMapper.serialize(data));
        this.output.flush();
    }

    @Override
    public void receiveMessage(String data) throws Exception {
        this.output.println(data);
        this.output.flush();
    }

    @Override
    public void receiveErrorMessage(ErrorMessage error) {
        this.output.println(ErrorMessageMapper.serialize(error));
        this.output.flush();
    }

    @Override
    public void updateLastTime() {
        this.lastTimeSeen = System.currentTimeMillis();
    }

    @Override
    public long getLastTime() {
        return lastTimeSeen;
    }

    @Override
    public void forceDisconnect() {
        //Forse the closure of the socket.
        //This will make the readLine in (jsonReq = input.readLine()) in runVirtualView to throw an error (that calls again closeConnection
        //but this is not a problem: close is idempotent.
        closeConnection();
    }
}
