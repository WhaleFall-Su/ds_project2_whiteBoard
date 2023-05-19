package manager;

public class ConnectionMethods {
    public static synchronized int verifyUser(String userName) {
        int result = LoginUI.managerUIBoard.showRequest(userName);
        return result;
    }
}
