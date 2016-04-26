package zanydruid.team10foodrecipe.utility;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import zanydruid.team10foodrecipe.BDconstance.DBConstant;
import zanydruid.team10foodrecipe.BDconstance.SQLCommand;
import zanydruid.team10foodrecipe.Models.Comment;
import zanydruid.team10foodrecipe.Models.Flavor;
import zanydruid.team10foodrecipe.Models.Ingredient;
import zanydruid.team10foodrecipe.Models.Nutrition;
import zanydruid.team10foodrecipe.Models.Recipe;
import zanydruid.team10foodrecipe.Models.Unit;

/**
 * Class to manipulate tables & data
 * Uses singleton pattern to create single instance
 */
public class Kitchen {
    private static Kitchen instance = null;
    private SQLiteDatabase mDatabase;
    private Context mContext;
    private List<Unit> mUnits;
    private static final String TAG = "Kitchen";

    private Kitchen(Context context) {
        //path of database file
        mContext = context.getApplicationContext();
        String path = DBConstant.DATABASE_PATH + "/" + DBConstant.DATABASE_FILE;
        File file = new File(path);
        if (file.exists() && !file.isDirectory()) {
            mDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
            // Initiate unit list
            mUnits = this.getUnitsFromDB();
        }else{
            Log.e(TAG,"path doesn't exist.");
        }

    }
    /*
     * Singleton Pattern
     * Why should we avoid multiple instances here?
     */
    public static Kitchen getInstance(Context context) {
        if (instance==null) instance = new Kitchen(context);
        return instance;
    }

    /**
     * Get List of Unit from database
     *
     * @return
     */
    private List<Unit> getUnitsFromDB(){
        Cursor cursor = this.execQuery(SQLCommand.GET_UNITS);
        CurserWrapper wrapper = new CurserWrapper(cursor);
        List<Unit> units = new ArrayList<>();
        try{
            wrapper.moveToFirst();
            while(!wrapper.isAfterLast()){
                units.add(wrapper.getUnits());
                wrapper.moveToNext();
            }
        } finally {
            wrapper.close();
        }
        return units;
    }

    /**
     * Actual method to get Units
     *
     * @return
     */
    public List<Unit> getUnits(){
        return this.mUnits;
    }

    // Get specified unit by id
    public Unit getUnitById(int id){
        for(Unit unit: mUnits){
            if(unit.getUnitId()==id){
                return unit;
            }
        }
        return null;
    }

    /**
     * Get ingredients list from database
     *
     * @return
     */
    public List<Ingredient> getIngredientsById(int id){
        String[] args = new String[1];
        args[0] = String.valueOf(id);
        Cursor cursor = this.execQuery(SQLCommand.GET_INGREDIENTS_BY_ID,args);
        CurserWrapper wrapper = new CurserWrapper(cursor);
        List<Ingredient> ingredients = new ArrayList<>();
        try{
            wrapper.moveToFirst();
            while(!wrapper.isAfterLast()){
                ingredients.add(wrapper.getIngredientDetail());
                wrapper.moveToNext();
            }
        } finally {
            wrapper.close();
        }
        return ingredients;
    }

    public List<Ingredient> getAllIngredients(){
        Cursor cursor = this.execQuery(SQLCommand.GET_INGREDIENTS);
        CurserWrapper wrapper = new CurserWrapper(cursor);
        List<Ingredient> ingredients = new ArrayList<>();
        try{
            wrapper.moveToFirst();
            while(!wrapper.isAfterLast()){
                ingredients.add(wrapper.getIngredient());
                wrapper.moveToNext();
            }
        } finally {
            wrapper.close();
        }
        return ingredients;
    }

    /**
     * Get nutrition list from database
     *
     * @return
     */
    public List<Nutrition> getNutritionsFromDB(int id){
        String[] args = new String[1];
        args[0] = String.valueOf(id);
        Cursor cursor = this.execQuery(SQLCommand.GET_NUTRITIONS_BY_ID,args);
        CurserWrapper wrapper = new CurserWrapper(cursor);
        List<Nutrition> nutritions = new ArrayList<>();
        try{
            wrapper.moveToFirst();
            while(!wrapper.isAfterLast()){
                nutritions.add(wrapper.getNutrition());
                wrapper.moveToNext();
            }
        } finally {
            wrapper.close();
        }
        return nutritions;
    }

    /**
     * Get flavor list from database
     *
     * @return
     */
    public List<Flavor> getFlavorsFromDB(int id){
        String[] args = new String[1];
        args[0] = String.valueOf(id);
        Cursor cursor = this.execQuery(SQLCommand.GET_FLAVORS_By_ID,args);
        CurserWrapper wrapper = new CurserWrapper(cursor);
        List<Flavor> flavors = new ArrayList<>();
        try{
            wrapper.moveToFirst();
            while(!wrapper.isAfterLast()){
                flavors.add(wrapper.getFlavor());
                wrapper.moveToNext();
            }
        } finally {
            wrapper.close();
        }
        return flavors;
    }

    public List<Flavor> getFlavorList(){
        Cursor cursor = this.execQuery(SQLCommand.GET_FLAVORS);
        CurserWrapper wrapper = new CurserWrapper(cursor);
        List<Flavor> list = new ArrayList<>();
        try{
            wrapper.moveToFirst();
            while(!wrapper.isAfterLast()){
                list.add(wrapper.getFlavorList());
                wrapper.moveToNext();
            }
        } finally {
            wrapper.close();
        }
        return list;
    }


    /**
     * Get all the recipes from database
     *
     * @return
     */
    public List<Recipe> getRecipesFromDB(){
        Cursor cursor = this.execQuery(SQLCommand.GET_RECIPES);
        CurserWrapper wrapper = new CurserWrapper(cursor);
        List<Recipe> recipes = new ArrayList<>();
        try{
            wrapper.moveToFirst();
            while(!wrapper.isAfterLast()){
                recipes.add(wrapper.getRecipeList());
                wrapper.moveToNext();
            }

        } finally {
            wrapper.close();
        }
        return recipes;
    }

    /**
     * Get specified recipe by its id
     *
     * @param id
     * @return
     */
    public Recipe getRecipeById(int id){
        String[]args = new String[1];
        args[0] = String.valueOf(id);
        Cursor cursor = this.execQuery(SQLCommand.GET_RECIPE_BY_ID, args);
        Cursor ratingCursor = this.execQuery(SQLCommand.GET_AVERAGE_RATING,args);
        CurserWrapper ratingWapper = new CurserWrapper(ratingCursor);
        CurserWrapper wrapper = new CurserWrapper(cursor);
        try{
            wrapper.moveToFirst();
            ratingWapper.moveToFirst();
           if(wrapper.getCount()==0||ratingWapper.getCount()==0){
               return null;
           }
            Recipe recipe = wrapper.getRecipe();
            recipe.setRating(ratingWapper.getAverageRating());
            return recipe;
        } finally {
            wrapper.close();
        }
    }

    /**
     * Get comments of one recipe by its id
     *
     * @param id
     * @return
     */
    public List<Comment> getCommentsById(int id){
        String[] args = new String[1];
        args[0] = String.valueOf(id);
        Cursor  cursor = this.execQuery(SQLCommand.GET_COMMENTS,args);
        CurserWrapper wrapper = new CurserWrapper(cursor);
        List<Comment> comments = new ArrayList<>();
        try{
            wrapper.moveToFirst();
            while(!wrapper.isAfterLast()){
                comments.add(wrapper.getComment());
                wrapper.moveToNext();
            }
        } finally {
            wrapper.close();
        }
        return comments;
    }

    public void insertRecipe(Recipe recipe){
        String[] args = new String[7];
        args[0] = String.valueOf(recipe.getId());
        args[1] = recipe.getName();
        args[2] = recipe.getDescription();
        args[3] = String.valueOf(recipe.getTime());
        args[4] = String.valueOf(recipe.getServePeople());
        args[5] = recipe.getSource();
        String realPath = recipe.getPhotoUri().getPath();
        //String realPath = getRealPathFromURI(mContext,recipe.getPhotoUri());
        args[6] = realPath;
        this.execSQL(SQLCommand.INSERT_RECIPE, args);
    }

//    public String getRealPathFromURI(Context context, Uri contentUri) {
//        Cursor cursor = null;
//        try {
//            String[] proj = { MediaStore.Images.Media.DATA };
//            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//            return cursor.getString(column_index);
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//    }

    public void updateRecipe(Recipe recipe){
        String[] args = new String[7];
        args[0] = recipe.getName();
        args[1] = String.valueOf(recipe.getTime());
        args[2] = String.valueOf(recipe.getServePeople());
        args[3] = recipe.getDescription();
        args[4] = recipe.getSource();
        args[5] = recipe.getPhotoUri().toString();
        args[6] = String.valueOf(recipe.getId());
        this.execSQL(SQLCommand.UPDATE_RECIPE,args);
    }

    public void insertFlavors(List<Flavor> flavors, int recipeId){
        String[] args = new String[3];
        for(Flavor flavor: flavors){
            args[0] = String.valueOf(flavor.getFlavorId());
            args[1] = String.valueOf(recipeId);
            args[2] = String.valueOf(flavor.getAmount());
            this.execSQL(SQLCommand.INSERT_FLAVORS,args);
        }
    }

    public void insertIngredients(List<Ingredient> ingredients, int recipeId){
        String[] args = new String[3];
        for(Ingredient ingredient: ingredients){
            args[0] = String.valueOf(ingredient.getIngredientId());
            args[1] = String.valueOf(recipeId);
            args[2] = String.valueOf(ingredient.getAmount());
            this.execSQL(SQLCommand.INSERT_INGREDIENTS_BY_ID,args);
        }
    }

    /**
     * Write a new comment into database
     *
     * @param commentString
     * @param rating
     */
    public void writeComment(int recipeId, String commentString, int rating){
        String[] args = new String[3];
        args[0] = String.valueOf(recipeId);
        args[1] = commentString;
        args[2] = String.valueOf(rating);
        this.execSQL(SQLCommand.WRITE_COMMENT,args);
    }

    /**
     * Copy database file
     * From assets folder (in the project) to android folder (on device)
     */
    public static void copyDB(Context context) throws IOException,FileNotFoundException{
        String path = DBConstant.DATABASE_PATH + "/" + DBConstant.DATABASE_FILE;
        File file = new File(path);
        if (!file.exists()){
            DBOpenHelper dbhelper = new DBOpenHelper(context, path ,1);
            dbhelper.getWritableDatabase();
            InputStream is = context.getAssets().open(DBConstant.DATABASE_FILE);
            OutputStream os = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer))>0){
                os.write(buffer, 0, length);
            }
            is.close();
            os.flush();
            os.close();
        }
    }

    /**
     * execute sql without returning data, such as alter
     * @param sql
     */
    public void execSQL(String sql) throws SQLException {
        mDatabase.execSQL(sql);
    }
    /**
     * execute sql such as update/delete/insert
     * @param sql
     * @param args
     * @throws SQLException
     */
    public void execSQL(String sql, Object[] args) throws SQLException {
        mDatabase.execSQL(sql, args);
    }
    /**
     * execute sql query
     * @param sql
     * @param selectionArgs
     * @return cursor
     * @throws SQLException
     */
    public Cursor execQuery(String sql,String[] selectionArgs) throws SQLException {
        return mDatabase.rawQuery(sql, selectionArgs);
    }
    /**
     * execute query without arguments
     * @param sql
     * @return
     * @throws SQLException
     */
    public Cursor execQuery(String sql) throws SQLException {
        return this.execQuery(sql, null);
    }
    /**
     * close database
     */
    public void closeDB()
    {
        if (mDatabase !=null) mDatabase.close();
    }

    public File getPhotoFile(Recipe recipe){
        File externalFileDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if(externalFileDir==null){
            return null;
        }
        return new File(externalFileDir,recipe.getPhotoFile());
    }

}
