// Bai 10: Xoa file va thu muc con bang de quy, sau do xoa thu muc goc.
import java.io.File;
import java.util.Scanner;

public class XoaThuMucDeQuy {
    public static boolean xoaThuMuc(String duongDan) {
        File thuMuc = new File(duongDan);
        if (!thuMuc.exists() || !thuMuc.isDirectory()) {
            System.out.println("Thu muc khong ton tai.");
            return false;
        }

        File[] danhSach = thuMuc.listFiles();
        if (danhSach != null) {
            for (File file : danhSach) {
                if (file.isDirectory()) {
                    xoaThuMuc(file.getAbsolutePath());
                } else {
                    file.delete();
                }
            }
        }
        return thuMuc.delete();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap duong dan thu muc can xoa hoan toan: ");
        String duongDan = sc.nextLine();

        if (xoaThuMuc(duongDan)) {
            System.out.println("Xoa thu muc thanh cong.");
        } else {
            System.out.println("Khong xoa duoc thu muc.");
        }
        sc.close();
    }
}
