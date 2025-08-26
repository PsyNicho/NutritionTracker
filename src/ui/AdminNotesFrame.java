package ui;

import user.User;
import user.Note;
import javax.swing.*;
import java.io.*;
import java.nio.file.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import dao.NoteDAO;
import dao.impl.NoteDAOImpl;

public class AdminNotesFrame extends JFrame {
    public AdminNotesFrame(User user) {
        super("Notes by Admin");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTable table = new JTable();
        table.setModel(new DefaultTableModel(new Object[]{"ID", "Note", "Date"}, 0));
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        NoteDAO noteDAO = new NoteDAOImpl();
        List<Note> notes = noteDAO.getNotesByUser(user.getId());
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (Note note : notes) {
            model.addRow(new Object[]{note.getId(), note.getNoteText(), note.getCreatedAt()});
        }
    }
}