import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n📚 Library Management System");
            System.out.println("1. Add a Book");
            System.out.println("2. Show All Books");
            System.out.println("3. Delete a Book");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character
            
            if (choice == 1) {
                System.out.print("Enter book title: ");
                String title = scanner.nextLine();
                
                System.out.print("Enter author: ");
                String author = scanner.nextLine();
                
                System.out.print("Enter publication year: ");
                int year = scanner.nextInt();
                scanner.nextLine();  // Consume the newline
                
                BookDAO.addBook(title, author, year);
                System.out.println("✅ Book added successfully!");
            } 
            else if (choice == 2) {
                System.out.println("\n📖 Book List:");
                BookDAO.getAllBooks();
            } 
            else if (choice == 3) {
                System.out.println("\n📖 Book List:");
                List<String> bookTitles = BookDAO.getBookTitles(); // Get list of books
                if (bookTitles.isEmpty()) {
                    System.out.println("⚠️ No books available to delete!");
                    continue;
                }

                // Display books with serial numbers
                for (int i = 0; i < bookTitles.size(); i++) {
                    System.out.println((i + 1) + ". " + bookTitles.get(i));
                }

                System.out.print("Enter serial number of book to delete: ");
                int serialNumber = scanner.nextInt();
                scanner.nextLine();  // Consume the newline

                if (serialNumber < 1 || serialNumber > bookTitles.size()) {
                    System.out.println("❌ Invalid serial number! Try again.");
                    continue;
                }

                String bookToDelete = bookTitles.get(serialNumber - 1); // Get title from list
                BookDAO.deleteBook(bookToDelete);
                System.out.println("✅ Book deleted successfully!");
            }
            else if (choice == 4) {
                System.out.println("👋 Exiting... Goodbye!");
                break;
            } 
            else {
                System.out.println("❌ Invalid choice! Try again.");
            }
        }
        
        scanner.close();
    }
}
