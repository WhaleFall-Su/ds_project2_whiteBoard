package manager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CanvasPainter extends JPanel implements ManagerConsts {
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
            String[] recordArr = recordList.toArray(new String[recordList.size()]);
            for (String line : recordArr) {
                String[] record = line.split(" ");
                int fromX, fromY, toX, toY, thickness, red, green, blue, diameter, width, height;
                String text;
                Color color;
                if (record[1].equals("|")) {
                    continue;
                }
                System.out.println(line);
                System.out.println(record[0]);
                switch (record[0]) {
                    case "line":
                        System.out.println("get");
                        thickness = Integer.parseInt(record[THICKNESS_INDEX]);
                        graphics2D.setStroke(new BasicStroke(thickness));
                        red = Integer.parseInt(record[RED_INDEX]);
                        green = Integer.parseInt(record[GREEN_INDEX]);
                        blue = Integer.parseInt(record[BLUE_INDEX]);
                        color = new Color(red, green, blue);
                        graphics2D.setColor(color);
                        fromX = Integer.parseInt(record[FROMX_INDEX]);
                        fromY = Integer.parseInt(record[FROMY_INDEX]);
                        toX = Integer.parseInt(record[TOX_INDEX]);
                        toY = Integer.parseInt(record[TOY_INDEX]);
                        graphics2D.drawLine(fromX, fromY, toX, toY);
                        break;
                    case "circle":
                        thickness = Integer.parseInt(record[THICKNESS_INDEX]);
                        graphics2D.setStroke(new BasicStroke(thickness));
                        red = Integer.parseInt(record[RED_INDEX]);
                        green = Integer.parseInt(record[GREEN_INDEX]);
                        blue = Integer.parseInt(record[BLUE_INDEX]);
                        color = new Color(red, green, blue);
                        graphics2D.setColor(color);
                        fromX = Integer.parseInt(record[FROMX_INDEX]);
                        fromY = Integer.parseInt(record[FROMY_INDEX]);
                        toX = Integer.parseInt(record[TOX_INDEX]);
                        toY = Integer.parseInt(record[TOY_INDEX]);
                        diameter = Integer.parseInt(record[DIAMETER_INDEX]);
                        graphics2D.drawOval(Math.min(fromX,toX),Math.min(fromY,toY),diameter,diameter);
                        break;
                    case "oval":
                        thickness = Integer.parseInt(record[THICKNESS_INDEX]);
                        graphics2D.setStroke(new BasicStroke(thickness));
                        red = Integer.parseInt(record[RED_INDEX]);
                        green = Integer.parseInt(record[GREEN_INDEX]);
                        blue = Integer.parseInt(record[BLUE_INDEX]);
                        color = new Color(red, green, blue);
                        graphics2D.setColor(color);
                        fromX = Integer.parseInt(record[FROMX_INDEX]);
                        fromY = Integer.parseInt(record[FROMY_INDEX]);
                        toX = Integer.parseInt(record[TOX_INDEX]);
                        toY = Integer.parseInt(record[TOY_INDEX]);
                        width = Integer.parseInt(record[OVAL_WIDTH_INDEX]);
                        height = Integer.parseInt(record[OVAL_HEIGHT_INDEX]);
                        graphics2D.drawOval(fromX - width, fromY - height, 2 * width, 2 * height);
                        break;
                    case "rect":
                        thickness = Integer.parseInt(record[THICKNESS_INDEX]);
                        graphics2D.setStroke(new BasicStroke(thickness));
                        red = Integer.parseInt(record[RED_INDEX]);
                        green = Integer.parseInt(record[GREEN_INDEX]);
                        blue = Integer.parseInt(record[BLUE_INDEX]);
                        color = new Color(red, green, blue);
                        graphics2D.setColor(color);
                        fromX = Integer.parseInt(record[FROMX_INDEX]);
                        fromY = Integer.parseInt(record[FROMY_INDEX]);
                        toX = Integer.parseInt(record[TOX_INDEX]);
                        toY = Integer.parseInt(record[TOY_INDEX]);
                        graphics2D.drawRect(Math.min(fromX, toX), Math.min(fromY, toY), Math.abs(fromX - toX), Math.abs(fromY - toY));
                        break;
                    case "A":
                        thickness = Integer.parseInt(record[THICKNESS_INDEX]);
                        graphics2D.setStroke(new BasicStroke(thickness));
                        red = Integer.parseInt(record[RED_INDEX]);
                        green = Integer.parseInt(record[GREEN_INDEX]);
                        blue = Integer.parseInt(record[BLUE_INDEX]);
                        color = new Color(red, green, blue);
                        graphics2D.setColor(color);
                        fromX = Integer.parseInt(record[FROMX_INDEX]);
                        fromY = Integer.parseInt(record[FROMY_INDEX]);
                        text = record[TEXT_CONTENT_INDEX];
                        Font font = new Font(null, Font.PLAIN, thickness + 10);
                        graphics2D.setFont(font);
                        graphics2D.drawString(text, fromX, fromY);
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
