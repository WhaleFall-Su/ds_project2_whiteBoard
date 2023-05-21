package client;

import manager.ManagerUIBoard;

import java.util.ArrayList;

public class ClientConnectionMethods {
    public static void updateUserList(ArrayList<String> memberList) {
        String[] memberArr = memberList.toArray(new String[0]);
        ClientUIBoard.memberList.setListData(memberArr);
    }
}
