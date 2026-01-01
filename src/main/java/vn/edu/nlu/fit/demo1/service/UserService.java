package vn.edu.nlu.fit.demo1.service;

import vn.edu.nlu.fit.demo1.model.User;
import java.util.HashMap;
import java.util.Map;

public class UserService {
    private static Map<String, User> userDatabase = new HashMap<>();

    static {
        User admin = new User("admin", "admin123", "Admin User", "admin@group15.com", "0912345678");
        admin.setAddress("TP. Hồ Chí Minh");
        admin.setGender("Nam");
        userDatabase.put("admin", admin);

        User user1 = new User("nguyenvana", "123456", "Nguyễn Văn A", "nguyenvana@gmail.com", "0912345678");
        user1.setAddress("TP. Hồ Chí Minh");
        user1.setGender("Nam");
        userDatabase.put("nguyenvana", user1);
    }

    public User authenticate(String username, String password) {
        if (username == null || password == null) {
            return null;
        }

        User user = userDatabase.get(username.trim());
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public boolean register(User user) {
        if (user == null || user.getUsername() == null) {
            return false;
        }

        if (userDatabase.containsKey(user.getUsername())) {
            return false;
        }

        userDatabase.put(user.getUsername(), user);
        return true;
    }

    public boolean isUsernameExists(String username) {
        return username != null && userDatabase.containsKey(username.trim());
    }

    public boolean isEmailExists(String email) {
        if (email == null) {
            return false;
        }

        String emailTrimmed = email.trim().toLowerCase();
        return userDatabase.values().stream()
                .anyMatch(user -> user.getEmail() != null &&
                        user.getEmail().trim().toLowerCase().equals(emailTrimmed));
    }

    public User getUserByUsername(String username) {
        if (username == null) {
            return null;
        }
        return userDatabase.get(username.trim());
    }

    public User getUserByEmail(String email) {
        if (email == null) {
            return null;
        }

        String emailTrimmed = email.trim().toLowerCase();
        return userDatabase.values().stream()
                .filter(user -> user.getEmail() != null &&
                        user.getEmail().trim().toLowerCase().equals(emailTrimmed))
                .findFirst()
                .orElse(null);
    }

    public boolean updateUser(User user) {
        if (user == null || user.getUsername() == null) {
            return false;
        }

        if (!userDatabase.containsKey(user.getUsername())) {
            return false;
        }

        userDatabase.put(user.getUsername(), user);
        return true;
    }

    public boolean changePassword(String username, String oldPassword, String newPassword) {
        User user = authenticate(username, oldPassword);
        if (user == null) {
            return false;
        }

        user.setPassword(newPassword);
        return updateUser(user);
    }

    public boolean resetPassword(String email, String newPassword) {
        User user = getUserByEmail(email);
        if (user == null) {
            return false;
        }

        user.setPassword(newPassword);
        return updateUser(user);
    }
}