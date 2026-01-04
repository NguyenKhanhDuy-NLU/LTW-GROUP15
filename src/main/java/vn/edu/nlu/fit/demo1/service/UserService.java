package vn.edu.nlu.fit.demo1.service;

import vn.edu.nlu.fit.demo1.dao.UserDAO;
import vn.edu.nlu.fit.demo1.model.User;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public User authenticate(String username, String password) {
        return userDAO.authenticate(username, password);
    }

    public boolean register(User user) {
        return userDAO.register(user);
    }

    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }

    public boolean changePassword(String username, String oldPassword, String newPassword) {
        return userDAO.changePassword(username, oldPassword, newPassword);
    }

    public boolean isUsernameExists(String username) {
        return userDAO.isUsernameExists(username);
    }

    public boolean isEmailExists(String email) {
        return userDAO.isEmailExists(email);
    }

    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    public User getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }
}