package zanydruid.team10foodrecipe.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import zanydruid.team10foodrecipe.fragments.RecipeListFragment;
import zanydruid.team10foodrecipe.utility.Kitchen;

/**
 * Created by yizhu on 4/8/16.
 */
public class RecipeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return RecipeListFragment.newInstance();
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
