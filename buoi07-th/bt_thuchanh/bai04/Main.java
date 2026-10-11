import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Connection conn = null;
        Statement stmt = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Scanner sc = new Scanner(System.in);

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:sanpham.db");

            stmt = conn.createStatement();

            String sqlCreate =
                "CREATE TABLE IF NOT EXISTS SanPham (" +
                "maSP TEXT PRIMARY KEY, tenSP TEXT NOT NULL, " +
                "gia REAL, soluong INTEGER)";

            stmt.executeUpdate(sqlCreate);

            int chon = -1;

            while (chon != 0) {
                System.out.println("\n===== QUAN LY SAN PHAM =====");
                System.out.println("1. Them san pham");
                System.out.println("2. Hien thi / Tim kiem");
                System.out.println("3. Cap nhat san pham");
                System.out.println("4. Xoa san pham");
                System.out.println("0. Thoat");
                System.out.print("Nhap lua chon: ");

                chon = Integer.parseInt(sc.nextLine());

                // 1. Them san pham
                if (chon == 1) {
                    System.out.print("Nhap ma SP: ");
                    String ma = sc.nextLine();

                    System.out.print("Nhap ten SP: ");
                    String ten = sc.nextLine();

                    System.out.print("Nhap gia: ");
                    double gia = Double.parseDouble(sc.nextLine());

                    System.out.print("Nhap so luong: ");
                    int sl = Integer.parseInt(sc.nextLine());

                    ps = conn.prepareStatement(
                        "INSERT INTO SanPham VALUES (?, ?, ?, ?)");

                    ps.setString(1, ma);
                    ps.setString(2, ten);
                    ps.setDouble(3, gia);
                    ps.setInt(4, sl);

                    ps.executeUpdate();
                    ps.close();
                    ps = null;

                    System.out.println("Them thanh cong!");

                // 2. Hien thi hoac tim kiem
                } else if (chon == 2) {
                    System.out.println("1. Hien thi tat ca");
                    System.out.println("2. Tim theo ten");
                    System.out.print("Nhap lua chon: ");

                    int luaChonDoc =
                        Integer.parseInt(sc.nextLine());

                    if (luaChonDoc == 1) {
                        ps = conn.prepareStatement(
                            "SELECT * FROM SanPham");
                    } else if (luaChonDoc == 2) {
                        System.out.print("Nhap ten can tim: ");
                        String ten = sc.nextLine();

                        ps = conn.prepareStatement(
                            "SELECT * FROM SanPham WHERE tenSP LIKE ?");

                        ps.setString(1, "%" + ten + "%");
                    } else {
                        System.out.println("Lua chon khong hop le!");
                    }

                    if (ps != null) {
                        rs = ps.executeQuery();

                        while (rs.next()) {
                            System.out.println("Ma SP: " +
                                rs.getString("maSP"));
                            System.out.println("Ten SP: " +
                                rs.getString("tenSP"));
                            System.out.println("Gia: " +
                                rs.getDouble("gia"));
                            System.out.println("So luong: " +
                                rs.getInt("soluong"));
                            System.out.println("-------------------");
                        }

                        rs.close();
                        rs = null;

                        ps.close();
                        ps = null;
                    }

                // 3. Cap nhat san pham
                } else if (chon == 3) {
                    System.out.print("Nhap ma SP: ");
                    String ma = sc.nextLine();

                    System.out.print("Nhap gia moi: ");
                    double gia = Double.parseDouble(sc.nextLine());

                    System.out.print("Nhap so luong moi: ");
                    int sl = Integer.parseInt(sc.nextLine());

                    ps = conn.prepareStatement(
                        "UPDATE SanPham SET gia = ?, soluong = ? " +
                        "WHERE maSP = ?");

                    ps.setDouble(1, gia);
                    ps.setInt(2, sl);
                    ps.setString(3, ma);

                    int kq = ps.executeUpdate();

                    ps.close();
                    ps = null;

                    if (kq > 0) {
                        System.out.println("Cap nhat thanh cong!");
                    } else {
                        System.out.println("Khong tim thay san pham!");
                    }

                // 4. Xoa san pham
                } else if (chon == 4) {
                    System.out.print("Nhap ma SP can xoa: ");
                    String ma = sc.nextLine();

                    ps = conn.prepareStatement(
                        "DELETE FROM SanPham WHERE maSP = ?");

                    ps.setString(1, ma);

                    int kq = ps.executeUpdate();

                    ps.close();
                    ps = null;

                    if (kq > 0) {
                        System.out.println("Xoa thanh cong!");
                    } else {
                        System.out.println("Khong tim thay san pham!");
                    }

                } else if (chon != 0) {
                    System.out.println("Lua chon khong hop le!");
                }
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Khong tim thay driver JDBC!");

        } catch (SQLException e) {
            System.out.println("Loi SQL!");
            e.printStackTrace();

        } catch (NumberFormatException e) {
            System.out.println("Du lieu so khong hop le!");

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            sc.close();
        }
    }
}