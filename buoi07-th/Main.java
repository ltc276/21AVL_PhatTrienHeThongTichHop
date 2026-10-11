import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // B1: Ket noi SQLite
            Class.forName("org.sqlite.JDBC");

            String url = "jdbc:sqlite:nhanvien.db";
            conn = DriverManager.getConnection(url);

            // B2: Tao Statement
            stmt = conn.createStatement();

            // B3.1: Tao bang NhanVien
            String sql_create_table =
                "CREATE TABLE IF NOT EXISTS NhanVien (" +
                "id INTEGER PRIMARY KEY, " +
                "ten TEXT NOT NULL, " +
                "chuc_vu TEXT)";

            stmt.executeUpdate(sql_create_table);

            // B3.2: Them du lieu
            String sql_insert1 =
                "INSERT INTO NhanVien (ten, chuc_vu) " +
                "VALUES ('Nguyen Van A', 'Lap trinh vien')";

            String sql_insert2 =
                "INSERT INTO NhanVien (ten, chuc_vu) " +
                "VALUES ('Tran Thi B', 'Ke toan')";

            String sql_insert3 =
                "INSERT INTO NhanVien (ten, chuc_vu) " +
                "VALUES ('Le Van C', 'Quan ly')";

            stmt.executeUpdate(sql_insert1);
            stmt.executeUpdate(sql_insert2);
            stmt.executeUpdate(sql_insert3);

            // B4: Truy van du lieu
            String sql_select =
                "SELECT id, ten, chuc_vu FROM NhanVien";

            rs = stmt.executeQuery(sql_select);

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Ten: " + rs.getString("ten"));
                System.out.println("Chuc vu: " + rs.getString("chuc_vu"));
                System.out.println("--------------------");
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Khong tim thay driver JDBC");

        } catch (SQLException e) {
            System.out.println("Loi SQL");
            e.printStackTrace();

        } finally {
            // B5: Dong cac tai nguyen

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
    }
}