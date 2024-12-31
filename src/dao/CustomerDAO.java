package dao;

import models.Customer;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private final String FILE_PATH = "resources/customers.txt";

    // Save a customer to file
    public void saveCustomer(Customer customer) {
        List<Customer> customers = getAllCustomers();
        customers.add(customer);
        writeToFile(customers);
    }

    // Fetch all customers
    public List<Customer> getAllCustomers() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>(); // Return empty list if file doesn't exist
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Customer>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading from file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Write customers list to file
    private void writeToFile(List<Customer> customers) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(customers);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Get customer by ID
    public Customer getCustomerById(String id) {
        return getAllCustomers().stream()
                .filter(customer -> customer.getCustomerId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Update a customer
    public void updateCustomer(Customer updatedCustomer) {
        List<Customer> customers = getAllCustomers();
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getCustomerId().equals(updatedCustomer.getCustomerId())) {
                customers.set(i, updatedCustomer);
                break;
            }
        }
        writeToFile(customers);
    }

    // Delete a customer by ID
    public void deleteCustomer(String id) {
        List<Customer> customers = getAllCustomers();
        customers.removeIf(customer -> customer.getCustomerId().equals(id));
        writeToFile(customers);
    }
    public void addCustomer(Customer customer) {
        List<Customer> customers = getAllCustomers();  // Fetch existing customers
        customers.add(customer);                       // Add new customer
        writeToFile(customers);                        // Save back to file
    }
}
