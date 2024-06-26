import java.math.BigDecimal;
import java.sql.*;
import java.util.Scanner;

public class EmployeeBackend {
    private Connection connection;

    public EmployeeBackend(Connection connection) {
        this.connection = connection;
    }

    public void addEmployee(String eid, String ename, String econtact, String eaddress, Date empJoiningDate,
            BigDecimal totalSales, BigDecimal eSalary) {
        try {
            String query = "INSERT INTO Employees (eid, ename, econtact, eaddress, emp_joing_date, total_sales, e_salary) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, eid);
                preparedStatement.setString(2, ename);
                preparedStatement.setString(3, econtact);
                preparedStatement.setString(4, eaddress);
                preparedStatement.setDate(5, empJoiningDate);
                preparedStatement.setBigDecimal(6, totalSales);
                preparedStatement.setBigDecimal(7, eSalary);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void top_performing_employee(int n) {
        try {
            String q = "SELECT eid, ename FROM Employees ORDER BY total_sales DESC LIMIT ?";
            try (PreparedStatement statement = connection.prepareStatement(q)) {
                statement.setInt(1, n);
                ResultSet rs = statement.executeQuery();
                System.out.println("Top " + n + " performing employees are:");
                while (rs.next()) {
                    String eid = rs.getString("eid");
                    String ename = rs.getString("ename");
                    System.out.println(ename + " " + eid);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void bottom_performing_employee(int n) {
        try {
            String q = "SELECT eid, ename FROM Employees ORDER BY total_sales ASC LIMIT ?";
            try (PreparedStatement statement = connection.prepareStatement(q)) {
                statement.setInt(1, n);
                ResultSet rs = statement.executeQuery();
                System.out.println("Bottom " + n + " performing employees are:");
                while (rs.next()) {
                    String eid = rs.getString("eid");
                    String ename = rs.getString("ename");
                    System.out.println(ename + " " + eid);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void top_bottom_salary(int n) {
        try {
            String q1 = "SELECT ename, eid FROM Employees ORDER BY e_salary DESC LIMIT ?";
            try (PreparedStatement statement1 = connection.prepareStatement(q1)) {
                statement1.setInt(1, n);
                ResultSet rs1 = statement1.executeQuery();
                System.out.println("Top " + n + " highest paid employees are:");
                while (rs1.next()) {
                    String ename = rs1.getString("ename");
                    String eid = rs1.getString("eid");
                    System.out.println(eid + " " + ename);
                }
            }

            String q2 = "SELECT ename, eid FROM Employees ORDER BY e_salary ASC LIMIT ?";
            try (PreparedStatement statement2 = connection.prepareStatement(q2)) {
                statement2.setInt(1, n);
                ResultSet rs2 = statement2.executeQuery();
                System.out.println("Top " + n + " lowest paid employees are:");
                while (rs2.next()) {
                    String ename = rs2.getString("ename");
                    String eid = rs2.getString("eid");
                    System.out.println(eid + " " + ename);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void show_employee_rating(String eid) {
        try {
            String q = "SELECT AVG(EmployeeRating) as emprating FROM Employees WHERE eid=?";
            try (PreparedStatement statement = connection.prepareStatement(q)) {
                statement.setString(1, eid);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    Float e_rating = rs.getFloat("emprating");
                    System.out.println(eid + " has a rating of: " + e_rating);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void details_of_employee(String seid) {
        try {
            String q = "SELECT * FROM Employees WHERE eid=?";
            try (PreparedStatement statement = connection.prepareStatement(q)) {
                statement.setString(1, seid);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        System.out.println(
                                rs.getString("eid") + " " + rs.getString("ename") + " " + rs.getString("econtact") + " "
                                        + rs.getString("eaddress") + " " + rs.getDate("emp_joing_date") + " "
                                        + rs.getBigDecimal("total_sales") + " " + rs.getBigDecimal("e_salary"));
                    } else {
                        System.out.println("No such employee found");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateemloyee_esalary(String seid, BigDecimal nesalary) {
        try {
            String q = "UPDATE Employees SET e_salary=? WHERE eid=?";
            try (PreparedStatement statement = connection.prepareStatement(q)) {
                statement.setBigDecimal(1, nesalary);
                statement.setString(2, seid);
                statement.executeUpdate();
                System.out.println("Updated successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updaeemployee_joiningdate(String seid, Date nemp_joining_date) {
        try {
            String q = "UPDATE Employees SET emp_joing_date=? WHERE eid=?";
            try (PreparedStatement statement = connection.prepareStatement(q)) {
                statement.setDate(1, nemp_joining_date);
                statement.setString(2, seid);
                statement.executeUpdate();
                System.out.println("Updated successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateemployee_name(String seid, String nename) {
        try {
            String q = "UPDATE Employees SET ename=? WHERE eid=?";
            try (PreparedStatement statement = connection.prepareStatement(q)) {
                statement.setString(1, nename);
                statement.setString(2, seid);
                statement.executeUpdate();
                System.out.println("Updated successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEmployeeContact(String seid, String necontact) {
        try {
            String q = "UPDATE Employees SET econtact=? WHERE eid=?";
            try (PreparedStatement statement = connection.prepareStatement(q)) {
                statement.setString(1, necontact);
                statement.setString(2, seid);
                statement.executeUpdate();
                System.out.println("Updated successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEmployeeAddress(String seid, String neaddress) {
        try {
            String q = "UPDATE Employees SET eaddress=? WHERE eid=?";
            try (PreparedStatement statement = connection.prepareStatement(q)) {
                statement.setString(1, neaddress);
                statement.setString(2, seid);
                statement.executeUpdate();
                System.out.println("Updated successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Connection connection = DBConnection.getConnection(); // Assuming this method exists in DBConnection class
            EmployeeBackend backend = new EmployeeBackend(connection);

            Scanner scanner = new Scanner(System.in);
            int choice;
            do {
                System.out.println("Employee Management System:");
                System.out.println("1. Add Employee");
                System.out.println("2. View Top Performing Employees");
                System.out.println("3. View Bottom Performing Employees");
                System.out.println("4. View Top and Bottom Salary Employees");
                System.out.println("5. View Employee Rating");
                System.out.println("6. View Employee Details");
                System.out.println("7. Update Employee Salary");
                System.out.println("8. Update Employee Joining Date");
                System.out.println("9. Update Employee Name");
                System.out.println("10. Update Employee Contact");
                System.out.println("11. Update Employee Address");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        // Add Employee
                        // Take user input for employee details and call addEmployee method
                        System.out.print("Enter employee ID: ");
                        String eid = scanner.next();
                        System.out.print("Enter employee name: ");
                        String ename = scanner.next();
                        System.out.print("Enter employee contact: ");
                        String econtact = scanner.next();
                        System.out.print("Enter employee address: ");
                        String eaddress = scanner.next();
                        System.out.print("Enter employee joining date (YYYY-MM-DD): ");
                        String joiningDateStr = scanner.next();
                        Date empJoiningDate = Date.valueOf(joiningDateStr);
                        System.out.print("Enter employee total sales: ");
                        BigDecimal totalSales = scanner.nextBigDecimal();
                        System.out.print("Enter employee salary: ");
                        BigDecimal eSalary = scanner.nextBigDecimal();
                        backend.addEmployee(eid, ename, econtact, eaddress, empJoiningDate, totalSales, eSalary);
                        break;
                    case 2:
                        // View Top Performing Employees
                        System.out.print("Enter number of top performing employees to view: ");
                        int topCount = scanner.nextInt();
                        backend.top_performing_employee(topCount);
                        break;
                    case 3:
                        // View Bottom Performing Employees
                        System.out.print("Enter number of bottom performing employees to view: ");
                        int bottomCount = scanner.nextInt();
                        backend.bottom_performing_employee(bottomCount);
                        break;
                    case 4:
                        // View Top and Bottom Salary Employees
                        System.out.print("Enter number of top and bottom salary employees to view: ");
                        int salaryCount = scanner.nextInt();
                        backend.top_bottom_salary(salaryCount);
                        break;
                    case 5:
                        // View Employee Rating
                        System.out.print("Enter employee ID: ");
                        String empId = scanner.next();
                        backend.show_employee_rating(empId);
                        break;
                    case 6:
                        // View Employee Details
                        System.out.print("Enter employee ID: ");
                        String empIdDetails = scanner.next();
                        backend.details_of_employee(empIdDetails);
                        break;
                    case 7:
                        // Update Employee Salary
                        System.out.print("Enter employee ID: ");
                        String empIdSalary = scanner.next();
                        System.out.print("Enter new salary: ");
                        BigDecimal newSalary = scanner.nextBigDecimal();
                        backend.updateemloyee_esalary(empIdSalary, newSalary);
                        break;
                    case 8:
                        // Update Employee Joining Date
                        System.out.print("Enter employee ID: ");
                        String empIdJoiningDate = scanner.next();
                        System.out.print("Enter new joining date (YYYY-MM-DD): ");
                        String newJoiningDateStr = scanner.next();
                        Date newJoiningDate = Date.valueOf(newJoiningDateStr);
                        backend.updaeemployee_joiningdate(empIdJoiningDate, newJoiningDate);
                        break;
                    case 9:
                        // Update Employee Name
                        System.out.print("Enter employee ID: ");
                        String empIdName = scanner.next();
                        System.out.print("Enter new name: ");
                        String newName = scanner.next();
                        backend.updateemployee_name(empIdName, newName);
                        break;
                    case 10:
                        // Update Employee Contact
                        System.out.print("Enter employee ID: ");
                        String empIdContact = scanner.next();
                        System.out.print("Enter new contact: ");
                        String newContact = scanner.next();
                        backend.updateEmployeeContact(empIdContact, newContact);
                        break;
                    case 11:
                        // Update Employee Address
                        System.out.print("Enter employee ID: ");
                        String empIdAddress = scanner.next();
                        System.out.print("Enter new address: ");
                        String newAddress = scanner.next();
                        backend.updateEmployeeAddress(empIdAddress, newAddress);
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
