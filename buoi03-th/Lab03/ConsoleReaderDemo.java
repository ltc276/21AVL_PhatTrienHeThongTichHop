import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
public class ConsoleReaderDemo {
 public static void main(String[] args) {
 BufferedReader reader = new BufferedReader(
 new InputStreamReader(System.in, StandardCharsets.UTF_8));
 int count = 0;
 System.out.println("Nhap van ban; nhap q de ket thuc:");
 try {
 while (true) {
 String line = reader.readLine();
 if (line == null || line.equalsIgnoreCase("q")) {
 break;
 }
 count++;
 System.out.printf("Dong %d: %s%n", count, line);
 }
 } catch (IOException e) {
 System.err.println("Khong the doc du lieu: " + e.getMessage());
 }
 System.out.println("Tong so dong da nhap: " + count);
 }
}