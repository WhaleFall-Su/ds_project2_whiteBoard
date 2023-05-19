package manager;

import javax.swing.*;
import java.awt.*;


public class LoginUI {

    private JFrame frame;

    public static String address;
    public static int port;
    public static String username;
    public static ManagerUIBoard managerUIBoard;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        if (args.length >= 3) {
            try {
                address = args[0];
                port = Integer.parseInt(args[1]);
                username = args[2];
            } catch (Exception e) {
                System.out.println("Type error.");
                System.exit(1);
            }
        } else {
            address = "localhost";
            port = 8080;
            username = "Manager";
            System.out.println("Launch by default");
        }
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new LoginUI();

//                    LoginUI window = new LoginUI();
//                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Server.launch(port, username);
    }

    /**
     * Create the application.
     */
    public LoginUI() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton addUserButton = new JButton("ENTER");
        addUserButton.setBounds(153, 197, 117, 47);
        frame.getContentPane().add(addUserButton);

        JTextArea userNameArea = new JTextArea();
        userNameArea.setLineWrap(true);
        userNameArea.setBounds(54, 94, 327, 74);
        frame.getContentPane().add(userNameArea);

        JLabel userNameLabel = new JLabel("Please input your username");
        userNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userNameLabel.setBounds(119, 59, 193, 23);
        frame.getContentPane().add(userNameLabel);

        addUserButton.addActionListener(e -> {
            if (e.getActionCommand().equals("ENTER")) {
                username = userNameArea.getText();
                frame.dispose();
                try {

                    managerUIBoard = new ManagerUIBoard(username);
                    /*//这段有什么用
                    managerUIBoard.setFrame(managerUIBoard);*/
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        frame.setVisible(true);
    }

}

