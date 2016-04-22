package zanydruid.team10foodrecipe.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import zanydruid.team10foodrecipe.Models.Flavor;
import zanydruid.team10foodrecipe.Models.Recipe;
import zanydruid.team10foodrecipe.R;
import zanydruid.team10foodrecipe.fragments.CommentFragment;
import zanydruid.team10foodrecipe.fragments.IngredientFragment;
import zanydruid.team10foodrecipe.fragments.NutritionFragment;
import zanydruid.team10foodrecipe.utility.Kitchen;

public class RecipeActivity extends AppCompatActivity {
    private Recipe mRecipe;
    private List<Flavor> mFlavorList;

    private ImageView mImageView;

    private TextView mRecipeName;
    private RatingBar mRatingBar;
    private TextView mTimeTextView;
    private TextView mFlavorTextView;
    private TextView mPeopleServe;
    private TextView mDescription;
    private TextView mSource;

    private FragmentTabHost mTabHost;

    private static final String EXTRA_RECIPE_ID = "recipeId";
    private static final String TAG = "recipe";

    public static Intent newIntent(Context context, int id){
        Intent intent = new Intent(context,RecipeActivity.class);
        intent.putExtra(EXTRA_RECIPE_ID,id);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        int id = (int)getIntent().getSerializableExtra(EXTRA_RECIPE_ID);
        mRecipe = Kitchen.getInstance(this).getRecipeById(id);

        mFlavorList = Kitchen.getInstance(this).getFlavorsFromDB(id);

        mImageView = (ImageView) findViewById(R.id.activity_recipe_photo_image_view);
        mImageView.setImageURI(mRecipe.getPhotoUri());

        mRecipeName = (TextView) findViewById(R.id.activity_recipe_name_text_view);
        mRecipeName.setText(mRecipe.getName());

        mRatingBar = (RatingBar) findViewById(R.id.activity_recipe_ratingbar_indicator);
        mRatingBar.setRating(mRecipe.getRating());

        mTimeTextView = (TextView) findViewById(R.id.recipe_infor_time_text_view);
        mTimeTextView.setText(String.valueOf(mRecipe.getTime()) + "min");

        mPeopleServe = (TextView) findViewById(R.id.recipe_infor_people_toserve_text_view);
        mPeopleServe.setText(String.valueOf(mRecipe.getServePeople())+" people");

        mFlavorTextView = (TextView) findViewById(R.id.recipe_infor_flavor_text_view);
        String flavors = new String();
        for(Flavor flavor: mFlavorList){
            flavors += flavor.getFlavorName()+", ";
        }
        mFlavorTextView.setText(flavors);

        mDescription = (TextView) findViewById(R.id.recipe_infor_description_text_view);
        mDescription.setText(mRecipe.getDescription());

        mSource = (TextView) findViewById(R.id.recipe_infor_source_text_view);
        mSource.setText(mRecipe.getSource());

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        Bundle ingredientArgs = new Bundle();
        ingredientArgs.putSerializable(IngredientFragment.ARG_INGREDIENT, mRecipe.getId());
        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("Ingredients"), IngredientFragment.class, ingredientArgs);
        Bundle nutritionArgs = new Bundle();
        nutritionArgs.putSerializable(NutritionFragment.ARG_NUTRITION, mRecipe.getId());
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("Nutritions"), NutritionFragment.class, nutritionArgs);
        Bundle commentArgs = new Bundle();
        commentArgs.putSerializable(CommentFragment.ARG_COMMENT, mRecipe.getId());
        mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator("Comments"), CommentFragment.class, commentArgs);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.recipe_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.recipe_menu_edit:
                Intent intent = RecipeCreateActivity.newIntent(this,mRecipe.getId());
                startActivity(intent);
                return true;
            default:
               return false;
        }
    }
}
