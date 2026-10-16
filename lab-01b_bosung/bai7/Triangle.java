package bai7;

public class Triangle implements Polygon {
    protected Point[] vertices;

    public Triangle(Point p1, Point p2, Point p3) {
        this.vertices = new Point[]{p1, p2, p3};
    }

    @Override
    public Point[] getVertices() { return vertices; }

    @Override
    public double perimeter() {
        double a = vertices[0].distanceTo(vertices[1]);
        double b = vertices[1].distanceTo(vertices[2]);
        double c = vertices[2].distanceTo(vertices[0]);
        return a + b + c;
    }

    @Override
    public double area() {
        double a = vertices[0].distanceTo(vertices[1]);
        double b = vertices[1].distanceTo(vertices[2]);
        double c = vertices[2].distanceTo(vertices[0]);
        double p = (a + b + c) / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }
}
