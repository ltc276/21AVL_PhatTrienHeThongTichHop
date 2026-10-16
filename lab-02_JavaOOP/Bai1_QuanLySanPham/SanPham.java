// Bai 1: Lop SanPham quan ly thong tin san pham va so luong ton kho.
public class SanPham {
    private String maSanPham;
    private String tenSanPham;
    private double donGia;
    private int soLuongTonKho;

    public SanPham(String maSanPham, String tenSanPham, double donGia, int soLuongTonKho) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.donGia = donGia;
        this.soLuongTonKho = soLuongTonKho;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public double getDonGia() {
        return donGia;
    }

    public int getSoLuongTonKho() {
        return soLuongTonKho;
    }

    public double tinhThanhTien() {
        return donGia * soLuongTonKho;
    }

    public void nhapHang(int soLuongNhap) {
        if (soLuongNhap > 0) {
            soLuongTonKho = soLuongTonKho + soLuongNhap;
            System.out.println("Nhap hang thanh cong.");
        } else {
            System.out.println("So luong nhap phai lon hon 0.");
        }
    }

    public boolean banHang(int soLuongBan) {
        if (soLuongBan <= 0) {
            System.out.println("So luong ban phai lon hon 0.");
            return false;
        }

        if (soLuongBan > soLuongTonKho) {
            System.out.println("Khong du hang trong kho.");
            return false;
        }

        soLuongTonKho = soLuongTonKho - soLuongBan;
        System.out.println("Ban hang thanh cong.");
        return true;
    }

    public void hienThiThongTin() {
        System.out.println("Ma san pham: " + maSanPham);
        System.out.println("Ten san pham: " + tenSanPham);
        System.out.println("Don gia: " + donGia);
        System.out.println("So luong ton kho: " + soLuongTonKho);
        System.out.println("Thanh tien: " + tinhThanhTien());
    }
}
