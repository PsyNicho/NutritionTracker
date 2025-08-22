package dao;

import user.User;
import java.util.List;

public interface UserDAO {
    void addUser(User user);
    User getUserByUsername(String username);
    User getUserById(int id);
    List<User> getAllUsers();
    List<User> getAllUsersByStatus(String status);
    List<User> getAllUsersByRole(String role);

    // NEW
    void updatePassword(int userId, String newPassword);
    void deleteUser(int userId);
}
