package ui;

import user.User;
import javax.swing.*;
import java.awt.*;

public class UserMenuDashboard extends JFrame {
    private final User user;
    private final LoginFrame login;

    public UserMenuDashboard(User user, LoginFrame login){
        super("Dashboard");
        this.user = user;
        this.login = login;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Color palette from image
        Color azure = new Color(0xCE, 0xE0, 0xDC);
        Color columbiaBlue = new Color(0xB9, 0xCF, 0xD4);
        Color roseQuartz = new Color(0xAF, 0xAA, 0xB9);
        Color cambridgeBlue = new Color(0x82, 0xAA, 0x9E);
        Color slateGray = new Color(0x79, 0x86, 0x93);

        // Sidebar panel for image, username, and logout
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(azure);
        sidebar.setBorder(BorderFactory.createEmptyBorder(32, 16, 32, 16));

        // Load image (save as user.png in src/ui)
        JLabel imgLabel = new JLabel();
        ImageIcon icon = new ImageIcon(getClass().getResource("user.png"));
        Image scaledImg = icon.getImage().getScaledInstance(150, 208, Image.SCALE_SMOOTH);
        imgLabel.setIcon(new ImageIcon(scaledImg));
        imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Username label
        JLabel userLabel = new JLabel(user.getUsername());
        userLabel.setFont(new Font("Arial", Font.BOLD, 18));
        userLabel.setForeground(Color.WHITE);
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userLabel.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));

        // Logout button
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

        // Use BoxLayout for 5 rows (vertical) for dashboard buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32));
        buttonPanel.setBackground(columbiaBlue);

        int buttonWidth = 200;
        int buttonHeight = 40;
        Dimension btnSize = new Dimension(buttonWidth, buttonHeight);

        JButton addFoodBtn = makeTile("Add Food", () -> new AddFoodFrame(user).setVisible(true), cambridgeBlue, Color.WHITE);
        JButton logFoodBtn = makeTile("Log Food", () -> new LogFoodFrame(user).setVisible(true), slateGray, Color.WHITE);
        JButton eatenTodayBtn = makeTile("Food Eaten Today", () -> new FoodTodayFrame(user).setVisible(true), cambridgeBlue, Color.WHITE);
        JButton foodHistoryBtn = makeTile("Food History", () -> new FoodHistoryFrame(user).setVisible(true), slateGray, Color.WHITE);
        JButton notesBtn = makeTile("Notes by Admin", () -> new NotesViewerFrame(user).setVisible(true), cambridgeBlue, Color.WHITE);

        addFoodBtn.setMaximumSize(btnSize);
        logFoodBtn.setMaximumSize(btnSize);
        eatenTodayBtn.setMaximumSize(btnSize);
        foodHistoryBtn.setMaximumSize(btnSize);
        notesBtn.setMaximumSize(btnSize);

        buttonPanel.add(addFoodBtn);
        buttonPanel.add(Box.createVerticalStrut(16));
        buttonPanel.add(logFoodBtn);
        buttonPanel.add(Box.createVerticalStrut(16));
        buttonPanel.add(eatenTodayBtn);
        buttonPanel.add(Box.createVerticalStrut(16));
        buttonPanel.add(foodHistoryBtn);
        buttonPanel.add(Box.createVerticalStrut(16));
        buttonPanel.add(notesBtn);

        add(sidebar, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.CENTER);
        setSize(468, 420);
        setLocationRelativeTo(null);
    }

    private JButton makeTile(String text, Runnable r, Color bg, Color fg){
        JButton b = new JButton(text);
        b.setPreferredSize(new Dimension(200,120));
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFocusPainted(false);
        b.setFont(new Font("Arial", Font.BOLD, 16));
        b.addActionListener(e -> r.run());
        return b;
    }
}
