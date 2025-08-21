
package dao;

import food.Food;
import java.util.List;

public interface FoodDAO {
    void addFood(Food food);
    Food getFoodByNameForUser(String name, int userId);
    Food getFoodById(int id);
    java.util.List<Food> getFoodsByUser(int userId);
    void updateFood(Food food);
    void deleteFood(int id);
}
