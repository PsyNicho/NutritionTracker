package ui;

import user.User;
import dao.FoodDAO;
import dao.FoodLogDAO;
import dao.impl.FoodDAOImpl;
import dao.impl.FoodLogDAOImpl;
import food.Food;
import food.FoodLog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

public class LogFoodFrame extends JFrame {
    private final User user;
    private JComboBox<Food> foodComboBox;
    private JTextField gramsField;

    public LogFoodFrame(User user) {
        super("Log Food");
        this.user = user;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(420, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        // Apply new color scheme
        Color azure = new Color(0xCE, 0xE0, 0xDC);
        Color columbiaBlue = new Color(0xB9, 0xCF, 0xD4);
        Color roseQuartz = new Color(0xAF, 0xAA, 0xB9);
        Color cambridgeBlue = new Color(0x82, 0xAA, 0x9E);
        Color slateGray = new Color(0x79, 0x86, 0x93);

        panel.setBackground(azure);

        panel.add(new JLabel("Select Food:"));
        foodComboBox = new JComboBox<>();
        loadFoods();
        panel.add(foodComboBox);

        panel.add(new JLabel("Amount eaten (grams):"));
        gramsField = new JTextField();
        panel.add(gramsField);

        JButton logButton = new JButton("Log Food");
        logButton.addActionListener(new LogFoodAction());
        logButton.setBackground(cambridgeBlue);
        logButton.setForeground(Color.WHITE);
        panel.add(logButton);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        closeButton.setBackground(slateGray);
        closeButton.setForeground(Color.WHITE);
        panel.add(closeButton);

        for (Component comp : panel.getComponents()) {
            if (comp instanceof JLabel) {
                comp.setForeground(roseQuartz);
            } else if (comp instanceof JTextField || comp instanceof JComboBox) {
                comp.setBackground(columbiaBlue);
                comp.setForeground(Color.BLACK);
            }
        }

        add(panel);
    }

    private void loadFoods() {
        FoodDAO foodDAO = new FoodDAOImpl();
        List<Food> foods = foodDAO.getFoodsByUser(user.getId());
        for (Food food : foods) {
            foodComboBox.addItem(food);
        }
    }

    private class LogFoodAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Food food = (Food) foodComboBox.getSelectedItem();
                if (food == null) {
                    JOptionPane.showMessageDialog(LogFoodFrame.this, "Please select a food.", "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                double grams = Double.parseDouble(gramsField.getText().trim());
                if (grams <= 0) {
                    JOptionPane.showMessageDialog(LogFoodFrame.this, "Please enter a positive amount in grams.", "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                FoodLog log = new FoodLog(user.getId(), food.getId(), grams, new Date());
                FoodLogDAO foodLogDAO = new FoodLogDAOImpl();
                foodLogDAO.addFoodLog(log);

                JOptionPane.showMessageDialog(LogFoodFrame.this, "Food logged successfully!");
                dispose();
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(LogFoodFrame.this, "Please enter a valid number for grams.", "Invalid input", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(LogFoodFrame.this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}