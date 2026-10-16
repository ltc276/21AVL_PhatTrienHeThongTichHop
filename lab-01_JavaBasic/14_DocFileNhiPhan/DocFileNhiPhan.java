// Bai 14: Doc lai danh sach sinh vien da ghi bang DataOutputStream o bai 13.
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DocFileNhiPhan {
    public static void docSinhVien(String duongDan) throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream(duongDan));
        int soSinhVien = dis.readInt();
        ArrayList<SinhVien> danhSach = new ArrayList<SinhVien>();

        for (int i = 0; i < soSinhVien; i++) {
            String mssv = dis.readUTF();
            String ten = dis.readUTF();
            int tuoi = dis.readInt();
            int soMonHoc = dis.readInt();
            ArrayList<MonHoc> listMH = new ArrayList<MonHoc>();

            for (int j = 0; j < soMonHoc; j++) {
                String tenMonHoc = dis.readUTF();
                int tinChi = dis.readInt();
                double diem = dis.readDouble();
                listMH.add(new MonHoc(tenMonHoc, tinChi, diem));
            }
            danhSach.add(new SinhVien(mssv, ten, tuoi, listMH));
        }
        dis.close();

        for (SinhVien sv : danhSach) {
            System.out.println(sv);
        }
    }

    public static void main(String[] args) throws IOException {
        // Chay bai 13 truoc de tao file sinhvien.dat.
        docSinhVien("../13_GhiFileNhiPhan/sinhvien.dat");
    }
}
