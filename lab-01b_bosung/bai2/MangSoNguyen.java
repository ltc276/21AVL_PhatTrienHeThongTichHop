package bai2;

import java.util.Scanner;

public class MangSoNguyen {
    private int[] a;
    private int n; // Số lượng phần tử hiện tại
    private int capacity;

    public MangSoNguyen(int capacity) {
        this.capacity = capacity;
        this.a = new int[capacity];
        this.n = 0;
    }

    public void nhapMang(Scanner sc) {
        System.out.print("Nhập số lượng phần tử (n <= " + capacity + "): ");
        n = sc.nextInt();
        if (n > capacity) n = capacity;
        System.out.println("Nhập " + n + " phần tử:");
        for (int i = 0; i < n; i++) {
            System.out.print("a[" + i + "] = ");
            a[i] = sc.nextInt();
        }
    }

    public void xuatMang() {
        System.out.print("Mảng (" + n + " phần tử): [ ");
        for (int i = 0; i < n; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println("]");
    }

    public void themDau(int y) {
        themTaiViTri(0, y);
    }

    public void themCuoi(int y) {
        themTaiViTri(n, y);
    }

    public void themTaiViTri(int index, int y) {
        if (n >= capacity) {
            System.out.println("Mảng đã đầy, không thể thêm!");
            return;
        }
        if (index < 0 || index > n) {
            System.out.println("Vị trí thêm không hợp lệ!");
            return;
        }
        for (int i = n; i > index; i--) {
            a[i] = a[i - 1];
        }
        a[index] = y;
        n++;
    }

    public void xoaPhanTuX(int x) {
        int i = 0;
        while (i < n) {
            if (a[i] == x) {
                xoaViTri(i);
            } else {
                i++;
            }
        }
    }

    public void xoaViTri(int index) {
        if (index < 0 || index >= n) {
            System.out.println("Vị trí xóa không hợp lệ!");
            return;
        }
        for (int i = index; i < n - 1; i++) {
            a[i] = a[i + 1];
        }
        n--;
    }

    // Sắp xếp Radix Sort
    public void radixSort(boolean tangDan) {
        if (n <= 1) return;
        
        int max = Math.abs(a[0]);
        for (int i = 1; i < n; i++) {
            if (Math.abs(a[i]) > max) max = Math.abs(a[i]);
        }

        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSort(exp);
        }

        if (!tangDan) {
            for (int i = 0; i < n / 2; i++) {
                int temp = a[i];
                a[i] = a[n - 1 - i];
                a[n - 1 - i] = temp;
            }
        }
    }

    private void countingSort(int exp) {
        int[] output = new int[n];
        int[] count = new int[10];

        for (int i = 0; i < n; i++) {
            int digit = (Math.abs(a[i]) / exp) % 10;
            count[digit]++;
        }

        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            int digit = (Math.abs(a[i]) / exp) % 10;
            output[count[digit] - 1] = a[i];
            count[digit]--;
        }

        for (int i = 0; i < n; i++) {
            a[i] = output[i];
        }
    }

    public int timKiemChuaSapXep(int b) {
        for (int i = 0; i < n; i++) {
            if (a[i] == b) return i;
        }
        return -1;
    }

    public int timKiemDaSapXep(int b) {
        int left = 0, right = n - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (a[mid] == b) return mid;
            if (a[mid] < b) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }
}
