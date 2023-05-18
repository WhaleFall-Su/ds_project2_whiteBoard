package manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class ManagerUIBoard {

    static Listener createDrawListener;

    private JFrame frame;
    int width;
    int height;
//    private String file = "./save/white_board";

    static ManagerUIBoard createWhiteBoard;

    static CanvasPainter canvas;
    public JList list;
    public static int curX, curY;
    public static JTextArea chatArea;

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
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ManagerUIBoard window = new ManagerUIBoard();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public ManagerUIBoard() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

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

        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setBounds(1284, 53, 310, 578);
        frame.getContentPane().add(textArea);

        JTextArea textArea_1 = new JTextArea();
        textArea_1.setLineWrap(true);
        textArea_1.setBounds(0, 408, 117, 358);
        frame.getContentPane().add(textArea_1);

        JTextArea textArea_2 = new JTextArea();
        textArea_2.setLineWrap(true);
        textArea_2.setBounds(1284, 661, 236, 105);
        frame.getContentPane().add(textArea_2);

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




    }
}
