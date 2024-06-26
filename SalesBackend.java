import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;
import java.util.Scanner;

public class SalesBackend {
    private Connection connection;

    public SalesBackend(Connection connection) {
        this.connection = connection;
    }

    public void addSale(String pid, String sid, BigDecimal cp, BigDecimal sp, BigDecimal profit,
            BigDecimal profitPercentage, String customerName, String payMode, String eid, int qty, Date salesMadeOn) {
        try {
            String query = "INSERT INTO Sales (pid, sid, cp, sp, profit, profit_perc, customer_name, pay_mode, eid, qty, sales_made_on) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, pid);
                preparedStatement.setString(2, sid);
                preparedStatement.setBigDecimal(3, cp);
                preparedStatement.setBigDecimal(4, sp);
                preparedStatement.setBigDecimal(5, profit);
                preparedStatement.setBigDecimal(6, profitPercentage);
                preparedStatement.setString(7, customerName);
                preparedStatement.setString(8, payMode);
                preparedStatement.setString(9, eid);
                preparedStatement.setInt(10, qty);
                preparedStatement.setDate(11, new java.sql.Date(salesMadeOn.getTime()));

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void customerBoughtProducts(String cName) {
        try {
            String q = "SELECT pid, qty, sp FROM sales WHERE customer_name=?";
            try (PreparedStatement statement = connection.prepareStatement(q)) {
                statement.setString(1, cName);
                try (ResultSet rs = statement.executeQuery()) {
                    System.out.println(cName + " bought these products:");
                    while (rs.next()) {
                        String pid = rs.getString("pid");
                        int qty = rs.getInt("qty");
                        double sp = rs.getDouble("sp");
                        System.out.println(pid + " " + qty + " " + sp);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void bottomProfitSales(int n) {
        try {
            String q = "SELECT * FROM sales ORDER BY profit_perc ASC LIMIT ?";
            try (PreparedStatement statement = connection.prepareStatement(q)) {
                statement.setInt(1, n);
                try (ResultSet rs = statement.executeQuery()) {
                    System.out.println("Bottom " + n + " profitable sales:");
                    while (rs.next()) {
                        System.out.println(
                                rs.getString(1) + " " + rs.getString(2) + " " + rs.getDouble(3) + " "
                                        + rs.getDouble(4) + " " + rs.getDouble(5) + " " + rs.getFloat(6) + " "
                                        + rs.getString(7) + " " + rs.getString(8) + " " + rs.getString(9) + " "
                                        + rs.getInt(10) + " " + rs.getString(11));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void topProfitSales(int n) {
        try {
            String q = "SELECT * FROM sales ORDER BY profit_perc DESC LIMIT ?";
            try (PreparedStatement statement = connection.prepareStatement(q)) {
                statement.setInt(1, n);
                try (ResultSet rs = statement.executeQuery()) {
                    System.out.println("Top " + n + " profitable sales:");
                    while (rs.next()) {
                        System.out.println(
                                rs.getString(1) + " " + rs.getString(2) + " " + rs.getDouble(3) + " "
                                        + rs.getDouble(4) + " " + rs.getDouble(5) + " " + rs.getFloat(6) + " "
                                        + rs.getString(7) + " " + rs.getString(8) + " " + rs.getString(9) + " "
                                        + rs.getInt(10) + " " + rs.getString(11));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void salesOnDate(String sDate) {
        try {
            String q = "SELECT * FROM sales WHERE sales_made_on=?";
            try (PreparedStatement statement = connection.prepareStatement(q)) {
                statement.setString(1, sDate);
                try (ResultSet rs = statement.executeQuery()) {
                    System.out.println("Sales made on " + sDate + " are:");
                    while (rs.next()) {
                        System.out.println(
                                rs.getString(1) + " " + rs.getString(2) + " " + rs.getDouble(3) + " "
                                        + rs.getDouble(4) + " " + rs.getDouble(5) + " " + rs.getFloat(6) + " "
                                        + rs.getString(7) + " " + rs.getString(8) + " " + rs.getString(9) + " "
                                        + rs.getInt(10) + " " + rs.getString(11));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showSales() {
        try {
            String q = "SELECT * FROM sales";
            try (Statement statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery(q)) {
                while (rs.next()) {
                    System.out.println(
                            rs.getString(1) + " " + rs.getString(2) + " " + rs.getDouble(3) + " "
                                    + rs.getDouble(4) + " " + rs.getDouble(5) + " " + rs.getFloat(6) + " "
                                    + rs.getString(7) + " " + rs.getString(8) + " " + rs.getString(9) + " "
                                    + rs.getInt(10) + " " + rs.getString(11));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void analyseEmployee() {
        try {
            String q = "SELECT eid FROM sales ORDER BY total_sales DESC LIMIT 5";
            try (Statement statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery(q)) {
                if (rs.next()) {
                    System.out.println("Top 5 sales made by the following employees:");
                    do {
                        String eid = rs.getString("eid");
                        System.out.println(eid);
                    } while (rs.next());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void analyseSales() {
        try {
            String q = "SELECT pid FROM sales ORDER BY profit_perc DESC LIMIT 5";
            try (Statement statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery(q)) {
                if (rs.next()) {
                    System.out.println("Top 5 profit products are:");
                    do {
                        String pid = rs.getString("pid");
                        System.out.println(pid);
                    } while (rs.next());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addSales() {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Enter product id of product sold:");
            String pid = sc.next();
            System.out.println("Enter supplier id of the product sold:");
            String sid = sc.next();
            System.out.println("Enter price at which the product was sold for:");
            double sp = sc.nextDouble();
            System.out.println("Enter customer name:");
            String customerName = sc.next();
            System.out.println("Enter mode of payment for transaction:");
            String payMode = sc.next();
            System.out.println("Enter employee id of the employee who sold the product:");
            String empId = sc.next();
            System.out.println("Enter quantity of product sold:");
            int qty = sc.nextInt();
            System.out.println("Enter date on which the sales was made in YYYY-MM-DD:");
            String salesMadeOn = sc.next();

            try {
                String cpQuery = "SELECT pprice FROM product WHERE pid=?";
                String insertQuery = "INSERT INTO sales VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement selectStatement = connection.prepareStatement(cpQuery);
                        PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                    selectStatement.setString(1, pid);
                    ResultSet rs = selectStatement.executeQuery();
                    double cp = 0;
                    if (rs.next()) {
                        cp = rs.getDouble("pprice");
                    }
                    double profit = sp - cp;
                    double profitPercentage = (profit * 100) / cp;

                    insertStatement.setString(1, pid);
                    insertStatement.setString(2, sid);
                    insertStatement.setDouble(3, cp);
                    insertStatement.setDouble(4, sp);
                    insertStatement.setDouble(5, profit);
                    insertStatement.setDouble(6, profitPercentage);
                    insertStatement.setString(7, customerName);
                    insertStatement.setString(8, payMode);
                    insertStatement.setString(9, empId);
                    insertStatement.setInt(10, qty);
                    insertStatement.setString(11, salesMadeOn);

                    insertStatement.executeUpdate();
                    System.out.println("Sales Record Added successfully!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            Connection connection = DBConnection.getConnection(); // Assuming this method exists in DBConnection class
            SalesBackend backend = new SalesBackend(connection);

            Scanner scanner = new Scanner(System.in);
            int choice;
            do {
                System.out.println("Sales Management System:");
                System.out.println("1. Add Sale");
                System.out.println("2. View Products Bought by Customer");
                System.out.println("3. View Bottom Profitable Sales");
                System.out.println("4. View Top Profitable Sales");
                System.out.println("5. View Sales Made on Date");
                System.out.println("6. View All Sales");
                System.out.println("7. Analyze Employee Sales");
                System.out.println("8. Analyze Product Sales");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        // Add Sale
                        // Take user input for sale details and call addSale method
                        // backend.addSale(pid, sid, cp, sp, profit, profitPercentage, customerName,
                        // payMode, eid, qty, salesMadeOn);
                        break;
                    case 2:
                        // View Products Bought by Customer
                        System.out.print("Enter customer name: ");
                        String customerName = scanner.next();
                        backend.customerBoughtProducts(customerName);
                        break;
                    case 3:
                        // View Bottom Profitable Sales
                        System.out.print("Enter number of bottom profitable sales to view: ");
                        int bottomCount = scanner.nextInt();
                        backend.bottomProfitSales(bottomCount);
                        break;
                    case 4:
                        // View Top Profitable Sales
                        System.out.print("Enter number of top profitable sales to view: ");
                        int topCount = scanner.nextInt();
                        backend.topProfitSales(topCount);
                        break;
                    case 5:
                        // View Sales Made on Date
                        System.out.print("Enter date (YYYY-MM-DD): ");
                        String salesDate = scanner.next();
                        backend.salesOnDate(salesDate);
                        break;
                    case 6:
                        // View All Sales
                        backend.showSales();
                        break;
                    case 7:
                        // Analyze Employee Sales
                        backend.analyseEmployee();
                        break;
                    case 8:
                        // Analyze Product Sales
                        backend.analyseSales();
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