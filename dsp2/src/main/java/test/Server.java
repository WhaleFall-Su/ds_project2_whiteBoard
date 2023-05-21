package test;

import manager.Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ConcurrentHashMap;


public class Server {

    /*public static ArrayList<Connection> connections = new ArrayList<Connection>();
    public static ArrayList<String> memberList = new ArrayList<String>();*/
    public static ConcurrentHashMap<String, Connection> memberMap = new ConcurrentHashMap<>();
    private static int clientNum = 0;

    public static void launch(int port, String username) {
        try {
            ServerSocket listeningSocket = new ServerSocket(port);
            Socket clientSocket = null;
            /*memberList.add(username);*/

            // assign a connection for each client request, it is multi-thread
            while (true) {
                try {
                    clientSocket = listeningSocket.accept();
                    clientNum += 1;
                    System.out.println(clientNum + " request to connect");
                    Connection connection = new Connection(clientSocket);
                    memberMap.put(username,connection);
                    connection.start();

                } catch (SocketException e) {
                    break;
                } catch (IOException e) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
