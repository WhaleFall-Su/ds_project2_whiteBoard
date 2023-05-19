package client;

import manager.ManagerUIBoard;
import manager.Server;

import javax.swing.*;
import java.awt.*;
import java.net.Socket;

public class ApplyJoin {

    private JFrame frame;

    public static String address;
    public static int port;
    public static String username;
    public GuestUIBoard guestUIBoard;
    public static ClientConnection clientConnection;

    public static Socket socket;
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
            username = "User";
            System.out.println("Launch by default");
        }

        try {
            socket = new Socket(address, port);

        } catch (Exception e) {
            System.out.println("connection refused");
            System.exit(1);
            e.printStackTrace();
        }
        clientConnection = new ClientConnection(socket);
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new ApplyJoin();

//                    LoginUI window = new LoginUI();
//                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        clientConnection.start();
    }

    /**
     * Create the application.
     */
    public ApplyJoin() {
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

        JTextArea userName = new JTextArea();
        userName.setLineWrap(true);
        userName.setBounds(54, 94, 327, 74);
        frame.getContentPane().add(userName);

        JLabel userNameLabel = new JLabel("Please input your username");
        userNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userNameLabel.setBounds(119, 59, 193, 23);
        frame.getContentPane().add(userNameLabel);

        addUserButton.addActionListener(e -> {
            if (e.getActionCommand().equals("ENTER")) {
                try {
                    username = userName.getText();
                    clientConnection.out.write("Join\n");
                    clientConnection.out.flush();
                    if (clientConnection.getStatus().equals("ok")) {
                        frame.dispose();
                        guestUIBoard = new GuestUIBoard(username);
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                /*username = userName.getText();
                frame.dispose();
                try {
                    guestUIBoard = new GuestUIBoard(username);
                    //这段有什么用
                    managerUIBoard.setFrame(managerUIBoard);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }*/
            }
        });

        frame.setVisible(true);
    }

}

