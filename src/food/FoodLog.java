package food;

import java.util.Date;

public class FoodLog {
    private int id;
    private int userId;
    private int foodId;
    private double gramsConsumed;
    private Date date;

    public FoodLog(int id, int userId, int foodId, double gramsConsumed, Date date) {
        this.id = id;
        this.userId = userId;
        this.foodId = foodId;
        this.gramsConsumed = gramsConsumed;
        this.date = date;
    }

    public FoodLog(int userId, int foodId, double gramsConsumed, Date date) {
        this.userId = userId;
        this.foodId = foodId;
        this.gramsConsumed = gramsConsumed;
        this.date = date;
    }

    // Getters and setters
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public int getFoodId() { return foodId; }
    public double getGramsConsumed() { return gramsConsumed; }
    public Date getDate() { return date; }

    public void setId(int id) { this.id = id; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setFoodId(int foodId) { this.foodId = foodId; }
    public void setGramsConsumed(double gramsConsumed) { this.gramsConsumed = gramsConsumed; }
    public void setDate(Date date) { this.date = date; }
}
