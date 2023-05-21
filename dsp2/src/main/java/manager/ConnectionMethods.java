package manager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;

public class ConnectionMethods {
    public static synchronized int verifyUser(String userName) {
        int result = LoginUI.managerUIBoard.showRequest(userName);
        return result;
    }

    public static void sendToAllUser(String mess) {
        System.out.println("still have users num: " + Server.memberMap.size());
        for (String mem : Server.memberMap.keySet()) {
            try {
                Connection st = Server.memberMap.get(mem);
                st.out.write(mess + "\n");
                st.out.flush();
            } catch (SocketException socketException) {
                System.out.println("remove " + mem);
                Server.memberMap.remove(mem);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /*for (Connection st : Server.memberMap.values()) {
            try {
                st.out.write(mess + "\n");
                st.out.flush();
            } catch (SocketException socketException) {

            }
        }*/
    }

    public static void sendPaintToAllUser(ArrayList<String> historyDraw) throws IOException {
        String[] historyArr = historyDraw.toArray(new String[historyDraw.size()]);

        /*StringBuffer history = new StringBuffer();
        for (String mess : historyArr) {
            history.append(mess);
        }*/

        JsonArray jsonArrayMem = new JsonArray();
        for (String draw : historyArr) {
            final JsonPrimitive jsonDraw = new JsonPrimitive(draw);
            jsonArrayMem.add(jsonDraw);
        }

        HashMap map = new Gson().fromJson("{\"feedback\":\"draw\"," +
                "\"historyDraw\":" + jsonArrayMem + "}", HashMap.class);
        String jsonCommand = new Gson().toJson(map);

        System.out.println(jsonCommand);
        for (Connection st : Server.memberMap.values()) {
//            st.out.write("draw|" + history + "\n");
            st.out.write(jsonCommand + "\n");
            st.out.flush();
        }
    }
}
