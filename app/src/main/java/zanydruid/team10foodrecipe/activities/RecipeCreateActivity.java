package zanydruid.team10foodrecipe.activities;

import android.support.v4.app.Fragment;

import zanydruid.team10foodrecipe.fragments.IngredientCreateFragment;

/**
 * Created by yizhu on 4/9/16.
 */
public class RecipeCreateActivity extends SingleFragmentActivity {


    private int mId;

    @Override
    protected Fragment createFragment() {
        return IngredientCreateFragment.newInstance();
    }
}
