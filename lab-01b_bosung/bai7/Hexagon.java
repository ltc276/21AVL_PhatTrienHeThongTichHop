package bai7;

public class Hexagon implements Polygon {
    private Point[] vertices;

    public Hexagon(Point[] vertices) {
        if (vertices.length != 6) throw new IllegalArgumentException("Lục giác phải có 6 đỉnh");
        this.vertices = vertices;
    }

    @Override
    public Point[] getVertices() { return vertices; }

    @Override
    public double perimeter() {
        double sum = 0;
        for (int i = 0; i < 6; i++) {
            sum += vertices[i].distanceTo(vertices[(i + 1) % 6]);
        }
        return sum;
    }

    @Override
    public double area() {
        double area = 0;
        for (int i = 0; i < 6; i++) {
            Point p1 = vertices[i];
            Point p2 = vertices[(i + 1) % 6];
            area += (p1.getX() * p2.getY()) - (p2.getX() * p1.getY());
        }
        return Math.abs(area) / 2.0;
    }
}
