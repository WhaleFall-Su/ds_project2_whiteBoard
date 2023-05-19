package manager;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class Connection extends Thread {
    Socket socket = null;
    private static Connection connection;

    BufferedReader in;
    BufferedWriter out;

    @Override
    public void run() {
        try {
            String req;
            /*while ((req = in.readLine()) != null) {
                String[] outList = req.split(" ", 2);
                switch (outList[0]) {
                    case "join":
                        System.out.println("join");
                        out.write( "ok\n");
                        out.flush();
                }
            }*/
            out.write( "ok\n");
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection(Socket clientSocket) {
        try {
            socket = clientSocket;

            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {

        } catch (IOException e) {

        }
    }
}
