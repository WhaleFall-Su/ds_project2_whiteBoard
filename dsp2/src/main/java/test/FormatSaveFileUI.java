package test;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class FormatSaveFileUI {

    private JFrame frame;

    public static String fileName;

    public static FormatSaveFileUI formatSaveFileUI;
    /**
     * Launch the application.
     */


    /**
     * Create the application.
     */
    public FormatSaveFileUI() {
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

        JTextArea fileNameArea = new JTextArea();
        fileNameArea.setLineWrap(true);
        fileNameArea.setBounds(90, 112, 256, 35);
        frame.getContentPane().add(fileNameArea);

        JButton saveFIleButton = new JButton("Save");
        saveFIleButton.setBounds(152, 177, 117, 51);
        frame.getContentPane().add(saveFIleButton);

        JLabel notifyLabel = new JLabel("enter the file name");
        notifyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        notifyLabel.setBounds(122, 73, 178, 16);
        frame.getContentPane().add(notifyLabel);

        fileName = fileNameArea.getText();

        frame.setVisible(true);
    }

}

