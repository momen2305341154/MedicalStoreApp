package services;

import dao.CustomerDAO;
import models.Customer;

public class CustomerService {
    private CustomerDAO customerDAO;

    // Constructor to initialize the DAO (Assuming DAO implementation exists)
    public CustomerService() {
        this.customerDAO = new CustomerDAO(); // This assumes that CustomerDAO has a default constructor
    }

    // Method to add a new customer
    public void addCustomer(Customer customer) {
        customerDAO.addCustomer(customer);  // Assuming CustomerDAO handles file/database operations
    }

    // Method to retrieve customer by ID
    public Customer getCustomerById(String id) {
        return customerDAO.getCustomerById(id);  // Assuming CustomerDAO has this method implemented
    }

    // Method to update a customer
    public void updateCustomer(Customer updateCustomer) {
        customerDAO.updateCustomer(updateCustomer);  // Assuming CustomerDAO handles the update logic
    }

    // Method to delete a customer by ID
    public void deleteCustomer(String deleteId) {
        customerDAO.deleteCustomer(deleteId);  // Assuming CustomerDAO handles the deletion logic
    }
}
