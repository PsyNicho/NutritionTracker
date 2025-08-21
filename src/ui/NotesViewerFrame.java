
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
    private final JTable table = new JTable(new DefaultTableModel(new Object[]{"Date","Note","From(AdminId)"},0));

    public NotesViewerFrame(User user){
        super("Notes");
        this.user = user;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        setSize(600,360);
        setLocationRelativeTo(null);
        refresh();
    }

    private void refresh(){
        DefaultTableModel m = (DefaultTableModel) table.getModel();
        m.setRowCount(0);
        for (Note n : noteDAO.getNotesByUser(user.getId())){
            m.addRow(new Object[]{ n.getCreatedAt(), n.getNoteText(), n.getAdminId() });
        }
    }
}
