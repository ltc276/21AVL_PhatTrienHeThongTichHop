// Lop SinhVien: chua thong tin sinh vien va danh sach mon hoc doc tu file.
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

    public String toString() {
        String ketQua = "MSSV: " + mssv + ", Ten: " + ten + ", Tuoi: " + tuoi;
        for (MonHoc mh : listMH) {
            ketQua += "\n  Mon hoc: " + mh.tenMonHoc + ", Tin chi: " + mh.tinChi
                    + ", Diem: " + mh.diem;
        }
        return ketQua;
    }
}
