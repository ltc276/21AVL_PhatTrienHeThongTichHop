// Bai 12: Sao chep noi dung tu file nguon sang file dich bang luong byte.
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class SaoChepFile {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap duong dan file nguon: ");
        String nguon = sc.nextLine();
        System.out.print("Nhap duong dan file dich: ");
        String dich = sc.nextLine();

        File fileNguon = new File(nguon);
        File fileDich = new File(dich);
        if (!fileNguon.exists() || !fileNguon.isFile()) {
            System.out.println("File nguon khong ton tai.");
        } else if (fileNguon.getAbsolutePath().equals(fileDich.getAbsolutePath())) {
            System.out.println("File nguon va file dich phai khac nhau.");
        } else {
            FileInputStream fis = new FileInputStream(fileNguon);
            FileOutputStream fos = new FileOutputStream(dich);
            byte[] mang = new byte[1024];
            int soByte;
            while ((soByte = fis.read(mang)) != -1) {
                fos.write(mang, 0, soByte);
            }
            fis.close();
            fos.close();
            System.out.println("Sao chep file thanh cong.");
        }
        sc.close();
    }
}
