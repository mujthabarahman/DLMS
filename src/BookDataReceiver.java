import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDataReceiver {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/dlms";
    private static final String USER = "root";
    private static final String PASSWORD = "Mujthaba@7356";

    public static void main(String[] args) {
        List<Book> books = retrieveBooks();
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public static List<Book> retrieveBooks() {
        List<Book> books = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            statement = connection.createStatement();
            String query = "SELECT * FROM books";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String author = resultSet.getString("author");
                String category = resultSet.getString("category");
                String language = resultSet.getString("language");
                Date date = resultSet.getDate("date");
                Book book = new Book(name, author, category, language, date);
                books.add(book);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
}

class Book {
    private String name;
    private String author;
    private String category;
    private String language;
    private Date date;

    public Book(String name, String author, String category, String language, Date date) {
        this.name = name;
        this.author = author;
        this.category = category;
        this.language = language;
        this.date = date;
    }

    // Create getters and setters for the book properties
    // ...

    // Implement toString() method to print the book details
    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", language='" + language + '\'' +
                ", date=" + date +
                '}';
    }
}
