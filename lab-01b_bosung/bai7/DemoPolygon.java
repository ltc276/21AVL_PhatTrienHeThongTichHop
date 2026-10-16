package bai7;

import java.util.Scanner;

public class DemoPolygon {

    public static boolean arePolygonsEqual(Polygon p1, Polygon p2) {
        Point[] v1 = p1.getVertices();
        Point[] v2 = p2.getVertices();

        if (v1.length != v2.length) return false;

        for (int i = 0; i < v1.length; i++) {
            if (!v1[i].equals(v2[i])) return false;
        }
        return true;
    }

    public static Point[] inputVertices(Scanner sc, int num) {
        Point[] pts = new Point[num];
        for (int i = 0; i < num; i++) {
            System.out.printf("Nhập tọa độ đỉnh %d (x y): ", i + 1);
            double x = sc.nextDouble();
            double y = sc.nextDouble();
            pts[i] = new Point(x, y);
        }
        return pts;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== DEMO BÀI 7: TẠO VÀ SO SÁNH ĐA GIÁC ===");

        System.out.println("\n--- TẠO ĐA GIÁC 1 ---");
        System.out.print("Nhập số đỉnh của đa giác 1 (3: Tam giác, 4: Tứ giác, 5: Ngũ giác, 6: Lục giác, 8: Bát giác): ");
        int n1 = sc.nextInt();
        Point[] pts1 = inputVertices(sc, n1);
        Polygon poly1 = createPolygon(n1, pts1);

        System.out.println("\n--- TẠO ĐA GIÁC 2 ---");
        System.out.print("Nhập số đỉnh của đa giác 2: ");
        int n2 = sc.nextInt();
        Point[] pts2 = inputVertices(sc, n2);
        Polygon poly2 = createPolygon(n2, pts2);

        System.out.println("\n================ KẾT QUẢ ================");
        if (poly1 != null) {
            System.out.printf("Đa giác 1 -> Chu vi: %.2f | Diện tích: %.2f%n", poly1.perimeter(), poly1.area());
        }
        if (poly2 != null) {
            System.out.printf("Đa giác 2 -> Chu vi: %.2f | Diện tích: %.2f%n", poly2.perimeter(), poly2.area());
        }

        if (poly1 != null && poly2 != null) {
            boolean equal = arePolygonsEqual(poly1, poly2);
            System.out.println("\nKiểm tra 2 đa giác có giống nhau (cùng tọa độ đỉnh): " + (equal ? "ĐÓNG KHỚP/GIỐNG NHAU" : "KHÁC NHAU"));
        }

        sc.close();
    }

    private static Polygon createPolygon(int num, Point[] pts) {
        switch (num) {
            case 3: return new Triangle(pts[0], pts[1], pts[2]);
            case 4: return new Quadrilateral(pts[0], pts[1], pts[2], pts[3]);
            case 5: return new Pentagon(pts);
            case 6: return new Hexagon(pts);
            case 8: return new Octagon(pts);
            default:
                System.out.println("Loại đa giác chưa được hỗ trợ!");
                return null;
        }
    }
}
