package zanydruid.team10foodrecipe.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import zanydruid.team10foodrecipe.Models.Recipe;
import zanydruid.team10foodrecipe.R;
import zanydruid.team10foodrecipe.utility.Kitchen;

public class RecipeActivity extends AppCompatActivity {
    private Recipe mRecipe;

    private TextView mRecipeName;
    private RatingBar mRatingBar;
    private TextView mTimeTextView;
    private TextView mCalorieTextView;
    private TextView mPeopleServe;
    private TextView mDescription;
    private TextView mSource;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        // Copy the db in assets to data/data/
        try{
            Kitchen.copyDB(getBaseContext());
        }catch(Exception e){
            e.printStackTrace();
        }
        mRecipe = Kitchen.getInstance(this).getRecipeById(1);
        mRecipeName = (TextView) findViewById(R.id.activity_recipe_name_text_view);
        mRecipeName.setText(mRecipe.getName());

        mRatingBar = (RatingBar) findViewById(R.id.activity_recipe_ratingbar_indicator);
        mRatingBar.setRating(mRecipe.getRating());

        mTimeTextView = (TextView) findViewById(R.id.recipe_infor_time_text_view);
        mTimeTextView.setText(String.valueOf(mRecipe.getTime())+"min");

        mCalorieTextView = (TextView) findViewById(R.id.recipe_infor_calories_text_view);
        mCalorieTextView.setText(String.valueOf(mRecipe.getCaloriesNum())+"cal");

        mPeopleServe = (TextView) findViewById(R.id.recipe_infor_people_toserve_text_view);
        mPeopleServe.setText(String.valueOf(mRecipe.getServePeople())+"people");

        mDescription = (TextView) findViewById(R.id.recipe_infor_description_text_view);
        mDescription.setText(mRecipe.getDescription());

        mSource = (TextView) findViewById(R.id.recipe_infor_source_text_view);
        mSource.setText(mRecipe.getSource());
        TabLayout tabLayout = (TabLayout)findViewById(R.id.activity_recipe_tab);
        tabLayout.addTab(tabLayout.newTab().setText("Ingredient"));
        tabLayout.addTab(tabLayout.newTab().setText("Nutrition"));
        tabLayout.addTab(tabLayout.newTab().setText("Comments"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

    }
}
