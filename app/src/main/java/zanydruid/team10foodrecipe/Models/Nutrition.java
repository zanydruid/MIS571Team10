package zanydruid.team10foodrecipe.Models;

/**
 * Created by yizhu on 4/6/16.
 */
public class Nutrition{
    int nutritionId;
    String nutriName;
    double amount;
    int unitId;

    public Nutrition(int id, String name, double amount, int uid){
        this.nutritionId = id;
        this.nutriName = name;;
        this.amount = amount;
        this.unitId = uid;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getUnitId() {
        return unitId;
    }

    public int getNutritionId() {
        return nutritionId;
    }

    public void setNutritionId(int nutritionId) {
        this.nutritionId = nutritionId;
    }

    public String getNutriName() {
        return nutriName;
    }

    public void setNutriName(String nutriName) {
        this.nutriName = nutriName;
    }

}
