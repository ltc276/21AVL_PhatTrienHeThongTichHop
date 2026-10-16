package bai7;

public class Pentagon implements Polygon {
    private Point[] vertices;

    public Pentagon(Point[] vertices) {
        if (vertices.length != 5) throw new IllegalArgumentException("Ngũ giác phải có 5 đỉnh");
        this.vertices = vertices;
    }

    @Override
    public Point[] getVertices() { return vertices; }

    @Override
    public double perimeter() {
        double sum = 0;
        for (int i = 0; i < 5; i++) {
            sum += vertices[i].distanceTo(vertices[(i + 1) % 5]);
        }
        return sum;
    }

    @Override
    public double area() {
        double area = 0;
        for (int i = 0; i < 5; i++) {
            Point p1 = vertices[i];
            Point p2 = vertices[(i + 1) % 5];
            area += (p1.getX() * p2.getY()) - (p2.getX() * p1.getY());
        }
        return Math.abs(area) / 2.0;
    }
}
