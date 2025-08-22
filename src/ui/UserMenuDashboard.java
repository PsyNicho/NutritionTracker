
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

        JPanel grid = new JPanel(new GridLayout(2,3,16,16));
        grid.setBorder(BorderFactory.createEmptyBorder(16,16,16,16));

        grid.add(makeTile("Add Food", () -> new AddFoodFrame(user).setVisible(true)));
        grid.add(makeTile("Log Food", () -> new LogFoodFrame(user).setVisible(true)));
        grid.add(makeTile("Food Eaten Today", () -> new FoodTodayFrame(user).setVisible(true)));
        grid.add(makeTile("Food History", () -> new FoodHistoryFrame(user).setVisible(true)));
        grid.add(makeTile("Notes by Admin", () -> new NotesViewerFrame(user).setVisible(true)));

        add(grid, BorderLayout.CENTER);
        setSize(780,420);
        setLocationRelativeTo(null);
    }

    private JButton makeTile(String text, Runnable r){
        JButton b = new JButton(text);
        b.setPreferredSize(new Dimension(200,120));
        b.addActionListener(e -> r.run());
        return b;
    }
}
