
package ui;

import dao.FoodDAO;
import dao.FoodLogDAO;
import dao.NoteDAO;
import dao.impl.FoodDAOImpl;
import dao.impl.FoodLogDAOImpl;
import dao.impl.NoteDAOImpl;
import food.Food;
import food.FoodLog;
import nutritionsummary.NutritionSummary;
import user.Note;
import user.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

public class UserDashboard extends JFrame {
    private final User user;
    private final LoginFrame login;
    private final FoodDAO foodDAO = new FoodDAOImpl();
    private final FoodLogDAO foodLogDAO = new FoodLogDAOImpl();
    private final NoteDAO noteDAO = new NoteDAOImpl();

    private final JTextField name = new JTextField(14);
    private final JTextField cal = new JTextField(6);
    private final JTextField pro = new JTextField(6);
    private final JTextField carb = new JTextField(6);
    private final JTextField fat = new JTextField(6);
    private final JTextField fib = new JTextField(6);
    private final JButton addFoodBtn = new JButton("Add Food");

    private final JComboBox<Food> foodSelect = new JComboBox<>();
    private final JTextField grams = new JTextField(6);
    private final JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
    private final JButton logBtn = new JButton("Log");

    private final JLabel calories = new JLabel("0");
    private final JLabel protein = new JLabel("0");
    private final JLabel carbs = new JLabel("0");
    private final JLabel fats = new JLabel("0");
    private final JLabel fiber = new JLabel("0");

    private final JTable notesTable = new JTable(new DefaultTableModel(
            new Object[]{"Date","Note","From(AdminId)"},0));

    private final JButton logoutBtn = new JButton("Logout");

    public UserDashboard(User user, LoginFrame login) {
        super("User Dashboard");
        this.user = user;
        this.login = login;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Logged in as: " + user.getUsername()));
        top.add(logoutBtn);
        add(top, BorderLayout.NORTH);

        JPanel addFoodPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addFoodPanel.setBorder(BorderFactory.createTitledBorder("Add Your Food"));
        addFoodPanel.add(new JLabel("Name")); addFoodPanel.add(name);
        addFoodPanel.add(new JLabel("Cal")); addFoodPanel.add(cal);
        addFoodPanel.add(new JLabel("Prot")); addFoodPanel.add(pro);
        addFoodPanel.add(new JLabel("Carb")); addFoodPanel.add(carb);
        addFoodPanel.add(new JLabel("Fat")); addFoodPanel.add(fat);
        addFoodPanel.add(new JLabel("Fib")); addFoodPanel.add(fib);
        addFoodPanel.add(addFoodBtn);

        JPanel logPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logPanel.setBorder(BorderFactory.createTitledBorder("Log Consumption"));
        logPanel.add(new JLabel("Food")); logPanel.add(foodSelect);
        logPanel.add(new JLabel("Grams")); logPanel.add(grams);
        logPanel.add(new JLabel("Date")); logPanel.add(dateSpinner);
        logPanel.add(logBtn);

        JPanel west = new JPanel();
        west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));
        west.add(addFoodPanel);
        west.add(logPanel);
        add(west, BorderLayout.WEST);

        JPanel summary = new JPanel(new GridLayout(1,5,8,8));
        summary.setBorder(BorderFactory.createTitledBorder("Today Summary"));
        summary.add(labeled("Calories", calories));
        summary.add(labeled("Protein", protein));
        summary.add(labeled("Carbs", carbs));
        summary.add(labeled("Fats", fats));
        summary.add(labeled("Fiber", fiber));
        add(summary, BorderLayout.CENTER);

        add(new JScrollPane(notesTable), BorderLayout.SOUTH);

        addFoodBtn.addActionListener(this::onAddFood);
        logBtn.addActionListener(this::onLog);
        logoutBtn.addActionListener(e -> {
            dispose();
            login.backToLogin();
        });

        pack();
        setLocationRelativeTo(null);

        refreshFoods();
        refreshSummary();
        refreshNotes();
    }

    private JPanel labeled(String title, JComponent comp){
        JPanel p = new JPanel(new BorderLayout());
        p.add(new JLabel(title, SwingConstants.CENTER), BorderLayout.NORTH);
        p.add(comp, BorderLayout.CENTER);
        return p;
    }

    private void onAddFood(ActionEvent e){
        try {
            Food f = new Food(0, user.getId(), name.getText().trim(),
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

    private void onLog(ActionEvent e){
        Food f = (Food) foodSelect.getSelectedItem();
        if (f == null){ JOptionPane.showMessageDialog(this, "Add a food first"); return; }
        try {
            double g = Double.parseDouble(grams.getText());
            java.util.Date d = (java.util.Date) dateSpinner.getValue();
            java.time.LocalDate ld = new java.sql.Date(d.getTime()).toLocalDate();
            FoodLog log = new FoodLog(0, user.getId(), f.getId(), g, java.sql.Date.valueOf(ld));
            foodLogDAO.addFoodLog(log);
            grams.setText("");
            refreshSummary();
        } catch (Exception ex){
            JOptionPane.showMessageDialog(this, "Invalid values: " + ex.getMessage());
        }
    }

    private void refreshFoods(){
        foodSelect.removeAllItems();
        for (Food f : foodDAO.getFoodsByUser(user.getId())){
            foodSelect.addItem(f);
        }
    }

    private void refreshSummary(){
        java.sql.Date today = java.sql.Date.valueOf(LocalDate.now());
NutritionSummary s = new NutritionSummary(
    foodLogDAO.getTotalCaloriesForUserAndDate(user.getId(), today),
    foodLogDAO.getTotalProteinForUserAndDate(user.getId(), today),
    foodLogDAO.getTotalCarbsForUserAndDate(user.getId(), today),
    foodLogDAO.getTotalFatsForUserAndDate(user.getId(), today),
    foodLogDAO.getTotalFiberForUserAndDate(user.getId(), today)
);
        calories.setText(String.format("%.0f", s.getCalories()));
        protein.setText(String.format("%.1f", s.getProtein()));
        carbs.setText(String.format("%.1f", s.getCarbs()));
        fats.setText(String.format("%.1f", s.getFats()));
        fiber.setText(String.format("%.1f", s.getFiber()));
    }

    private void refreshNotes(){
        DefaultTableModel model = (DefaultTableModel) notesTable.getModel();
        model.setRowCount(0);
        for (Note n : noteDAO.getNotesByUser(user.getId())){
            model.addRow(new Object[]{ n.getCreatedAt(), n.getNoteText(), n.getAdminId() });
        }
    }
}
