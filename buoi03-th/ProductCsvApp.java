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

        try (BufferedReader reader = Files.newBufferedReader(
                input, StandardCharsets.UTF_8)) {

            reader.readLine(); // bo qua dong tieu de

            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {

                lineNumber++;

                if (line.isBlank()) {
                    continue;
                }

                String[] parts = line.split(",", -1);

                if (parts.length != 4) {
                    System.err.println("Bo qua dong " + lineNumber);
                    continue;
                }

                try {
                    products.add(new Product(
                            parts[0].trim(),
                            parts[1].trim(),
                            Double.parseDouble(parts[2].trim()),
                            Integer.parseInt(parts[3].trim())
                    ));

                } catch (IllegalArgumentException e) {

                    System.err.println(
                            "Dong " + lineNumber
                                    + " khong hop le: "
                                    + e.getMessage()
                    );
                }
            }

        } catch (IOException e) {

            System.err.println(
                    "Khong doc duoc CSV: "
                            + e.getMessage()
            );

            return;
        }

        double total = 0;

        for (Product product : products) {
            System.out.println(product);
            total += product.inventoryValue();
        }

        try (BufferedWriter writer = Files.newBufferedWriter(
                report, StandardCharsets.UTF_8)) {

            writer.write(
                    "So san pham: "
                            + products.size()
            );

            writer.newLine();

            writer.write(
                    "Tong gia tri ton kho: "
                            + "%,.0f VND".formatted(total)
            );

            writer.newLine();

        } catch (IOException e) {

            System.err.println(
                    "Khong ghi duoc bao cao: "
                            + e.getMessage()
            );
        }
    }
}