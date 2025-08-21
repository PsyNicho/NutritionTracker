
package ui;

import dao.UserDAO;
import dao.impl.UserDAOImpl;
import user.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminUserListFrame extends JFrame {
    private final User admin;
    private final UserDAO userDAO = new UserDAOImpl();
    private final JTable table = new JTable(new DefaultTableModel(new Object[]{"ID","Username","Role","View"},0));

    public AdminUserListFrame(User admin){
        super("All Users");
        this.admin = admin;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        setSize(600,360);
        setLocationRelativeTo(null);
        refresh();
        table.getColumn("View").setCellRenderer(new ButtonRenderer());
        table.getColumn("View").setCellEditor(new ButtonEditor(new JCheckBox(), (id) -> new ViewUserFrame(admin, id).setVisible(true)));
    }

    private void refresh(){
        DefaultTableModel m = (DefaultTableModel) table.getModel();
        m.setRowCount(0);
        for (User u : userDAO.getAllUsers()){
            m.addRow(new Object[]{u.getId(), u.getUsername(), u.getRole(), "View"});
        }
    }
}

class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
    public ButtonRenderer(){ setOpaque(true); }
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        setText(value == null ? "" : value.toString());
        return this;
    }
}

class ButtonEditor extends DefaultCellEditor {
    private String label;
    private final java.util.function.IntConsumer onClick;
    public ButtonEditor(JCheckBox checkBox, java.util.function.IntConsumer onClick) {
        super(checkBox); this.onClick = onClick;
    }
    public java.awt.Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column){
        label = value==null?"":value.toString();
        JButton b = new JButton(label);
        b.addActionListener(e -> {
            int id = (Integer) table.getValueAt(row, 0);
            onClick.accept(id);
            fireEditingStopped();
        });
        return b;
    }
    public Object getCellEditorValue(){ return label; }
}
