import java.sql.*;
import java.util.Scanner;

public class SupplierBackend {
    private Connection connection;

    public SupplierBackend(Connection connection) {
        this.connection = connection;
    }

    public void addSupplier() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter supplier id:");
            String sid = sc.next();
            System.out.println("Enter supplier name:");
            String sname = sc.next();
            System.out.println("Enter supplier contact no:");
            String scontact = sc.next();
            System.out.println("Enter product id of the product provided by the supplier:");
            String pid = sc.next();
            System.out.println("Give supplier a rating out of 5:");
            float srating = sc.nextFloat();
            System.out.println("Enter supplier address:");
            String saddress = sc.next();

            String avgRatingQuery = "SELECT AVG(ProductRating) AS s_rating FROM aftersales WHERE sid = ?";
            String insertQuery = "INSERT INTO supplier VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement avgRatingStatement = connection.prepareStatement(avgRatingQuery);
                    PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {

                avgRatingStatement.setString(1, sid);
                ResultSet rs1 = avgRatingStatement.executeQuery();
                float avgrate = 0.0f;
                if (rs1.next()) {
                    avgrate = rs1.getFloat("s_rating");
                }

                insertStatement.setString(1, sid);
                insertStatement.setString(2, sname);
                insertStatement.setString(3, scontact);
                insertStatement.setString(4, pid);
                insertStatement.setFloat(5, Math.max(avgrate, srating));
                insertStatement.setString(6, saddress);

                insertStatement.executeUpdate();
                System.out.println("Added Supplier successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showSuppliers() {
        try {
            String q = "SELECT * FROM supplier";
            try (Statement statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery(q)) {
                while (rs.next()) {
                    System.out.println(
                            rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4)
                                    + " " + rs.getFloat(5) + " " + rs.getString(6));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSupplierName(String ssid, String nsname) {
        try {
            String q = "UPDATE supplier SET sname=? WHERE sid=?";
            try (PreparedStatement statement = connection.prepareStatement(q)) {
                statement.setString(1, nsname);
                statement.setString(2, ssid);
                statement.executeUpdate();
                System.out.println("Supplier name updated successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Implement methods to update contact, product, and address similar to the name
    // update method

    public void updateSupplierRating(String ssid, float nsrating) {
        try {
            String q = "UPDATE supplier SET srating=? WHERE sid=?";
            try (PreparedStatement statement = connection.prepareStatement(q)) {
                statement.setFloat(1, nsrating);
                statement.setString(2, ssid);
                statement.executeUpdate();
                System.out.println("Supplier rating updated successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void highestRatedSuppliers(int n) {
        try {
            String q = "SELECT * FROM supplier ORDER BY srating DESC LIMIT ?";
            try (PreparedStatement statement = connection.prepareStatement(q)) {
                statement.setInt(1, n);
                try (ResultSet rs = statement.executeQuery()) {
                    System.out.println("Highest Rated Suppliers:");
                    while (rs.next()) {
                        System.out.println(
                                rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4)
                                        + " " + rs.getFloat(5) + " " + rs.getString(6));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void lowestRatedSuppliers(int n) {
        try {
            String q = "SELECT * FROM supplier ORDER BY srating ASC LIMIT ?";
            try (PreparedStatement statement = connection.prepareStatement(q)) {
                statement.setInt(1, n);
                try (ResultSet rs = statement.executeQuery()) {
                    System.out.println("Lowest Rated Suppliers:");
                    while (rs.next()) {
                        System.out.println(
                                rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4)
                                        + " " + rs.getFloat(5) + " " + rs.getString(6));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Connection connection = DBConnection.getConnection();
            SupplierBackend backend = new SupplierBackend(connection);

            Scanner scanner = new Scanner(System.in);
            int choice;
            do {
                System.out.println("Supplier Management System:");
                System.out.println("1. Add Supplier");
                System.out.println("2. View Suppliers");
                System.out.println("3. Update Supplier Name");
                // Add similar menu options for updating contact, product, and address
                System.out.println("4. Update Supplier Rating");
                System.out.println("5. View Highest Rated Suppliers");
                System.out.println("6. View Lowest Rated Suppliers");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        // Add Supplier
                        backend.addSupplier();
                        break;
                    case 2:
                        // View Suppliers
                        backend.showSuppliers();
                        break;
                    case 3:
                        // Update Supplier Name
                        System.out.print("Enter Supplier ID: ");
                        String sid = scanner.next();
                        System.out.print("Enter New Supplier Name: ");
                        String nsname = scanner.next();
                        backend.updateSupplierName(sid, nsname);
                        break;
                    // Add cases for updating contact, product, and address
                    case 4:
                        // Update Supplier Rating
                        System.out.print("Enter Supplier ID: ");
                        sid = scanner.next();
                        System.out.print("Enter New Supplier Rating: ");
                        float nsrating = scanner.nextFloat();
                        backend.updateSupplierRating(sid, nsrating);
                        break;
                    case 5:
                        // View Highest Rated Suppliers
                        System.out.print("Enter Number of Suppliers to View: ");
                        int nHighest = scanner.nextInt();
                        backend.highestRatedSuppliers(nHighest);
                        break;
                    case 6:
                        // View Lowest Rated Suppliers
                        System.out.print("Enter Number of Suppliers to View: ");
                        int nLowest = scanner.nextInt();
                        backend.lowestRatedSuppliers(nLowest);
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
