package ui;

import dao.UserDAO;
import dao.impl.UserDAOImpl;
import user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {

    private final JTextField username = new JTextField(20);
    private final JPasswordField password = new JPasswordField(20);
    private final JButton loginBtn = new JButton("Log In");
    private final UserDAO userDAO = new UserDAOImpl();

    public interface LoginCallback {

        void onLogin(User user);
    }

    public LoginFrame() {
        super("Nutrition Tracker - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        // Background panel as main content pane
        JPanel backgroundPanel = new JPanel() {
            private final Image backgroundImage;
            {
                java.net.URL imgUrl = LoginFrame.class.getResource("background.png");
                System.out.println("Image URL: " + imgUrl);
                backgroundImage = imgUrl != null ? new ImageIcon(imgUrl).getImage() : null;
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    // Move image 30 pixels up (negative y offset)
                    g.drawImage(backgroundImage, 0, -40, getWidth(), getHeight(), this);
                }
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setOpaque(false); // <-- Important: keep transparent
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        formPanel.setBorder(BorderFactory.createEmptyBorder(270, 0, 0, 0)); // Shift down by 110px (was 80)

        username.setMaximumSize(new Dimension(280, 40));
        username.setBorder(BorderFactory.createTitledBorder("Username"));

        password.setMaximumSize(new Dimension(280, 40));
        password.setBorder(BorderFactory.createTitledBorder("Password"));

        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setBackground(new Color(34, 85, 34));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setPreferredSize(new Dimension(280, 40));

        formPanel.add(username);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(password);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(loginBtn);

        // Add components
        backgroundPanel.add(formPanel, BorderLayout.CENTER);

        // Button action
        loginBtn.addActionListener((ActionEvent e) -> doLogin());
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
