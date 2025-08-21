
package dao.impl;

import connection.DatabaseConnection;
import dao.FoodDAO;
import food.Food;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodDAOImpl implements FoodDAO {
    @Override
    public void addFood(Food food) {
        String sql = "INSERT INTO foods (user_id, name, calories_per_100g, protein_per_100g, carbs_per_100g, fats_per_100g, fiber_per_100g) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, food.getUserId());
            ps.setString(2, food.getName());
            ps.setDouble(3, food.getCaloriesPer100g());
            ps.setDouble(4, food.getProteinPer100g());
            ps.setDouble(5, food.getCarbsPer100g());
            ps.setDouble(6, food.getFatsPer100g());
            ps.setDouble(7, food.getFiberPer100g());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Food getFoodByNameForUser(String name, int userId) {
        String sql = "SELECT * FROM foods WHERE user_id=? AND name=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Food getFoodById(int id) {
        String sql = "SELECT * FROM foods WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Food> getFoodsByUser(int userId) {
        String sql = "SELECT * FROM foods WHERE user_id=? ORDER BY name";
        List<Food> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void updateFood(Food food) {
        String sql = "UPDATE foods SET name=?, calories_per_100g=?, protein_per_100g=?, carbs_per_100g=?, fats_per_100g=?, fiber_per_100g=? WHERE id=? AND user_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, food.getName());
            ps.setDouble(2, food.getCaloriesPer100g());
            ps.setDouble(3, food.getProteinPer100g());
            ps.setDouble(4, food.getCarbsPer100g());
            ps.setDouble(5, food.getFatsPer100g());
            ps.setDouble(6, food.getFiberPer100g());
            ps.setInt(7, food.getId());
            ps.setInt(8, food.getUserId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteFood(int id) {
        String sql = "DELETE FROM foods WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Food map(ResultSet rs) throws SQLException {
        return new Food(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getString("name"),
                rs.getDouble("calories_per_100g"),
                rs.getDouble("protein_per_100g"),
                rs.getDouble("carbs_per_100g"),
                rs.getDouble("fats_per_100g"),
                rs.getDouble("fiber_per_100g")
        );
    }
}
