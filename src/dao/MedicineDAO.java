package dao;

import models.Medicine;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MedicineDAO {
    private final String FILE_PATH = "resources/medicines.txt";

    // Save a single medicine object to file
    public void saveMedicine(Medicine medicine) {
        List<Medicine> medicines = getAllMedicines();
        medicines.add(medicine);
        writeToFile(medicines);
    }

    // Fetch a list of all medicines
    public List<Medicine> getAllMedicines() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>(); // Return empty list if file doesn't exist
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Medicine>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading from file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Write list of medicines to the file
    private void writeToFile(List<Medicine> medicines) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(medicines);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Get a medicine by its ID
    public Medicine getMedicineById(String id) {
        return getAllMedicines().stream()
                .filter(medicine -> medicine.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Update a medicine in the file
    public void updateMedicine(Medicine updatedMedicine) {
        List<Medicine> medicines = getAllMedicines();
        for (int i = 0; i < medicines.size(); i++) {
            if (medicines.get(i).getId().equals(updatedMedicine.getId())) {
                medicines.set(i, updatedMedicine);
                break;
            }
        }
        writeToFile(medicines);
    }

    // Delete a medicine by its ID
    public void deleteMedicine(String id) {
        List<Medicine> medicines = getAllMedicines();
        medicines.removeIf(medicine -> medicine.getId().equals(id));
        writeToFile(medicines);
    }
}
