import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class ProductBackend {
    private Connection connection;

    public ProductBackend(Connection connection) {
        this.connection = connection;
    }

    public void addProduct(String productId, String productName, int categoryId, BigDecimal price, int stockQuantity) {
        try {
            String query = "INSERT INTO Products (ProductID, ProductName, CategoryID, Price, StockQuantity) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, productId);
                preparedStatement.setString(2, productName);
                preparedStatement.setInt(3, categoryId);
                preparedStatement.setBigDecimal(4, price);
                preparedStatement.setInt(5, stockQuantity);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProductPrice(String spid, double npprice) {
        try {
            String q = "UPDATE product SET pprice=? WHERE pid=?";
            try (PreparedStatement statement = connection.prepareStatement(q)) {
                statement.setDouble(1, npprice);
                statement.setString(2, spid);
                statement.executeUpdate();
                System.out.println("Updated successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProductSupplier(String spid, String nsid) {
        try {
            String q = "UPDATE product SET sid=? WHERE pid=?";
            try (PreparedStatement statement = connection.prepareStatement(q)) {
                statement.setString(1, nsid);
                statement.setString(2, spid);
                statement.executeUpdate();
                System.out.println("Updated successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProductName(String spid, String spname) {
        try {
            String q = "UPDATE product SET pname=? WHERE pid=?";
            try (PreparedStatement statement = connection.prepareStatement(q)) {
                statement.setString(1, spname);
                statement.setString(2, spid);
                statement.executeUpdate();
                System.out.println("Updated successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProductQty(String spid, int npqty) {
        try {
            String q = "UPDATE product SET pqty=? WHERE pid=?";
            try (PreparedStatement statement = connection.prepareStatement(q)) {
                statement.setInt(1, npqty);
                statement.setString(2, spid);
                statement.executeUpdate();
                System.out.println("Updated successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProductDiscount(String spid, double ndiscount) {
        try {
            String q = "UPDATE product SET discount=? WHERE pid=?";
            try (PreparedStatement statement = connection.prepareStatement(q)) {
                statement.setDouble(1, ndiscount);
                statement.setString(2, spid);
                statement.executeUpdate();
                System.out.println("Updated successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProductBoughtOn(String spid, String nproductBoughtOn) {
        try {
            String q = "UPDATE product SET product_bought_on=? WHERE pid=?";
            try (PreparedStatement statement = connection.prepareStatement(q)) {
                statement.setString(1, nproductBoughtOn);
                statement.setString(2, spid);
                statement.executeUpdate();
                System.out.println("Updated successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void detailsOfProduct(String spid) {
        try {
            String q = "SELECT * FROM product WHERE pid=?";
            try (PreparedStatement statement = connection.prepareStatement(q)) {
                statement.setString(1, spid);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        System.out.println(
                                rs.getString("pid") + " " +
                                        rs.getDouble("price") + " " +
                                        rs.getInt("quantity") + " " +
                                        rs.getFloat("weight") + " " +
                                        rs.getDouble("discount") + " " +
                                        rs.getString("name") + " " +
                                        rs.getString("category") + " " +
                                        rs.getString("description"));
                    } else {
                        System.out.println("Product with ID " + spid + " not found.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showProductRating(String spid) {
        try {
            String q = "SELECT AVG(ProductRating) as avgproduct_rating FROM aftersales WHERE pid=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(q)) {
                preparedStatement.setString(1, spid);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        double proRating = rs.getDouble("avgproduct_rating");
                        System.out.println("The average rating for " + spid + " is: " + proRating);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void maxMinPriceProduct(int n) {
        try {
            String q1 = "SELECT pname, pid FROM product ORDER BY pprice DESC LIMIT ?";
            try (PreparedStatement statement1 = connection.prepareStatement(q1)) {
                statement1.setInt(1, n);
                try (ResultSet rs1 = statement1.executeQuery()) {
                    System.out.println("Product with highest price: ");
                    while (rs1.next()) {
                        String pname = rs1.getString("pname");
                        String pid = rs1.getString("pid");
                        System.out.println(pname + " " + pid);
                    }
                }
            }

            String q2 = "SELECT pname, pid FROM product ORDER BY pprice ASC LIMIT ?";
            try (PreparedStatement statement2 = connection.prepareStatement(q2)) {
                statement2.setInt(1, n);
                try (ResultSet rs2 = statement2.executeQuery()) {
                    System.out.println("Product with lowest price: ");
                    while (rs2.next()) {
                        String pname = rs2.getString("pname");
                        String pid = rs2.getString("pid");
                        System.out.println(pname + " " + pid);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void maxMinDiscount(int n) {
        try {
            String q1 = "SELECT pname, pid FROM product ORDER BY discount DESC LIMIT ?";
            try (PreparedStatement statement1 = connection.prepareStatement(q1)) {
                statement1.setInt(1, n);
                try (ResultSet rs1 = statement1.executeQuery()) {
                    System.out.println("Product with highest discount: ");
                    while (rs1.next()) {
                        String pname = rs1.getString("pname");
                        String pid = rs1.getString("pid");
                        System.out.println(pname + " " + pid);
                    }
                }
            }

            String q2 = "SELECT pname, pid FROM product ORDER BY discount ASC LIMIT ?";
            try (PreparedStatement statement2 = connection.prepareStatement(q2)) {
                statement2.setInt(1, n);
                try (ResultSet rs2 = statement2.executeQuery()) {
                    System.out.println("Product with lowest discount: ");
                    while (rs2.next()) {
                        String pname = rs2.getString("pname");
                        String pid = rs2.getString("pid");
                        System.out.println(pname + " " + pid);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void productBoughtOnDate(String date) {
        try {
            String q = "SELECT pid, pname FROM product WHERE product_bought_on=?";
            try (PreparedStatement statement = connection.prepareStatement(q)) {
                statement.setString(1, date);
                try (ResultSet rs = statement.executeQuery()) {
                    System.out.println("Product bought on " + date + " are: ");
                    while (rs.next()) {
                        String pname = rs.getString("pname");
                        String pid = rs.getString("pid");
                        System.out.println(pname + " " + pid);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Connection connection = DBConnection.getConnection(); // Assuming this method exists in DBConnection class
            ProductBackend backend = new ProductBackend(connection);

            Scanner scanner = new Scanner(System.in);
            int choice;
            do {
                System.out.println("Product Management System:");
                System.out.println("1. Add Product");
                System.out.println("2. Update Product Price");
                System.out.println("3. Update Product Supplier");
                System.out.println("4. Update Product Name");
                System.out.println("5. Update Product Quantity");
                System.out.println("6. Update Product Discount");
                System.out.println("7. Update Product Bought On Date");
                System.out.println("8. View Product Details");
                System.out.println("9. View Product Rating");
                System.out.println("10. View Products with Maximum and Minimum Price");
                System.out.println("11. View Products with Maximum and Minimum Discount");
                System.out.println("12. View Products Bought on Date");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        // Add Product
                        // Take user input for product details and call addProduct method
                        System.out.print("Enter Product ID: ");
                        String productId = scanner.next();
                        System.out.print("Enter Product Name: ");
                        String productName = scanner.next();
                        System.out.print("Enter Category ID: ");
                        int categoryId = scanner.nextInt();
                        System.out.print("Enter Price: ");
                        BigDecimal price = scanner.nextBigDecimal();
                        System.out.print("Enter Stock Quantity: ");
                        int stockQuantity = scanner.nextInt();
                        backend.addProduct(productId, productName, categoryId, price, stockQuantity);
                        break;

                    case 2:
                        // Update Product Price
                        System.out.print("Enter Product ID: ");
                        String productIdPrice = scanner.next();
                        System.out.print("Enter New Price: ");
                        double newPrice = scanner.nextDouble();
                        backend.updateProductPrice(productIdPrice, newPrice);
                        break;

                    case 3:
                        // Update Product Supplier
                        System.out.print("Enter Product ID: ");
                        String productIdSupplier = scanner.next();
                        System.out.print("Enter New Supplier ID: ");
                        String newSupplierId = scanner.next();
                        backend.updateProductSupplier(productIdSupplier, newSupplierId);
                        break;

                    case 4:
                        // Update Product Name
                        System.out.print("Enter Product ID: ");
                        String productIdName = scanner.next();
                        System.out.print("Enter New Name: ");
                        String newName = scanner.next();
                        backend.updateProductName(productIdName, newName);
                        break;

                    case 5:
                        // Update Product Quantity
                        System.out.print("Enter Product ID: ");
                        String productIdQuantity = scanner.next();
                        System.out.print("Enter New Quantity: ");
                        int newQty = scanner.nextInt();
                        backend.updateProductQty(productIdQuantity, newQty);
                        break;

                    case 6:
                        // Update Product Discount
                        System.out.print("Enter Product ID: ");
                        String productIdDiscount = scanner.next();
                        System.out.print("Enter New Discount: ");
                        double newDiscount = scanner.nextDouble();
                        backend.updateProductDiscount(productIdDiscount, newDiscount);
                        break;

                    case 7:
                        // Update Product Bought On Date
                        System.out.print("Enter Product ID: ");
                        String productIdBoughtOn = scanner.next();
                        System.out.print("Enter New Bought On Date (YYYY-MM-DD): ");
                        String newBoughtOnDate = scanner.next();
                        backend.updateProductBoughtOn(productIdBoughtOn, newBoughtOnDate);
                        break;

                    case 8:
                        // View Product Details
                        System.out.print("Enter Product ID: ");
                        String productIdDetails = scanner.next();
                        backend.detailsOfProduct(productIdDetails);
                        break;

                    case 9:
                        // View Product Rating
                        System.out.print("Enter Product ID: ");
                        String productIdRating = scanner.next();
                        backend.showProductRating(productIdRating);
                        break;

                    case 10:
                        // View Products with Maximum and Minimum Price
                        System.out.print("Enter number of products to view: ");
                        int numProductsPrice = scanner.nextInt();
                        backend.maxMinPriceProduct(numProductsPrice);
                        break;

                    case 11:
                        // View Products with Maximum and Minimum Discount
                        System.out.print("Enter number of products to view: ");
                        int numProductsDiscount = scanner.nextInt();
                        backend.maxMinDiscount(numProductsDiscount);
                        break;

                    case 12:
                        // View Products Bought on Date
                        System.out.print("Enter date (YYYY-MM-DD): ");
                        String date = scanner.next();
                        backend.productBoughtOnDate(date);
                        break;

                    case 0:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice!");
                        break;
                }
            } while (choice != 0);

            // Close connection when done
            connection.close();
            scanner.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}