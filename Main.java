import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Store Management System");
        System.out.println("Which data do you want to access?");
        System.out.println("1. After Sales");
        System.out.println("2. Sales");
        System.out.println("3. Supplier");
        System.out.println("4. Product");
        System.out.println("5. Miscellaneous");
        System.out.println("6. Employee");
        System.out.print("Enter your choice (1-6): ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                AfterSalesBackend.main(args);
                break;
            case 2:
                SalesBackend.main(args);
                break;
            case 3:
                SupplierBackend.main(args);
                break;
            case 4:
                ProductBackend.main(args);
                break;
            case 5:
                MiscellaneousBackend.main(args);
                break;
            case 6:
                EmployeeBackend.main(args);
                break;
            default:
                System.out.println("Invalid choice! Please enter a number between 1 and 6.");
        }

        scanner.close();
    }
}
