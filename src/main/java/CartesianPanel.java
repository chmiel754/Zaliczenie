import javax.swing.*;
import java.awt.*;

import static java.lang.StrictMath.abs;

public class CartesianPanel extends JPanel {

    private int FONT_SIZE = 8;

    private int X_AXIS_COORDS_COUNT = 50;
    private int Y_AXIS_COORDS_COUNT = 50;

    private int X_AXIS_MARGIN_LEFT = 50;
    private int X_AXIS_SPACE = 700;
    private int X_AXIS_Y_COORD = 750;

    private int Y_AXIS_MARGIN_LEFT = 50;
    private int Y_AXIS_SPACE = 750;
    private int Y_AXIS_X_COORD = 50;
    private int ORIGIN_COORDINATE_LENGHT = 6;
    private int AXIS_STRING_DISTANCE = 20;

    private int FIRST_LENGHT = 10;
    private int SECOND_LENGHT = 5;

    private int xLength = (X_AXIS_SPACE - X_AXIS_MARGIN_LEFT) / X_AXIS_COORDS_COUNT;
    private int yLength = (Y_AXIS_SPACE - Y_AXIS_MARGIN_LEFT) / Y_AXIS_COORDS_COUNT;


    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g.setFont(new Font("TimesRoman", Font.PLAIN, FONT_SIZE));
        g2.scale(CartesianFrame.SCALE_WIDTH_PCT, CartesianFrame.SCALE_HEIGHT_PCT);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        this.drawXYlines(g2);
        this.drawArrows(g2);
        this.drawOriginPoint(g2);
        this.drawXYsigns(g2);
        this.drawVerticalLines(g2, xLength);
        this.drawHorizontalLines(g2, yLength);
        this.numerateAxis(g2);
        this.drawShape(g2);
    }

    void rescale() {
        this.validate();
        this.repaint();
    }

    private void drawXYlines(Graphics2D g2){
        g2.drawLine(X_AXIS_MARGIN_LEFT, X_AXIS_Y_COORD,
                X_AXIS_SPACE, X_AXIS_Y_COORD);
        g2.drawLine(Y_AXIS_X_COORD, Y_AXIS_MARGIN_LEFT,
                Y_AXIS_X_COORD, Y_AXIS_SPACE);
    }

    private void drawArrows(Graphics2D g2) {
        g2.drawLine(Y_AXIS_X_COORD - SECOND_LENGHT,
                Y_AXIS_MARGIN_LEFT + FIRST_LENGHT,
                Y_AXIS_X_COORD, Y_AXIS_MARGIN_LEFT);
        g2.drawLine(Y_AXIS_X_COORD + SECOND_LENGHT,
                Y_AXIS_MARGIN_LEFT + FIRST_LENGHT,
                Y_AXIS_X_COORD, Y_AXIS_MARGIN_LEFT);
    }


    private void drawShape(Graphics2D g2) {
        Polygon polygon = new Polygon();
        for (ExtendedPoint point : UserGui.points) {
            polygon.addPoint(getPointXToPixelValue(point), getPointYToPixelValue(point));
            g2.drawString(point.name, getPointXToPixelValue(point) + ORIGIN_COORDINATE_LENGHT, getPointYToPixelValue(point) + ORIGIN_COORDINATE_LENGHT);
        }
        g2.drawPolygon(polygon);
    }

    private void drawOriginPoint(Graphics2D g2) {
        int ORIGIN_COORDINATE_LENGHT = 6;
        g2.fillOval(
                X_AXIS_MARGIN_LEFT - (ORIGIN_COORDINATE_LENGHT / 2),
                Y_AXIS_SPACE - (ORIGIN_COORDINATE_LENGHT / 2),
                ORIGIN_COORDINATE_LENGHT, ORIGIN_COORDINATE_LENGHT);
    }

    private void drawXYsigns(Graphics2D g2) {
        g2.drawString("X", X_AXIS_SPACE - AXIS_STRING_DISTANCE / 2,
                X_AXIS_Y_COORD + AXIS_STRING_DISTANCE);
        g2.drawString("Y", Y_AXIS_X_COORD - AXIS_STRING_DISTANCE,
                Y_AXIS_MARGIN_LEFT + AXIS_STRING_DISTANCE / 2);
        g2.drawString("(0, 0)", X_AXIS_MARGIN_LEFT - AXIS_STRING_DISTANCE,
                Y_AXIS_SPACE + AXIS_STRING_DISTANCE);
    }

    private void numerateAxis(Graphics2D g2) {
        for (int i = 1; i < X_AXIS_COORDS_COUNT; i++) {
            g2.drawLine(X_AXIS_MARGIN_LEFT + (i * xLength),
                    X_AXIS_Y_COORD - SECOND_LENGHT,
                    X_AXIS_MARGIN_LEFT + (i * xLength),
                    X_AXIS_Y_COORD + SECOND_LENGHT);
            g2.drawString(Integer.toString(i),
                    X_AXIS_MARGIN_LEFT + (i * xLength) - 3,
                    X_AXIS_Y_COORD + AXIS_STRING_DISTANCE);
        }

        //draw y-axis numbers
        for (int i = 1; i < Y_AXIS_COORDS_COUNT; i++) {
            g2.drawLine(Y_AXIS_X_COORD - SECOND_LENGHT,
                    Y_AXIS_SPACE - (i * yLength),
                    Y_AXIS_X_COORD + SECOND_LENGHT,
                    Y_AXIS_SPACE - (i * yLength));
            g2.drawString(Integer.toString(i),
                    Y_AXIS_X_COORD - AXIS_STRING_DISTANCE,
                    Y_AXIS_SPACE - (i * yLength));
        }
    }

    private void drawVerticalLines(Graphics2D g2, int xLength) {
        g2.setColor(new Color(177, 180, 181));
        for (int i = 1; i < X_AXIS_COORDS_COUNT; i++) {
            g2.drawLine(X_AXIS_MARGIN_LEFT + (i * xLength), X_AXIS_Y_COORD, X_AXIS_MARGIN_LEFT + (i * xLength), X_AXIS_Y_COORD - X_AXIS_SPACE);
        }
        g2.setColor(Color.BLACK);
    }

    private void drawHorizontalLines(Graphics2D g2, int yLength) {
        g2.setColor(new Color(177, 180, 181));
        for (int i = 1; i < Y_AXIS_COORDS_COUNT; i++) {
            g2.drawLine(Y_AXIS_X_COORD, X_AXIS_Y_COORD - (i * yLength), X_AXIS_SPACE, X_AXIS_Y_COORD - (i * yLength));
        }
        g2.setColor(Color.BLACK);
    }

    private int getPointXToPixelValue(Point point) {
        return abs(X_AXIS_MARGIN_LEFT + (point.x * ((X_AXIS_SPACE - X_AXIS_MARGIN_LEFT) / X_AXIS_COORDS_COUNT)));
    }

    private int getPointYToPixelValue(Point point) {
        return abs(X_AXIS_Y_COORD - (point.y * ((Y_AXIS_SPACE - Y_AXIS_MARGIN_LEFT) / Y_AXIS_COORDS_COUNT)));
    }
}