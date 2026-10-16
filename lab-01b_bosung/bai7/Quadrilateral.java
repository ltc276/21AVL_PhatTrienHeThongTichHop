package bai7;

public class Quadrilateral implements Polygon {
    protected Point[] vertices;

    public Quadrilateral(Point p1, Point p2, Point p3, Point p4) {
        this.vertices = new Point[]{p1, p2, p3, p4};
    }

    @Override
    public Point[] getVertices() { return vertices; }

    @Override
    public double perimeter() {
        double sum = 0;
        for (int i = 0; i < 4; i++) {
            sum += vertices[i].distanceTo(vertices[(i + 1) % 4]);
        }
        return sum;
    }

    @Override
    public double area() {
        double area = 0;
        for (int i = 0; i < 4; i++) {
            Point p1 = vertices[i];
            Point p2 = vertices[(i + 1) % 4];
            area += (p1.getX() * p2.getY()) - (p2.getX() * p1.getY());
        }
        return Math.abs(area) / 2.0;
    }
}
