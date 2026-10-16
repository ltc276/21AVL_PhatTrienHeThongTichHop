// Bai 1: Tao san pham va thu nghiem nhap hang, ban hang theo yeu cau.
public class Main {
    public static void main(String[] args) {
        SanPham sp1 = new SanPham("SP01", "But bi", 5000, 10);
        SanPham sp2 = new SanPham("SP02", "Vo hoc sinh", 12000, 20);

        System.out.println("=== Thong tin san pham ban dau ===");
        sp1.hienThiThongTin();
        System.out.println();
        sp2.hienThiThongTin();

        System.out.println("\n=== Nhap them 5 but bi ===");
        sp1.nhapHang(5);
        sp1.hienThiThongTin();

        System.out.println("\n=== Ban 3 but bi ===");
        sp1.banHang(3);
        sp1.hienThiThongTin();

        System.out.println("\n=== Thu ban 50 but bi khi khong du hang ===");
        sp1.banHang(50);
        sp1.hienThiThongTin();
    }
}
