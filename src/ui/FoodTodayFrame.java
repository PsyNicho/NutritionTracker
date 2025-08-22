package ui;

import dao.FoodLogDAO;
import dao.impl.FoodLogDAOImpl;
import food.FoodLogDetail;
import user.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

public class FoodTodayFrame extends JFrame {
    private final User user;
    private final FoodLogDAO logDAO = new FoodLogDAOImpl();
    private final JTable table = new JTable(new DefaultTableModel(
            new Object[]{"Food","Grams","Calories","Protein","Carbs","Fats","Fiber"}, 0));
    private final JLabel totals = new JLabel("Totals: 0 cal, P0 C0 F0 Fib0");

    public FoodTodayFrame(User user){
        super("Food Eaten Today");
        this.user = user;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(totals, BorderLayout.SOUTH);
        setSize(700,400);
        setLocationRelativeTo(null);
        refresh();
    }

    private void refresh(){
        DefaultTableModel m = (DefaultTableModel) table.getModel();
        m.setRowCount(0);
        double tc=0,tp=0,tcab=0,tf=0,tfi=0;
        for (FoodLogDetail d : logDAO.getDetailedLogsByUserAndDate(user.getId(), java.sql.Date.valueOf(LocalDate.now()))){
    m.addRow(new Object[]{ d.getFoodName(), d.getGramsConsumed(),
                    round(d.getCalories()), round(d.getProtein()), round(d.getCarbs()), round(d.getFats()), round(d.getFiber())});
            tc += d.getCalories(); tp += d.getProtein(); tcab += d.getCarbs(); tf += d.getFats(); tfi += d.getFiber();
        }
        totals.setText(String.format("Totals: %.0f cal, P%.1f C%.1f F%.1f Fib%.1f", tc,tp,tcab,tf,tfi));
    }
    private double round(double v){ return Math.round(v*10.0)/10.0; }
}
