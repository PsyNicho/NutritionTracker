
package ui;

import dao.UserDAO;
import dao.impl.UserDAOImpl;
import user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {
    private final JTextField username = new JTextField(15);
    private final JPasswordField password = new JPasswordField(15);
    private final JButton loginBtn = new JButton("Login");
    private final UserDAO userDAO = new UserDAOImpl();

    public interface LoginCallback {
        void onLogin(User user);
    }

    public LoginFrame() {
        super("Nutrition Tracker - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(3,2,8,8));
        form.setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        form.add(new JLabel("Username:")); form.add(username);
        form.add(new JLabel("Password:")); form.add(password);
        form.add(new JLabel()); form.add(loginBtn);

        add(form, BorderLayout.CENTER);

        loginBtn.addActionListener((ActionEvent e) -> doLogin());

        pack();
        setLocationRelativeTo(null);
    }

    private void doLogin() {
        String u = username.getText().trim();
        String p = new String(password.getPassword());
        User user = userDAO.getUserByUsername(u);
        if (user == null || !user.getPassword().equals(p)) {
            JOptionPane.showMessageDialog(this, "Invalid username or password");
            return;
        }
        // Open appropriate dashboard
        SwingUtilities.invokeLater(() -> {
            if ("admin".equalsIgnoreCase(user.getRole())) {
                new AdminMenuDashboard(user, this).setVisible(true);
            } else {
                new UserMenuDashboard(user, this).setVisible(true);
            }
            setVisible(false);
        });
    }

    public void backToLogin() {
        username.setText("");
        password.setText("");
        setVisible(true);
        toFront();
        requestFocus();
    }
}
