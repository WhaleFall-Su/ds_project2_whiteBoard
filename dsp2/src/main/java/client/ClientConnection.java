package client;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientConnection extends Thread{

    Socket socket;
    BufferedReader in;
    BufferedWriter out;
    private String status = "wait";


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
                ArrayList<String> userList = new ArrayList<>();

                // Get the array in json
                JsonArray jsonArray = new JsonArray();
                JsonParser parser = new JsonParser();
                jsonArray = new JsonParser().parse(req).getAsJsonObject().getAsJsonArray("memberList");
                for (int i = 0; i < jsonArray.size(); i++) {
                    userList.add(jsonArray.get(i).toString());
                }
//                System.out.println("status is " + userList.toString());
                switch (status) {
                    case "approve entre":
                        String[] strings = {"ada", "dad"};
                        System.out.println("status is " + userList.toString());
                        GuestUIBoard.memberList.setListData(strings);
                        out.write("draw" + "\n");
                        out.flush();
                        break;
                }
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

    public String getStatus() {
        return status;
    }


}
