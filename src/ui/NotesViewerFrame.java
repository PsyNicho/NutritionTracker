package ui;

import dao.NoteDAO;
import dao.impl.NoteDAOImpl;
import user.Note;
import user.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class NotesViewerFrame extends JFrame {
    private final User user;
    private final NoteDAO noteDAO = new NoteDAOImpl();
    private final JTable table = new JTable(new DefaultTableModel(new Object[]{"Date","Note","From"},0));

    public NotesViewerFrame(User user){
        super("Notes");
        this.user = user;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(600,360);
        setLocationRelativeTo(null);
        refresh();

        // Apply new color scheme
        Color azure = new Color(0xCE, 0xE0, 0xDC);
        Color columbiaBlue = new Color(0xB9, 0xCF, 0xD4);
        Color roseQuartz = new Color(0xAF, 0xAA, 0xB9);
        Color cambridgeBlue = new Color(0x82, 0xAA, 0x9E);
        Color slateGray = new Color(0x79, 0x86, 0x93);

        // Ensure background color is applied to the entire frame and table area
        getContentPane().setBackground(azure);

        // Explicitly set the background color for the table's parent scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(columbiaBlue);
        add(scrollPane, BorderLayout.CENTER);
        
        table.setBackground(slateGray);
        table.setForeground(Color.BLACK);
        table.setGridColor(roseQuartz);
        table.setSelectionBackground(cambridgeBlue);
        table.setSelectionForeground(Color.WHITE);
    }

    private void refresh(){
        DefaultTableModel m = (DefaultTableModel) table.getModel();
        m.setRowCount(0);
        for (Note n : noteDAO.getNotesByUser(user.getId())){
            // Fetch admin name using adminId
            String adminName = "";
            try {
                user.User admin = new dao.impl.UserDAOImpl().getUserById(n.getAdminId());
                adminName = (admin != null) ? admin.getUsername() : "Unknown";
            } catch (Exception e) {
                adminName = "Unknown";
            }
            m.addRow(new Object[]{ n.getCreatedAt(), n.getNoteText(), adminName });
        }
    }
}
