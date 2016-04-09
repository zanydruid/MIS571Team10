package zanydruid.team10foodrecipe.utility;

import android.database.Cursor;
import android.database.CursorWrapper;

import zanydruid.team10foodrecipe.Models.Comment;
import zanydruid.team10foodrecipe.Models.Flavor;
import zanydruid.team10foodrecipe.Models.Ingredient;
import zanydruid.team10foodrecipe.Models.Nutrition;
import zanydruid.team10foodrecipe.Models.Recipe;
import zanydruid.team10foodrecipe.Models.Unit;

/**
 * Created by yizhu on 4/4/16.
 */
public class CurserWrapper extends android.database.CursorWrapper {

    public CurserWrapper(Cursor cursor){
        super(cursor);
    }

    /**
     * Get Unit
     *
     * @return
     */
    public Unit getUnits(){
        int uid = getInt(0);
        String unitString = getString(1);
        Unit unit = new Unit(uid,unitString);
        return unit;
    }

    /**
     * Get Ingredient
     *
     * @return
     */
    public Ingredient getIngredient(){
        int ingredientId = getInt(0);
        String ingredientName = getString(1);
        double amount = getDouble(2);
        int unitId = getInt(3);
        Ingredient ingredient = new Ingredient(ingredientId,ingredientName,amount,unitId);
        return ingredient;
    }

    public Nutrition getNutrition(){
        int nutritionId = getInt(0);
        String nutritionName = getString(1);
        double amount = getDouble(2);
        int unitId = getInt(3);
        Nutrition nutrition = new Nutrition(nutritionId,nutritionName,amount,unitId);
        return nutrition;
    }

    public Flavor getFlavor(){
        int flavorId = getInt(0);
        String flavorName = getString(1);
        Flavor flavor = new Flavor(flavorId,flavorName);
        return flavor;
    }

    public Recipe getRecipe(){
        int RecipeId = getInt(0);
        String name = getString(1);
        int rating = getInt(2);
        String description = getString(3);
        int time = getInt(4);
        int calories = getInt(5);
        int ingredientNum = getInt(6);
        int servePeople = getInt(7);
        String source = getString(8);
        Recipe recipe = new Recipe();
        recipe.setId(RecipeId);
        recipe.setName(name);
        recipe.setRating(rating);
        recipe.setDescription(description);
        recipe.setTime(time);
        recipe.setCaloriesNum(calories);
        recipe.setIngredientNum(ingredientNum);
        recipe.setServePeople(servePeople);
        recipe.setSource(source);
        return recipe;
    }

    public Comment getComment(){
        int rating = getInt(0);
        String commentString = getString(1);
        Comment comment = new Comment(commentString,rating);
        return comment;
    }

}
