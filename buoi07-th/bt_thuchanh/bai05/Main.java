import java.sql.*;

public class Main {
    public static void main(String[] args) {

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // 1. Ket noi SQLite
            Class.forName("org.sqlite.JDBC");

            conn = DriverManager.getConnection(
                "jdbc:sqlite:nhanvien_luong.db");

            stmt = conn.createStatement();

            // 2. Tao bang NhanVien
            String sqlCreate =
                "CREATE TABLE IF NOT EXISTS NhanVien (" +
                "id INTEGER PRIMARY KEY, " +
                "ten TEXT NOT NULL, " +
                "chuc_vu TEXT, " +
                "luong REAL)";

            stmt.executeUpdate(sqlCreate);

            // 3. Them du lieu mau neu bang chua co du lieu
            rs = stmt.executeQuery(
                "SELECT COUNT(*) AS tong FROM NhanVien");

            rs.next();
            int tong = rs.getInt("tong");

            rs.close();
            rs = null;

            if (tong == 0) {
                stmt.executeUpdate(
                    "INSERT INTO NhanVien VALUES " +
                    "(1, 'Nguyen Van A', 'Lap trinh vien', 15000000)");

                stmt.executeUpdate(
                    "INSERT INTO NhanVien VALUES " +
                    "(2, 'Tran Thi B', 'Ke toan', 12000000)");

                stmt.executeUpdate(
                    "INSERT INTO NhanVien VALUES " +
                    "(3, 'Le Van C', 'Lap trinh vien', 18000000)");

                stmt.executeUpdate(
                    "INSERT INTO NhanVien VALUES " +
                    "(4, 'Pham Thi D', 'Quan ly', 20000000)");

                stmt.executeUpdate(
                    "INSERT INTO NhanVien VALUES " +
                    "(5, 'Hoang Van E', 'Ke toan', 14000000)");
            }

            // 4. Tinh tong, trung binh, cao nhat, thap nhat
            String sqlLuong =
                "SELECT SUM(luong) AS tongLuong, " +
                "AVG(luong) AS luongTB, " +
                "MAX(luong) AS luongCaoNhat, " +
                "MIN(luong) AS luongThapNhat " +
                "FROM NhanVien";

            rs = stmt.executeQuery(sqlLuong);

            if (rs.next()) {
                System.out.println("Tong luong: " +
                    rs.getDouble("tongLuong"));

                System.out.println("Luong trung binh: " +
                    rs.getDouble("luongTB"));

                System.out.println("Luong cao nhat: " +
                    rs.getDouble("luongCaoNhat"));

                System.out.println("Luong thap nhat: " +
                    rs.getDouble("luongThapNhat"));
            }

            rs.close();
            rs = null;

            // 5. Tim nhan vien co luong cao nhat
            String sqlMax =
                "SELECT ten, luong FROM NhanVien " +
                "WHERE luong = (SELECT MAX(luong) FROM NhanVien)";

            rs = stmt.executeQuery(sqlMax);

            System.out.println("\nNhan vien co luong cao nhat:");

            while (rs.next()) {
                System.out.println(
                    rs.getString("ten") + " - " +
                    rs.getDouble("luong"));
            }

            rs.close();
            rs = null;

            // 6. Tim nhan vien co luong thap nhat
            String sqlMin =
                "SELECT ten, luong FROM NhanVien " +
                "WHERE luong = (SELECT MIN(luong) FROM NhanVien)";

            rs = stmt.executeQuery(sqlMin);

            System.out.println("\nNhan vien co luong thap nhat:");

            while (rs.next()) {
                System.out.println(
                    rs.getString("ten") + " - " +
                    rs.getDouble("luong"));
            }

            rs.close();
            rs = null;

            // 7. Dem so nhan vien theo chuc vu
            String sqlCount =
                "SELECT chuc_vu, COUNT(*) AS soLuong " +
                "FROM NhanVien GROUP BY chuc_vu";

            rs = stmt.executeQuery(sqlCount);

            System.out.println("\nSo nhan vien theo chuc vu:");

            while (rs.next()) {
                System.out.println(
                    rs.getString("chuc_vu") + ": " +
                    rs.getInt("soLuong") + " nhan vien");
            }

            rs.close();
            rs = null;

        } catch (ClassNotFoundException e) {
            System.out.println("Khong tim thay driver JDBC!");

        } catch (SQLException e) {
            System.out.println("Loi SQL!");
            e.printStackTrace();

        } finally {
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