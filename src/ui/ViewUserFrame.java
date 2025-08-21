
package ui;

import dao.FoodLogDAO;
import dao.NoteDAO;
import dao.impl.FoodLogDAOImpl;
import dao.impl.NoteDAOImpl;
import food.FoodLogDetail;
import user.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

public class ViewUserFrame extends JFrame {
    private final User admin;
    private final int targetUserId;
    private final FoodLogDAO logDAO = new FoodLogDAOImpl();
    private final NoteDAO noteDAO = new NoteDAOImpl();

    private final JTable table = new JTable(new DefaultTableModel(new Object[]{"Food","Grams","Calories","Protein","Carbs","Fats","Fiber"},0));
    private final JLabel totals = new JLabel("");
    private final JTextArea noteText = new JTextArea(3, 40);
    private final JButton addNoteBtn = new JButton("Add Note");

    public ViewUserFrame(User admin, int targetUserId){
        super("User Food & Notes");
        this.admin = admin;
        this.targetUserId = targetUserId;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JSpinner date = new JSpinner(new SpinnerDateModel());
        date.setEditor(new JSpinner.DateEditor(date, "yyyy-MM-dd"));
        JButton show = new JButton("Show");
        top.add(new JLabel("Date:")); top.add(date); top.add(show);
        add(top, BorderLayout.NORTH);

        add(new JScrollPane(table), BorderLayout.CENTER);
        JPanel south = new JPanel(new BorderLayout());
        south.add(totals, BorderLayout.NORTH);
        JPanel notePanel = new JPanel(new BorderLayout());
        notePanel.setBorder(BorderFactory.createTitledBorder("Add Note"));
        notePanel.add(new JScrollPane(noteText), BorderLayout.CENTER);
        notePanel.add(addNoteBtn, BorderLayout.EAST);
        south.add(notePanel, BorderLayout.SOUTH);
        add(south, BorderLayout.SOUTH);

        setSize(760,480);
        setLocationRelativeTo(null);

        show.addActionListener(e -> {
            java.util.Date d = (java.util.Date) date.getValue();
            LocalDate ld = new java.sql.Date(d.getTime()).toLocalDate();
            refresh(ld);
        });
        addNoteBtn.addActionListener(e -> onAddNote());
        refresh(LocalDate.now());
    }

    private void refresh(LocalDate date){
        DefaultTableModel m = (DefaultTableModel) table.getModel();
        m.setRowCount(0);
        double tc=0,tp=0,tcab=0,tf=0,tfi=0;
        for (FoodLogDetail r : logDAO.getDetailedLogsByUserAndDate(targetUserId, java.sql.Date.valueOf(date))){
    m.addRow(new Object[]{ r.getFoodName(), r.getGramsConsumed(),
                    round(r.getCalories()), round(r.getProtein()), round(r.getCarbs()), round(r.getFats()), round(r.getFiber())});
            tc += r.getCalories(); tp += r.getProtein(); tcab += r.getCarbs(); tf += r.getFats(); tfi += r.getFiber();
        }
        totals.setText(String.format("Totals: %.0f cal, P%.1f C%.1f F%.1f Fib%.1f", tc,tp,tcab,tf,tfi));
    }

    private void onAddNote(){
        String txt = noteText.getText().trim();
        if (txt.isEmpty()){ JOptionPane.showMessageDialog(this, "Enter note text."); return; }
        user.Note n = new user.Note(0, targetUserId, admin.getId(), txt, java.time.LocalDateTime.now());
        noteDAO.addNote(n);
        noteText.setText("");
        JOptionPane.showMessageDialog(this, "Note added.");
    }

    private double round(double v){ return Math.round(v*10.0)/10.0; }
}
