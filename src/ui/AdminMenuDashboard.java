package ui;

import user.User;

import javax.swing.*;
import java.awt.*;

public class AdminMenuDashboard extends JFrame {
    private final User admin;
    private final LoginFrame login;

    public AdminMenuDashboard(User admin, LoginFrame login){
        super("Admin Dashboard");
        this.admin = admin; 
        this.login = login;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Sidebar panel for image, username, and logout
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(Color.WHITE);
        sidebar.setBorder(BorderFactory.createEmptyBorder(32, 16, 32, 16));

        // Load image (save as admin.png in src/ui)
        JLabel imgLabel = new JLabel();
        ImageIcon icon = new ImageIcon(getClass().getResource("admin.png"));
        Image scaledImg = icon.getImage().getScaledInstance(208, 208, Image.SCALE_SMOOTH); // Half of 415x416 is ~208x208
        imgLabel.setIcon(new ImageIcon(scaledImg));
        imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Username label
        JLabel userLabel = new JLabel(admin.getUsername());
        userLabel.setFont(new Font("Arial", Font.BOLD, 18));
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userLabel.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));

        // Logout button
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutBtn.setBackground(new Color(220, 53, 69));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setMaximumSize(new Dimension(120, 36));
        logoutBtn.addActionListener(e -> {
            dispose();
            login.backToLogin();
        });

        sidebar.add(imgLabel);
        sidebar.add(userLabel);
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(logoutBtn);

        // Dashboard grid
        JPanel grid = new JPanel(new GridLayout(2,3,16,16));
        grid.setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        grid.add(makeTile("Add Food", () -> new AddFoodFrame(admin).setVisible(true)));
        grid.add(makeTile("Log Food", () -> new LogFoodFrame(admin).setVisible(true)));
        grid.add(makeTile("Food Eaten Today", () -> new FoodTodayFrame(admin).setVisible(true)));
        grid.add(makeTile("Food History", () -> new FoodHistoryFrame(admin).setVisible(true)));
        grid.add(makeTile("Manage Accounts", () -> new ManageAccountsFrame(admin).setVisible(true)));
        grid.add(makeTile("All Users (View)", () -> new AdminUserListFrame(admin).setVisible(true)));

        add(sidebar, BorderLayout.WEST);
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
