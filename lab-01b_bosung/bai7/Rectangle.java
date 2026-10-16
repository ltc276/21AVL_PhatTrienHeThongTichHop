package bai7;

public class Rectangle extends Quadrilateral {
    public Rectangle(Point p1, Point p2, Point p3, Point p4) {
        super(p1, p2, p3, p4);
    }

    public Rectangle(double x, double y, double width, double height) {
        super(new Point(x, y), new Point(x + width, y), new Point(x + width, y + height), new Point(x, y + height));
    }
}
