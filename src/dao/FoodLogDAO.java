package dao;

import food.FoodLog;
import food.FoodLogDetail;
import java.util.Date;
import java.util.List;

public interface FoodLogDAO {
    void addFoodLog(FoodLog log);
    List<FoodLog> getLogsByUserAndDate(int userId, Date date);

    // New methods for totals
    double getTotalCaloriesForUserAndDate(int userId, Date date);
    double getTotalProteinForUserAndDate(int userId, Date date);
    double getTotalCarbsForUserAndDate(int userId, Date date);
    double getTotalFatsForUserAndDate(int userId, Date date);
    double getTotalFiberForUserAndDate(int userId, Date date);
    
    // New method for joined detail rows
    List<FoodLogDetail> getDetailedLogsByUserAndDate(int userId, Date date);
}
