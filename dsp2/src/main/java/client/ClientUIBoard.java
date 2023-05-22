package client;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import manager.CanvasPainter;
import manager.ConnectionMethods;
import manager.Listener;
import manager.ManagerUIBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;


public class ClientUIBoard {

    static ClientListener createDrawListener;

    public JFrame frame;
    int width;
    int height;
//    private String file = "./save/white_board";


    static client.CanvasPainter canvas;
    public static JList memberList;
    public static int curX, curY;
    public static JTextArea chatArea;
    public static ClientUIBoard createClientUIBoard;
    public static String userName;

    /*// 得到图标图片，存在问题
    ImageIcon circle = new ImageIcon("/icon/circle.png");
    ImageIcon color = new ImageIcon(Manager.class.getResource("/icon/color.png"));
    ImageIcon line = new ImageIcon(Manager.class.getResource("/icon/line.png"));
    ImageIcon oval = new ImageIcon(Manager.class.getResource("/icon/oval.png"));
    ImageIcon text = new ImageIcon(Manager.class.getResource("/icon/text.png"));
    ImageIcon rectangle = new ImageIcon(Manager.class.getResource("/icon/rectangle.png"));*/

//    ImageIcon[] icons = {line, circle, rectangle, oval, text};
    /**
     * Launch the application.
     */


    /**
     * Create the application.
     */
    public ClientUIBoard(ClientConnection clientConnection, String userName) {
        try {
            initialize(clientConnection, userName);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize(ClientConnection clientConnection, String userName) {
        ClientUIBoard.userName = userName;

        frame = new JFrame();
        frame.setBounds(100, 100, 1600, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createDrawListener = new ClientListener(frame);
        frame.getContentPane().setLayout(null);


        JPanel toolPanel = new JPanel();
        toolPanel.setBounds(0, 4, 117, 246);
        frame.getContentPane().add(toolPanel);
        toolPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        //        JPanel toolPanel = new JPanel();
        //        toolPanel.setBounds(0,0,80,450);
        //        toolPanel.setLayout(new FlowLayout(FlowLayout.LEFT));



        // tool
        JButton lineButton = new JButton("line");
//        lineButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//            }
//        });
//        //        Image temp = line.getImage().getScaledInstance(21, 21, line.getImage().SCALE_DEFAULT);
        lineButton.setActionCommand("line");
        lineButton.setPreferredSize(new Dimension(60, 30));
        lineButton.addActionListener(createDrawListener);
        toolPanel.add(lineButton);

        JButton circleButton = new JButton("circle");
        toolPanel.add(circleButton);


        JButton ovalButton = new JButton("oval");
        toolPanel.add(ovalButton);

        JButton rectangleButton = new JButton("rect");
        toolPanel.add(rectangleButton);

        JButton colorButton = new JButton("color");
        colorButton.setActionCommand("color");
        toolPanel.add(colorButton);
        colorButton.addActionListener(createDrawListener);

        JButton textButton = new JButton("A");

        if (textButton != null) {
            toolPanel.add(textButton);
        }
        textButton.addActionListener(createDrawListener);
        textButton.setFont(new Font(null, 0, 15));
        textButton.setPreferredSize(new Dimension(50, 30));
        rectangleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        rectangleButton.addActionListener(createDrawListener);
        ovalButton.addActionListener(createDrawListener);
        circleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        circleButton.addActionListener(createDrawListener);


        //全局设置画笔粗细
        createDrawListener.setThickness(3);

        //画板
        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(toolPanel);
        canvas = new client.CanvasPainter();
        canvas.setBorder(null);
        canvas.setBounds(120, 20, 1152, 748);
        width = canvas.getWidth();
        height = canvas.getHeight();
        canvas.setBackground(Color.WHITE);
        canvas.setList(createDrawListener.getRecordList());
        frame.getContentPane().add(canvas);
        canvas.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);
        canvas.addMouseListener(createDrawListener);
        createDrawListener.setGraphics2D(canvas.getGraphics());
//        JPanel background = new JPanel();
//        background.setBounds(120, 20, 1152, 748);
//        background.setBackground(Color.WHITE);
//        frame.getContentPane().add(background);

        chatArea = new JTextArea();
        chatArea.setLineWrap(true);
        chatArea.setBounds(1284, 53, 310, 578);
        frame.getContentPane().add(chatArea);


        /*JTextArea memberList = new JTextArea();
        memberList.setLineWrap(true);
        memberList.setBounds(0, 408, 117, 358);
        frame.getContentPane().add(memberList);*/

        memberList = new JList();
        memberList.setBounds(0, 406, 117, 320);

        // 添加滚轮
        JScrollPane memberListScrollPane = new JScrollPane(memberList);
        memberListScrollPane.setBounds(0, 406, 117, 320);
        memberListScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        String myName = userName;
        String[] nameList = {myName};
        memberList.setListData(nameList);
        frame.getContentPane().add(memberListScrollPane);

        JTextArea sendMessArea = new JTextArea();
        sendMessArea.setLineWrap(true);
        sendMessArea.setBounds(1284, 661, 236, 105);
        frame.getContentPane().add(sendMessArea);

        JButton sendMessButton = new JButton("Send");
        sendMessButton.setBounds(1520, 739, 74, 29);
        sendMessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sendMess = sendMessArea.getText();
                sendMess = userName + ": " + sendMess;
                chatArea.append(sendMess + "\n");
                String[] sendMessArr = {sendMess};

                JsonArray jsonArrayMess = new JsonArray();
                for (String draw : sendMessArr) {
                    final JsonPrimitive jsonDraw = new JsonPrimitive(draw);
                    jsonArrayMess.add(jsonDraw);
                }
                HashMap map = new Gson().fromJson("{\"feedback\":\"message\"," +
                        "\"sendMess\":" + jsonArrayMess + "}", HashMap.class);
                String sendMessJson = new Gson().toJson(map);
                try {
                    clientConnection.out.write(sendMessJson+"\n");
                    clientConnection.out.flush();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        frame.getContentPane().add(sendMessButton);


        JLabel chat = new JLabel("Chat");
        chat.setHorizontalAlignment(SwingConstants.CENTER);
        chat.setBounds(1402, 20, 61, 27);
        frame.getContentPane().add(chat);

        JLabel member = new JLabel("Member List");
        member.setBounds(0, 391, 108, 16);
        frame.getContentPane().add(member);

        JTextArea userDisplayArea = new JTextArea();
        userDisplayArea.setText("User: " + userName);
        userDisplayArea.setEnabled(false);
        userDisplayArea.setLineWrap(true);
        // 添加滚轮
        JScrollPane userDisplayAreaScrollPane = new JScrollPane(userDisplayArea);
        userDisplayAreaScrollPane.setBounds(2, 309, 113, 45);
        userDisplayAreaScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.getContentPane().add(userDisplayAreaScrollPane);

        HashMap map = new Gson().fromJson("{\"feedback\":\"begin\"" + "}", HashMap.class);
        String begin = new Gson().toJson(map);
        try {
            clientConnection.out.write(begin + "\n");
            clientConnection.out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    // 这段的用处
    public void setFrame(ClientUIBoard clientUIBoard) {
        createClientUIBoard = clientUIBoard;
    }
}





