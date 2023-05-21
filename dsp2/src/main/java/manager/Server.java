package manager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;


public class Server {

    public static ArrayList<Connection> connections = new ArrayList<Connection>();
    public static ArrayList<String> memberList = new ArrayList<String>();
    /*public static ConcurrentHashMap<String, Connection> memberMap = new ConcurrentHashMap<>();*/
    private static int clientNum = 0;



    public static void launch(int port, String username) {
        Connection connection = null;
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            Socket clientSocket;
            memberList.add(username);

            // assign a connection for each client request, it is multi-thread
            while (true) {
                try {
                    clientSocket = server.accept();
                    clientNum += 1;
                    System.out.println(clientNum + " request to connect");
                    connection = new Connection(clientSocket);
                    /*memberMap.put(username, connection);*/
                    connections.add(connection);
                    connection.start();
                } catch (Exception e) {
                    System.out.println("Connection fail");
                    System.exit(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
