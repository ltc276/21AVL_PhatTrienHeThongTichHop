// Bai 9: Xoa cac file nam truc tiep trong mot thu muc, sau do xoa thu muc.
// Bai nay xu ly file trong mot cap thu muc; thu muc con neu co se khong bi xoa.
import java.io.File;
import java.util.Scanner;

public class XoaFileTrongThuMuc {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap duong dan thu muc can xoa file ben trong: ");
        String duongDan = sc.nextLine();
        File thuMuc = new File(duongDan);

        if (thuMuc.exists() && thuMuc.isDirectory()) {
            File[] danhSach = thuMuc.listFiles();
            if (danhSach != null) {
                for (File file : danhSach) {
                    if (file.isFile() && !file.delete()) {
                        System.out.println("Khong xoa duoc: " + file.getName());
                    }
                }
            }
            if (thuMuc.delete()) {
                System.out.println("Xoa thu muc thanh cong.");
            } else {
                System.out.println("Khong xoa duoc thu muc. Co the van con thu muc con.");
            }
        } else {
            System.out.println("Thu muc khong ton tai.");
        }
        sc.close();
    }
}
