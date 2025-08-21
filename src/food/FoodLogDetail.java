package food;

public class FoodLogDetail {
    private int id;
    private String foodName;
    private double gramsConsumed;
    private double calories;
    private double protein;
    private double carbs;
    private double fats;
    private double fiber;

    public FoodLogDetail(int id, String foodName, double gramsConsumed,
                         double calories, double protein,
                         double carbs, double fats, double fiber) {
        this.id = id;
        this.foodName = foodName;
        this.gramsConsumed = gramsConsumed;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;
        this.fiber = fiber;
    }

    // Getters
    public int getId() { return id; }
    public String getFoodName() { return foodName; }
    public double getGramsConsumed() { return gramsConsumed; }
    public double getCalories() { return calories; }
    public double getProtein() { return protein; }
    public double getCarbs() { return carbs; }
    public double getFats() { return fats; }
    public double getFiber() { return fiber; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setFoodName(String foodName) { this.foodName = foodName; }
    public void setGramsConsumed(double gramsConsumed) { this.gramsConsumed = gramsConsumed; }
    public void setCalories(double calories) { this.calories = calories; }
    public void setProtein(double protein) { this.protein = protein; }
    public void setCarbs(double carbs) { this.carbs = carbs; }
    public void setFats(double fats) { this.fats = fats; }
    public void setFiber(double fiber) { this.fiber = fiber; }
}
