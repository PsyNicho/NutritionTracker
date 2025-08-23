package ui;

import user.User;
import dao.FoodDAO;
import dao.impl.FoodDAOImpl;
import food.Food;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddFoodFrame extends JFrame {
    private final User user;
    private JTextField nameField;
    private JTextField caloriesField;
    private JTextField proteinField;
    private JTextField carbsField;
    private JTextField fatsField;
    private JTextField fiberField;

    public AddFoodFrame(User user) {
        super("Add Food");
        this.user = user;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(420, 320);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(7, 2, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Calories per 100g:"));
        caloriesField = new JTextField();
        panel.add(caloriesField);

        panel.add(new JLabel("Protein per 100g (g):"));
        proteinField = new JTextField();
        panel.add(proteinField);

        panel.add(new JLabel("Carbs per 100g (g):"));
        carbsField = new JTextField();
        panel.add(carbsField);

        panel.add(new JLabel("Fats per 100g (g):"));
        fatsField = new JTextField();
        panel.add(fatsField);

        panel.add(new JLabel("Fiber per 100g (g):"));
        fiberField = new JTextField("0");
        panel.add(fiberField);

        JButton addButton = new JButton("Add Food");
        addButton.addActionListener(new AddFoodAction());
        panel.add(addButton);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        panel.add(closeButton);

        add(panel);

        // Apply new color scheme
        Color azure = new Color(0xCE, 0xE0, 0xDC);
        Color columbiaBlue = new Color(0xB9, 0xCF, 0xD4);
        Color roseQuartz = new Color(0xAF, 0xAA, 0xB9);
        Color cambridgeBlue = new Color(0x82, 0xAA, 0x9E);
        Color slateGray = new Color(0x79, 0x86, 0x93);

        panel.setBackground(azure);
        addButton.setBackground(cambridgeBlue);
        addButton.setForeground(Color.WHITE);
        closeButton.setBackground(slateGray);
        closeButton.setForeground(Color.WHITE);

        for (Component comp : panel.getComponents()) {
            if (comp instanceof JLabel) {
                comp.setForeground(roseQuartz);
            } else if (comp instanceof JTextField) {
                comp.setBackground(columbiaBlue);
                comp.setForeground(Color.BLACK);
            }
        }
    }

    private class AddFoodAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(AddFoodFrame.this, "Please enter a food name.", "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                double calories = Double.parseDouble(caloriesField.getText().trim());
                double protein = Double.parseDouble(proteinField.getText().trim());
                double carbs = Double.parseDouble(carbsField.getText().trim());
                double fats = Double.parseDouble(fatsField.getText().trim());
                double fiber = 0.0;
                String fiberTxt = fiberField.getText().trim();
                if (!fiberTxt.isEmpty()) {
                    fiber = Double.parseDouble(fiberTxt);
                }

                // Use default constructor + setters to match Food.java
                Food food = new Food();
                food.setUserId(user.getId());
                food.setName(name);
                food.setCaloriesPer100g(calories);
                food.setProteinPer100g(protein);
                food.setCarbsPer100g(carbs);
                food.setFatsPer100g(fats);
                food.setFiberPer100g(fiber);

                FoodDAO foodDAO = new FoodDAOImpl();
                foodDAO.addFood(food);

                JOptionPane.showMessageDialog(AddFoodFrame.this, "Food added successfully!");
                dispose();
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(AddFoodFrame.this, "Please enter valid numeric values for calories/macros.", "Invalid input", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(AddFoodFrame.this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}