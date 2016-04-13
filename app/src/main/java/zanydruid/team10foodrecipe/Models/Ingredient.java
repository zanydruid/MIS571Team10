package zanydruid.team10foodrecipe.Models;

/**
 * Created by yizhu on 4/6/16.
 */
public class Ingredient{
    int ingredientId;
    String ingreName;
    double amount;
    int unitId;

    public Ingredient(int id, String name, double amount,int uid){
        this.ingredientId = id;
        this.ingreName = name;
        this.amount = amount;
        this.unitId = uid;
    }

    public Ingredient(int id, String name){
        this.ingredientId = id;
        this.ingreName = name;
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

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngreName() {
        return ingreName;
    }

    public void setIngreName(String ingreName) {
        this.ingreName = ingreName;
    }
}