package bai2;

import java.util.Scanner;

public class DemoArray {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MangSoNguyen m = new MangSoNguyen(100);

        System.out.println("=== DEMO BÀI 2: MẢNG SỐ NGUYÊN ===");
        m.nhapMang(sc);
        m.xuatMang();

        System.out.println("\nThêm 99 vào đầu:");
        m.themDau(99);
        m.xuatMang();

        System.out.println("\nThêm 88 vào cuối:");
        m.themCuoi(88);
        m.xuatMang();

        System.out.println("\nThêm 77 vào vị trí 2:");
        m.themTaiViTri(2, 77);
        m.xuatMang();

        System.out.print("\nNhập giá trị B cần tìm (chưa sắp xếp): ");
        int b = sc.nextInt();
        int pos = m.timKiemChuaSapXep(b);
        System.out.println(pos != -1 ? "Tìm thấy tại vị trí: " + pos : "Không tìm thấy!");

        System.out.println("\nSắp xếp tăng dần theo Radix Sort:");
        m.radixSort(true);
        m.xuatMang();

        System.out.print("\nNhập giá trị B cần tìm bằng Binary Search (đã sắp xếp): ");
        b = sc.nextInt();
        pos = m.timKiemDaSapXep(b);
        System.out.println(pos != -1 ? "Tìm thấy tại vị trí: " + pos : "Không tìm thấy!");

        sc.close();
    }
}
