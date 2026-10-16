// Bai 13: Ghi danh sach sinh vien va cac mon hoc vao file nhi phan sinhvien.dat.
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class GhiFileNhiPhan {
    public static void ghiSinhVien(String duongDan, ArrayList<SinhVien> danhSach) throws IOException {
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(duongDan));
        dos.writeInt(danhSach.size());

        for (SinhVien sv : danhSach) {
            dos.writeUTF(sv.mssv);
            dos.writeUTF(sv.ten);
            dos.writeInt(sv.tuoi);
            dos.writeInt(sv.listMH.size());

            for (MonHoc mh : sv.listMH) {
                dos.writeUTF(mh.tenMonHoc);
                dos.writeInt(mh.tinChi);
                dos.writeDouble(mh.diem);
            }
        }
        dos.close();
        System.out.println("Da ghi file sinhvien.dat thanh cong.");
    }

    public static void main(String[] args) throws IOException {
        ArrayList<MonHoc> monHoc = new ArrayList<MonHoc>();
        monHoc.add(new MonHoc("ltcb", 3, 6.7));
        monHoc.add(new MonHoc("ltw", 3, 7.5));
        monHoc.add(new MonHoc("tkhdt", 3, 8.0));

        ArrayList<SinhVien> danhSach = new ArrayList<SinhVien>();
        danhSach.add(new SinhVien("25000901", "Le Tan Cuong", 20, monHoc));
        danhSach.add(new SinhVien("25000902", "Nguyen Van A", 20, monHoc));

        ghiSinhVien("sinhvien.dat", danhSach);
    }
}
