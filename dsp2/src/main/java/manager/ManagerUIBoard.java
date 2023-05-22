package manager;


import com.google.gson.Gson;
import test.FormatSaveFileUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import javax.imageio.*;


public class ManagerUIBoard {

    static Listener createDrawListener;

    public JFrame frame;
    int width;
    int height;
//    private String file = "./save/white_board";

    public static FormatSaveFileUI formatSaveFileUI;



    static ManagerUIBoard createManagerUI;

    static CanvasPainter canvas;
    public static JList memberList;
    public static int curX, curY;
    public static JTextArea chatArea;
    public static String selectedUser;

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
    public ManagerUIBoard(String userName) {
        initialize(userName);
    }


    public int showRequest(String userName) {
        int option = JOptionPane.showConfirmDialog(null, userName + " wants to join your white board",
                "Confirm", JOptionPane.INFORMATION_MESSAGE);
        return option;
    }
    /**
     * Initialize the contents of the frame.
     */
    private void initialize(String userName) {

        frame = new JFrame();
        frame.setBounds(100, 100, 1600, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createDrawListener = new Listener(frame);
        frame.getContentPane().setLayout(null);


        JPanel toolPanel = new JPanel();
        toolPanel.setBounds(0, 4, 117, 246);
        frame.getContentPane().add(toolPanel);
        toolPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        //        JPanel toolPanel = new JPanel();
        //        toolPanel.setBounds(0,0,80,450);
        //        toolPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // menu
        JComboBox menu = new JComboBox();
        toolPanel.add(menu);
        menu.setModel(new DefaultComboBoxModel(new String[]{"New", "Save", "Save as", "Open", "Exit"}));
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) menu.getSelectedItem();

                if (selectedItem.equals("New")) {
                    //清空白板

                    /*createDrawListener.cleanHistoryRecord();

                    HashMap map = new Gson().fromJson("{\"feedback\":\"clean\"" + "}", HashMap.class);
                    String jsonCommand = new Gson().toJson(map);

                    ConnectionMethods.sendToAllUser(jsonCommand);
                    canvas.repaint();
                    try {
                        ConnectionMethods.sendPaintToAllUser(createDrawListener.getRecordList());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }*/
                    cleanBoard();

                    System.out.println("new selected");
                } else if (selectedItem.equals("Save")) {
                    String fileName = "save";
                    File file = new File("./save.jpg");
                    try {
                        saveRecord("./",fileName);
                        saveAsImage(file, "jpg");
                    } catch (Exception exception) {
                    }

                    System.out.println("Save selected");
                } else if (selectedItem.equals("Save as")) {
                    //
                    saveAsFile();
                    System.out.println("Save selected");
                } else if (selectedItem.equals("Open")) {
                    openFile();
                } else if (selectedItem.equals("Exit")) {
                    System.exit(0);
                }
            }
        });

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
        canvas = new CanvasPainter();
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

        JTextArea chatArea = new JTextArea();
        chatArea.setEnabled(false);
        chatArea.setLineWrap(true);
        chatArea.setBounds(1284, 53, 310, 578);
        frame.getContentPane().add(chatArea);

        /*JTextArea memberList = new JTextArea();
        memberList.setLineWrap(true);
        memberList.setBounds(0, 408, 117, 318);
        for (String member : Server.memberList) {
            memberList.setText(member + "\n");
        }
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
        memberList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        memberList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    selectedUser = (String) memberList.getSelectedValue();
                    System.out.println("selectedUser "+selectedUser);
                }
            }
        });
        frame.getContentPane().add(memberListScrollPane);

        JTextArea sendMessArea = new JTextArea();
        sendMessArea.setLineWrap(true);
        sendMessArea.setBounds(1284, 661, 236, 105);
        frame.getContentPane().add(sendMessArea);

        JButton send = new JButton("Send");
        send.setBounds(1520, 739, 74, 29);
        frame.getContentPane().add(send);

        JLabel chat = new JLabel("Chat");
        chat.setHorizontalAlignment(SwingConstants.CENTER);
        chat.setBounds(1402, 20, 61, 27);
        frame.getContentPane().add(chat);

        JLabel member = new JLabel("Member List");
        member.setBounds(0, 391, 108, 16);
        frame.getContentPane().add(member);

        JButton kickButton = new JButton("Kick");
        kickButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // manager无法踢出自己
                if (selectedUser.equals(userName)) {
                    return;
                }


                for (Connection connection : Server.connections) {
                    if (connection.receiveName.equals(selectedUser)) {

                        HashMap map = new Gson().fromJson("{\"feedback\":\"kick\"" + "}", HashMap.class);
                        String kickCommand = new Gson().toJson(map);

                        Server.memberList.remove(selectedUser);

                        /*ConnectionMethods.sendToAllUser(kickCommand);*/
                        try {
                            connection.out.write(kickCommand + "\n");
                            connection.out.flush();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        Server.connections.remove(connection);
                        break;
                    }
                }
                try {
                    ConnectionMethods.sendMemberToAllUser(Server.memberList);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }


            }
        });
        kickButton.setBounds(0, 725, 117, 42);
        frame.getContentPane().add(kickButton);

        JTextArea userDisplayArea = new JTextArea();
        userDisplayArea.setText("Manager: " + userName);
        userDisplayArea.setEnabled(false);
        userDisplayArea.setLineWrap(true);
        // 添加滚轮
        JScrollPane userDisplayAreaScrollPane = new JScrollPane(userDisplayArea);
        userDisplayAreaScrollPane.setBounds(2, 309, 113, 45);
        userDisplayAreaScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.getContentPane().add(userDisplayAreaScrollPane);

    }


    public void setFrame(ManagerUIBoard managerUIBoard) {
        createManagerUI = managerUIBoard;
    }
    public static ManagerUIBoard getCreateManagerUI() {
        return createManagerUI;
    }

    public void cleanBoard() {
        createDrawListener.cleanHistoryRecord();

        HashMap map = new Gson().fromJson("{\"feedback\":\"clean\"" + "}", HashMap.class);
        String jsonCommand = new Gson().toJson(map);

        ConnectionMethods.sendToAllUser(jsonCommand);
        canvas.repaint();
        try {
            ConnectionMethods.sendPaintToAllUser(createDrawListener.getRecordList());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void saveAsFile() {
        //选择要保存的位置以及文件名字和信息
        JFileChooser chooser = new JFileChooser();
        chooser.showSaveDialog(null);
        File file = chooser.getSelectedFile();
        if (file == null) {
            JOptionPane.showMessageDialog(null, "没有选择文件");
        } else {

            try {
                // 分割文件名
                String[] fileArr = file.getName().split("\\.");
                System.out.println(file.getAbsolutePath());
                System.out.println(file.getParent());
                if (fileArr.length > 1) {
                    // 得到当前的文件格式
                    String fileName = fileArr[0];
                    String format = fileArr[1];

                    if (format.equals("jpg") || format.equals("png")) {
                        String parentPath = file.getParent();
                        saveRecord(parentPath, fileName);
                        saveAsImage(file, format);

                        JOptionPane.showMessageDialog(null, "save success！");
                    } else {
                        JOptionPane.showMessageDialog(null, "save fail！");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "save fail！");
                }

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    public void saveAsImage(File file, String format) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = image.createGraphics();
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, width, height);
        canvas.draw(graphics2D, createDrawListener.getRecordList());
        try {
            if (format.equals("jpg")) {
                ImageIO.write(image, "JPEG", file);
            } else if (format.equals("png")) {
                ImageIO.write(image, "PNG", file);
            }
        } catch (IOException e) {
            System.out.println("wrong file");
        }
    }

    public void saveRecord(String parentPath, String fileName) {
        try {
            String file = fileName + ".txt";
            FileWriter writer = new FileWriter(parentPath + "/" + file);

            // 写入文件内容
            for (String record : createDrawListener.recordList) {
                writer.write(record);
                writer.write("\n");
            }

            writer.close();

            System.out.println("save record file success！");
        } catch (IOException e) {
            System.out.println("save record file failed：" + e.getMessage());
        }
    }

    public void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(null);

        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String filePath = file.getAbsolutePath();

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                cleanBoard();
                while ((line = reader.readLine()) != null) {
                    createDrawListener.updateRecord(line);
                    System.out.println("record are "+line);
                }
                canvas.repaint();
                try {
                    ConnectionMethods.sendPaintToAllUser(createDrawListener.getRecordList());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } catch (IOException e) {
                System.out.println("open file failed：" + e.getMessage());
            }
        }
    }



}