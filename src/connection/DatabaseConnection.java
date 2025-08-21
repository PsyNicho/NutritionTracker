
package connection;

import java.sql.*;

public final class DatabaseConnection {
    private static final String DB_NAME = "nutritiondb";
    private static final String BASE_URL = "jdbc:mysql://localhost:3306/";
    private static final String PARAMS = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";    // TODO: set your MySQL user
    private static final String PASSWORD = "";    // TODO: set your MySQL password

    private static Connection connection;

    private DatabaseConnection() {}

    public static synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try (Connection c = DriverManager.getConnection(BASE_URL + PARAMS, USER, PASSWORD);
                 Statement st = c.createStatement()) {
                st.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            }
            connection = DriverManager.getConnection(BASE_URL + DB_NAME + PARAMS, USER, PASSWORD);
            ensureSchema(connection);
            ensureDefaultAdmin(connection);
        }
        return connection;
    }

    private static void ensureSchema(Connection conn) {
        try (Statement st = conn.createStatement()) {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(100) NOT NULL UNIQUE," +
                    "password VARCHAR(255) NOT NULL," +
                    "role VARCHAR(20) NOT NULL DEFAULT 'user'," +
                    "goal_calories DOUBLE NOT NULL DEFAULT 0," +
                    "goal_protein DOUBLE NOT NULL DEFAULT 0," +
                    "goal_carbs DOUBLE NOT NULL DEFAULT 0," +
                    "goal_fats DOUBLE NOT NULL DEFAULT 0," +
                    "goal_fiber DOUBLE NOT NULL DEFAULT 0" +
                    ")");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS foods (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(150) NOT NULL UNIQUE," +
                    "calories_per_100g DOUBLE NOT NULL DEFAULT 0," +
                    "protein_per_100g DOUBLE NOT NULL DEFAULT 0," +
                    "carbs_per_100g DOUBLE NOT NULL DEFAULT 0," +
                    "fats_per_100g DOUBLE NOT NULL DEFAULT 0," +
                    "fiber_per_100g DOUBLE NOT NULL DEFAULT 0" +
                    ")");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS food_log (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "user_id INT NOT NULL," +
                    "food_id INT NOT NULL," +
                    "grams_consumed DOUBLE NOT NULL," +
                    "date DATE NOT NULL," +
                    "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE," +
                    "FOREIGN KEY (food_id) REFERENCES foods(id) ON DELETE CASCADE," +
                    "INDEX idx_log_user_date (user_id, date)" +
                    ")");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS notes (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "user_id INT NOT NULL," +
                    "admin_id INT NOT NULL," +
                    "note_text TEXT NOT NULL," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE" +
                    ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void ensureDefaultAdmin(Connection conn) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM users WHERE role='admin'");
             ResultSet rs = ps.executeQuery()) {
            if (rs.next() && rs.getLong(1) == 0) {
                try (PreparedStatement ins = conn.prepareStatement(
                        "INSERT INTO users (username, password, role) VALUES ('admin','admin123','admin')")) {
                    ins.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
