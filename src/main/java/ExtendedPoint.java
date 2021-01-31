import java.awt.*;

public class ExtendedPoint extends Point {
    public String name;

    public ExtendedPoint(String name, int x, int y) {
        super(x, y);
        this.name = name;
    }

    @Override
    public String toString() {
        String var10000 = this.getClass().getName();
        return var10000 + "[PointName=" + this.name + ",x=" + this.x + ",y=" + this.y + "]";
    }
}
