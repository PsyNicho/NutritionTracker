
package ui;

import dao.FoodLogDAO;
import dao.impl.FoodLogDAOImpl;
import food.FoodLogDetail;
import user.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

public class FoodHistoryFrame extends JFrame {
    private final User user;
    private final FoodLogDAO logDAO = new FoodLogDAOImpl();
    private final JTable table = new JTable(new DefaultTableModel(
            new Object[]{"Food","Grams","Calories","Protein","Carbs","Fats","Fiber"}, 0));
    private final JLabel totals = new JLabel("Totals");
    private final JSpinner date = new JSpinner(new SpinnerDateModel());

    public FoodHistoryFrame(User user){
        super("Food History");
        this.user = user;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
        north.add(new JLabel("Date:"));
        date.setEditor(new JSpinner.DateEditor(date, "yyyy-MM-dd"));
        north.add(date);
        JButton go = new JButton("Show");
        north.add(go);
        add(north, BorderLayout.NORTH);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(totals, BorderLayout.SOUTH);
        setSize(720,420);
        setLocationRelativeTo(null);

        go.addActionListener(e -> refresh());
        refresh();
    }

    private void refresh(){
        java.util.Date d = (java.util.Date) date.getValue();
        LocalDate ld = new java.sql.Date(d.getTime()).toLocalDate();
        DefaultTableModel m = (DefaultTableModel) table.getModel();
        m.setRowCount(0);
        double tc=0,tp=0,tcab=0,tf=0,tfi=0;
        for (FoodLogDetail r : logDAO.getDetailedLogsByUserAndDate(user.getId(), java.sql.Date.valueOf(ld))){
    m.addRow(new Object[]{ r.getFoodName(), r.getGramsConsumed(),
                    round(r.getCalories()), round(r.getProtein()), round(r.getCarbs()), round(r.getFats()), round(r.getFiber())});
            tc += r.getCalories(); tp += r.getProtein(); tcab += r.getCarbs(); tf += r.getFats(); tfi += r.getFiber();
        }
        totals.setText(String.format("Totals: %.0f cal, P%.1f C%.1f F%.1f Fib%.1f", tc,tp,tcab,tf,tfi));
    }
    private double round(double v){ return Math.round(v*10.0)/10.0; }
}
