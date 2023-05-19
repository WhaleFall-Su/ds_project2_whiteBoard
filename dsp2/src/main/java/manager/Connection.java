package manager;

import javax.swing.*;
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
            while ((req = in.readLine()) != null) {
                String[] outList = req.split(" ", 2);
                switch (outList[0]) {
                    case "join":
                        String receiveName = outList[1];
                        if (Server.memberList.contains(receiveName)) {
                            out.write("not approve\n");
                            out.flush();
                        } else {
                            int verify = 0;
                            try {
                                verify = ConnectionMethods.verifyUser(receiveName);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (verify == JOptionPane.YES_OPTION) {
                                if (Server.memberList.contains(receiveName)) {
                                    try {
                                        out.write("not approve\n");
                                        out.flush();
                                        Server.connections.remove(this);
                                        socket.close();
                                        break;
                                    } catch (Exception e) {
                                        Server.connections.remove(this);
                                        e.printStackTrace();
                                    }
                                } else {
                                    try {
                                        Server.memberList.add(receiveName);
                                        out.write("approve enter\n");
                                        out.flush();
                                    } catch (Exception e) {

                                    }

                                }
                            } else if (verify == JOptionPane.CANCEL_OPTION || verify == JOptionPane.CLOSED_OPTION
                                    || verify == JOptionPane.NO_OPTION) {
                                out.write("reject");
                                out.flush();
                                Server.connections.remove(this);
                            }
                        }
                        break;
                }
            }
            /*out.write( "ok\n");
            out.flush();*/
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
