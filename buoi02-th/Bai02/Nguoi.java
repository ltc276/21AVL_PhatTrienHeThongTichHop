public class Nguoi{
    private String hoTen;
    private int namSinh;
    private String diaChi;
    public Nguoi(String hoTen, int namSinh,String diaChi){
        this.hoTen=hoTen;
        this.namSinh=namSinh;
        this.diaChi=diaChi;
    }
    public String getHoTen(){
        return hoTen;
    }
    public int getNamSinh(){
        return namSinh;
    }
    public String getDiaChi(){
        return diaChi;
    }
    public String setHoten(){
        return this.hoTen;
    }
    public int setNamSinh(){
        return this.namSinh;
    }
    public String setDiaChi(){
        return this.diaChi;
    }
    public int tinhTuoi(){
        return 2026-namSinh;
    }
    public void hienThiThongTin() {
        System.out.println("Ho va Ten: " + hoTen );
        System.out.println("Nam Sinh: " + namSinh);
        System.out.println("Dia chi: " + diaChi);
        System.out.println("Tuoi: " + tinhTuoi());
    }
}