//package manager;
//
//import javax.swing.*;
//import java.awt.*;
//import java.net.http.WebSocket;
//
//public class CreateManagerUI {
//    private JFrame frame;
//
//    CreateManagerUI createManagerUI = new CreateManagerUI();
//    public static String address;
//    public static int port;
//    public static String username;
//
//    public static void main(String[] args) {
//        if (args.length >= 3) {
//            try {
//                address = args[0];
//                port = Integer.parseInt(args[1]);
//                username = args[2];
//            } catch (Exception e) {
//                System.out.println("Type error.");
//                System.exit(1);
//            }
//        } else {
//            address = "localhost";
//            port = 8080;
//            username = "Manager";
//            System.out.println("Launch by default");
//        }
//        EventQueue.invokeLater(() -> {
//            try {
//                new CreateManagerUI();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }
//
//    public CreateManagerUI() {
//        initialize();
//    }
//
//    private void initialize() {
//        frame.setResizable(false);
//    }
//
//
//}
