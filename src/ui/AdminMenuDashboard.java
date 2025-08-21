
package ui;

import user.User;

import javax.swing.*;
import java.awt.*;

public class AdminMenuDashboard extends JFrame {
    private final User admin;
    private final LoginFrame login;

    public AdminMenuDashboard(User admin, LoginFrame login){
        super("Admin Dashboard");
        this.admin = admin; this.login = login;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel grid = new JPanel(new GridLayout(2,3,16,16));
        grid.setBorder(BorderFactory.createEmptyBorder(16,16,16,16));

        grid.add(makeTile("Add Food", () -> new AdminDashboard(admin, login).setVisible(true)));
        grid.add(makeTile("Log Food", () -> new UserDashboard(admin, login).setVisible(true)));
        grid.add(makeTile("Food Eaten Today", () -> new FoodTodayFrame(admin).setVisible(true)));
        grid.add(makeTile("Food History", () -> new FoodHistoryFrame(admin).setVisible(true)));
        grid.add(makeTile("Manage Accounts", () -> new ManageAccountsFrame(admin).setVisible(true)));
        grid.add(makeTile("All Users (View)", () -> new AdminUserListFrame(admin).setVisible(true)));

        add(grid, BorderLayout.CENTER);
        setSize(800,440);
        setLocationRelativeTo(null);
    }

    private JButton makeTile(String text, Runnable r){
        JButton b = new JButton(text);
        b.setPreferredSize(new java.awt.Dimension(200,120));
        b.addActionListener(e -> r.run());
        return b;
    }
}
