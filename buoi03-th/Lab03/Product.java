public class Product {

    private final String code;
    private final String name;
    private final double unitPrice;
    private final int quantity;

    public Product(String code, String name, double unitPrice, int quantity) {

        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Ma khong duoc rong");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Ten khong duoc rong");
        }

        if (unitPrice <= 0 || quantity < 0) {
            throw new IllegalArgumentException("Gia hoac so luong khong hop le");
        }

        this.code = code;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public double inventoryValue() {
        return unitPrice * quantity;
    }

    @Override
    public String toString() {
        return "%s - %s: %,.0f VND".formatted(
                code, name, inventoryValue()
        );
    }
}