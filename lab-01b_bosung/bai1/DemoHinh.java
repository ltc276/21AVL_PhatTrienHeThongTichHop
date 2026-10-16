package bai1;

public class DemoHinh {
    public static void main(String[] args) {
        System.out.println("=== DEMO BÀI 1: CÁC HÌNH HỌC ===");
        
        HCN hcn = new HCN(5.0, 3.0);
        hcn.xuatThongTin();

        HVuong hv = new HVuong(4.0);
        hv.xuatThongTin();

        HTG htg = new HTG(3.0, 4.0, 5.0);
        htg.xuatThongTin();
    }
}
