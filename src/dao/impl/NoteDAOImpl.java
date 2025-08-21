
package dao.impl;

import connection.DatabaseConnection;
import dao.NoteDAO;
import user.Note;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteDAOImpl implements NoteDAO {
    @Override
    public void addNote(Note note) {
        String sql = "INSERT INTO notes (user_id, admin_id, note_text) VALUES (?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, note.getUserId());
            ps.setInt(2, note.getAdminId());
            ps.setString(3, note.getNoteText());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public List<Note> getNotesByUser(int userId) {
        List<Note> list = new ArrayList<>();
        String sql = "SELECT * FROM notes WHERE user_id=? ORDER BY created_at DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Note(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getInt("admin_id"),
                            rs.getString("note_text"),
                            rs.getTimestamp("created_at").toLocalDateTime()
                    ));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}
