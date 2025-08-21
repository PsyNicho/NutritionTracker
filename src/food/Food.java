
package food;

public class Food {
    private int id;
    private int userId;
    private String name;
    private double caloriesPer100g;
    private double proteinPer100g;
    private double carbsPer100g;
    private double fatsPer100g;
    private double fiberPer100g;

    public Food() {}

    public Food(int id, int userId, String name, double caloriesPer100g, double proteinPer100g,
                double carbsPer100g, double fatsPer100g, double fiberPer100g) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.caloriesPer100g = caloriesPer100g;
        this.proteinPer100g = proteinPer100g;
        this.carbsPer100g = carbsPer100g;
        this.fatsPer100g = fatsPer100g;
        this.fiberPer100g = fiberPer100g;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getCaloriesPer100g() { return caloriesPer100g; }
    public void setCaloriesPer100g(double caloriesPer100g) { this.caloriesPer100g = caloriesPer100g; }

    public double getProteinPer100g() { return proteinPer100g; }
    public void setProteinPer100g(double proteinPer100g) { this.proteinPer100g = proteinPer100g; }

    public double getCarbsPer100g() { return carbsPer100g; }
    public void setCarbsPer100g(double carbsPer100g) { this.carbsPer100g = carbsPer100g; }

    public double getFatsPer100g() { return fatsPer100g; }
    public void setFatsPer100g(double fatsPer100g) { this.fatsPer100g = fatsPer100g; }

    public double getFiberPer100g() { return fiberPer100g; }
    public void setFiberPer100g(double fiberPer100g) { this.fiberPer100g = fiberPer100g; }

    @Override
    public String toString(){
        return name;
    }
}
