package dao;

import user.User;
import java.util.List;

public interface UserDAO {
    void addUser(User user);
    User getUserByUsername(String username);
    User getUserById(int id);
    List<User> getAllUsers();

    // NEW
    void updatePassword(int userId, String newPassword);
    void deleteUser(int userId);
}
