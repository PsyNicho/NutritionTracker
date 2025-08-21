
package nutritiontracker;

import connection.DatabaseConnection;
import ui.AdminDashboard;
import ui.LoginFrame;
import ui.UserDashboard;
import user.User;

import javax.swing.*;
import java.sql.Connection;

public class NutritionTracker {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
