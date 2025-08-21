
package nutritionsummary;

public class NutritionSummary {
    private double calories;
    private double protein;
    private double carbs;
    private double fats;
    private double fiber;

    public NutritionSummary(){}

    public NutritionSummary(double calories, double protein, double carbs, double fats, double fiber) {
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;
        this.fiber = fiber;
    }

    public double getCalories() { return calories; }
    public double getProtein() { return protein; }
    public double getCarbs() { return carbs; }
    public double getFats() { return fats; }
    public double getFiber() { return fiber; }

    @Override public String toString(){
        return String.format("NutritionSummary{{calories=%.2f, protein=%.2f, carbs=%.2f, fats=%.2f, fiber=%.2f}}",
                calories, protein, carbs, fats, fiber);
    }
}
