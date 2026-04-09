package it.polimi.ingsw.am31.am31.network;

import it.polimi.ingsw.am31.am31.network.rmi.client.RmiServerConnection;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {
    //main client class
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Nickname: ");
        String nickname = scanner.nextLine();

        //modifica:
        System.out.println("Enter server IP: ");
        String ip = scanner.nextLine(); // aggiunto

        System.out.println("Enter 1 for RMI, 2 for Socket: ");
        int type = scanner.nextInt();
        ServerConnection connection = null;
        switch (type) {
            //ip is localhost
            //case 1 starts
            case 1: connection = new RmiServerConnection(ip,1100,nickname);
                    ((RmiServerConnection) connection).runCli();
                    break;
            case 2://connection = new SocketServerConnection
                break;
            default:
                break;
        }

    }
}
