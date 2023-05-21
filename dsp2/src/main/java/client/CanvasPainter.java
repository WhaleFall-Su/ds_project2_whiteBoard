package client;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CanvasPainter extends JPanel {
    private ArrayList<String> recordList = new ArrayList<String>();

    public void setList(ArrayList<String> recordList) {
        this.recordList = recordList;
    }

    public void paint(Graphics graphics) {
        super.paint(graphics);
        draw((Graphics2D) graphics, this.recordList);
    }

    public void draw(Graphics2D graphics2D, ArrayList<String> recordList) {
        try {
            String[] recordArr = recordList.toArray(new String[0]);
            for (String line : recordArr) {
                String[] record = line.split(" ");
                int fromX, fromY, toX, toY, thickness, red, green, blue;
                Color color;
                if (record[1].equals("|")) {
                    continue;
                }
                System.out.println(line);
                System.out.println(record[0]);
                switch (record[0]) {
                    case "line":
                        System.out.println("get");
                        thickness = Integer.parseInt(record[1]);
                        graphics2D.setStroke(new BasicStroke(thickness));
                        red = Integer.parseInt(record[2]);
                        green = Integer.parseInt(record[3]);
                        blue = Integer.parseInt(record[4]);
                        color = new Color(red, green, blue);
                        graphics2D.setColor(color);
                        fromX = Integer.parseInt(record[5]);
                        fromY = Integer.parseInt(record[6]);
                        toX = Integer.parseInt(record[7]);
                        toY = Integer.parseInt(record[8]);
                        graphics2D.drawLine(fromX, fromY, toX, toY);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeAll() {

    }
}

