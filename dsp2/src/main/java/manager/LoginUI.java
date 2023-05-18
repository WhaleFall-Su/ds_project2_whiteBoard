//package manager;
//
//import javax.swing.*;
//import java.awt.*;
//
//
//public class LoginUI {
//
//    private JFrame frame;
//
//    LoginUI loginUI = new LoginUI();
//    public static String address;
//    public static int port;
//    public static String username;
//    public ManagerUIBoard managerUIBoard;
//    /**
//     * Launch the application.
//     */
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
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    LoginUI window = new LoginUI();
//                    window.frame.setVisible(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//    /**
//     * Create the application.
//     */
//    public LoginUI() {
//        initialize();
//    }
//
//    /**
//     * Initialize the contents of the frame.
//     */
//    private void initialize() {
//        frame = new JFrame();
//        frame.setBounds(100, 100, 450, 300);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.getContentPane().setLayout(null);
//
//        JButton addUserButton = new JButton("ENTER");
//        addUserButton.setBounds(153, 197, 117, 47);
//        frame.getContentPane().add(addUserButton);
//
//        JTextArea userName = new JTextArea();
//        userName.setLineWrap(true);
//        userName.setBounds(54, 94, 327, 74);
//        frame.getContentPane().add(userName);
//
//        JLabel userNameLabel = new JLabel("Please input your username");
//        userNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
//        userNameLabel.setBounds(119, 59, 193, 23);
//        frame.getContentPane().add(userNameLabel);
//
//        addUserButton.addActionListener(e -> {
//            if (e.getActionCommand().equals("ENTER")) {
//                username = userName.getText();
//                frame.dispose();
//                try {
//                    managerUIBoard = new ManagerUIBoard(username);
//                } catch (Exception exception) {
//                    exception.printStackTrace();
//                }
//            }
//        });
//    }
//
//}
//
