package bai3_4_5;

import java.util.Scanner;

public class Student extends Person {
    private double gpa;
    private String email;

    public Student() {
        super();
    }

    public Student(String name, String gender, String dob, String address, double gpa, String email) {
        super(name, gender, dob, address);
        this.gpa = gpa;
        this.email = email;
    }

    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public void inputInfo() {
        super.inputInfo();
        Scanner sc = new Scanner(System.in);

        do {
            System.out.print("Nhập điểm trung bình (0.0 - 10.0): ");
            this.gpa = Double.parseDouble(sc.nextLine());
            if (gpa < 0.0 || gpa > 10.0) {
                System.out.println("Điểm trung bình không hợp lệ! Vui lòng nhập lại.");
            }
        } while (gpa < 0.0 || gpa > 10.0);

        do {
            System.out.print("Nhập email (phải chứa '@' và không chứa khoảng trắng): ");
            this.email = sc.nextLine();
            if (!email.contains("@") || email.contains(" ")) {
                System.out.println("Email không hợp lệ! Vui lòng nhập lại.");
            }
        } while (!email.contains("@") || email.contains(" "));
    }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.printf("   -> ĐTB: %.2f | Email: %s | Học bổng: %s%n",
                gpa, email, (isHocBong() ? "CÓ" : "KHÔNG"));
    }

    public boolean isHocBong() {
        return gpa > 8.0;
    }
}
