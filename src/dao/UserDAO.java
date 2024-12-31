package dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import models.User;

public class UserDAO {
    private final String FILE_PATH = "resources/users.txt";

    public void saveUser(User user) {
        List<User> users = getAllUsers();
        users.add(user);
        writeToFile(users);
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        File file = new File(FILE_PATH);

        // Create the file if it doesn't exist
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs(); // Create directories if they don't exist
                file.createNewFile();          // Create the file if it doesn't exist
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
                return users;
            }
        }

        // Try reading the file if it has content
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            users = (List<User>) ois.readObject();
        } catch (EOFException e) {
            // File is empty, no action needed
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }

        return users;
    }

    private void writeToFile(List<User> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public User getUserByUsername(String username) {
        return getAllUsers().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public void updateUser(User updatedUser) {
        List<User> users = getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(updatedUser.getUsername())) {
                users.set(i, updatedUser);
                break;
            }
        }
        writeToFile(users);
    }

    public void deleteUser(String username) {
        List<User> users = getAllUsers();
        users.removeIf(user -> user.getUsername().equals(username));
        writeToFile(users);
    }
}
