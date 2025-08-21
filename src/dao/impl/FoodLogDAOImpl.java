package dao.impl;

import dao.FoodLogDAO;
import food.FoodLog;
import food.FoodLogDetail;
import connection.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FoodLogDAOImpl implements FoodLogDAO {

    @Override
    public void addFoodLog(FoodLog log) {
        String sql = "INSERT INTO food_log (user_id, food_id, grams_consumed, date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, log.getUserId());
            stmt.setInt(2, log.getFoodId());
            stmt.setDouble(3, log.getGramsConsumed());
            stmt.setDate(4, new java.sql.Date(log.getDate().getTime()));
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<FoodLog> getLogsByUserAndDate(int userId, Date date) {
        List<FoodLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM food_log WHERE user_id = ? AND date = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setDate(2, new java.sql.Date(date.getTime()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                logs.add(new FoodLog(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("food_id"),
                        rs.getDouble("grams_consumed"),
                        rs.getDate("date")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logs;
    }

    // --- Totals Helper ---
    private double getTotalForColumn(String column, int userId, Date date) {
        double total = 0.0;
        String sql = "SELECT SUM((f." + column + " / 100) * fl.grams_consumed) AS total " +
                     "FROM food_log fl " +
                     "JOIN foods f ON fl.food_id = f.id " +
                     "WHERE fl.user_id = ? AND fl.date = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setDate(2, new java.sql.Date(date.getTime()));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    @Override
    public double getTotalCaloriesForUserAndDate(int userId, Date date) {
        return getTotalForColumn("calories_per_100g", userId, date);
    }

    @Override
    public double getTotalProteinForUserAndDate(int userId, Date date) {
        return getTotalForColumn("protein_per_100g", userId, date);
    }

    @Override
    public double getTotalCarbsForUserAndDate(int userId, Date date) {
        return getTotalForColumn("carbs_per_100g", userId, date);
    }

    @Override
    public double getTotalFatsForUserAndDate(int userId, Date date) {
        return getTotalForColumn("fats_per_100g", userId, date);
    }

    @Override
    public double getTotalFiberForUserAndDate(int userId, Date date) {
        return getTotalForColumn("fiber_per_100g", userId, date);
    }

    @Override
    public List<FoodLogDetail> getDetailedLogsByUserAndDate(int userId, Date date) {
        List<FoodLogDetail> details = new ArrayList<>();
        String sql = "SELECT fl.id, f.name, fl.grams_consumed, " +
                     "(f.calories_per_100g/100 * fl.grams_consumed) AS calories, " +
                     "(f.protein_per_100g/100 * fl.grams_consumed) AS protein, " +
                     "(f.carbs_per_100g/100 * fl.grams_consumed) AS carbs, " +
                     "(f.fats_per_100g/100 * fl.grams_consumed) AS fats, " +
                     "(f.fiber_per_100g/100 * fl.grams_consumed) AS fiber " +
                     "FROM food_log fl " +
                     "JOIN foods f ON fl.food_id = f.id " +
                     "WHERE fl.user_id = ? AND fl.date = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setDate(2, new java.sql.Date(date.getTime()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                details.add(new FoodLogDetail(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("grams_consumed"),
                        rs.getDouble("calories"),
                        rs.getDouble("protein"),
                        rs.getDouble("carbs"),
                        rs.getDouble("fats"),
                        rs.getDouble("fiber")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return details;
    }
}
