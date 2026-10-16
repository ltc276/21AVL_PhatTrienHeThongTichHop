// Bai 3: Nhap hai so A, B va in ra tong cua hai so.
import java.util.Scanner;

public class TinhTong {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap so A: ");
        int a = sc.nextInt();
        System.out.print("Nhap so B: ");
        int b = sc.nextInt();
        System.out.println("Tong = " + (a + b));
        sc.close();
    }
}
