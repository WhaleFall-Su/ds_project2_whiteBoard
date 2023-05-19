package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientConnection extends Thread{

    private Socket socket;
    BufferedReader in;
    BufferedWriter out;
    private String status = " ";

    public String getStatus() {
        return status;
    }

    @Override
    public void run() {
        try {
            while (true) {
                status = in.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ClientConnection(Socket socket) {
        try {
            this.socket = socket;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
