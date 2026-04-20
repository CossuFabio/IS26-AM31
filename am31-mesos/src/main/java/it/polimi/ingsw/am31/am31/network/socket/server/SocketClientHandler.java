package it.polimi.ingsw.am31.am31.network.socket.server;

import it.polimi.ingsw.am31.am31.network.Server;
import it.polimi.ingsw.am31.am31.network.VirtualView;
import it.polimi.ingsw.am31.am31.network.errorMessage.ErrorMessage;
import it.polimi.ingsw.am31.am31.network.errorMessage.ErrorMessageMapper;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.RequestsMapper;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMapper;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class SocketClientHandler implements VirtualView {

    final Server mainServer;
    final SocketServer socketServer;
    final BufferedReader input;
    final PrintWriter output;


    public SocketClientHandler(Server mainServer, SocketServer socketServer, BufferedReader input, PrintWriter output){
        this.mainServer = mainServer;
        this.socketServer = socketServer;
        this.input = input;
        this.output = output;
    }


    public void runVirtualView(){
        String jsonReq;
        try{
            while((jsonReq = input.readLine()) != null){
                System.out.println(jsonReq);
                NetworkRequest req = RequestsMapper.deserialize(jsonReq);
                mainServer.handleNetworkRequest(req);
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
            if (!(input == null)) input.close();
            if (!(output == null)) output.close();
        }catch(IOException e){
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
}
