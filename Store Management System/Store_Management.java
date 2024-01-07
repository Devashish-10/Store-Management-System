import java.util.*;

import java.sql.*;

public class Store_Management {
    private DBConnection connection;
    Scanner sc = new Scanner(System.in);

    public Store_Management(DBConnection connection) {
        this.connection = connection;
    }

    public void addProduct() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter product id:");
        String pid = sc.next();
        System.out.println("Enter product price:");
        double pprice = sc.nextDouble();
        System.out.println("Enter product quantity:");
        int pqty = sc.nextInt();
        System.out.println("Enter product rating:");
        double prating = sc.nextDouble();
        System.out.println("Enter discount% on the product from suppplier: ");
        float pdiscount = sc.nextFloat();
        System.out.print("Enter date of purchase of product in YYYY-MM-DD:");
        String product_bought_on = sc.next();
        System.out.println("Enter supplier id:");
        String sid = sc.next();
        System.out.println("Enter product name:");
        String pname = sc.next();
        try {
            Connection con = connection.getConnection();
            String q = "INSERT INTO  product values(pid,pprice,pqty,pdiscount,product_bought_on,sid,pname)";
            Statement statement = con.createStatement();
            int rowsAffected = statement.executeUpdate(q);
            if (rowsAffected > 0) {
                System.out.println("Record added in Product table successfully");
            } else {
                System.out.println("Cannot add Record in Product table");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sc.close();
    }

    public void addSupplier() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter supplier id:");
        String sid = sc.next();
        System.out.println("Enter supplier name:");
        String sname = sc.next();
        System.out.println("Enter supplier contactt no:");
        String scontact = sc.next();
        System.out.println("Enter product id of the product provided by the supplier:");
        String pid = sc.next();
        System.out.println("Give supplier a rating out of 5:");
        float srating = sc.nextInt();
        System.out.println("Enter supplier address:");
        String saddress = sc.next();
        try {
            Connection con = connection.getConnection();
            String sq = "SELECT AVG(ProductRating) AS s_rating FROM aftersales WHERE sid = '" + sid + "'";
            Statement statement1 = con.createStatement();
            ResultSet rs1 = statement1.executeQuery(sq);
            float avgrate = 0.0f;
            if (rs1.next()) {
                avgrate = rs1.getFloat("s_rating");
            }
            String q = "INSERT INTO  supplier values (sid,sname,scontact,pid,srating,saddress)";
            Statement statement = con.createStatement();
            statement.executeUpdate(q);
            System.out.println("Added Supplier successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addSales() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter product id of product sold:");
        String pid = sc.next();
        System.out.println("Enter supplier id of the product sold :");
        String sid = sc.next();
        System.out.println("Enter price at which the product was sold for:");
        double sp = sc.nextDouble();
        System.out.println("Enter customer name :");
        String customer_name = sc.next();
        System.out.println("Enter mode of payment for transaction:");
        String pay_mode = sc.next();
        System.out.println("Enter employee id of the employee who sold the product :");
        String empid = sc.next();
        System.out.println("Enter quantity of product sold: ");
        int qty = sc.nextInt();
        System.out.println("Enter date on which the sales was made in YYYY-MM-DD:");
        String sales_made_on = sc.next();
        try {
            Connection con = connection.getConnection();
            String sq = "SELECT cp  FROM product WHERE pid=product.pid";
            String q = "INSERT INTO  sales VALUES(pid,sid,cp,sp,profit,profit_perc,customer_name,pay_mode,empid)";
            PreparedStatement selectstatement = con.prepareStatement(sq);
            PreparedStatement insertstatement = con.prepareStatement(q);
            selectstatement.setString(1, pid);
            ResultSet rs = selectstatement.executeQuery();
            double cp = 0;
            if (rs.next()) {
                cp = rs.getDouble("pprice");
            }
            double profit = sp - cp;
            double profit_perc = (profit * 100) / cp;
            insertstatement.setString(1, pid);
            insertstatement.setString(2, sid);
            insertstatement.setDouble(3, cp);
            insertstatement.setDouble(4, sp);
            insertstatement.setDouble(5, profit);
            insertstatement.setDouble(6, profit_perc);
            insertstatement.setString(7, customer_name);
            insertstatement.setString(8, pay_mode);
            insertstatement.setString(9, empid);
            insertstatement.setInt(10, qty);
            insertstatement.setString(11, sales_made_on);
            System.out.println("Sales Recod Added successfuully!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addAftersales() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter employee id of the employee who sold the product sold :");
        String empid = sc.next();
        System.out.println("Enter product id of product sold:");
        String pid = sc.next();
        System.out.println("Enter employee rating given to the employee:");
        float EmployeeRating = sc.nextFloat();
        System.out.println("Enter product rating received from customer of product sold:");
        float ProductRating = sc.nextFloat();
        System.out.println("Enter supplier id who supplied this product: ");
        String sid = sc.next();

        try {
            Connection con = connection.getConnection();
            String q = "INSERT INTO  aftersales values (empid,pid,EmployeeRating,ProductRating,sid)";
            Statement statement = con.createStatement();
            statement.executeUpdate(q);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("After Sales record Updated!");
    }

    public void addmiscellaneous() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter amount of money withdrawn:");
        double amount = sc.nextDouble();
        System.out.println("Enter date of withrawal in YYYY-MM-DD format:");
        String date_of_expense = sc.next();
        System.out.println("Enter cause of withdrawl of money :");
        String cause = sc.next();
        try {
            Connection con = connection.getConnection();
            String q = "INSERT INTO  miscellaneous values (amount,date_of_expense,cause)";
            Statement statement = con.createStatement();
            statement.executeUpdate(q);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Miscellaneous Expense of Store added");
    }

    public void addEmployee() throws SQLException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Employee id:");
        String empid = sc.next();
        System.out.println("Enter Employee name:");
        String ename = sc.next();
        System.out.println("Enter Employee contact no:");
        String econtact = sc.next();
        System.out.println("Enter employee address:");
        String eaddress = sc.next();
        System.out.println("Enter employee date of joining in dd-mm-yy format:");
        String emp_joining_date = sc.next();
        System.out.println("Enter Employee salary:");
        double esalary = sc.nextDouble();

        try {
            Connection con = connection.getConnection();
            String sq = "SELECT SUM(qty * sp) AS total_sales FROM sales WHERE empid = ?";
            PreparedStatement salesStatement = con.prepareStatement(sq);
            salesStatement.setString(1, empid);
            ResultSet rs = salesStatement.executeQuery();

            double totalSales = 0;
            if (rs.next()) {
                totalSales = rs.getDouble("total_sales");
            }

            String q = "INSERT INTO employee (empid, ename, econtact, eaddress, emp_joining_date, total_sales, esalary) "
                    +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement insertStatement = con.prepareStatement(q);
            insertStatement.setString(1, empid);
            insertStatement.setString(2, ename);
            insertStatement.setString(3, econtact);
            insertStatement.setString(4, eaddress);
            insertStatement.setString(5, emp_joining_date);
            insertStatement.setDouble(6, totalSales);
            insertStatement.setDouble(7, esalary);

            int rowsAffected = insertStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("New Employee record added");
            } else {
                System.out.println("Failed to add Employee record!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }

    public void analysesales() throws SQLException {
        try {
            Connection con = connection.getConnection();
            String q = "SELECT pid from sales ORDER BY profit_perc DESC LIMIT 5 ";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(q);
            if (rs.next()) {
                System.out.println("Top 5 profit product are:");
                do {
                    String pid = rs.getString("pid");
                    System.out.println("pid");
                } while (rs.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void analyseemployee() throws SQLException {
        try {
            Connection con = connection.getConnection();
            String q = "SELECT eid from sales ORDER BY total_sales DESC LIMIT 5";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(q);
            if (rs.next()) {
                System.out.println("Top 5 sales made by following employee");
                do {
                    String eid = rs.getString("eid");
                    System.out.println(eid);
                } while (rs.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showproduct() throws SQLException {
        try {
            Connection con = connection.getConnection();
            String q = "SELECT * from product";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(q);
            while (rs.next()) {
                System.out.println(
                        rs.getString(1) + " " + rs.getDouble(2) + " " + rs.getInt(3) + " " + rs.getFloat(4) + " "
                                + " " + rs.getString(5) + " " + rs.getString(6) + " " + rs.getString(7));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showsales() throws SQLException {
        try {
            Connection con = connection.getConnection();
            String q = "SELECT * from sales";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(q);
            while (rs.next()) {
                System.out
                        .println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getDouble(3) + " " + rs.getDouble(4)
                                + " " + rs.getDouble(5) + " " + rs.getFloat(6) + " " + rs.getString(7) + " "
                                + rs.getString(8) + " "
                                + rs.getString(9) + " " + rs.getInt(10) + " " + rs.getString(11));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showaftersales() throws SQLException {
        try {
            Connection con = connection.getConnection();
            String q = "SELECT * from aftersales";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(q);
            while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getFloat(3) + " " + rs.getFloat(4)
                        + " " + rs.getString(5));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showemployee() throws SQLException {
        try {
            Connection con = connection.getConnection();
            String q = "SELECT * from employee";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(q);
            while (rs.next()) {
                System.out
                        .println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4)
                                + " " + rs.getString(5) + " " + rs.getDouble(6) + " " + rs.getDouble(7));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showmiscellaneous() throws SQLException {
        try {
            Connection con = connection.getConnection();
            String q = "SELECT * from miscellaneous";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(q);
            while (rs.next()) {
                System.out.println(rs.getDouble(1) + " " + rs.getString(2) + " " + rs.getString(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showsupplier() throws SQLException {
        try {
            Connection con = connection.getConnection();
            String q = "SELECT * from supplier";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(q);
            while (rs.next()) {
                System.out
                        .println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4)
                                + " " + rs.getFloat(5) + " " + rs.getString(6));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateproduct_price(String spid, double npprice) throws SQLException {
        try {
            Connection con = connection.getConnection();
            String q = "UPDATE product SET pprice=npprice WHERE product.pid=spid";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(q);
            System.out.println("Updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateproduct_supplier(String spid, String nsid) throws SQLException {
        try {
            Connection con = connection.getConnection();
            String q = "UPDATE product SET product.sid=nsidWHERE product.pid=spid";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(q);
            System.out.println("Updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateproduct_name(String spid, String spname) throws SQLException {
        try {
            Connection con = connection.getConnection();
            String q = "UPDATE product SET product.pname=spname WHERE product.pid=spid";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(q);
            System.out.println("Updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateproduct_qty(String spid, int npqty) throws SQLException {
        try {
            Connection con = connection.getConnection();
            String q = "UPDATE product SET product.pqty=npqtty WHERE product.pid=spid";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(q);
            System.out.println("Updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateproduct_discount(String spid, double ndiscount) throws SQLException {
        try {
            Connection con = connection.getConnection();
            String q = "UPDATE product SET product.discount=ndsicount WHERE product.pid=spid";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(q);
            System.out.println("Updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateproduct_boughton(String spid, String nproduct_bought_on) throws SQLException {
        try {
            Connection con = connection.getConnection();
            String q = "UPDATE product SET product.product_bought_on=nproduct_bought_on WHERE product.pid=spid";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(q);
            System.out.println("Updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateemployee_name(String seid, String nename) throws SQLException {
        try {
            Connection con = connection.getConnection();
            String q = "UPDATE employee SET employee.ename=sename WHERE employee.eid=seid";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(q);
            System.out.println("Updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updaeemployee_contact(String seid, String necontact) throws SQLException {
        try {
            Connection con = connection.getConnection();
            String q = "UPDATE employee SET employee.econtact=necontact WHERE employee.eid=seid";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(q);
            System.out.println("Updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateemployee_address(String seid, String neaddress) throws SQLException {
        try {
            Connection con = connection.getConnection();
            String q = "UPDATE employee SET employee.eaddress=neaddress WHERE employee.eid=seid";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(q);
            System.out.println("Updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updaeemployee_joiningdate(String seid, String nemp_joing_date) throws SQLException {
        try {
            Connection con = connection.getConnection();
            String q = "UPDATE employee SET employee.emp_joing_date=nemp_joing_date WHERE employee.eid=seid";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(q);
            System.out.println("Updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateemloyee_esalary(String seid, Double nesalary) throws SQLException {
        try {
            Connection con = connection.getConnection();
            String q = "UPDATE employee SET employee.esalary=nesalary WHERE employee.eid=seid";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(q);
            System.out.println("Updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatesupplier_name(String ssid, String nsname) throws SQLException {
        try {
            Connection con = connection.getConnection();
            String q = "UPDATE supplier SET supplier.sname=nsname WHERE supplier.sid=ssid";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(q);
            System.out.println("Updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatesupplier_contact(String ssid, String nscontact) throws SQLException {
        try {
            Connection con = connection.getConnection();
            String q = "UPDATE supplier SET supplier.scontact=nscontact WHERE supplier.sid=ssid";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(q);
            System.out.println("Updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatesupplier_product(String ssid, String npid) throws SQLException {
        try {
            Connection con = connection.getConnection();
            String q = "UPDATE supplier SET supplier.pid=npid WHERE supplier.sid=ssid";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(q);
            System.out.println("Updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatesupplier_address(String ssid, String nsaddress) throws SQLException {
        try {
            Connection con = connection.getConnection();
            String q = "UPDATE supplier SET supplier.saddress=nsaddress WHERE supplier.sid=ssid";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(q);
            System.out.println("Updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void details_of_product(String spid) throws SQLException {
        try (Connection con = connection.getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT * FROM product WHERE pid=?")) {
            ps.setString(1, spid);
            try (ResultSet rs = ps.executeQuery()) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void details_of_supplier(String ssid) throws SQLException {
        try (Connection con = connection.getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT * FROM supplier WHERE sid=?")) {
            ps.setString(1, ssid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println(
                            rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4)
                                    + " "
                                    + rs.getFloat(5) + " " + rs.getString(6));
                } else {
                    System.out.println("No such supplier found");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void details_of_employee(String seid) throws SQLException {
        try (Connection con = connection.getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT * FROM employee WHERE eid=?")) {
            ps.setString(1, seid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println(
                            rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4)
                                    + " "
                                    + rs.getString(5) + " " + rs.getDouble(6) + " " + rs.getDouble(7));
                } else {
                    System.out.println("No such em[ployee found");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sales_on_date(String sdate) throws SQLException {
        try {
            Connection con = connection.getConnection();
            String q = "SELECT * FROM sales WHERE sales.pid=sdate";
            PreparedStatement statement = con.prepareStatement(q);
            ResultSet rs = statement.executeQuery();
            System.out.println("Sales made on " + sdate + "are :");
            if (rs.next()) {
                do {
                    System.out.println(
                            rs.getString(1) + " " + rs.getString(2) + " " + rs.getDouble(3) + " " + rs.getDouble(4)
                                    + " "
                                    + rs.getDouble(5) + " " + rs.getFloat(6) + " " + rs.getString(7) + " "
                                    + rs.getString(8)
                                    + " " + rs.getString(9) + " " + rs.getInt(10) + " " + rs.getString(11));
                } while (rs.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showemprating(String seid) {

        try {
            Connection con = connection.getConnection();
            String q = "SELECT AVG(EmployeeRating) as avg_rating FROM aftersales WHERE aftersales.eid=seid";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(q);
            if (rs.next()) {
                double avgrating = rs.getDouble("avg_rating");
                System.out.println("Employee Rtaing for " + seid + "is :" + avgrating);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showproductrating(String spid) {

        try {
            Connection con = connection.getConnection();
            String q = "SELECT AVG(ProductRating) as avgproduct_rating FROM aftersales WHERE aftersales.pid=spid";
            PreparedStatement preparedStatement = con.prepareStatement(q);
            preparedStatement.setString(1, spid);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                double pro_rating = rs.getDouble("avgproduct_rating");
                System.out.println("The average rating for " + spid + " is :" + pro_rating);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void showsupplierrating(String ssid) {

        try {
            Connection con = connection.getConnection();
            String q = "SELECT AVG(ProductRating)as avg_srate FROM aftersales WHERE aftersales.sid=ssid";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(q);
            if (rs.next()) {
                double srate = rs.getDouble("avg_srate");
                System.out.println("Average rating for " + ssid + "is :" + srate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void top_bottom_salary(int n) {
        try {
            Connection con = connection.getConnection();
            String q1 = "SELECT ename,eid FROM employee ORDER BY e_salary DESC LIMIT = ?";
            PreparedStatement statement1 = con.prepareStatement(q1);
            statement1.setInt(1, n);
            ResultSet rs1 = statement1.executeQuery();
            System.out.println("Top " + n + " highest paid employee are:");
            while (rs1.next()) {

                String ename = rs1.getString("ename");
                String eid = rs1.getString("eid");
                System.out.println(eid + " " + ename);
            }
            String q2 = "SELECT ename,eid FROM employee ORDER BY e_salary ASC LIMIT = ?";
            PreparedStatement statement2 = con.prepareStatement(q1);
            statement2.setInt(1, n);
            ResultSet rs2 = statement2.executeQuery();
            System.out.println("Top " + n + " lowest paid employee are:");
            while (rs2.next()) {

                String ename = rs2.getString("ename");
                String eid = rs2.getString("eid");
                System.out.println(eid + " " + ename);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void top_performing_employee(int n) {

        try {
            Connection con = connection.getConnection();
            String q = "SELECT  eid,ename FROM employee ORDER BY total_sales DESC LIMIT =?";
            PreparedStatement statement = con.prepareStatement(q);
            statement.setInt(1, n);
            ResultSet rs = statement.executeQuery();
            System.out.println("Top " + n + " performing employee are:");
            while (rs.next()) {
                String eid = rs.getString(("eid"));
                String ename = rs.getString("ename");
                System.out.println(ename + " " + eid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void bottom_performing_employee(int n) {

        try {
            Connection con = connection.getConnection();
            String q = "SELECT  eid,ename FROM employee ORDER BY total_sales ASC LIMIT =?";
            PreparedStatement statement = con.prepareStatement(q);
            statement.setInt(1, n);
            ResultSet rs = statement.executeQuery();
            System.out.println("Bottom " + n + " performing employee are:");
            while (rs.next()) {
                String eid = rs.getString(("eid"));
                String ename = rs.getString("ename");
                System.out.println(ename + " " + eid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void max_min_price_product(int n) {
        try {
            Connection con = connection.getConnection();
            String q1 = "SELECT pname,pid from product ORDER BY pprice DESC LIMIT ?";
            PreparedStatement statement1 = con.prepareStatement(q1);
            statement1.setInt(1, n);
            ResultSet rs1 = statement1.executeQuery();
            System.out.println("Product with highest price: ");
            while (rs1.next()) {
                String pname = rs1.getString("pname");
                String pid = rs1.getString("pid");
                System.out.println(pname + " " + pid);
            }
            String q2 = "SELECT pname,pid from product ORDER BY pprice ASC LIMIT ?";
            PreparedStatement statement2 = con.prepareStatement(q2);
            statement2.setInt(1, n);
            ResultSet rs2 = statement2.executeQuery();
            System.out.println("Product with lowest price: ");
            while (rs2.next()) {
                String pname = rs1.getString("pname");
                String pid = rs1.getString("pid");
                System.out.println(pname + " " + pid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void max_min_discount(int n) {
        try {
            Connection con = connection.getConnection();
            String q1 = "SELECT pname,pid from product ORDER BY pdiscount DESC LIMIT ?";
            PreparedStatement statement1 = con.prepareStatement(q1);
            statement1.setInt(1, n);
            ResultSet rs1 = statement1.executeQuery();
            System.out.println("Product with highest discount: ");
            while (rs1.next()) {
                String pname = rs1.getString("pname");
                String pid = rs1.getString("pid");
                System.out.println(pname + " " + pid);
            }
            String q2 = "SELECT pname,pid from product ORDER BY pdiscount ASC LIMIT ?";
            PreparedStatement statement2 = con.prepareStatement(q2);
            statement2.setInt(1, n);
            ResultSet rs2 = statement2.executeQuery();
            System.out.println("Product with lowest discount: ");
            while (rs2.next()) {
                String pname = rs1.getString("pname");
                String pid = rs1.getString("pid");
                System.out.println(pname + " " + pid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void top_profit_sales(int n) throws SQLException {
        try {
            Connection con = connection.getConnection();
            String q = "SELECT * FROM sales ORDER BY profit_perc DESC LIMIT=?";
            PreparedStatement statement = con.prepareStatement(q);
            statement.setInt(1, n);
            ResultSet rs = statement.executeQuery();
            System.out.println("Top 5 profitable sales:");
            while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getDouble(3) + " "
                        + rs.getDouble(4) + " " + rs.getDouble(5) + " " + rs.getFloat(6) + " " + rs.getString(7) + " "
                        + rs.getString(8) + " " + rs.getString(9) + " " + rs.getInt(10) + " " + rs.getString(11));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bottom_profit_sales(int n) throws SQLException {
        try {
            Connection con = connection.getConnection();
            String q = "SELECT * FROM sales ORDER BY profit_perc ASC LIMIT=?";
            PreparedStatement statement = con.prepareStatement(q);
            statement.setInt(1, n);
            ResultSet rs = statement.executeQuery();
            System.out.println("Top 5 profitable sales:");
            while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getDouble(3) + " "
                        + rs.getDouble(4) + " " + rs.getDouble(5) + " " + rs.getFloat(6) + " " + rs.getString(7) + " "
                        + rs.getString(8) + " " + rs.getString(9) + " " + rs.getInt(10) + " " + rs.getString(11));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void product_bought_on_date(String date) {
        try {
            Connection con = connection.getConnection();
            String q = "SELECT pid,pnmae FROM product WHERE product_bought_on=?";
            PreparedStatement statement = con.prepareStatement(q);
            statement.setString(1, date);
            ResultSet rs = statement.executeQuery();
            System.out.println("Prodcut bought on " + date + "are: ");
            while (rs.next()) {
                String pname = rs.getString("pname");
                String pid = rs.getString("pid");
                System.out.println(pname + " " + pid);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void customer_bought_products(String c_name) throws SQLException {
        try {
            Connection con = connection.getConnection();
            String q = "SELECT pid,qty,sp FROM sales WHERE customer_name=?";
            PreparedStatement statement = con.prepareStatement(q);
            statement.setString(1, c_name);
            ResultSet rs = statement.executeQuery();
            System.out.println(c_name + " Bought these product:");
            while (rs.next()) {
                String pid = rs.getString("pid");
                int qty = rs.getInt("qty");
                double sp = rs.getDouble("sp");
                System.out.println(pid + " " + qty + " " + sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void show_employee_rating(String eid) {
        try {
            Connection con = connection.getConnection();
            String q = "SELECT AVG(EmployeeRating) as emprating WHERE eid=?";
            PreparedStatement statement = con.prepareStatement(q);
            statement.setString(1, eid);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Float e_rateing = rs.getFloat("emprating");
                System.out.println(eid + " has a rating of : " + e_rateing);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void money_taken_out_on_date(String date) {
        try {
            Connection con = connection.getConnection();
            String q = "SELECT amount,cause FROM miscellaneous WHERE date_of_expense=?";
            PreparedStatement statement = con.prepareStatement(q);
            statement.setString(1, date);
            ResultSet rs = statement.executeQuery();
            System.out.println("The following transactions were made on that day: ");
            if (rs.next()) {
                double amount = rs.getDouble("amount");
                String cause = rs.getString("cause");
                System.out.println(amount + " " + cause);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws SQLException {
        try {
            String url = "jdbc:mysql://localhost:3306/storedb";
            String user = "username";
            String pass = "password";
            DBConnection connection = new DBConnection(url, user, pass);
            connection.connect();
            Store_Management sm = new Store_Management(connection);
            Scanner sc = new Scanner(System.in);
            System.out.println(
                    "Press 1 for Product \n 2 for Supplier\n 3 for Employee \n 4 for Sales \n 5 for aftersales service \n 6 for Miscellaneous ");
            int ch = sc.nextInt();
            switch (ch) {
                case 1:
                    System.out.println(
                            "Press 1 for Showing Product Table \n 2 for Adding Product \n 3 for Seeing top n products with hightest lowest price  \n 4 to watch product with most and lowest discount \n 5 to watch product bought on specific date \n 6 to see Product rating");
                    int pch = sc.nextInt();
                    switch (pch) {
                        case 1:
                            sm.showproduct();
                            break;
                        case 2:
                            sm.addProduct();
                            break;
                        case 3:
                            System.out.println(
                                    "Enter value for n where determines the no of top or bottom products to be seen with respect to product price ");
                            int n = sc.nextInt();
                            sm.max_min_price_product(n);
                            break;
                        case 4:
                            System.out.println(
                                    "Enter value for n where determines the no of top or bottom products to be seen with respect to discount");
                            int n2 = sc.nextInt();
                            sm.max_min_discount(n2);
                            break;
                        case 5:
                            System.out.println("Enter date in YYYY-MM-DD to watch which products bought on that day:");
                            String date = sc.next();
                            sm.product_bought_on_date(date);
                            break;
                        case 6:
                            System.out.println("Enter pid of Product whose rating you wish to see:");
                            String pid = sc.next();
                            sm.showproductrating(pid);
                        default:
                            System.out.println("Enter a valid option please");

                    }
                    break;
                case 2:
                    System.out
                            .println("Press 1 to see supplier table \n 2 to add supplier \n 3 to see supllier rating");
                    int sch = sc.nextInt();
                    switch (sch) {
                        case 1:
                            sm.showsupplier();
                            break;
                        case 2:
                            sm.addSupplier();
                            break;
                        case 3:
                            System.out.println("Enter supplier id whose rating you want to see:");
                            String sid = sc.next();
                            sm.showsupplierrating(sid);
                            break;
                        default:
                            System.out.println("Enter a valid option");

                    }
                    break;
                case 3:
                    System.out.println(
                            "Press 1 to see employee table \n 2 to add employee \n 3 to see employee rating \n 4 to see top performing employee \n 5 to see bottom performing employee \n 6 to check highest and lowest paid employee ");
                    int ech = sc.nextInt();
                    switch (ech) {
                        case 1:
                            sm.showemployee();
                            break;
                        case 2:
                            sm.addEmployee();
                            break;
                        case 3:
                            System.out.println("Enter ID of the Employee whose rating you wish to see:");
                            String empid = sc.next();
                            sm.showemprating(empid);
                            break;
                        case 4:
                            System.out.println("Enter value of n where n display top no of employee: ");
                            int n = sc.nextInt();
                            sm.top_performing_employee(n);
                            break;
                        case 5:
                            System.out.println("Enter value of n where n display top no of employee: ");
                            int n2 = sc.nextInt();
                            sm.bottom_performing_employee(n2);
                            break;
                        case 6:
                            System.out.println("Enter value of n where n display top no/bottom no of employee: ");
                            int n3 = sc.nextInt();
                            sm.top_bottom_salary(n3);
                            break;
                        default:
                            System.out.println("Enter a valid option");

                    }
                    break;
                case 4:
                    System.out.println(
                            "Press 1 to see sales record \n 2 to add a new sales record \3 to Analyse Sales record");
                    int salesch = sc.nextInt();
                    switch (salesch) {
                        case 1:
                            sm.showsales();
                            break;
                        case 2:
                            sm.addSales();
                            break;
                        case 3:
                            System.out.println(
                                    ("Press 1 to see particular sales record for a day \n 2 to check top profitable sales \n 3 to check bottom profit percentage \n 4 to check what a customer buys:"));
                            int analyzesales = sc.nextInt();
                            switch (analyzesales) {
                                case 1:
                                    System.out.println("Enter date whose sales recordd you want to check:");
                                    String date = sc.next();
                                    sm.sales_on_date(date);
                                    break;
                                case 2:
                                    System.out.println(
                                            "Enter value of n where n is number of top profitable sale record");
                                    int n = sc.nextInt();
                                    sm.top_profit_sales(n);
                                    break;
                                case 3:
                                    System.out.println(
                                            "Enter value of n where n is number of bottom profitable sale record");
                                    int n2 = sc.nextInt();
                                    sm.bottom_profit_sales(n2);
                                case 4:
                                    System.out.println("Enter a customer whose purchased item you want to check:");
                                    String customer_name = sc.next();
                                    sm.customer_bought_products(customer_name);
                                    break;
                                default:
                                    System.out.println("Enter a valid option");

                            }
                    }
                case 5:
                    System.out.println(
                            "Press 1 to show aftersales \n 2 to add record to aftersales:\n 3 to see product review");
                    int ach = sc.nextInt();
                    switch (ach) {
                        case 1:
                            sm.showaftersales();
                            break;
                        case 2:
                            sm.addAftersales();
                            break;
                        case 3:
                            System.out.println("Enter product id  whose rating you want ot see:");
                            String pid = sc.next();
                            sm.showproductrating(pid);
                            break;
                        case 4:
                            System.out.println("Enter employee id  whose rating you want ot see:");
                            String eid = sc.next();
                            sm.show_employee_rating(eid);
                            break;
                        default:
                            System.out.println("Enter a valid choice");
                    }
                case 6:
                    System.out.println(
                            "Press 1 to see miscellaneous table \n 2 to add record to mscellaneous \n 3 to check if money is taken out at a particular date:");
                    int mch = sc.nextInt();
                    switch (mch) {
                        case 1:
                            sm.showmiscellaneous();
                            break;
                        case 2:
                            sm.addmiscellaneous();
                            break;
                        case 3:
                            System.out.println(
                                    "Enter the date(YYYY-MM-DD)on which you want to check money is taken out for miscellaneous expense:");
                            String date = sc.next();
                            sm.money_taken_out_on_date(date);
                            break;
                        default:
                            System.out.println("Enter a valid option");
                    }

            }
            sc.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}