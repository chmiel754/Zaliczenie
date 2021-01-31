import java.awt.*;
import java.util.ArrayList;


public class PolygonService {


    // https://en.wikipedia.org/wiki/Shoelace_formula
    // Shoelace formula
    public static double polygonArea(ArrayList<ExtendedPoint> points) {
        double area = 0.0;
        int j = points.size() - 1;
        for (int i = 0; i < points.size(); i++) {
            area += (points.get(j).x + points.get(i).x) * (points.get(j).y - points.get(i).y);
            j = i;
        }
        return Math.abs(area / 2.0);
    }

    public static double polygonPerimeter(ArrayList<ExtendedPoint> points) {
        int len = points.size();
        double distance = 0;

        for (int i = len - 1, j = 0; j < len; i = j, j++) {
            distance += points.get(i).distance(points.get(j));
        }
        return Math.round(distance);
    }
}
