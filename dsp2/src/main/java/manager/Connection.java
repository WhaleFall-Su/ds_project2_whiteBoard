package manager;
import client.ClientUIBoard;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

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
//                socket.setKeepAlive(true);
                if (isServerClose(socket)) {
                    Server.memberMap.remove(receiveName);
                    System.out.println(receiveName + " has left");
                    System.out.println("members are: " + Server.memberMap.keySet());
                    break;
                    /*try
                    {
                        socket.close();
                        System.out.println(receiveName + " has left");
                        System.out.println("members are: " + Server.memberMap.keySet());
                        break;
                    }
                    catch (IOException e)
                    {

                    }*/
                } else {
                    while ((req = in.readLine()) != null) {

                        HashMap reqMap = new Gson().fromJson(req, HashMap.class);
                        String status = reqMap.get("feedback").toString();
//                        String[] outList = req.split(" ", 2);
                        System.out.println(req);
                        switch (status) {
                            case "draw":
                                try {
                                    // 将消息发送给所有的用户
                                    ConnectionMethods.sendToAllUser(req);

                                    ArrayList<String> historyDraw = new ArrayList<>();
                                    JsonArray jsonArray = new JsonArray();
                                    JsonParser parser = new JsonParser();
                                    jsonArray = new JsonParser().parse(req).getAsJsonObject().getAsJsonArray("historyDraw");
                                    for (int i = 0; i < jsonArray.size(); i++) {
                                        // json解析出来的字符会包含"，所以要去掉
                                        historyDraw.add(jsonArray.get(i).toString().replace("\"",""));
                                    }
                                    System.out.println("draw from client: " + historyDraw);

                                    // 更新画画record
                                    for (String draw : historyDraw) {
                                        ManagerUIBoard.createDrawListener.updateRecord(draw);
                                        ManagerUIBoard.canvas.repaint();
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
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
                                receiveName = reqMap.get("userName").toString();;
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
                }
            }
            /*while ((req = in.readLine()) != null) {

                HashMap reqMap = new Gson().fromJson(req, HashMap.class);
                String status = reqMap.get("feedback").toString();
//                        String[] outList = req.split(" ", 2);
                System.out.println(req);
                switch (status) {
                    case "draw":
                        try {
                            // 将消息发送给所有的用户
                            ConnectionMethods.sendToAllUser(req);

                            ArrayList<String> historyDraw = new ArrayList<>();
                            JsonArray jsonArray = new JsonArray();
                            JsonParser parser = new JsonParser();
                            jsonArray = new JsonParser().parse(req).getAsJsonObject().getAsJsonArray("historyDraw");
                            for (int i = 0; i < jsonArray.size(); i++) {
                                // json解析出来的字符会包含"，所以要去掉
                                historyDraw.add(jsonArray.get(i).toString().replace("\"",""));
                            }
                            System.out.println("draw from client: " + historyDraw);

                            // 更新画画record
                            for (String draw : historyDraw) {
                                ManagerUIBoard.createDrawListener.updateRecord(draw);
                                ManagerUIBoard.canvas.repaint();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
                        receiveName = reqMap.get("userName").toString();;
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
            }*/




        } catch (SocketException socketException) {
//            System.out.println(receiveName+" has left");
//            clientLeave(receiveName);
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
