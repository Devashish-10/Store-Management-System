import java.sql.*;
import java.util.*;

public class AfterSalesBackend {
    private Connection connection;

    public AfterSalesBackend(Connection connection) {
        this.connection = connection;
    }

    public void addAfterSales() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter employee id of the employee who sold the product sold:");
            String empid = sc.next();
            System.out.println("Enter product id of product sold:");
            String pid = sc.next();
            System.out.println("Enter employee rating given to the employee:");
            float employeeRating = sc.nextFloat();
            System.out.println("Enter product rating received from customer of product sold:");
            float productRating = sc.nextFloat();
            System.out.println("Enter supplier id who supplied this product:");
            String sid = sc.next();

            String q = "INSERT INTO aftersales VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(q)) {
                statement.setString(1, empid);
                statement.setString(2, pid);
                statement.setFloat(3, employeeRating);
                statement.setFloat(4, productRating);
                statement.setString(5, sid);

                statement.executeUpdate();
            }
            System.out.println("After Sales record Updated!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showAfterSales() {
        try {
            String q = "SELECT * FROM aftersales";
            try (Statement statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery(q)) {
                while (rs.next()) {
                    System.out.println(
                            rs.getString(1) + " " + rs.getString(2) + " " + rs.getFloat(3) + " " + rs.getFloat(4)
                                    + " " + rs.getString(5));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Connection connection = DBConnection.getConnection();
            AfterSalesBackend backend = new AfterSalesBackend(connection);

            Scanner scanner = new Scanner(System.in);
            int choice;
            do {
                System.out.println("After Sales Management System:");
                System.out.println("1. Add After Sales Record");
                System.out.println("2. View After Sales Records");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        // Add After Sales Record
                        backend.addAfterSales();
                        break;
                    case 2:
                        // View After Sales Records
                        backend.showAfterSales();
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
