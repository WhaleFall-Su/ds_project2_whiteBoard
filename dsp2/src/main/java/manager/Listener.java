package manager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Listener implements ActionListener, MouseListener, MouseMotionListener {
    JFrame frame;
    Graphics2D graphics2D;
    int thickness = 1;

    String type = "line";
    static Color color = Color.BLACK;

    int fromX, fromY, toX, toY;
    String rgb = "0 0 0";
    String record;
    ArrayList<String> recordList = new ArrayList<String>();


    public Listener(JFrame frame) {
        this.frame = frame;
    }

    public void setThickness(int size) {
        this.thickness = size;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //调色盘
        switch (e.getActionCommand()) {
            case "color":
                final JFrame jf = new JFrame("color panel");
                jf.setSize(340, 340);
                jf.setLocationRelativeTo(null);
                jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                Color curColor = JColorChooser.showDialog(jf, "Select a color", color);
                if (curColor != null) {
                    color = curColor;
                }
                break;
            case "line":
                this.type = "line";
                Cursor lineCur = new Cursor(Cursor.CROSSHAIR_CURSOR);
                frame.setCursor(lineCur);
                break;
            case "circle":
                this.type = "circle";
                Cursor circleCur = new Cursor(Cursor.CROSSHAIR_CURSOR);
                frame.setCursor(circleCur);
                break;
            case "oval":
                this.type = "oval";
                Cursor ovalCur = new Cursor(Cursor.CROSSHAIR_CURSOR);
                frame.setCursor(ovalCur);
                break;
            case "rect":
                this.type = "rect";
                Cursor rectCur = new Cursor(Cursor.CROSSHAIR_CURSOR);
                frame.setCursor(rectCur);
                break;
            case "A":
                this.type = "A";
                Cursor textCur = new Cursor(Cursor.TEXT_CURSOR);
                frame.setCursor(textCur);
                break;
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        fromX = e.getX();
        fromY = e.getY();
        if (!graphics2D.getColor().equals(color)) {
            graphics2D.setColor(color);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        toX = e.getX();
        toY = e.getY();
        switch (type) {
            case "line":
                // 网络间传输用
                rgb = colorToString(color);

                //画笔粗细
                graphics2D.setStroke(new BasicStroke(thickness));

                graphics2D.drawLine(fromX, fromY, toX, toY);
                record = "line " + this.thickness + " " + rgb + " " + fromX + " " + fromY + " " + toX + " " + toY + " |";
                recordList.add(record);
                break;
            case "oval":
                rgb = colorToString(color);
                graphics2D.setStroke(new BasicStroke(thickness));
                int width = Math.abs(toX - fromX);
                int height = Math.abs(toY - fromY);
                graphics2D.drawOval(fromX - width, fromY - height, 2 * width, 2 * height);
                record = "oval " + this.thickness + " " + rgb + " " + fromX + " " + fromY + " " + toX + " " + toY + " |";
                recordList.add(record);
                break;
            case "circle":
                rgb = colorToString(color);
                graphics2D.setStroke(new BasicStroke(thickness));
                int diameter = Math.min(Math.abs(fromX - toX), Math.abs(fromY - toY));
                graphics2D.drawOval(Math.min(fromX,toX),Math.min(fromY,toY),diameter,diameter);
                record = "circle " + thickness + " " + rgb + " " + fromX + " " + fromY + " " + toX + " " + toY + " |";
                recordList.add(record);
                break;
            case "rect":
                rgb = colorToString(color);
                graphics2D.setStroke(new BasicStroke(thickness));
//                g.drawRect(Math.min(x2,x3),Math.min(y2,y3),Math.abs(x3-x2),Math.abs(y3-y2))
                graphics2D.drawRect(Math.min(fromX, toX), Math.min(fromY, toY), Math.abs(fromX - toX), Math.abs(fromY - toY));
                record = "rect " + thickness + " " + rgb + " " + fromX + " " + fromY + " " + toX + " " + toY + " |";
                recordList.add(record);
                break;
            case "A":
                String text = JOptionPane.showInputDialog("Please enter text");
                if (text != null) {
                    Font f = new Font(null, Font.PLAIN, this.thickness + 10);
                    graphics2D.setFont(f);
                    graphics2D.drawString(text, toX, toY);
                    rgb = colorToString(color);
                    record = "Text " + thickness + " " + rgb + " " + toX + " " + toY + text + " |";
                    recordList.add(record);
                }
        }
        try {
            JsonArray jsonArrayMem = new JsonArray();
            final JsonPrimitive jsonDraw = new JsonPrimitive(record);
            jsonArrayMem.add(jsonDraw);

            HashMap map = new Gson().fromJson("{\"feedback\":\"draw\"," +
                    "\"historyDraw\":" + jsonArrayMem + "}", HashMap.class);
            String jsonCommand = new Gson().toJson(map);
            System.out.println("paint " + jsonCommand);
            ConnectionMethods.sendToAllUser(jsonCommand);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        /*try {
            JsonArray jsonArrayMem = new JsonArray();
            final JsonPrimitive jsonDraw = new JsonPrimitive(record);
            jsonArrayMem.add(jsonDraw);

            HashMap map = new Gson().fromJson("{\"feedback\":\"draw\"," +
                    "\"historyDraw\":" + jsonArrayMem + "}", HashMap.class);
            String jsonCommand = new Gson().toJson(map);

            ConnectionMethods.sendToAllUser(jsonCommand);
        } catch (Exception exception) {
            exception.printStackTrace();
        }*/
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public void setGraphics2D(Graphics graphics) {
        this.graphics2D = (Graphics2D) graphics;
    }

    public ArrayList<String> getRecordList() {
        return recordList;
    }

    private String colorToString(Color color) {
        String str = color.getRed() + " " + color.getGreen() + " " + color.getBlue();
        return str;
    }

    public void updateRecord(String line) {
        recordList.add(line);
    }

    public void cleanHistoryRecord() {
        recordList.clear();
    }

}
