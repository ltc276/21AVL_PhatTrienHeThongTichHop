public class Main {
public static void main(String[] args) {
    SinhVien sv1 = new SinhVien("Nguyen Van An",2004,"Ha Noi","SV01","Cong nghe thong tin",8.5);
    SinhVien sv2 = new SinhVien("Tran Thi Binh",2003,"Hai Phong","SV02","Ke toan",7.2);
    GiangVien gv1 = new GiangVien("Nguyen Van Minh",1980,"Ha Noi","GV01","Lap trinh Java",5000000,2.5);
    GiangVien gv2 = new GiangVien("Le Thi Hoa",1985,"Da Nang","GV02","Co so du lieu",6000000,2.0);
    System.out.println("======THONG TIN SINH VIEN======");
    sv1.hienThiThongTin();
    System.out.println("Sinh vien " + sv1.getMaSinhVien()+ ": " + sv1.xepLoai());
    System.out.println("\n----------------------------------------");
    sv2.hienThiThongTin();
    System.out.println("Sinh vien " + sv2.getMaSinhVien()+ ": " + sv2.xepLoai());
    System.out.println("\n======THONG TIN GIANG VIEN======");
    gv1.hienThiThongTin();
    System.out.println("\n----------------------------------------");
    gv2.hienThiThongTin();
}
}