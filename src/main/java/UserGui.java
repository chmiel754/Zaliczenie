import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserGui {
    public static ArrayList<ExtendedPoint> points = new ArrayList<ExtendedPoint>();
    private final int MIN_FRAME_HEIGHT = 200;
    private int corners = 4;
    private Map<String, String> pointsValue = new HashMap<String, String>() {{
        put("X_POINT_A", "5");
        put("Y_POINT_A", "10");
        put("X_POINT_B", "15");
        put("Y_POINT_B", "35");
        put("X_POINT_C", "35");
        put("Y_POINT_C", "35");
        put("X_POINT_D", "45");
        put("Y_POINT_D", "10");
    }};
    private CartesianFrame cartesianFrame = new CartesianFrame();
    private JButton drawButton = new JButton("Show cartesian");
    private JButton removePointButton = new JButton("Remove point");
    private JButton addPointButton = new JButton("Add point");

    private JFormattedTextField surfaceField;
    private JFormattedTextField perimeterField;

    void createWindow() {
        JFrame frame = new JFrame("Draftsman - menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createUI(frame);
        frame.setSize(700, MIN_FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void createUI(final JFrame frame) {
        JPanel mainPanel = new JPanel();
        JPanel panel = new JPanel();
        JPanel dataPanel = new JPanel();

        panel.setLayout(new GridLayout(corners + 1, 4));
        panel.setBorder(BorderFactory.createTitledBorder("Coordinates"));

        dataPanel.setLayout(new GridLayout(2, 2));
        dataPanel.setBorder(BorderFactory.createTitledBorder("Shape data"));

        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (points.size() != 0) points.clear();
                for (int i = 0; i < corners; i++) {
                    String x = getFieldValueByName(panel, getFieldNameByIndex(i, "X"));
                    String y = getFieldValueByName(panel, getFieldNameByIndex(i, "Y"));

                    if (!x.equals("") && !y.equals("")) {
                        points.add(new ExtendedPoint("" + (char) (65 + i), Integer.parseInt(x), Integer.parseInt(y)));
                        pointsValue.put(getFieldNameByIndex(i, "X"), x);
                        pointsValue.put(getFieldNameByIndex(i, "Y"), y);
                    }
                }
                surfaceField.setValue(PolygonService.polygonArea(points) + " cm2");
                perimeterField.setValue(PolygonService.polygonPerimeter(points) + " cm");

                if (cartesianFrame.isShowing()) {
                    cartesianFrame.repaint();
                } else {
                    cartesianFrame.showUI();
                }
            }
        });

        removePointButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (corners > 4) corners--;
                updateSavedPointsValue(panel, corners - 1);

                resizeWindow(panel, frame);
                initPolygonPointsInputs(panel);
                initButtons(panel);

                frame.revalidate();
                frame.repaint();
            }
        });

        addPointButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSavedPointsValue(panel, corners-1);
                corners++;

                resizeWindow(panel, frame);

                initPolygonPointsInputs(panel);
                initButtons(panel);

                frame.revalidate();
                frame.repaint();
            }
        });

        initPolygonPointsInputs(panel);
        initButtons(panel);

        mainPanel.add(panel);


        initDataPanel(dataPanel);
        mainPanel.add(dataPanel);

        frame.getContentPane().add(mainPanel);

    }

    private void initDataPanel(JPanel dataPanel) {
        surfaceField = new JFormattedTextField();
        surfaceField.setName("surfaceField");
        surfaceField.setColumns(10);
        surfaceField.setEditable(false);
        surfaceField.setValue("0");

        perimeterField = new JFormattedTextField();
        perimeterField.setName("perimeterField");
        perimeterField.setColumns(10);
        perimeterField.setEditable(false);
        perimeterField.setValue("0");

        dataPanel.add(new JLabel("Surface:"));
        dataPanel.add(surfaceField);
        dataPanel.add(new JLabel("Perimeter:"));
        dataPanel.add(perimeterField);
    }


    private void initPolygonPointsInputs(JPanel panel) {
        panel.removeAll();
        NumberFormat inputFormat = NumberFormat.getNumberInstance();
        for (int i = 0; i < corners; i++) {
            JFormattedTextField x_field = new JFormattedTextField(inputFormat);
            x_field.setName(getFieldNameByIndex(i, "X"));
            x_field.setColumns(10);

            if (pointsValue.get(getFieldNameByIndex(i, "X")) != null) {
                x_field.setValue(Integer.parseInt(pointsValue.get(getFieldNameByIndex(i, "X"))));
            } else {
                x_field.setValue(0);
            }

            JFormattedTextField y_field = new JFormattedTextField(inputFormat);
            y_field.setName(getFieldNameByIndex(i, "Y"));
            y_field.setColumns(10);

            if (pointsValue.get(getFieldNameByIndex(i, "Y")) != null) {
                y_field.setValue(Integer.parseInt(pointsValue.get(getFieldNameByIndex(i, "Y"))));
            } else {
                y_field.setValue(0);
            }

            JLabel label = new JLabel("Point " + (char) (65 + i));
            label.setName("label_" + i);

            panel.add(new JLabel("Point " + (char) (65 + i)));
            panel.add(x_field);
            panel.add(y_field);
        }
    }

    private void initButtons(JPanel panel) {
        panel.add(drawButton);
        panel.add(removePointButton);
        panel.add(addPointButton);
        panel.add(addPointButton);
    }

    private String getFieldValueByName(JPanel panel, String name) {
        for (Component field : panel.getComponents()) {
            if (field instanceof JFormattedTextField) {
                if (field.getName().equals(name)) {
                    return ((JFormattedTextField) field).getText();
                }
            }
        }
        return "";
    }

    private String getFieldNameByIndex(int index, String axis) {
        return axis.toUpperCase() + "_POINT_" + (char) (65 + index);
    }

    private void resizeWindow(JPanel panel, JFrame frame) {
        panel.setLayout(new GridLayout(corners + 1, 4));
        frame.setSize(700, MIN_FRAME_HEIGHT + ((corners - 4) * 25));
    }

    private void updateSavedPointsValue(JPanel panel, int index) {
        String x = getFieldValueByName(panel, getFieldNameByIndex(index, "X"));
        String y = getFieldValueByName(panel, getFieldNameByIndex(index, "Y"));

        if (!x.equals("") && !y.equals("")) {
            pointsValue.put(getFieldNameByIndex(index, "X"), x);
            pointsValue.put(getFieldNameByIndex(index, "Y"), y);
        }
    }


}