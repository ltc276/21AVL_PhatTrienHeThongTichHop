package bai3_4_5;

import java.util.Scanner;

public class Person {
    private String name;
    private String gender;
    private String dob;
    private String address;

    public Person() {}

    public Person(String name, String gender, String dob, String address) {
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.address = address;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public void inputInfo() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập họ tên: ");
        this.name = sc.nextLine();
        System.out.print("Nhập giới tính: ");
        this.gender = sc.nextLine();
        System.out.print("Nhập ngày sinh (dd/mm/yyyy): ");
        this.dob = sc.nextLine();
        System.out.print("Nhập địa chỉ: ");
        this.address = sc.nextLine();
    }

    public void printInfo() {
        System.out.println("Tên: " + name + " | Giới tính: " + gender + " | Ngày sinh: " + dob + " | Địa chỉ: " + address);
    }
}
