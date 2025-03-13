import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/library_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";  // Change if needed
    private static final String PASSWORD = "dhiru5213";  // Change your MySQL password

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ MySQL Driver Loaded Successfully!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("❌ MySQL Driver Not Found!", e);
        }
    }

    // ✅ Add Book (Prevents duplicates)
    public static void addBook(String title, String author, int year) {
        String query = "INSERT INTO books (title, author, year) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setInt(3, year);
            stmt.executeUpdate();

            System.out.println("✅ Book Added: " + title);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Get All Books Ordered by Title (with Serial Numbers)
    public static void getAllBooks() {
        String query = "SELECT title, author, year FROM books ORDER BY title ASC"; 
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\n📚 Book List:");
            int serialNo = 1; // Instead of using database IDs, we use serial numbers
            while (rs.next()) {
                System.out.printf("%d | %s | %s | %d\n", serialNo++, rs.getString("title"), rs.getString("author"), rs.getInt("year"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // ✅ Get List of Book Titles
public static List<String> getBookTitles() {
    List<String> bookTitles = new ArrayList<>();
    String query = "SELECT title FROM books ORDER BY title ASC";

    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {

        while (rs.next()) {
            bookTitles.add(rs.getString("title"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return bookTitles;
}


    // ✅ Delete Book by Title (Instead of ID)
    public static void deleteBook(String title) {
        String deleteQuery = "DELETE FROM books WHERE title = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {

            deleteStmt.setString(1, title);
            int rows = deleteStmt.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Book Deleted: " + title);
            } else {
                System.out.println("⚠️ Book Not Found: " + title);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
