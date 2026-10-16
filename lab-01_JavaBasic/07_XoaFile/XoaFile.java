// Bai 7: Nhap duong dan va xoa mot file neu file ton tai.
import java.io.File;
import java.util.Scanner;

public class XoaFile {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap duong dan file can xoa: ");
        String duongDan = sc.nextLine();

        File file = new File(duongDan);
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("Xoa file thanh cong.");
            } else {
                System.out.println("Khong xoa duoc file.");
            }
        } else {
            System.out.println("File khong ton tai.");
        }
        sc.close();
    }
}
