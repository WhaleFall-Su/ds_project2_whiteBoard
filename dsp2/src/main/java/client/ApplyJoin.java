package client;

import com.google.gson.Gson;
import manager.Consts;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class ApplyJoin {

    private JFrame frame;

    public static String address;
    public static int port;
    public static String username;
    public static ClientUIBoard clientUIBoard;
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
    public ApplyJoin() throws IOException {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() throws IOException {
        /*clientConnection.out.write("begin" + "\n");
        clientConnection.out.flush();*/
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
                try {
                    username = userNameArea.getText();

                    HashMap map = new Gson().fromJson("{\"feedback\":\"join\"," +
                            "\"userName\":" + username + "}", HashMap.class);
                    String join = new Gson().toJson(map);

                    clientConnection.out.write(join + "\n");
                    clientConnection.out.flush();
                    int time = 0;
                    while (clientConnection.getStatus().equals("wait") && time < 100000) {
                        TimeUnit.MILLISECONDS.sleep(100);
                        time += 100;
                    }

                    String status = clientConnection.getStatus();

                    System.out.println("getStatus() is " + status);
                    if (status.equals("approve enter")) {
                        frame.dispose();

                        /*HashMap beginMap = new Gson().fromJson("{\"feedback\":\"begin\"" + "}", HashMap.class);
                        String begin = new Gson().toJson(beginMap);
                        try {
                            clientConnection.out.write(begin + "\n");
                            clientConnection.out.flush();
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }*/

                        clientUIBoard = new ClientUIBoard(clientConnection, username);
                        clientUIBoard.setFrame(clientUIBoard);
                    } else if (status.equals(Consts.NOT_APPROVE)) {
                        JOptionPane.showMessageDialog(frame, "Username exists");
                        clientConnection.setStatus("wait");
                    } else if (status.equals("reject")) {
                        JOptionPane.showMessageDialog(frame, "Refused by Manager");
                        frame.dispose();
                        try {
                            socket.close();
                            System.exit(1);
                        } catch (Exception exception) {

                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        frame.setVisible(true);
    }

}

