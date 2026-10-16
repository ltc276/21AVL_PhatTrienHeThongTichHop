public class Product {
    private final String code;
    private final String name;
    private final double unitPrice;
    private final int quantity;

    public Product(String code, String name, double unitPrice, int quantity) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Ma san pham khong duoc rong");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Ten san pham khong duoc rong");
        }
        if (unitPrice <= 0) {
            throw new IllegalArgumentException("Don gia phai lon hon 0");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("So luong khong duoc am");
        }
        this.code = code;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public double inventoryValue() {
        return unitPrice * quantity;
    }

    public String toCsvRow() {
        return String.format("%s,%s,%.2f,%d", code, name, unitPrice, quantity);
    }

    @Override
    public String toString() {
        return String.format("Ma: %s | Ten: %s | Don gia: %,.0f VND | So luong: %d | Ton kho: %,.0f VND",
                code, name, unitPrice, quantity, inventoryValue());
    }
}
