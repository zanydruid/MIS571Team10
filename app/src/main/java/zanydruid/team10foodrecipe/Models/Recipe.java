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
    int time = 0;
    int servePeople = 0;
    String source;
    Uri photoUri;

    public Uri getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(Uri photoUri) {
        this.photoUri = photoUri;
    }

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
        return "Recipe"+ getName()+String.valueOf(getId()) + ".jpg";
    }

}

