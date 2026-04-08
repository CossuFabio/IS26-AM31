package it.polimi.ingsw.am31.am31.network;

import it.polimi.ingsw.am31.am31.network.rmi.client.RmiClient;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {
    //main client class
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Nickname: ");
        String nickname = scanner.nextLine();
        System.out.println("Enter 1 for RMI, 2 for Socket: ");
        int type = scanner.nextInt();
        ServerConnection connection = null;
        switch (type) {
            //ip is localhost
            case 1: connection = new RmiClient("127.0.0.1",1100,nickname);
                    ((RmiClient) connection).runCli();
            case 2://connection = new SocketClient
                ;
            default:
                break;
        }

    }
}
