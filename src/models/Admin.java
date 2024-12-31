package models;

public class Admin extends User {
    public Admin(String userId, String username, String password) {
        super(userId, username, password);
    }

    @Override
    public boolean login(String username, String password) {
        return this.getUsername().equals(username) && this.getPassword().equals(password);
    }

    @Override
    public String toString() {
        return "Admin [userId=" + getUserId() + ", username=" + getUsername() + "]";
    }
}
