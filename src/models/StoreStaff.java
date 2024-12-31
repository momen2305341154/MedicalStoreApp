package models;

public class StoreStaff extends User {

    // Constructor
    public StoreStaff(String userId, String username, String password) {
        super(userId, username, password); // Calls the parent class constructor
    }

    // Override login method (optional if you want specific behavior for StoreStaff)
    @Override
    public boolean login(String username, String password) {
        return getUsername().equals(username) && getPassword().equals(password);
    }

    @Override
    public String toString() {
        return "StoreStaff [userId=" + getUserId() + ", username=" + getUsername() + "]";
    }
}
