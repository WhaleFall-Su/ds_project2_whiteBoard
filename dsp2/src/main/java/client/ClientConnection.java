package client;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import manager.ConnectionMethods;
import manager.ManagerUIBoard;
import manager.Server;

import javax.imageio.IIOException;
import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ClientConnection extends Thread{

    Socket socket;
    BufferedReader in;
    BufferedWriter out;
    private String status = "wait";
    private boolean kick = false;


    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void run() {
        try {
            while (true) {


                String req = in.readLine();
                //{"feedback":"approve enter","memberList":["Manager","hu","hy"]}
                System.out.println(req);


                HashMap reqMap = new Gson().fromJson(req, HashMap.class);
                status = reqMap.get("feedback").toString();
                ArrayList<String> historyDraw = new ArrayList<>();
                ArrayList<String> memberList = new ArrayList<>();

                JsonArray jsonArray = new JsonArray();
                JsonParser parser = new JsonParser();
                switch (status) {
                    case "kick":
                        kick = true;
                        System.out.println("you are kick");
                        // 这里socket要先关闭，不然manager要等待cient点击弹窗确定后才能更新list
                        socket.close();
                        JOptionPane.showMessageDialog(ApplyJoin.clientUIBoard.frame, "You are kicked by Manager!");
                        break;
                    case "clean":
                        //加这个判断是防止client还没收到server端允许进入白板时，server端画图等操作传给当前client
                        // 但此时client的白板还没打开，会报错
                        if (ApplyJoin.clientUIBoard != null) {
                            ClientUIBoard.createDrawListener.cleanHistoryRecord();
                            ClientUIBoard.canvas.repaint();
                        } else {
                            resetStatus("wait");
                        }

                        break;
                    /*HashMap map = new Gson().fromJson("{\"feedback\":\"userList\"," +
                            "\"memberList\":" + jsonArrayMem + "}", HashMap.class);*/
                    case "userList":
                        if (ApplyJoin.clientUIBoard != null) {
                            jsonArray = new JsonParser().parse(req).getAsJsonObject().getAsJsonArray("memberList");
                            for (int i = 0; i < jsonArray.size(); i++) {
                                memberList.add(jsonArray.get(i).toString().replace("\"", ""));
                            }
                            String[] memberArr = memberList.toArray(new String[0]);
                            System.out.println(Arrays.toString(memberArr));
                            ClientConnectionMethods.updateUserList(memberList);
                        } else {
                            resetStatus("wait");
                        }

                        break;
                    case "draw":
                        if (ApplyJoin.clientUIBoard != null) {
                            // Get the array in json
                            jsonArray = new JsonArray();
                            parser = new JsonParser();
                            jsonArray = new JsonParser().parse(req).getAsJsonObject().getAsJsonArray("historyDraw");
                            if (jsonArray.size() != 0) {
                                for (int i = 0; i < jsonArray.size(); i++) {
                                    historyDraw.add(jsonArray.get(i).toString().replace("\"", ""));
                                }
                                String[] recordArr = historyDraw.toArray(new String[0]);
                                System.out.println(Arrays.toString(recordArr));
                                for (String draw : recordArr) {
                                    ClientUIBoard.createDrawListener.updateRecord(draw);
                                    ClientUIBoard.canvas.repaint();
                                }
//                        ClientUIBoard.canvas.setList(historyDraw);

                            }
                        } else {
                            resetStatus("wait");
                        }

                        break;
                }




            }
        } catch (Exception e) {
            e.printStackTrace();
            if (!kick) {
                System.out.println("exit");
                try {
                    JOptionPane.showMessageDialog(ApplyJoin.clientUIBoard.frame, "Disconnected with server");
                } catch (Exception exception) {

                }

            }
            System.exit(0);
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

    public String getStatus() {
        return status;
    }

    public void resetStatus(String reset) {
        this.status = reset;
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
