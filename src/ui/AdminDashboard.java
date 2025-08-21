
package ui;

import dao.FoodDAO;
import dao.NoteDAO;
import dao.UserDAO;
import dao.impl.FoodDAOImpl;
import dao.impl.NoteDAOImpl;
import dao.impl.UserDAOImpl;
import food.Food;
import user.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AdminDashboard extends JFrame {
    private final User admin;
    private final LoginFrame login;

    private final FoodDAO foodDAO = new FoodDAOImpl();
    private final UserDAO userDAO = new UserDAOImpl();
    private final NoteDAO noteDAO = new NoteDAOImpl(); // kept for continuity

    private final JTextField name = new JTextField(14);
    private final JTextField cal = new JTextField(6);
    private final JTextField pro = new JTextField(6);
    private final JTextField carb = new JTextField(6);
    private final JTextField fat = new JTextField(6);
    private final JTextField fib = new JTextField(6);
    private final JButton addFoodBtn = new JButton("Add Food");

    private final JTable foodsTable = new JTable(new DefaultTableModel(
            new Object[]{"ID","Name","Cal/100g","Prot/100g","Carb/100g","Fat/100g","Fib/100g"},0));

    // User creation controls
    private final JTextField newUsername = new JTextField(12);
    private final JPasswordField newPassword = new JPasswordField(12);
    private final JComboBox<String> roleCombo = new JComboBox<>(new String[]{"user","admin"});
    private final JButton createUserBtn = new JButton("Create Account");

    private final JButton logoutBtn = new JButton("Logout");

    public AdminDashboard(User admin, LoginFrame login) {
        super("Admin Dashboard");
        this.admin = admin;
        this.login = login;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Logged in as: " + admin.getUsername() + " (admin)"));
        top.add(logoutBtn);
        add(top, BorderLayout.NORTH);

        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));
        form.setBorder(BorderFactory.createTitledBorder("Add Your Food (visible only to you)"));
        form.add(new JLabel("Name")); form.add(name);
        form.add(new JLabel("Cal")); form.add(cal);
        form.add(new JLabel("Prot")); form.add(pro);
        form.add(new JLabel("Carb")); form.add(carb);
        form.add(new JLabel("Fat")); form.add(fat);
        form.add(new JLabel("Fib")); form.add(fib);
        form.add(addFoodBtn);

        JPanel create = new JPanel(new FlowLayout(FlowLayout.LEFT));
        create.setBorder(BorderFactory.createTitledBorder("Create Account"));
        create.add(new JLabel("Username")); create.add(newUsername);
        create.add(new JLabel("Password")); create.add(newPassword);
        create.add(new JLabel("Role")); create.add(roleCombo);
        create.add(createUserBtn);

        add(form, BorderLayout.WEST);
        add(new JScrollPane(foodsTable), BorderLayout.CENTER);
        add(create, BorderLayout.SOUTH);

        addFoodBtn.addActionListener(this::onAddFood);
        createUserBtn.addActionListener(this::onCreateUser);
        logoutBtn.addActionListener(e -> {
            dispose();
            login.backToLogin();
        });

        pack();
        setLocationRelativeTo(null);

        refreshFoods();
    }

    private void onAddFood(ActionEvent e){
        try {
            Food f = new Food(0, admin.getId(), name.getText().trim(),
                    Double.parseDouble(cal.getText()),
                    Double.parseDouble(pro.getText()),
                    Double.parseDouble(carb.getText()),
                    Double.parseDouble(fat.getText()),
                    Double.parseDouble(fib.getText()));
            foodDAO.addFood(f);
            name.setText(""); cal.setText(""); pro.setText(""); carb.setText(""); fat.setText(""); fib.setText("");
            refreshFoods();
        } catch (Exception ex){
            JOptionPane.showMessageDialog(this, "Invalid values: " + ex.getMessage());
        }
    }

    private void onCreateUser(ActionEvent e){
        String u = newUsername.getText().trim();
        String p = new String(newPassword.getPassword());
        String r = (String) roleCombo.getSelectedItem();
        if (u.isEmpty() || p.isEmpty()){
            JOptionPane.showMessageDialog(this, "Username and password required");
            return;
        }
        try {
            user.User nu = new user.User(0, u, p, r);
            userDAO.addUser(nu);
            newUsername.setText("");
            newPassword.setText("");
            JOptionPane.showMessageDialog(this, "Account created");
        } catch (Exception ex){
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void refreshFoods(){
        DefaultTableModel model = (DefaultTableModel) foodsTable.getModel();
        model.setRowCount(0);
        for (Food f : foodDAO.getFoodsByUser(admin.getId())){
            model.addRow(new Object[]{f.getId(), f.getName(), f.getCaloriesPer100g(), f.getProteinPer100g(),
                    f.getCarbsPer100g(), f.getFatsPer100g(), f.getFiberPer100g()});
        }
    }
}
