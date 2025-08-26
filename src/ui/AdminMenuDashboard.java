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

        Color azure = new Color(0xCE, 0xE0, 0xDC);
        Color columbiaBlue = new Color(0xB9, 0xCF, 0xD4);
        Color roseQuartz = new Color(0xAF, 0xAA, 0xB9);
        Color cambridgeBlue = new Color(0x82, 0xAA, 0x9E);
        Color slateGray = new Color(0x79, 0x86, 0x93);

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(azure);
        sidebar.setBorder(BorderFactory.createEmptyBorder(32, 16, 32, 16));

        // admin dashboard side image
        JLabel imgLabel = new JLabel();
        ImageIcon icon = new ImageIcon(getClass().getResource("admin.png"));
        Image scaledImg = icon.getImage().getScaledInstance(208, 208, Image.SCALE_SMOOTH);
        imgLabel.setIcon(new ImageIcon(scaledImg));
        imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // username(batman/user)
        JLabel userLabel = new JLabel(admin.getUsername());
        userLabel.setFont(new Font("Arial", Font.BOLD, 18));
        userLabel.setForeground(Color.WHITE);
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userLabel.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));

        // logout
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutBtn.setBackground(roseQuartz);
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

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32));
        buttonPanel.setBackground(columbiaBlue);

        int buttonWidth = 190;
        int buttonHeight = 40;
        Dimension btnSize = new Dimension(buttonWidth, buttonHeight);

        JButton addFoodBtn = makeTile("Add Food", () -> new AddFoodFrame(admin).setVisible(true), cambridgeBlue, Color.WHITE);
        JButton logFoodBtn = makeTile("Log Food", () -> new LogFoodFrame(admin).setVisible(true), slateGray, Color.WHITE);
        JButton eatenTodayBtn = makeTile("Food Eaten Today", () -> new FoodTodayFrame(admin).setVisible(true), cambridgeBlue, Color.WHITE);
        JButton foodHistoryBtn = makeTile("Food History", () -> new FoodHistoryFrame(admin).setVisible(true), slateGray, Color.WHITE);
        JButton manageAccountsBtn = makeTile("Manage Accounts", () -> new ManageAccountsFrame(admin).setVisible(true), cambridgeBlue, Color.WHITE);
        JButton allUsersBtn = makeTile("All Users (View)", () -> new AdminUserListFrame(admin).setVisible(true), slateGray, Color.WHITE);

        int newButtonWidth = 330; 
        Dimension newBtnSize = new Dimension(newButtonWidth, buttonHeight);
        addFoodBtn.setMaximumSize(newBtnSize);
        logFoodBtn.setMaximumSize(newBtnSize);
        eatenTodayBtn.setMaximumSize(newBtnSize);
        foodHistoryBtn.setMaximumSize(newBtnSize);
        manageAccountsBtn.setMaximumSize(newBtnSize);
        allUsersBtn.setMaximumSize(newBtnSize);

        addFoodBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
        logFoodBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
        eatenTodayBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
        foodHistoryBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
        manageAccountsBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
        allUsersBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);

        addFoodBtn.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        logFoodBtn.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        eatenTodayBtn.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        foodHistoryBtn.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        manageAccountsBtn.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        allUsersBtn.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));

        addFoodBtn.setMargin(new Insets(0, 30, 0, 0));
        logFoodBtn.setMargin(new Insets(0, 30, 0, 0));
        eatenTodayBtn.setMargin(new Insets(0, 30, 0, 0));
        foodHistoryBtn.setMargin(new Insets(0, 30, 0, 0));
        manageAccountsBtn.setMargin(new Insets(0, 30, 0, 0));
        allUsersBtn.setMargin(new Insets(0, 30, 0, 0));

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

        buttonPanel.setPreferredSize(new Dimension(340, buttonPanel.getPreferredSize().height));

        add(sidebar, BorderLayout.WEST);
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
