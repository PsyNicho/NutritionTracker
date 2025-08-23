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
    private final JTable table = new JTable(new DefaultTableModel(new Object[]{"ID","Username","View"},0));

    public AdminUserListFrame(User admin){
        super("All Users");
        this.admin = admin;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Apply new color scheme
        Color azure = new Color(0xCE, 0xE0, 0xDC);
        Color columbiaBlue = new Color(0xB9, 0xCF, 0xD4);
        Color roseQuartz = new Color(0xAF, 0xAA, 0xB9);
        Color cambridgeBlue = new Color(0x82, 0xAA, 0x9E);
        Color slateGray = new Color(0x79, 0x86, 0x93);

        getContentPane().setBackground(azure);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(columbiaBlue);
        add(scrollPane, BorderLayout.CENTER);

        table.setBackground(slateGray);
        table.setForeground(Color.BLACK);
        table.setGridColor(roseQuartz);
        table.setSelectionBackground(cambridgeBlue);
        table.setSelectionForeground(Color.WHITE);

        setSize(600,360);
        setLocationRelativeTo(null);
        refresh();
        // Set the background color of the "View" button to AFAAB9
        table.getColumn("View").setCellRenderer(new ButtonRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JButton button = (JButton) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                button.setBackground(new Color(0xAF, 0xAA, 0xB9));
                button.setForeground(Color.WHITE);
                return button;
            }
        });
        table.getColumn("View").setCellEditor(new ButtonEditor(new JCheckBox(), (id) -> new ViewUserFrame(admin, id).setVisible(true)));
    }

    private void refresh(){
        DefaultTableModel m = (DefaultTableModel) table.getModel();
        m.setRowCount(0);
        List<User> users = userDAO.getAllUsersByRole("user");
        for (User u : users){
            m.addRow(new Object[]{u.getId(), u.getUsername(), "View"});
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