package ui;

import dao.UserDAO;
import dao.impl.UserDAOImpl;
import user.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ManageAccountsFrame extends JFrame {
    private final User admin;
    private final UserDAO userDAO = new UserDAOImpl();
    private final JTable table = new JTable(new DefaultTableModel(new Object[]{"ID","Username","Role"},0));

    private final JTextField username = new JTextField(12);
    private final JPasswordField password = new JPasswordField(12);
    private final JComboBox<String> role = new JComboBox<>(new String[]{"user","admin"});
    private final JButton createBtn = new JButton("Create");

    private final JTextField userIdForPw = new JTextField(5);
    private final JPasswordField newPw = new JPasswordField(12);
    private final JButton changePwBtn = new JButton("Change Password");

    private final JTextField userIdForDelete = new JTextField(5);
    private final JButton deleteBtn = new JButton("Delete User");

    public ManageAccountsFrame(User admin){
        super("Manage Accounts");
        this.admin = admin;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel create = new JPanel(new FlowLayout(FlowLayout.LEFT));
        create.setBorder(BorderFactory.createTitledBorder("Create Account"));
        create.add(new JLabel("Username")); create.add(username);
        create.add(new JLabel("Password")); create.add(password);
        create.add(new JLabel("Role")); create.add(role);
        create.add(createBtn);

        JPanel change = new JPanel(new FlowLayout(FlowLayout.LEFT));
        change.setBorder(BorderFactory.createTitledBorder("Change Password"));
        change.add(new JLabel("User ID")); change.add(userIdForPw);
        change.add(new JLabel("New Password")); change.add(newPw);
        change.add(changePwBtn);

        JPanel del = new JPanel(new FlowLayout(FlowLayout.LEFT));
        del.setBorder(BorderFactory.createTitledBorder("Delete Account"));
        del.add(new JLabel("User ID")); del.add(userIdForDelete);
        del.add(deleteBtn);

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.add(create); left.add(change); left.add(del);

        add(left, BorderLayout.NORTH);
        // Apply new color scheme
        Color azure = new Color(0xCE, 0xE0, 0xDC);
        Color columbiaBlue = new Color(0xB9, 0xCF, 0xD4);
        Color roseQuartz = new Color(0xAF, 0xAA, 0xB9);
        Color cambridgeBlue = new Color(0x82, 0xAA, 0x9E);
        Color slateGray = new Color(0x79, 0x86, 0x93);

        getContentPane().setBackground(azure);
        table.setBackground(slateGray);
        table.setForeground(Color.BLACK);
        table.setGridColor(roseQuartz);
        table.setSelectionBackground(cambridgeBlue);
        table.setSelectionForeground(Color.WHITE);

        createBtn.setBackground(roseQuartz);
        createBtn.setForeground(Color.WHITE);
        createBtn.setOpaque(true);

        changePwBtn.setBackground(roseQuartz);
        changePwBtn.setForeground(Color.WHITE);
        changePwBtn.setOpaque(true);

        deleteBtn.setBackground(roseQuartz);
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setOpaque(true);

        setSize(760,480);
        setLocationRelativeTo(null);

        createBtn.addActionListener(e -> onCreate());
        changePwBtn.addActionListener(e -> onChangePw());
        deleteBtn.addActionListener(e -> onDelete());
        refresh();

        // Set background color for empty table
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(columbiaBlue);
        add(scrollPane, BorderLayout.CENTER);

        // Update background colors for panels
        create.setBackground(new Color(0x82, 0xAA, 0x9E)); // Cambridge Blue
        change.setBackground(new Color(0xB9, 0xCF, 0xD4)); // Columbia Blue
        del.setBackground(new Color(0x82, 0xAA, 0x9E)); // Cambridge Blue
    }

    private void onCreate(){
        String u = username.getText().trim();
        String p = new String(password.getPassword());
        String r = (String) role.getSelectedItem();
        if (u.isEmpty() || p.isEmpty()){ JOptionPane.showMessageDialog(this, "Enter username & password."); return; }
        userDAO.addUser(new User(0, u, p, r, "user")); // or the appropriate status
        username.setText(""); password.setText("");
        refresh();
    }
    private void onChangePw(){
        try{
            int id = Integer.parseInt(userIdForPw.getText().trim());
            String p = new String(newPw.getPassword());
            if (p.isEmpty()){ JOptionPane.showMessageDialog(this, "Password empty"); return; }
            userDAO.updatePassword(id, p);
            newPw.setText(""); userIdForPw.setText("");
            JOptionPane.showMessageDialog(this, "Password updated.");
        } catch(Exception ex){ JOptionPane.showMessageDialog(this, "Invalid ID."); }
    }
    private void onDelete(){
        try{
            int id = Integer.parseInt(userIdForDelete.getText().trim());
            int c = JOptionPane.showConfirmDialog(this, "Delete user " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (c == JOptionPane.YES_OPTION){ userDAO.deleteUser(id); refresh(); }
        } catch(Exception ex){ JOptionPane.showMessageDialog(this, "Invalid ID."); }
    }

    private void refresh(){
        DefaultTableModel m = (DefaultTableModel) table.getModel();
        m.setRowCount(0);
        for (User u : userDAO.getAllUsers()){
            m.addRow(new Object[]{ u.getId(), u.getUsername(), u.getRole() });
        }
    }
}
