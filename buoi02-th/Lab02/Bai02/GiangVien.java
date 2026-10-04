public class GiangVien extends Nguoi {

    private String maGiangVien;
    private String chuyenMon;
    private double luongCoBan;
    private double heSoLuong;
    public GiangVien(String hoTen, int namSinh, String diaChi,
                     String maGiangVien, String chuyenMon,
                     double luongCoBan, double heSoLuong) {
        super(hoTen, namSinh, diaChi);
        this.maGiangVien = maGiangVien;
        this.chuyenMon = chuyenMon;
        this.luongCoBan = luongCoBan;
        this.heSoLuong = heSoLuong;
    }
    public String getMaGiangVien() {
        return maGiangVien;
    }

    public String getChuyenMon() {
        return chuyenMon;
    }

    public double getLuongCoBan() {
        return luongCoBan;
    }

    public double getHeSoLuong() {
        return heSoLuong;
    }
    public void setMaGiangVien(String maGiangVien) {
        this.maGiangVien = maGiangVien;
    }

    public void setChuyenMon(String chuyenMon) {
        this.chuyenMon = chuyenMon;
    }

    public void setLuongCoBan(double luongCoBan) {
        this.luongCoBan = luongCoBan;
    }

    public void setHeSoLuong(double heSoLuong) {
        this.heSoLuong = heSoLuong;
    }
    public double tinhLuong() {
        return luongCoBan * heSoLuong;
    }
    @Override
    public void hienThiThongTin() {
        super.hienThiThongTin();

        System.out.println("Ma giang vien: " + maGiangVien);
        System.out.println("Chuyen mon: " + chuyenMon);
        System.out.println("Luong co ban: " + luongCoBan);
        System.out.println("He so luong: " + heSoLuong);
        System.out.println("Luong: " + tinhLuong());
    }
}
