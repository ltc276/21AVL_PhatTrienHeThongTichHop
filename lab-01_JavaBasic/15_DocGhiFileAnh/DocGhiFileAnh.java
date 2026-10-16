// Bai 15: Doc mot file anh va ghi anh do ra file PNG moi.
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class DocGhiFileAnh {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap duong dan file anh can doc: ");
        String duongDanAnh = sc.nextLine();
        System.out.print("Nhap duong dan file PNG muon ghi: ");
        String duongDanMoi = sc.nextLine();

        BufferedImage anh = ImageIO.read(new File(duongDanAnh));
        if (anh == null) {
            System.out.println("Khong doc duoc anh. Hay kiem tra file va dinh dang.");
        } else {
            ImageIO.write(anh, "png", new File(duongDanMoi));
            System.out.println("Da doc anh va ghi file PNG thanh cong.");
        }
        sc.close();
    }
}
