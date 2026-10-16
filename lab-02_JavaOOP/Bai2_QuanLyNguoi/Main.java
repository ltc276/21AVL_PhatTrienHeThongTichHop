// Bai 2: Tao hai sinh vien, hai giang vien va hien thi thong tin cua tung nguoi.
public class Main {
    public static void main(String[] args) {
        SinhVien sv1 = new SinhVien("Nguyen Van An", 2004, "TPHCM", "SV001",
                "Cong nghe thong tin", 8.7);
        SinhVien sv2 = new SinhVien("Tran Thi Binh", 2003, "Dong Nai", "SV002",
                "Ke toan", 6.5);

        GiangVien gv1 = new GiangVien("Le Van Cuong", 1980, "TPHCM", "GV001",
                "Lap trinh Java", 10000000, 2.5);
        GiangVien gv2 = new GiangVien("Pham Thi Hoa", 1985, "Binh Duong", "GV002",
                "Co so du lieu", 9000000, 2.2);

        System.out.println("=== Sinh vien 1 ===");
        sv1.hienThiThongTin();
        System.out.println("Xep loai: " + sv1.xepLoai());

        System.out.println("\n=== Sinh vien 2 ===");
        sv2.hienThiThongTin();
        System.out.println("Xep loai: " + sv2.xepLoai());

        System.out.println("\n=== Giang vien 1 ===");
        gv1.hienThiThongTin();

        System.out.println("\n=== Giang vien 2 ===");
        gv2.hienThiThongTin();
    }
}
