
package user;

import java.time.LocalDateTime;

public class Note {
    private int id;
    private int userId;
    private int adminId;
    private String noteText;
    private LocalDateTime createdAt;

    public Note(){}

    public Note(int id, int userId, int adminId, String noteText, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.adminId = adminId;
        this.noteText = noteText;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public int getAdminId() { return adminId; }
    public String getNoteText() { return noteText; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
