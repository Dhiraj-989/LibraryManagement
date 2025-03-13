import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LibraryGUI {
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private JTextField bookText, authorText, yearText;

    public LibraryGUI() {
        // Create JFrame
        JFrame frame = new JFrame("Library Management System");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Panel for Adding Books
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Book Name:"));
        bookText = new JTextField(10);
        inputPanel.add(bookText);

        inputPanel.add(new JLabel("Author:"));
        authorText = new JTextField(10);
        inputPanel.add(authorText);

        inputPanel.add(new JLabel("Year:"));
        yearText = new JTextField(5);
        inputPanel.add(yearText);

        JButton addButton = new JButton("Add Book");
        inputPanel.add(addButton);

        frame.add(inputPanel, BorderLayout.NORTH);

        // Table to Display Books
        String[] columns = {"Serial No.", "Title", "Author", "Year"};
        tableModel = new DefaultTableModel(columns, 0);
        bookTable = new JTable(tableModel);
        
        // Add Table to ScrollPane
        JScrollPane scrollPane = new JScrollPane(bookTable);
        frame.add(scrollPane, BorderLayout.CENTER);  // ✅ Important! Add JScrollPane, not just JTable

        // Load Books from Database
        loadBooks();

        // Button Action - Add Book
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String bookName = bookText.getText();
                String author = authorText.getText();
                int year = Integer.parseInt(yearText.getText());

                addBookToDatabase(bookName, author, year);
                loadBooks(); // Refresh table after adding book
            }
        });

        frame.setVisible(true);
    }

    // Load Books from Database (With Serial Numbers)
    private void loadBooks() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db", "root", "dhiru5213");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT title, author, year FROM books ORDER BY title ASC")) { 

            tableModel.setRowCount(0); // Clear the table before adding new rows
            int serialNumber = 1; // ✅ Ensure it starts from 1

            while (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");
                int year = rs.getInt("year");

                tableModel.addRow(new Object[]{serialNumber++, title, author, year});  // ✅ Ensure incrementing properly
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Add Book to Database
    private void addBookToDatabase(String title, String author, int year) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db", "root", "dhiru5213");
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO books (title, author, year) VALUES (?, ?, ?)")) {

            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setInt(3, year);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibraryGUI());
    }
}
