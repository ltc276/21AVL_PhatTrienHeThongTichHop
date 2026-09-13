import java.io.File;

public class FinFileIO {

    public void findFile(String source, String key) {

        File file = new File(source);

        // Kiem tra source ton tai
        if (file.exists()) {

            // Neu la file
            if (file.isFile()) {

                // Kiem tra ten file co ket thuc bang key khong
                if (file.getName().endsWith(key)) {
                    System.out.println(file.getAbsolutePath());
                }
            }

            // Neu la folder thi lay danh sach file
            File[] listFile = file.listFiles();

            if (listFile != null) {

                for (File f : listFile) {

                    // De quy de tim file trong folder
                    findFile(f.getAbsolutePath(), key);
                }
            }

        } else {

            System.out.println("Source khong ton tai");
        }
    }

    public static void main(String[] args) {

        FinFileIO findFile = new FinFileIO();

        findFile.findFile(
            "D:/Java/CoKhoa",
            ".txt"
        );
    }
}