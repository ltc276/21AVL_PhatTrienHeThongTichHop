import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class TextFileDemo {
    public static void main(String[] args) {
        Path file = Path.of("data", "ghi_chu.txt");
        try {
            if (file.getParent() != null) {
                Files.createDirectories(file.getParent());
            }
            
            // Ghi file
            try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
                writer.write("Java I/O lam viec voi cac luong du lieu.");
                writer.newLine();
                writer.write("BufferedWriter giup ghi van ban hieu qua.");
                writer.newLine();
                writer.write("UTF-8 ho tro tieng Viet on dinh.");
            }

            // Doc file
            try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
                String line;
                int number = 1;
                System.out.println("Noi dung file ghi_chu.txt:");
                while ((line = reader.readLine()) != null) {
                    System.out.printf("%d. %s%n", number++, line);
                }
            }
        } catch (IOException e) {
            System.err.println("Loi xu ly tep " + file + ": " + e.getMessage());
        }
    }
}
