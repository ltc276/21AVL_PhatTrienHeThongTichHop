import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        Scanner sc = new Scanner(System.in);

        try {
            // 1. Ket noi SQLite
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:sanpham.db");

            // 2. Tao Statement
            stmt = conn.createStatement();

            // 3. Tao bang SanPham
            String sqlCreate =
                "CREATE TABLE IF NOT EXISTS SanPham (" +
                "maSP TEXT PRIMARY KEY, " +
                "tenSP TEXT NOT NULL, " +
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
                    String ma = sc.nextLine().replace("'", "''");

                    System.out.print("Nhap ten SP: ");
                    String ten = sc.nextLine().replace("'", "''");

                    System.out.print("Nhap gia: ");
                    double gia = Double.parseDouble(sc.nextLine());

                    System.out.print("Nhap so luong: ");
                    int sl = Integer.parseInt(sc.nextLine());

                    String sql =
                        "INSERT INTO SanPham VALUES ('" + ma + "', '" +
                        ten + "', " + gia + ", " + sl + ")";

                    stmt.executeUpdate(sql);
                    System.out.println("Them thanh cong!");

                // 2. Hien thi hoac tim kiem
                } else if (chon == 2) {
                    System.out.println("1. Hien thi tat ca");
                    System.out.println("2. Tim theo ten");
                    System.out.print("Nhap lua chon: ");

                    int luaChonDoc = Integer.parseInt(sc.nextLine());
                    String sql = null;

                    if (luaChonDoc == 1) {
                        sql = "SELECT * FROM SanPham";
                    } else if (luaChonDoc == 2) {
                        System.out.print("Nhap ten can tim: ");
                        String ten = sc.nextLine().replace("'", "''");

                        sql = "SELECT * FROM SanPham " +
                              "WHERE tenSP LIKE '%" + ten + "%'";
                    }

                    if (sql != null) {
                        rs = stmt.executeQuery(sql);

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
                    } else {
                        System.out.println("Lua chon khong hop le!");
                    }

                // 3. Cap nhat san pham
                } else if (chon == 3) {
                    System.out.print("Nhap ma SP can cap nhat: ");
                    String ma = sc.nextLine().replace("'", "''");

                    System.out.print("Nhap gia moi: ");
                    double gia = Double.parseDouble(sc.nextLine());

                    System.out.print("Nhap so luong moi: ");
                    int sl = Integer.parseInt(sc.nextLine());

                    String sql =
                        "UPDATE SanPham SET gia = " + gia +
                        ", soluong = " + sl +
                        " WHERE maSP = '" + ma + "'";

                    int kq = stmt.executeUpdate(sql);

                    if (kq > 0) {
                        System.out.println("Cap nhat thanh cong!");
                    } else {
                        System.out.println("Khong tim thay san pham!");
                    }

                // 4. Xoa san pham
                } else if (chon == 4) {
                    System.out.print("Nhap ma SP can xoa: ");
                    String ma = sc.nextLine().replace("'", "''");

                    String sql =
                        "DELETE FROM SanPham WHERE maSP = '" + ma + "'";

                    int kq = stmt.executeUpdate(sql);

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
            // Dong ResultSet
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Dong Statement
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Dong Connection
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