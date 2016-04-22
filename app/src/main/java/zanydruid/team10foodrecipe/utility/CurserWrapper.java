package zanydruid.team10foodrecipe.utility;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.net.Uri;

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
    public Ingredient getIngredientDetail(){
        int ingredientId = getInt(0);
        String ingredientName = getString(1);
        double amount = getDouble(2);
        int unitId = getInt(3);
        Ingredient ingredient = new Ingredient(ingredientId,ingredientName,amount,unitId);
        return ingredient;
    }

    /**
     * Get Ingredient for choices
     *
     * @return
     */
    public Ingredient getIngredient(){
        int ingredientId = getInt(0);
        String ingredientName = getString(1);
        int unitId = getInt(2);
        return new Ingredient(ingredientId, ingredientName,unitId);
    }

    /**
     * Get Nutrition by recipe id
     *
     * @return
     */
    public Nutrition getNutrition(){
        int nutritionId = getInt(0);
        String nutritionName = getString(1);
        double amount = getDouble(2);
        int unitId = getInt(3);
        Nutrition nutrition = new Nutrition(nutritionId,nutritionName,amount,unitId);
        return nutrition;
    }

    public Flavor getFlavorList(){
        int flavorId = getInt(0);
        String flavorName = getString(1);
        Flavor flavor = new Flavor(flavorId,flavorName);
        return flavor;
    }

    public Flavor getFlavor(){
        String flavorName = getString(0);
        int amount = getInt(1);
        Flavor flavor = new Flavor(flavorName,amount);
        return flavor;
    }

    public Recipe getRecipeList(){
        int recipeId = getInt(0);
        String name = getString(1);
        int time = getInt(2);
        String photoUriString = getString(3);

        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        recipe.setName(name);
        recipe.setTime(time);
        if(!(photoUriString==null)) {
            Uri uri = Uri.parse(photoUriString);
            recipe.setPhotoUri(uri);
        }
        return recipe;
    }

    public int getAverageRating(){
        return getInt(0);
    }

    public Recipe getRecipe(){
        int recipeId = getInt(0);
        String name = getString(1);
        int time = getInt(2);
        int servePeople = getInt(3);
        String description = getString(4);
        String source = getString(5);
        String photoUriString = getString(6);

        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        recipe.setName(name);
        recipe.setDescription(description);
        recipe.setTime(time);
        recipe.setServePeople(servePeople);
        recipe.setSource(source);
        if(!(photoUriString==null)) {
            Uri uri = Uri.parse(photoUriString);
            recipe.setPhotoUri(uri);
        }
        return recipe;
    }

    public Comment getComment(){
        int rating = getInt(0);
        String commentString = getString(1);
        Comment comment = new Comment(commentString,rating);
        return comment;
    }

}
