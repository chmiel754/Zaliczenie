import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class CartesianFrame extends JFrame {

    public static int UI_WIDTH = 800;
    public static int UI_HEIGHT = 850;

    public static double SCALE_WIDTH_PCT = 1;
    public static double SCALE_HEIGHT_PCT = 1;

    private CartesianPanel panel;

    CartesianFrame() {
        panel = new CartesianPanel();
        add(panel);

        panel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                Component c = (Component) evt.getSource();

                SCALE_HEIGHT_PCT = (double) ((c.getBounds().height * 100) / UI_HEIGHT) / 100;
                SCALE_WIDTH_PCT = (double) ((c.getBounds().width * 100) / UI_WIDTH) / 100;

                panel.rescale();
            }
        });
    }


    void showUI() {
        setTitle("Draftsman - Cartesian coordinate system");
        setSize(UI_WIDTH, UI_HEIGHT);
        setVisible(true);

    }
}

