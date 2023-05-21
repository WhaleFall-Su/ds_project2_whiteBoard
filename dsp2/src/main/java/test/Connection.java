/*
package test;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import manager.ConnectionMethods;
import manager.Consts;
import manager.ManagerUIBoard;
import manager.Server;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;

public class Connection extends Thread {
    Socket socket = null;
    private static Connection connection;
    String receiveName;
    BufferedReader in;
    BufferedWriter out;

    @Override
    public void run() {
        try {
            String req;
            while (true) {
                if (isServerClose(socket)) {
                    System.out.println("disconnect");
                    Server.memberMap.remove(receiveName);
                }
            }

            while ((req = in.readLine()) != null) {
                String[] outList = req.split(" ", 2);
                System.out.println(req);
                switch (outList[0]) {
                    case "begin":
                        ArrayList<String> historyDraw = ManagerUIBoard.createDrawListener.getRecordList();
//                        System.out.println(historyDraw);
                        try {
                            ConnectionMethods.sendPaintToAllUser(historyDraw);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case "join":
                        receiveName = outList[1];
                        if (Server.memberMap.containsKey(receiveName)) {
                            HashMap map = new Gson().fromJson("{\"feedback\":\"enter not approve\"" + "}", HashMap.class);
                            String jsonCommand = new Gson().toJson(map);
                            out.write(jsonCommand + "\n");
                            out.flush();
                        } else {
                            int verify = 0;
                            try {
                                verify = ConnectionMethods.verifyUser(receiveName);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (verify == JOptionPane.YES_OPTION) {
                                if (Server.memberMap.containsKey(receiveName)) {
                                    try {
                                        out.write(Consts.NOT_APPROVE + "\n");
                                        out.flush();
                                        Server.memberMap.remove(receiveName);
                                        socket.close();
                                        break;
                                    } catch (Exception e) {
                                        Server.memberMap.remove(receiveName);
                                        e.printStackTrace();
                                    }
                                } else {
                                    try {
                                        Server.memberMap.put(receiveName, this);
                                        String[] strings = Server.memberMap.keySet().toArray(new String[0]);
                                        System.out.println(Server.memberMap.keySet());

                                        JsonArray jsonArrayMem = new JsonArray();
                                        for (String member : Server.memberMap.keySet()) {
                                            final JsonPrimitive jsonMember = new JsonPrimitive(member);
                                            jsonArrayMem.add(jsonMember);
                                        }

                                        HashMap map = new Gson().fromJson("{\"feedback\":\"approve enter\"," +
                                                "\"memberList\":" + jsonArrayMem + "}", HashMap.class);
                                        String jsonCommand = new Gson().toJson(map);
                                        System.out.println(jsonCommand);
                                        ManagerUIBoard.memberList.setListData(strings);
                                        out.write(jsonCommand + "\n");
                                        out.flush();
                                    } catch (Exception e) {

                                    }

                                }
                            } else if (verify == JOptionPane.CANCEL_OPTION || verify == JOptionPane.CLOSED_OPTION
                                    || verify == JOptionPane.NO_OPTION) {
                                HashMap map = new Gson().fromJson("{\"feedback\":\"reject\"" + "}", HashMap.class);
                                String jsonCommand = new Gson().toJson(map);
                                System.out.println(jsonCommand);
                                out.write(jsonCommand + "\n");
                                out.flush();
                                Server.memberMap.remove(receiveName);
                            }
                        }
                        break;
                }
            }
        } catch (SocketException socketException) {
            System.out.println(receiveName+" has left");
            clientLeave(receiveName);
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

    public void clientLeave(String clientName) {
        Server.memberMap.remove(clientName);
    }

    public Boolean isServerClose(Socket socket){
        try{
            socket.sendUrgentData(0xFF);//发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信
            return false;
        }catch(Exception se){
            return true;
        }
    }
}
*/
