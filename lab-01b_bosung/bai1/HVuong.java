package bai1;

public class HVuong extends HCN {

    public HVuong() {
        super();
    }

    public HVuong(double canh) {
        super(canh, canh);
    }

    public double getCanh() {
        return getDai();
    }

    public void setCanh(double canh) {
        setDai(canh);
        setRong(canh);
    }

    @Override
    public void xuatThongTin() {
        System.out.printf("Hình Vuông [Cạnh: %.2f | Chu vi: %.2f, Diện tích: %.2f]%n",
                getCanh(), tinhChuVi(), tinhDienTich());
    }
}
