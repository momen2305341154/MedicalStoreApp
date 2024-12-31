package main;

import models.*;
import services.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MedicalStoreApp {

    private static User currentUser = null; // Track logged-in user

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize services
        MedicineService medicineService = new MedicineService();
        CustomerService customerService = new CustomerService();
        SupplierService supplierService = new SupplierService();
        UserService userService = new UserService();

        boolean running = true;

        while (running) {
            System.out.println("\n--- Medical Store Management System ---");
            if (currentUser == null) {
                System.out.println("1. Login");
            } else {
                System.out.println("1. Logout (" + currentUser.getUsername() + ")");
            }
            System.out.println("2. Manage Medicines");
            System.out.println("3. Manage Customers");
            System.out.println("4. Manage Suppliers");
            System.out.println("5. Manage Users");
            System.out.println("6. Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    if (currentUser == null) {
                        login(scanner, userService); // Handle login
                    } else {
                        logout(); // Handle logout
                    }
                    break;
                case 2:
                    if (isLoggedIn()) {
                        try {
                            manageMedicines(scanner, medicineService);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }

                    }
                    break;
                case 3:
                    if (isLoggedIn()) manageCustomers(scanner, customerService);
                    break;
                case 4:
                    if (isLoggedIn()) manageSuppliers(scanner, supplierService);
                    break;
                case 5:
                    if (isLoggedIn()) manageUsers(scanner, userService);
                    break;
                case 6:
                    System.out.println("Exiting the system.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
        scanner.close();
    }

    private static void manageMedicines(Scanner scanner, MedicineService medicineService) throws ParseException {
        System.out.println("\n--- Manage Medicines ---");
        System.out.println("1. Add Medicine");
        System.out.println("2. Update Medicine");
        System.out.println("3. Delete Medicine");
        System.out.println("4. Get Medicine");
        System.out.print("Choose an option: ");
        int option = scanner.nextInt();

        switch (option) {
            case 1:
                // Adding a new medicine
                System.out.print("Enter Medicine ID: ");
                String id = scanner.next();
                System.out.print("Enter Medicine Name: ");
                String name = scanner.next();
                System.out.print("Enter Manufacturer: ");
                String manufacturer = scanner.next();
                System.out.print("Enter Quantity: ");
                int quantity = scanner.nextInt();
                System.out.print("Enter Price: ");
                double price = scanner.nextDouble();
                System.out.print("Enter Expiry Date (dd/MM/yyyy): "); // Prompt for expiry date
                String expiryDateString = scanner.next();

                // Parse the expiry date
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date expiryDate = null;

                // Handle the potential ParseException here
                try {
                    expiryDate = dateFormat.parse(expiryDateString);
                } catch (ParseException e) {
                    System.out.println("Invalid date format. Please use dd/MM/yyyy.");
                    return; // Exit the method if date parsing fails
                }
                Medicine newMedicine = new Medicine(id, name, manufacturer, quantity, price, expiryDate);
                medicineService.addMedicine(newMedicine);
                System.out.println("Medicine added successfully!");
                break;

            case 2:
                // Updating an existing medicine
                System.out.print("Enter Medicine ID to update: ");
                String updateId = scanner.next();
                Medicine updateMedicine = medicineService.getMedicineById(updateId);
                if (updateMedicine != null) {
                    System.out.print("Enter New Quantity: ");
                    updateMedicine.setQuantity(scanner.nextInt());
                    System.out.print("Enter New Price: ");
                    updateMedicine.setPrice(scanner.nextDouble());
                    medicineService.updateMedicine(updateMedicine);
                    System.out.println("Medicine updated successfully!");
                } else {
                    System.out.println("Medicine not found.");
                }
                break;

            case 3:
                // Deleting a medicine
                System.out.print("Enter Medicine ID to delete: ");
                String deleteId = scanner.next();
                medicineService.deleteMedicine(deleteId);
                System.out.println("Medicine deleted successfully!");
                break;

            case 4:
                // Retrieving a medicine
                System.out.print("Enter Medicine ID to retrieve: ");
                String getId = scanner.next();
                Medicine getMedicine = medicineService.getMedicineById(getId);
                if (getMedicine != null) {
                    System.out.println("Medicine: " + getMedicine);
                } else {
                    System.out.println("Medicine not found.");
                }
                break;

            default:
                System.out.println("Invalid option.");
        }
    }


    // Login functionality
    private static void login(Scanner scanner, UserService userService) {
        System.out.print("Enter Username: ");
        String username = scanner.next();
        System.out.print("Enter Password: ");
        String password = scanner.next();

        User user = userService.getUserByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            System.out.println("Login successful! Welcome, " + username + ".");
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    // Logout functionality
    private static void logout() {
        System.out.println("User " + currentUser.getUsername() + " logged out successfully.");
        currentUser = null;
    }

    // Check if a user is logged in
    private static boolean isLoggedIn() {
        if (currentUser == null) {
            System.out.println("You need to log in first.");
            return false;
        }
        return true;
    }

    // Method to manage users
    private static void manageUsers(Scanner scanner, UserService userService) {
        System.out.println("\n--- Manage Users ---");
        System.out.println("1. Add User");
        System.out.println("2. Update User");
        System.out.println("3. Delete User");
        System.out.println("4. Get User");
        System.out.print("Choose an option: ");
        int option = scanner.nextInt();

        switch (option) {
            case 1:
                System.out.print("Enter User ID: ");
                String id = scanner.next();
                System.out.print("Enter Username: ");
                String username = scanner.next();
                System.out.print("Enter Password: ");
                String password = scanner.next();

                User newUser = new StoreStaff(id, username, password);
                userService.addUser(newUser);
                System.out.println("User added successfully!");
                break;

            case 2:
                System.out.print("Enter Username to update: ");
                String updateUsername = scanner.next();
                User updateUser = userService.getUserByUsername(updateUsername);
                if (updateUser != null) {
                    System.out.print("Enter New Password: ");
                    updateUser.setPassword(scanner.next());
                    userService.updateUser(updateUser);
                    System.out.println("User updated successfully!");
                } else {
                    System.out.println("User not found.");
                }
                break;

            case 3:
                System.out.print("Enter Username to delete: ");
                String deleteUsername = scanner.next();
                userService.deleteUser(deleteUsername);
                System.out.println("User deleted successfully!");
                break;

            case 4:
                System.out.print("Enter Username to retrieve: ");
                String getUsername = scanner.next();
                User getUser = userService.getUserByUsername(getUsername);
                if (getUser != null) {
                    System.out.println("User: " + getUser);
                } else {
                    System.out.println("User not found.");
                }
                break;

            default:
                System.out.println("Invalid option.");
        }
    }

    // Method to manage customers
    private static void manageCustomers(Scanner scanner, CustomerService customerService) {
        System.out.println("\n--- Manage Customers ---");
        System.out.println("1. Add Customer");
        System.out.println("2. Update Customer");
        System.out.println("3. Delete Customer");
        System.out.println("4. Get Customer");
        System.out.print("Choose an option: ");
        int option = scanner.nextInt();

        switch (option) {
            case 1:
                System.out.print("Enter Customer ID: ");
                String id = scanner.next();
                System.out.print("Enter Customer Name: ");
                String name = scanner.next();
                System.out.print("Enter Customer Phone Number: ");
                String phoneNumber = scanner.next();
                List<Medicine> purchasedMedicines = new ArrayList<>();
                System.out.print("Enter the number of medicines: ");
                int numMedicines = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                for (int i = 0; i < numMedicines; i++) {
                    System.out.println("Enter details for medicine " + (i + 1) + ":");

                    System.out.print("Name: ");
                    String medicineName = scanner.nextLine();

                    System.out.print("Price: ");
                    double price = scanner.nextDouble();

                    System.out.print("Quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    // Add each Medicine to the list
                    purchasedMedicines.add(new Medicine(medicineName, price, quantity));
                }

                Customer newCustomer = new Customer(id, name, phoneNumber, purchasedMedicines);
                customerService.addCustomer(newCustomer);
                System.out.println("Customer added successfully!");
                break;

            case 2:
                System.out.print("Enter Customer ID to update: ");
                String updateId = scanner.next();
                Customer updateCustomer = customerService.getCustomerById(updateId);
                if (updateCustomer != null) {
                    System.out.print("Enter New Name: ");
                    updateCustomer.setName(scanner.next());
                    customerService.updateCustomer(updateCustomer);
                    System.out.println("Customer updated successfully!");
                } else {
                    System.out.println("Customer not found.");
                }
                break;

            case 3:
                System.out.print("Enter Customer ID to delete: ");
                String deleteId = scanner.next();
                customerService.deleteCustomer(deleteId);
                System.out.println("Customer deleted successfully!");
                break;

            case 4:
                System.out.print("Enter Customer ID to retrieve: ");
                String getId = scanner.next();
                Customer getCustomer = customerService.getCustomerById(getId);
                if (getCustomer != null) {
                    System.out.println("Customer: " + getCustomer);
                } else {
                    System.out.println("Customer not found.");
                }
                break;

            default:
                System.out.println("Invalid option.");
        }
    }

    // Method to manage suppliers
    private static void manageSuppliers(Scanner scanner, SupplierService supplierService) {
        System.out.println("\n--- Manage Suppliers ---");
        System.out.println("1. Add Supplier");
        System.out.println("2. Update Supplier");
        System.out.println("3. Delete Supplier");
        System.out.println("4. Get Supplier");
        System.out.print("Choose an option: ");
        int option = scanner.nextInt();

        switch (option) {
            case 1:
                System.out.print("Enter Supplier ID: ");
                String id = scanner.next();
                System.out.print("Enter Supplier Name: ");
                String name = scanner.next();
                System.out.print("Enter Supplier Phone Number: ");
                String contactInfo = scanner.next();
                System.out.print("Enter supplier's company name: ");
                String companyName = scanner.next();
                Supplier newSupplier = new Supplier(id, name, companyName, contactInfo);
                supplierService.addSupplier(newSupplier);
                System.out.println("Supplier added successfully!");
                break;

            case 2:
                System.out.print("Enter Supplier ID to update: ");
                String updateId = scanner.next();
                Supplier updateSupplier = supplierService.getSupplierById(updateId);
                if (updateSupplier != null) {
                    System.out.print("Enter New Name: ");
                    updateSupplier.setSupplierName(scanner.next());
                    System.out.print("Enter new contact name: ");
                    updateSupplier.setContactInfo(scanner.next());
                    System.out.print("Enter new company name: ");
                    updateSupplier.setCompanyName(scanner.next());

                    supplierService.updateSupplier(updateSupplier);

                    System.out.println("Supplier updated successfully!");
                } else {
                    System.out.println("Supplier not found.");
                }
                break;

            case 3:
                System.out.print("Enter Supplier ID to delete: ");
                String deleteId = scanner.next();
                supplierService.deleteSupplier(deleteId);
                System.out.println("Supplier deleted successfully!");
                break;

            case 4:
                System.out.print("Enter Supplier ID to retrieve: ");
                String getId = scanner.next();
                Supplier getSupplier = supplierService.getSupplierById(getId);
                if (getSupplier != null) {
                    System.out.println("Supplier: " + getSupplier);
                } else {
                    System.out.println("Supplier not found.");
                }
                break;

            default:
                System.out.println("Invalid option.");
        }
    }
}
