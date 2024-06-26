import java.sql.*;
import java.util.*;

public class MiscellaneousBackend {
    private Connection connection;

    public MiscellaneousBackend(Connection connection) {
        this.connection = connection;
    }

    public void addMiscellaneous() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter amount of money withdrawn:");
            double amount = sc.nextDouble();
            System.out.println("Enter date of withdrawal in YYYY-MM-DD format:");
            String date_of_expense = sc.next();
            System.out.println("Enter cause of withdrawal of money :");
            String cause = sc.next();

            String q = "INSERT INTO miscellaneous VALUES (?, ?, ?)";
            sc.close();

            try (PreparedStatement statement = connection.prepareStatement(q)) {
                statement.setDouble(1, amount);
                statement.setString(2, date_of_expense);
                statement.setString(3, cause);

                statement.executeUpdate();
            }
            System.out.println("Miscellaneous Expense of Store added");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showMiscellaneous() {
        try {
            String q = "SELECT * FROM miscellaneous";
            try (Statement statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery(q)) {
                while (rs.next()) {
                    System.out.println(rs.getDouble(1) + " " + rs.getString(2) + " " + rs.getString(3));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Connection connection = DBConnection.getConnection();
            MiscellaneousBackend backend = new MiscellaneousBackend(connection);

            Scanner scanner = new Scanner(System.in);
            int choice;
            do {
                System.out.println("Miscellaneous Expense Management System:");
                System.out.println("1. Add Miscellaneous Expense");
                System.out.println("2. View Miscellaneous Expenses");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        // Add Miscellaneous Expense
                        backend.addMiscellaneous();
                        break;
                    case 2:
                        // View Miscellaneous Expenses
                        backend.showMiscellaneous();
                        break;
                    case 0:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice!");
                        break;
                }
            } while (choice != 0);

            connection.close();
            scanner.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
