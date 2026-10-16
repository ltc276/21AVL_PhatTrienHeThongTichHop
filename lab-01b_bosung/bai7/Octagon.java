package bai7;

public class Octagon implements Polygon {
    private Point[] vertices;

    public Octagon(Point[] vertices) {
        if (vertices.length != 8) throw new IllegalArgumentException("Bát giác phải có 8 đỉnh");
        this.vertices = vertices;
    }

    @Override
    public Point[] getVertices() { return vertices; }

    @Override
    public double perimeter() {
        double sum = 0;
        for (int i = 0; i < 8; i++) {
            sum += vertices[i].distanceTo(vertices[(i + 1) % 8]);
        }
        return sum;
    }

    @Override
    public double area() {
        double area = 0;
        for (int i = 0; i < 8; i++) {
            Point p1 = vertices[i];
            Point p2 = vertices[(i + 1) % 8];
            area += (p1.getX() * p2.getY()) - (p2.getX() * p1.getY());
        }
        return Math.abs(area) / 2.0;
    }
}
