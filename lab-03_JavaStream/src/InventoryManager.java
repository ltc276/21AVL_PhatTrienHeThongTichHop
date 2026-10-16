import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InventoryManager {
    private static final Path CSV_FILE = Path.of("data", "inventory.csv");
    private static final Path REPORT_FILE = Path.of("data", "inventory-report.txt");

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Product> products = new ArrayList<>();

        System.out.println("=== CHUONG TRINH QUAN LY TON KHO (INVENTORY MANAGER) ===");

        // 1. Nhap danh sach san pham tu ban phim
        System.out.print("Nhap so luong san pham can nhap: ");
        int n = 0;
        try {
            n = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("So luong khong hop le!");
        }

        for (int i = 0; i < n; i++) {
            System.out.println("
--- Nhap san pham thu " + (i + 1) + " ---");
            while (true) {
                try {
                    System.out.print("Ma san pham: ");
                    String code = sc.nextLine();
                    System.out.print("Ten san pham: ");
                    String name = sc.nextLine();
                    System.out.print("Don gia (>0): ");
                    double price = Double.parseDouble(sc.nextLine().trim());
                    System.out.print("So luong (>=0): ");
                    int qty = Integer.parseInt(sc.nextLine().trim());

                    Product p = new Product(code, name, price, qty);
                    products.add(p);
                    break;
                } catch (Exception e) {
                    System.out.println("Loi: " + e.getMessage() + ". Vui long nhap lai!");
                }
            }
        }

        // 2. Luu danh sach vao inventory.csv
        saveToCsv(products);

        // 3. Doc lai tep CSV va tai tao danh sach san pham
        List<Product> loadedProducts = loadFromCsv();

        // 4. Hien thi san pham va tong gia tri ton kho
        System.out.println("
=== DANH SACH SAN PHAM DOC TU CSV ===");
        double totalInventoryValue = 0;
        Product maxProduct = null;

        for (Product p : loadedProducts) {
            System.out.println(p);
            double val = p.inventoryValue();
            totalInventoryValue += val;

            if (maxProduct == null || val > maxProduct.inventoryValue()) {
                maxProduct = p;
            }
        }

        System.out.printf("
Tong gia tri ton kho: %,.0f VND%n", totalInventoryValue);

        // 5. Tim san pham co gia tri ton kho cao nhat
        if (maxProduct != null) {
            System.out.println("San pham co gia tri ton kho cao nhat: " + maxProduct.getName() +
                    " (" + String.format("%,.0f VND", maxProduct.inventoryValue()) + ")");
        }

        // 6. Ghi bao cao vao inventory-report.txt
        writeReport(loadedProducts, totalInventoryValue, maxProduct);

        sc.close();
    }

    private static void saveToCsv(List<Product> list) {
        try {
            if (CSV_FILE.getParent() != null) {
                Files.createDirectories(CSV_FILE.getParent());
            }
            try (BufferedWriter writer = Files.newBufferedWriter(CSV_FILE, StandardCharsets.UTF_8)) {
                writer.write("ma,ten,donGia,soLuong");
                writer.newLine();
                for (Product p : list) {
                    writer.write(p.toCsvRow());
                    writer.newLine();
                }
            }
            System.out.println("
Da luu danh sach vao " + CSV_FILE);
        } catch (IOException e) {
            System.err.println("Loi ghi file CSV: " + e.getMessage());
        }
    }

    private static List<Product> loadFromCsv() {
        List<Product> list = new ArrayList<>();
        if (!Files.exists(CSV_FILE)) {
            System.err.println("Tep CSV khong ton tai: " + CSV_FILE);
            return list;
        }

        try (BufferedReader reader = Files.newBufferedReader(CSV_FILE, StandardCharsets.UTF_8)) {
            reader.readLine(); // Bo dong header
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) continue;
                String[] parts = line.split(",", -1);
                if (parts.length != 4) {
                    System.err.println("Bo qua dong " + lineNumber + ": Khong du 4 cot");
                    continue;
                }
                try {
                    Product p = new Product(
                            parts[0].trim(),
                            parts[1].trim(),
                            Double.parseDouble(parts[2].trim()),
                            Integer.parseInt(parts[3].trim())
                    );
                    list.add(p);
                } catch (Exception e) {
                    System.err.println("Dong " + lineNumber + " khong hop le: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Loi doc file CSV: " + e.getMessage());
        }
        return list;
    }

    private static void writeReport(List<Product> list, double total, Product maxProduct) {
        try {
            if (REPORT_FILE.getParent() != null) {
                Files.createDirectories(REPORT_FILE.getParent());
            }
            try (BufferedWriter writer = Files.newBufferedWriter(REPORT_FILE, StandardCharsets.UTF_8)) {
                writer.write("=== BAO CAO QUAN LY TON KHO ===");
                writer.newLine();
                writer.write("So luong san pham: " + list.size());
                writer.newLine();
                writer.write(String.format("Tong gia tri ton kho: %,.0f VND", total));
                writer.newLine();
                if (maxProduct != null) {
                    writer.write("San pham ton kho cao nhat: " + maxProduct.getName() +
                            " - " + String.format("%,.0f VND", maxProduct.inventoryValue()));
                    writer.newLine();
                }
            }
            System.out.println("Da xuat bao cao vao " + REPORT_FILE);
        } catch (IOException e) {
            System.err.println("Loi ghi bao cao: " + e.getMessage());
        }
    }
}
