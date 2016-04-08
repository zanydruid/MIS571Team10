package zanydruid.team10foodrecipe.Models;

/**
 * Created by yizhu on 4/6/16.
 */
public class Nutrition{
    int nutritionId;
    String nutriName;
    int unitId;

    public Nutrition(int id, String name, int uid){
        this.nutritionId = id;
        this.nutriName = name;
        this.unitId = uid;
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
