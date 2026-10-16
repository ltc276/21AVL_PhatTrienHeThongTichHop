// Lop SinhVien: luu ma so, ten, tuoi va danh sach mon hoc cua sinh vien.
import java.util.ArrayList;

public class SinhVien {
    String mssv;
    String ten;
    int tuoi;
    ArrayList<MonHoc> listMH;

    public SinhVien(String mssv, String ten, int tuoi, ArrayList<MonHoc> listMH) {
        this.mssv = mssv;
        this.ten = ten;
        this.tuoi = tuoi;
        this.listMH = listMH;
    }
}
