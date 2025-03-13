import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDBConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/library_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "root";  // Your MySQL us.ername
        String password = "dhiru5213"; // Your MySQL password

        // Try-with-resources for automatic resource management
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            if (conn != null) {
                System.out.println("✅ Database Connection Successful!");
            } else {
                System.out.println("❌ Connection returned null!");
            }
        } catch (SQLException e) {
            System.out.println("❌ Database Connection Failed!");
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("SQL State: " + e.getSQLState());
            e.printStackTrace();
        }
    }
}
