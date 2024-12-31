package dao;

import models.Supplier;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {
    private final String FILE_PATH = "resources/suppliers.txt";

    // Constructor to ensure the resources directory exists
    public SupplierDAO() {
        File directory = new File("resources");
        if (!directory.exists()) {
            directory.mkdir(); // Create the directory if it doesn't exist
        }
    }

    // Save a single supplier object to file
    public void saveSupplier(Supplier supplier) {
        List<Supplier> suppliers = getAllSuppliers(); // Retrieve existing suppliers
        suppliers.add(supplier); // Add the new supplier
        writeToFile(suppliers); // Write the updated list back to file
    }

    // Fetch a list of all suppliers
    public List<Supplier> getAllSuppliers() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>(); // Return empty list if file doesn't exist
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Supplier>) ois.readObject();
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
            return new ArrayList<>(); // Return empty list on error
        } catch (ClassNotFoundException e) {
            System.out.println("Supplier class not found: " + e.getMessage());
            return new ArrayList<>(); // Return empty list on error
        }
    }

    // Write list of suppliers to the file
    private void writeToFile(List<Supplier> suppliers) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(suppliers);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Get a supplier by its ID
    public Supplier getSupplierById(String id) {
        return getAllSuppliers().stream()
                .filter(supplier -> supplier != null && supplier.getSupplierId() != null && supplier.getSupplierId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Update a supplier in the file
    public void updateSupplier(Supplier updatedSupplier) {
        List<Supplier> suppliers = getAllSuppliers();
        for (int i = 0; i < suppliers.size(); i++) {
            if (suppliers.get(i).getSupplierId().equals(updatedSupplier.getSupplierId())) {
                suppliers.set(i, updatedSupplier);
                writeToFile(suppliers); // Write back to the file after updating
                return; // Exit after update
            }
        }
        System.out.println("Supplier not found for update.");
    }

    // Delete a supplier by its ID
    public void deleteSupplier(String id) {
        List<Supplier> suppliers = getAllSuppliers();
        boolean removed = suppliers.removeIf(supplier -> supplier.getSupplierId().equals(id));
        if (removed) {
            writeToFile(suppliers); // Write back to the file after deletion
        } else {
            System.out.println("Supplier not found for deletion.");
        }
    }
}
