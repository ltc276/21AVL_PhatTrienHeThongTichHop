import java.util.Scanner;

public class SanPham {
    String maSP;
    String tenSP;
    double donGia;
    int soLuongKho;

    public SanPham(String maSP, String tenSP, double donGia, int soLuongKho) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.donGia = donGia;
        this.soLuongKho = soLuongKho;
    }

    public double tinhThanhTien() {
        return donGia * soLuongKho;
    }

    void hienThiThongTin() {
        System.out.println("Ma SP: " + maSP + " - Ten SP: " + tenSP);
        System.out.println("Don gia: " + donGia);
        System.out.println("So luong: " + soLuongKho);
        System.out.println("Thanh tien: " + tinhThanhTien());
    }

    public void nhapHang(int soLuongNhap) {
        if (soLuongNhap <= 0) {
            System.out.println("So luong nhap vao phai > 0 !");
            return;
        }

        soLuongKho += soLuongNhap;
        System.out.println("Nhap kho thanh cong !");
        System.out.println("So luong kho hien tai la " + soLuongKho);
    }

    public boolean banHang(int soLuongBan) {
        if (soLuongBan <= 0) {
            System.out.println("So luong hang ban phai > 0 !");
            return false;
        } else if (soLuongBan > soLuongKho) {
            System.out.println("Thieu hang !");
            return false;
        } else {
            soLuongKho = soLuongKho - soLuongBan;
            System.out.println("Ban hang thanh cong !, So luong kho con lai: " + soLuongKho);
            return true;
        }
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        SanPham sp1 = new SanPham("SP01", "Ban phim", 350000, 2);
        SanPham sp2 = new SanPham("SP02", "Chuot", 150000, 3);

        System.out.print("Nhap so luong hang can nhap kho SP1>");
        int soLuongNhap = s.nextInt();
        sp1.nhapHang(soLuongNhap);

        System.out.print("Nhap so luong hang can nhap kho SP2>");
        soLuongNhap = s.nextInt();
        sp2.nhapHang(soLuongNhap);

        sp1.hienThiThongTin();
        sp2.hienThiThongTin();

        System.out.print("Nhap so luong hang can ban SP1>");
        int soLuongBan = s.nextInt();
        sp1.banHang(soLuongBan);
        System.out.print("Nhap so luong hang can ban SP2>");
        soLuongBan = s.nextInt();
        sp2.banHang(soLuongBan);

        System.out.println("======Thong tin san pham sau khi ban hang======");
        sp1.hienThiThongTin();
        sp2.hienThiThongTin();

        s.close();
    }
}
