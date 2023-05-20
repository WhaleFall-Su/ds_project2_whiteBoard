package manager;

import java.io.IOException;
import java.util.ArrayList;

public class ConnectionMethods {
    public static synchronized int verifyUser(String userName) {
        int result = LoginUI.managerUIBoard.showRequest(userName);
        return result;
    }

    public static void sendToAllUser(String mess) throws IOException {
        for (Connection st : Server.memberMap.values()) {
            st.out.write(mess+"\n");
            st.out.flush();
        }
    }

    public static void sendPaintToAllUser(ArrayList<String> historyDraw) throws IOException {
        String[] historyArr = historyDraw.toArray(new String[historyDraw.size()]);
        StringBuffer history = new StringBuffer();
        for (String mess : historyArr) {
            history.append(mess);
        }
        System.out.println(history);
        for (Connection st : Server.memberMap.values()) {
            st.out.write("draw " + history + "\n");
            st.out.flush();
        }
    }
}
