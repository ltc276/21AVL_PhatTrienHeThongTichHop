// Bai 11: Tim file trong thu muc va cac thu muc con theo phan duoi ten.
// Vi du nhap .txt de tim cac file co ten ket thuc bang .txt.
import java.io.File;
import java.util.Scanner;

public class TimKiemFile {
    public static void timFile(String duongDan, String tuKhoa) {
        File file = new File(duongDan);
        if (!file.exists()) {
            System.out.println("Duong dan khong ton tai.");
            return;
        }

        if (file.isFile()) {
            if (file.getName().endsWith(tuKhoa)) {
                System.out.println(file.getAbsolutePath());
            }
        } else {
            File[] danhSach = file.listFiles();
            if (danhSach != null) {
                for (File f : danhSach) {
                    timFile(f.getAbsolutePath(), tuKhoa);
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap duong dan thu muc can tim: ");
        String thuMuc = sc.nextLine();
        System.out.print("Nhap phan duoi ten file can tim (vi du .txt): ");
        String tuKhoa = sc.nextLine();
        timFile(thuMuc, tuKhoa);
        sc.close();
    }
}
