import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        Scanner sc = new Scanner(System.in);

        try {
            // Ket noi SQLite
            Class.forName("org.sqlite.JDBC");

            String url = "jdbc:sqlite:sanpham.db";
            conn = DriverManager.getConnection(url);

            stmt = conn.createStatement();

            // Tao bang SanPham
            String sql_create =
                "CREATE TABLE IF NOT EXISTS SanPham (" +
                "maSP TEXT PRIMARY KEY, " +
                "tenSP TEXT NOT NULL, " +
                "gia REAL, " +
                "soluong INTEGER)";

            stmt.executeUpdate(sql_create);

            // Them 3 san pham mau neu bang dang rong
            rs = stmt.executeQuery(
                "SELECT COUNT(*) AS tong FROM SanPham"
            );

            if (rs.next() && rs.getInt("tong") == 0) {
                rs.close();
                rs = null;

                stmt.executeUpdate(
                    "INSERT INTO SanPham VALUES " +
                    "('SP01', 'Laptop', 15000000, 10)"
                );

                stmt.executeUpdate(
                    "INSERT INTO SanPham VALUES " +
                    "('SP02', 'Chuot', 200000, 30)"
                );

                stmt.executeUpdate(
                    "INSERT INTO SanPham VALUES " +
                    "('SP03', 'Ban phim', 500000, 20)"
                );
            } else {
                rs.close();
                rs = null;
            }

            int chon;

            do {
                System.out.println("\n===== QUAN LY SAN PHAM =====");
                System.out.println("1. Them san pham");
                System.out.println("2. Hien thi / Tim kiem san pham");
                System.out.println("3. Cap nhat gia va so luong");
                System.out.println("4. Xoa san pham");
                System.out.println("0. Thoat");
                System.out.print("Nhap lua chon: ");

                chon = Integer.parseInt(sc.nextLine());

                // 1. CREATE - Them san pham
                if (chon == 1) {
                    System.out.print("Nhap ma SP: ");
                    String maSP = sc.nextLine().replace("'", "''");

                    System.out.print("Nhap ten SP: ");
                    String tenSP = sc.nextLine().replace("'", "''");

                    System.out.print("Nhap gia: ");
                    double gia = Double.parseDouble(sc.nextLine());

                    System.out.print("Nhap so luong: ");
                    int soluong = Integer.parseInt(sc.nextLine());

                    String sql_insert =
                        "INSERT INTO SanPham VALUES ('" +
                        maSP + "', '" + tenSP + "', " +
                        gia + ", " + soluong + ")";

                    stmt.executeUpdate(sql_insert);

                    System.out.println("Them san pham thanh cong!");

                // 2. READ - Hien thi hoac tim kiem
                } else if (chon == 2) {
                    System.out.println("1. Hien thi tat ca san pham");
                    System.out.println("2. Tim theo ten san pham");
                    System.out.print("Nhap lua chon: ");

                    int chonDoc = Integer.parseInt(sc.nextLine());

                    String sql_select;

                    if (chonDoc == 1) {
                        sql_select = "SELECT * FROM SanPham";
                    } else if (chonDoc == 2) {
                        System.out.print("Nhap ten can tim: ");
                        String tenTim = sc.nextLine()
                                             .replace("'", "''");

                        sql_select =
                            "SELECT * FROM SanPham WHERE tenSP LIKE '%" +
                            tenTim + "%'";
                    } else {
                        System.out.println("Lua chon khong hop le!");
                        continue;
                    }

                    rs = stmt.executeQuery(sql_select);

                    while (rs.next()) {
                        System.out.println("Ma SP: " +
                            rs.getString("maSP"));
                        System.out.println("Ten SP: " +
                            rs.getString("tenSP"));
                        System.out.println("Gia: " +
                            rs.getDouble("gia"));
                        System.out.println("So luong: " +
                            rs.getInt("soluong"));
                        System.out.println("--------------------");
                    }

                    rs.close();
                    rs = null;

                // 3. UPDATE - Cap nhat san pham
                } else if (chon == 3) {
                    System.out.print("Nhap ma SP can cap nhat: ");
                    String maSP = sc.nextLine().replace("'", "''");

                    System.out.print("Nhap gia moi: ");
                    double gia = Double.parseDouble(sc.nextLine());

                    System.out.print("Nhap so luong moi: ");
                    int soluong = Integer.parseInt(sc.nextLine());

                    String sql_update =
                        "UPDATE SanPham SET gia = " + gia +
                        ", soluong = " + soluong +
                        " WHERE maSP = '" + maSP + "'";

                    int kq = stmt.executeUpdate(sql_update);

                    if (kq > 0) {
                        System.out.println("Cap nhat thanh cong!");
                    } else {
                        System.out.println("Khong tim thay ma san pham!");
                    }

                // 4. DELETE - Xoa san pham
                } else if (chon == 4) {
                    System.out.print("Nhap ma SP can xoa: ");
                    String maSP = sc.nextLine().replace("'", "''");

                    String sql_delete =
                        "DELETE FROM SanPham WHERE maSP = '" +
                        maSP + "'";

                    int kq = stmt.executeUpdate(sql_delete);

                    if (kq > 0) {
                        System.out.println("Xoa san pham thanh cong!");
                    } else {
                        System.out.println("Khong tim thay ma san pham!");
                    }

                } else if (chon != 0) {
                    System.out.println("Lua chon khong hop le!");
                }

            } while (chon != 0);

        } catch (ClassNotFoundException e) {
            System.out.println("Khong tim thay driver JDBC!");

        } catch (SQLException e) {
            System.out.println("Loi SQL!");
            e.printStackTrace();

        } finally {
            // Dong cac tai nguyen
            try {
                if (rs != null) {
                    rs.close();
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
        }

        sc.close();
    }
}