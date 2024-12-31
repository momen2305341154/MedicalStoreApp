package models;

import java.io.Serializable;
import java.util.List;

public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    private String customerId;
    private String name;
    private String phoneNumber;  // This represents the contact information
    private List<Medicine> purchasedMedicines;

    // Constructor with all fields
    public Customer(String customerId, String name, String phoneNumber, List<Medicine> purchasedMedicines) {
        this.customerId = customerId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.purchasedMedicines = purchasedMedicines;
    }





    // Getter and Setter methods
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Medicine> getPurchasedMedicines() {
        return purchasedMedicines;
    }

    public void setPurchasedMedicines(List<Medicine> purchasedMedicines) {
        this.purchasedMedicines = purchasedMedicines;
    }

    // Correct implementation of setContactInfo (replaces phoneNumber)
    public void setContactInfo(String contactInfo) {
        this.phoneNumber = contactInfo;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", purchasedMedicines=" + purchasedMedicines +
                '}';
    }
}
