import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ProductCsvApp {
    public static void main(String[] args) {
        Path input = Path.of("data", "products.csv");
        Path report = Path.of("data", "report.txt");

        List<Product> products = new ArrayList<>();

        if (!Files.exists(input)) {
            System.err.println("Tep khong ton tai: " + input.toAbsolutePath());
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(input, StandardCharsets.UTF_8)) {
            reader.readLine(); // Bo qua dong tieu de
            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) continue;

                String[] parts = line.split(",", -1);
                if (parts.length != 4) {
                    System.err.println("Bo qua dong " + lineNumber + ": Thieu hoac thuo cot");
                    continue;
                }

                try {
                    Product p = new Product(
                            parts[0].trim(),
                            parts[1].trim(),
                            Double.parseDouble(parts[2].trim()),
                            Integer.parseInt(parts[3].trim())
                    );
                    products.add(p);
                } catch (IllegalArgumentException e) {
                    System.err.println("Dong " + lineNumber + " khong hop le: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Khong doc duoc CSV: " + e.getMessage());
            return;
        }

        System.out.println("=== DANH SACH SAN PHAM ===");
        double total = 0;
        for (Product product : products) {
            System.out.println(product);
            total += product.inventoryValue();
        }

        try {
            if (report.getParent() != null) {
                Files.createDirectories(report.getParent());
            }
            try (BufferedWriter writer = Files.newBufferedWriter(report, StandardCharsets.UTF_8)) {
                writer.write("So san pham: " + products.size());
                writer.newLine();
                writer.write(String.format("Tong gia tri ton kho: %,.0f VND", total));
            }
            System.out.println("
Da ghi bao cao thanh cong vao: " + report);
        } catch (IOException e) {
            System.err.println("Khong ghi duoc bao cao: " + e.getMessage());
        }
    }
}
