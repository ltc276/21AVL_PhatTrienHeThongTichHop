package bai1;

public class HTG {
    private double a, b, c;

    public HTG() {
        this.a = 0;
        this.b = 0;
        this.c = 0;
    }

    public HTG(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double getA() { return a; }
    public void setA(double a) { this.a = a; }

    public double getB() { return b; }
    public void setB(double b) { this.b = b; }

    public double getC() { return c; }
    public void setC(double c) { this.c = c; }

    public double tinhChuVi() {
        return a + b + c;
    }

    public double tinhDienTich() {
        double p = tinhChuVi() / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    public void xuatThongTin() {
        System.out.printf("Hình Tam Giác [Cạnh: %.2f, %.2f, %.2f | Chu vi: %.2f, Diện tích: %.2f]%n",
                a, b, c, tinhChuVi(), tinhDienTich());
    }
}
