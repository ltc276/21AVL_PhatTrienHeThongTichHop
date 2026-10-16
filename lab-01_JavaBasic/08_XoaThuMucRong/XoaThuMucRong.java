// Bai 8: Xoa mot thu muc rong theo duong dan do nguoi dung nhap.
import java.io.File;
import java.util.Scanner;

public class XoaThuMucRong {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap duong dan thu muc rong can xoa: ");
        String duongDan = sc.nextLine();

        File thuMuc = new File(duongDan);
        if (thuMuc.exists() && thuMuc.isDirectory()) {
            if (thuMuc.delete()) {
                System.out.println("Xoa thu muc thanh cong.");
            } else {
                System.out.println("Khong xoa duoc. Hay kiem tra thu muc co rong khong.");
            }
        } else {
            System.out.println("Thu muc khong ton tai.");
        }
        sc.close();
    }
}
