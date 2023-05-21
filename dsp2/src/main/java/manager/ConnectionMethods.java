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
        // 这里设置为-1是因为如果manager没有开启board，而有用户申请进入，那么会收到拒绝的消息
        int result = -1;
        if (LoginUI.managerUIBoard != null) {
            result = LoginUI.managerUIBoard.showRequest(userName);
            System.out.println("result is " + result);
        }
        return result;

    }

    public static void sendToAllUser(String mess) {
        System.out.println("still have users num: " + Server.memberList.size());

        for (Connection st : Server.connections) {
            try {
                st.out.write(mess + "\n");
                st.out.flush();
            } catch (SocketException socketException) {

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        for (Connection st : Server.connections) {
//            st.out.write("draw|" + history + "\n");
            st.out.write(jsonCommand + "\n");
            st.out.flush();
        }
    }

    public static void sendMemberToAllUser(ArrayList<String> memberList) throws IOException {
        String[] memberArr = memberList.toArray(new String[0]);

        /*StringBuffer history = new StringBuffer();
        for (String mess : historyArr) {
            history.append(mess);
        }*/

        JsonArray jsonArrayMem = new JsonArray();
        for (String member : memberArr) {
            final JsonPrimitive jsonDraw = new JsonPrimitive(member);
            jsonArrayMem.add(jsonDraw);
        }

        HashMap map = new Gson().fromJson("{\"feedback\":\"userList\"," +
                "\"memberList\":" + jsonArrayMem + "}", HashMap.class);
        String jsonCommand = new Gson().toJson(map);

        System.out.println(jsonCommand);
        for (Connection st : Server.connections) {
//            st.out.write("draw|" + history + "\n");
            st.out.write(jsonCommand + "\n");
            st.out.flush();
        }
    }

    public static void updateUserList(ArrayList<String> memberList) {
        String[] memberArr = memberList.toArray(new String[0]);
        ManagerUIBoard.memberList.setListData(memberArr);
    }
}
