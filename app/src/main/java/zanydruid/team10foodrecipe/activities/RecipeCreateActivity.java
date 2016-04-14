package zanydruid.team10foodrecipe.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import zanydruid.team10foodrecipe.fragments.IngredientCreateFragment;
import zanydruid.team10foodrecipe.utility.Kitchen;

/**
 * Created by yizhu on 4/9/16.
 */
public class RecipeCreateActivity extends SingleFragmentActivity {


    private int mId;

    @Override
    protected Fragment createFragment() {
        return IngredientCreateFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Copy the db in assets to data/data/
        try{
            Kitchen.copyDB(getBaseContext());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
