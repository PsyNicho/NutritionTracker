package ui;

import user.User;
import javax.swing.*;
import java.io.*;
import java.nio.file.*;

public class AdminNotesFrame extends JFrame {
    public AdminNotesFrame(User user) {
        super("Notes by Admin");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea notesArea = new JTextArea();
        notesArea.setEditable(false);

        // Read notes from file
        try {
            String notes = Files.readString(Paths.get("admin_notes.txt"));
            notesArea.setText(notes);
        } catch (IOException e) {
            notesArea.setText("No notes from admin yet.");
        }

        add(new JScrollPane(notesArea));
    }
}