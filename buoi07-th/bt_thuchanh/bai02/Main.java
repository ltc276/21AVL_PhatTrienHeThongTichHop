import java.sql.*;

public class Main {
    public static void main(String[] args) {

        Connection conn = null;
        Statement stmt = null;

        try {
            // Ket noi SQLite
            Class.forName("org.sqlite.JDBC");

            String url = "jdbc:sqlite:../bai01/sanpham.db";
            conn = DriverManager.getConnection(url);

            stmt = conn.createStatement();

            // Bat dau giao dich
            conn.setAutoCommit(false);

            // Cap nhat gia san pham SP01
            String sql1 =
                "UPDATE SanPham SET gia = 16000000 " +
                "WHERE maSP = 'SP01'";

            // Cap nhat so luong san pham SP02
            String sql2 =
                "UPDATE SanPham SET soluong = 25 " +
                "WHERE maSP = 'SP02'";

            int kq1 = stmt.executeUpdate(sql1);
            int kq2 = stmt.executeUpdate(sql2);

            if (kq1 > 0 && kq2 > 0) {
                conn.commit();
                System.out.println("Cap nhat thanh cong!");
                System.out.println("Giao dich da duoc luu.");
            } else {
                conn.rollback();
                System.out.println("Khong tim thay san pham!");
                System.out.println("Da huy giao dich.");
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Khong tim thay driver JDBC!");

        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            System.out.println("Loi SQL! Da huy giao dich.");
            e.printStackTrace();

        } finally {
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
    }
}