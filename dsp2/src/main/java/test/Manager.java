//package manager;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class Manager {
//    static Listener createDrawListener;
//
//    public JFrame frame;
//    int width;
//    int height;
//    private String file = "./save/white_board";
//
//    static Manager createWhiteBoard;
//
//    static CanvasPainter canvas;
//    public JList list;
//    public static int curX, curY;
//    public static JTextArea chatArea;
//
////    ImageIcon circle = new ImageIcon(Manager.class.getResource("/icon/circle.png"));
////    ImageIcon color = new ImageIcon(Manager.class.getResource("/icon/color.png"));
////    ImageIcon line = new ImageIcon(Manager.class.getResource("/icon/line.png"));
////    ImageIcon oval = new ImageIcon(Manager.class.getResource("/icon/oval.png"));
////    ImageIcon pencil = new ImageIcon(Manager.class.getResource("/icon/text.png"));
////    ImageIcon rectangle = new ImageIcon(Manager.class.getResource("/icon/rectangle.png"));
////
////    ImageIcon[] icons = {line, circle, rectangle, oval, pencil};
//
//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    Manager window = new Manager();
//                    window.frame.setVisible(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
////    public Manager(String mName) {
////        initialize(mName);
////    }
//
//    public Manager() {
//        initialize();
//    }
//
//    public int showRequest(String name) {
//        int option = JOptionPane.showConfirmDialog(null, name + "wants to share " +
//                "your white board", "Confirm", JOptionPane.INFORMATION_MESSAGE);
//        return option;
//    }
//
//
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    private void initialize() {
//        frame = new JFrame();
////        frame.setTitle("Distributed Whiteboard (Manager): " + name);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setBounds(100, 100, 1600, 800);
//        createDrawListener = new Listener(frame);
//        JPanel toolPanel = new JPanel();
//        toolPanel.setBounds(0, 0, 80, 450);
//        toolPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
//        JComboBox menu = new JComboBox();
//        menu.setModel(new DefaultComboBoxModel(new String[]{"New", "Save", "Save as", "Open", "Exit"}));
//        menu.addActionListener(e ->{
//            if (menu.getSelectedItem().toString().equals("New")) {
//                canvas.removeAll();
//
//            }
//        });
//    }
//
//
//}
