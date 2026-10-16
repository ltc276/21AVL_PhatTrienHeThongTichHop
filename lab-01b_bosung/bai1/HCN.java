package bai1;

public class HCN {
    private double dai;
    private double rong;

    public HCN() {
        this.dai = 0;
        this.rong = 0;
    }

    public HCN(double dai, double rong) {
        this.dai = dai;
        this.rong = rong;
    }

    public double getDai() { return dai; }
    public void setDai(double dai) { this.dai = dai; }

    public double getRong() { return rong; }
    public void setRong(double rong) { this.rong = rong; }

    public double tinhChuVi() {
        return 2 * (dai + rong);
    }

    public double tinhDienTich() {
        return dai * rong;
    }

    public void xuatThongTin() {
        System.out.printf("Hình Chữ Nhật [Dài: %.2f, Rộng: %.2f | Chu vi: %.2f, Diện tích: %.2f]%n",
                dai, rong, tinhChuVi(), tinhDienTich());
    }
}
