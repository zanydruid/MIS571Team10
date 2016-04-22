package zanydruid.team10foodrecipe.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.UUID;

import zanydruid.team10foodrecipe.R;
import zanydruid.team10foodrecipe.fragments.IngredientCreateFragment;
import zanydruid.team10foodrecipe.fragments.RecipeCreateFragment;
import zanydruid.team10foodrecipe.utility.Kitchen;

/**
 * Created by yizhu on 4/9/16.
 */
public class RecipeCreateActivity extends SingleFragmentActivity implements RecipeCreateFragment.CallBack, IngredientCreateFragment.IngredientCallBack {


    private int mId;
    private static final String EXTRA_RECIPE_ID = "recipeCreate";

    public static Intent newIntent(Context context, int id){
        Intent intent = new Intent(context,RecipeCreateActivity.class);
        intent.putExtra(EXTRA_RECIPE_ID,id);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return RecipeCreateFragment.newInstance(mId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mId = (int) getIntent().getSerializableExtra(EXTRA_RECIPE_ID);
        super.onCreate(savedInstanceState);
        // Copy the db in assets to data/data/
        try{
            Kitchen.copyDB(getBaseContext());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void nextStep(int id) {
        IngredientCreateFragment fragment = IngredientCreateFragment.newInstance(id);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_single_fragment_container, fragment)
                .commit();
    }

    @Override
    public void preStep(int id) {
        RecipeCreateFragment fragment = RecipeCreateFragment.newInstance(id);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_single_fragment_container, fragment)
                .commit();
    }
}
