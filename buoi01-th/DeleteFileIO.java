import java.io.File;
import java.util.Scanner;

public class DeleteFileIO {
    private void deleteFile(String source) {
        //new file
        File file = new File(source);
        //check file exist
        // neu ton tai
        if(file.exists()) {
            System.out.println("file ton tai");
            file.delete();
            System.out.print("xoa file thanh cong");
        }
        else {
            System.out.println("file khong ton tai");
        }
}
//Delete Thu muc rong
public boolean deleteEmptyFolder(String source) {
        File folder = new File(source);

        if (folder.exists()) {
            folder.delete();
            System.out.println("folder ton tai\nxoa folder thanh cong");
            return true;
        }
        else {
            System.out.println("folder khong ton tai");
        }

        return false;
    }
//Delete thu muc co file
public boolean deleteListFileInfolder(String source) {

    File folder = new File(source);

    // Folder ton tai
    if (folder.exists()) {

        // Danh sach file
        File[] listFile = folder.listFiles();

        if (listFile.length != 0) {

            for (File f : listFile) {

                // Neu la file thi xoa
                if (f.isFile()) {
                    f.delete();
                }
            }
        }

        // Xoa folder
        folder.delete();

        System.out.println("Delete folder thanh cong!");
        return true;

    } else {

        System.out.println("Folder khong ton tai");
        return false;
    }
}
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DeleteFileIO deleteFileIO = new DeleteFileIO();
        System.out.println("1. Xoa file");
        System.out.println("2. Xoa folder");
        System.out.println("3. Xoa folder chua file");
        System.out.print("Chon: ");
        int a = scanner.nextInt();
        scanner.nextLine();
          switch (a) {

            case 1:
                deleteFileIO.deleteFile("D:/Java/CoKhoa/demo.txt");
                break;
            case 2:
                deleteFileIO.deleteEmptyFolder("D:/Java/CoKhoa/test");
                break;
            case 3:
                deleteFileIO.deleteListFileInfolder("D:/Java/CoKhoa/test2");
                break;
            default:
                System.out.println("Lua chon khong hop le");
        }

        scanner.close();
    }
}