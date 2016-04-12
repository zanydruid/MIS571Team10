package zanydruid.team10foodrecipe.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by yizhu on 4/9/16.
 */
public class RecipeCreateFragment extends Fragment {

    private int mId;
    private static final String ARG_RECIPE_ID = "recipeId";

    public static RecipeCreateFragment newInstance(int id){
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECIPE_ID, id);
        RecipeCreateFragment fragment = new RecipeCreateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mId = (int) getArguments().getSerializable(ARG_RECIPE_ID);
    }
}
