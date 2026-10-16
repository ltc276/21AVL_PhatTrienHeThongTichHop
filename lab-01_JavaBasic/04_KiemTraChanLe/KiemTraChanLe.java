// Bai 4: Nhap mot so nguyen va kiem tra so do chan hay le bang if/else.
import java.util.Scanner;

public class KiemTraChanLe {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap mot so nguyen: ");
        int so = sc.nextInt();

        if (so % 2 == 0) {
            System.out.println(so + " la so chan.");
        } else {
            System.out.println(so + " la so le.");
        }
        sc.close();
    }
}
