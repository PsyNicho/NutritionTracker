
package dao;

import user.Note;
import java.util.List;

public interface NoteDAO {
    void addNote(Note note);
    java.util.List<Note> getNotesByUser(int userId);
}
