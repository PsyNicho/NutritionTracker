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

        // Color palette from image
        Color jasper = new Color(0xDD, 0x61, 0x4A);
        Color coral = new Color(0xF4, 0x86, 0x68);
        Color melon = new Color(0xF4, 0xA6, 0x98);
        Color sage = new Color(0x61, 0xA7, 0x67);
        Color cambridgeBlue = new Color(0x73, 0xA5, 0x80);

        // Sidebar panel for image, username, and logout
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(jasper);
        sidebar.setBorder(BorderFactory.createEmptyBorder(32, 16, 32, 16));

        // Load image (save as admin.png in src/ui)
        JLabel imgLabel = new JLabel();
        ImageIcon icon = new ImageIcon(getClass().getResource("admin.png"));
        Image scaledImg = icon.getImage().getScaledInstance(208, 208, Image.SCALE_SMOOTH);
        imgLabel.setIcon(new ImageIcon(scaledImg));
        imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Username label
        JLabel userLabel = new JLabel(admin.getUsername());
        userLabel.setFont(new Font("Arial", Font.BOLD, 18));
        userLabel.setForeground(Color.WHITE);
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userLabel.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));

        // Logout button
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutBtn.setBackground(melon);
        logoutBtn.setForeground(Color.BLACK);
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

        // Dashboard panel with vertical buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32));
        buttonPanel.setBackground(coral);

        int buttonWidth = 190;
        int buttonHeight = 40;
        Dimension btnSize = new Dimension(buttonWidth, buttonHeight);

        JButton addFoodBtn = makeTile("Add Food", () -> new AddFoodFrame(admin).setVisible(true), cambridgeBlue, Color.WHITE);
        JButton logFoodBtn = makeTile("Log Food", () -> new LogFoodFrame(admin).setVisible(true), sage, Color.WHITE);
        JButton eatenTodayBtn = makeTile("Food Eaten Today", () -> new FoodTodayFrame(admin).setVisible(true), cambridgeBlue, Color.WHITE);
        JButton foodHistoryBtn = makeTile("Food History", () -> new FoodHistoryFrame(admin).setVisible(true), sage, Color.WHITE);
        JButton manageAccountsBtn = makeTile("Manage Accounts", () -> new ManageAccountsFrame(admin).setVisible(true), cambridgeBlue, Color.WHITE);
        JButton allUsersBtn = makeTile("All Users (View)", () -> new AdminUserListFrame(admin).setVisible(true), sage, Color.WHITE);

        // Adjust button sizes to fit the wider panel
        int newButtonWidth = 330; // Match the new panel width
        Dimension newBtnSize = new Dimension(newButtonWidth, buttonHeight);
        addFoodBtn.setMaximumSize(newBtnSize);
        logFoodBtn.setMaximumSize(newBtnSize);
        eatenTodayBtn.setMaximumSize(newBtnSize);
        foodHistoryBtn.setMaximumSize(newBtnSize);
        manageAccountsBtn.setMaximumSize(newBtnSize);
        allUsersBtn.setMaximumSize(newBtnSize);

        // Shift all buttons (except login) 10 pixels to the right
        addFoodBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
        logFoodBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
        eatenTodayBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
        foodHistoryBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
        manageAccountsBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
        allUsersBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);

        // Increase padding to shift all buttons (except login) 20 pixels more to the right
        addFoodBtn.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        logFoodBtn.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        eatenTodayBtn.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        foodHistoryBtn.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        manageAccountsBtn.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        allUsersBtn.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));

        // Add margin to shift all buttons (except login) 30 pixels to the right
        addFoodBtn.setMargin(new Insets(0, 30, 0, 0));
        logFoodBtn.setMargin(new Insets(0, 30, 0, 0));
        eatenTodayBtn.setMargin(new Insets(0, 30, 0, 0));
        foodHistoryBtn.setMargin(new Insets(0, 30, 0, 0));
        manageAccountsBtn.setMargin(new Insets(0, 30, 0, 0));
        allUsersBtn.setMargin(new Insets(0, 30, 0, 0));

        // Add buttons vertically with spacing
        buttonPanel.add(addFoodBtn);
        buttonPanel.add(Box.createVerticalStrut(16));
        buttonPanel.add(logFoodBtn);
        buttonPanel.add(Box.createVerticalStrut(16));
        buttonPanel.add(eatenTodayBtn);
        buttonPanel.add(Box.createVerticalStrut(16));
        buttonPanel.add(foodHistoryBtn);
        buttonPanel.add(Box.createVerticalStrut(16));
        buttonPanel.add(manageAccountsBtn);
        buttonPanel.add(Box.createVerticalStrut(16));
        buttonPanel.add(allUsersBtn);

        // Adjust button panel width to make it 70% wider
        buttonPanel.setPreferredSize(new Dimension(340, buttonPanel.getPreferredSize().height));

        add(sidebar, BorderLayout.WEST);
        // Adjust layout to allow the button panel to expand
        add(buttonPanel, BorderLayout.EAST);
        setSize(585, 480); // Increase frame width to accommodate wider button panel
        setLocationRelativeTo(null);
    }

    private JButton makeTile(String text, Runnable r, Color bg, Color fg){
        JButton b = new JButton(text);
        b.setPreferredSize(new java.awt.Dimension(190, 120));
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFont(new Font("Arial", Font.BOLD, 16));
        b.setFocusPainted(false);
        b.addActionListener(e -> r.run());
        return b;
    }
}
