import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CopyFileIO {

    public boolean copyFile(String source, String dest)
            throws FileNotFoundException, IOException {

        // File nguon
        File sourceFile = new File(source);

        // File dich
        File destFile = new File(dest);

        // Kiem tra file nguon co ton tai khong
        if (sourceFile.exists()) {

            // Luong doc file
            FileInputStream fis = new FileInputStream(sourceFile);

            // Luong ghi file
            FileOutputStream fos = new FileOutputStream(destFile);

            byte[] arr = new byte[1024];

            int n;

            while ((n = fis.read(arr)) != -1) {
                fos.write(arr, 0, n);
            }

            fis.close();
            fos.close();

            System.out.println("Copy thanh cong");
            return true;

        } else {

            System.out.println("File nguon khong ton tai");
            return false;
        }
    }

    public static void main(String[] args) throws IOException {

        CopyFileIO copyFileIO = new CopyFileIO();

        copyFileIO.copyFile(
            "D:/Java/CoKhoa/demo.txt",
            "D:/Java/CoKhoa/demo_copy.txt"
        );
    }
}