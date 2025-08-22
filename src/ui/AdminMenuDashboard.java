package ui;

import user.User;
import javax.swing.*;
import java.awt.*;

public class AdminMenuDashboard extends JFrame {
    private final User user;
    private final LoginFrame login;

    public AdminMenuDashboard(User user, LoginFrame login){
        super("Admin Dashboard");
        this.user = user;
        this.login = login;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel grid = new JPanel(new GridLayout(2,3,16,16));
        grid.setBorder(BorderFactory.createEmptyBorder(16,16,16,16));

        // Updated to open new frames instead of AdminDashboard/UserDashboard
        grid.add(makeTile("Add Food", () -> new AddFoodFrame(user).setVisible(true)));
        grid.add(makeTile("Log Food", () -> new LogFoodFrame(user).setVisible(true)));

        // Keep the rest as they are
        grid.add(makeTile("Food History", () -> new FoodHistoryFrame(user).setVisible(true)));
        grid.add(makeTile("Notes by Admin", () -> new NotesViewerFrame(user).setVisible(true)));
        grid.add(makeTile("Manage Accounts", () -> new ManageAccountsFrame(user).setVisible(true)));

        add(grid, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    private JPanel makeTile(String text, Runnable action){
        JPanel panel = new JPanel(new BorderLayout());
        JButton button = new JButton(text);
        button.addActionListener(e -> action.run());
        panel.add(button, BorderLayout.CENTER);
        return panel;
    }
}
