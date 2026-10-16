// Bai 2: Nhap ten va in ra loi chao theo mau "Hi, I am ...".
import java.util.Scanner;

public class NhapTen {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap ten cua ban: ");
        String ten = sc.nextLine();
        System.out.println("Hi, I am " + ten);
        sc.close();
    }
}
