package bai3_4_5;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập số lượng sinh viên n: ");
        int n = Integer.parseInt(sc.nextLine());

        List<Student> students = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            System.out.println("\n--- Nhập sinh viên thứ " + (i + 1) + " ---");
            Student st = new Student();
            st.inputInfo();
            students.add(st);
        }

        System.out.println("\n================ DANH SÁCH TẤT CẢ SINH VIÊN ================");
        for (Student st : students) {
            st.printInfo();
        }

        if (!students.isEmpty()) {
            double maxGpa = students.get(0).getGpa();
            double minGpa = students.get(0).getGpa();

            for (Student st : students) {
                if (st.getGpa() > maxGpa) maxGpa = st.getGpa();
                if (st.getGpa() < minGpa) minGpa = st.getGpa();
            }

            System.out.println("\n---------------- SINH VIÊN ĐIỂM CAO NHẤT (GPA: " + maxGpa + ") ----------------");
            for (Student st : students) {
                if (st.getGpa() == maxGpa) st.printInfo();
            }

            System.out.println("\n---------------- SINH VIÊN ĐIỂM THẤP NHẤT (GPA: " + minGpa + ") ----------------");
            for (Student st : students) {
                if (st.getGpa() == minGpa) st.printInfo();
            }

            System.out.println("\n---------------- DANH SÁCH SINH VIÊN ĐẠT HỌC BỔNG ----------------");
            boolean coHocBong = false;
            for (Student st : students) {
                if (st.isHocBong()) {
                    st.printInfo();
                    coHocBong = true;
                }
            }
            if (!coHocBong) {
                System.out.println("Không có sinh viên nào đạt học bổng.");
            }
        }

        sc.close();
    }
}
