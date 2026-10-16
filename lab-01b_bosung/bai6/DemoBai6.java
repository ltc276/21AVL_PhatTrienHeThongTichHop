package bai6;

public class DemoBai6 {
    public static void main(String[] args) {
        System.out.println("=== DEMO BÀI 6: TRUY CẬP BIẾN X CỦA LỚP A TỪ C ===");
        C c = new C();

        System.out.println("Giá trị ban đầu:");
        System.out.println("c.x (C) = " + c.x);
        System.out.println("((B)c).x (B) = " + ((B) c).x);
        System.out.println("c.getXA() (A) = " + c.getXA());

        System.out.println("\nThực hiện c.setXA(100)...");
        c.setXA(100);

        System.out.println("\nGiá trị sau khi cập nhật x của A:");
        System.out.println("c.x (C) = " + c.x + " (Không đổi)");
        System.out.println("((B)c).x (B) = " + ((B) c).x + " (Không đổi)");
        System.out.println("c.getXA() (A) = " + c.getXA() + " (Đã đổi thành 100)");
    }
}
