package zanydruid.team10foodrecipe.Models;

import android.net.Uri;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by yizhu on 4/3/16.
 */
public class Recipe {
    int id;
    String name;
    int rating;
    String description;
    int time;
    int caloriesNum;
    int ingredientNum;
    int servePeople;
    String source;
    //String photoFile;
//    private List<Comment> commentList;
//    private HashMap<Integer,Double> ingredientDetail;
//    private HashMap<Integer,Double> nutritionDetail;
//    private HashMap<Integer,Double> flavorDetail;
//
//    public void addComment(String commentString,int rating){
//        Comment comment = new Comment(commentString,rating);
//        this.commentList.add(comment);
//    }
//
//    public List<Comment> getComments(){
//        return this.commentList;
//    }
//
//    public void addIngredient(int ingredientId, double amount){
//        this.ingredientDetail.put(ingredientId,amount);
//    }
//
//    public double getIngredientAmount(int id){
//        return this.ingredientDetail.get(id);
//    }
//
//    public void addNutrition(int nutritionId, double amount){
//        this.nutritionDetail.put(nutritionId,amount);
//    }
//
//    public double getNutritionAmount(int id){
//        return this.nutritionDetail.get(id);
//    }
//
//    public void addFlavor(int flavorId, double amount){
//        this.flavorDetail.put(flavorId,amount);
//    }
//
//    public double getFlavorAmount(int id){
//        return this.flavorDetail.get(id);
//    }

    // Constructor

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCaloriesNum() {
        return caloriesNum;
    }

    public void setCaloriesNum(int caloriesNum) {
        this.caloriesNum = caloriesNum;
    }

    public int getIngredientNum() {
        return ingredientNum;
    }

    public void setIngredientNum(int ingredientNum) {
        this.ingredientNum = ingredientNum;
    }

    public int getServePeople() {
        return servePeople;
    }

    public void setServePeople(int servePeople) {
        this.servePeople = servePeople;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPhotoFile() {
        return "Recipe"+ String.valueOf(getId()) + ".jpg";
    }

}

