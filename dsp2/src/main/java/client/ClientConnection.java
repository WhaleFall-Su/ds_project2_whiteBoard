package client;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

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

//                System.out.println("status is " + userList.toString());
                switch (status) {
                    case "approve entre":

                        break;
                    case "draw":
                        // Get the array in json
                        JsonArray jsonArray = new JsonArray();
                        JsonParser parser = new JsonParser();
                        jsonArray = new JsonParser().parse(req).getAsJsonObject().getAsJsonArray("historyDraw");
                        if (jsonArray.size() != 0) {
                            for (int i = 0; i < jsonArray.size(); i++) {
                                historyDraw.add(jsonArray.get(i).toString().replace("\"",""));
                            }
                            String[] recordArr = historyDraw.toArray(new String[0]);
                            System.out.println(Arrays.toString(recordArr));
                            for (String draw : recordArr) {
                                ClientUIBoard.createDrawListener.updateRecord(draw);
                                ClientUIBoard.canvas.repaint();
                            }
//                        ClientUIBoard.canvas.setList(historyDraw);

                        }

                        break;
                }
            }
        } catch (IOException e) {
            try {
                /*if (kick) {
                    JOptionPane.showMessageDialog(ApplyJoin.clientUIBoard.frame, "Disconnected with server");
                }*/
                System.out.println("exit");
                JOptionPane.showMessageDialog(ApplyJoin.clientUIBoard.frame, "Disconnected with server");
            } catch (Exception exception) {

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


}
