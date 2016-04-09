package zanydruid.team10foodrecipe.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import zanydruid.team10foodrecipe.Models.Recipe;
import zanydruid.team10foodrecipe.R;
import zanydruid.team10foodrecipe.activities.RecipeActivity;
import zanydruid.team10foodrecipe.utility.Kitchen;

/**
 * Created by yizhu on 4/9/16.
 */
public class RecipeListFragment extends Fragment {

    private static final String ARG_RCIPELIST = "recipeList";
    private List<Recipe> mRecipeList;
    private RecyclerView mRecyclerView;
    private RecipeAdapter mAdapter;

    public static RecipeListFragment newInstance(){
        return new RecipeListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecipeList = Kitchen.getInstance(getActivity()).getRecipesFromDB();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view_layout,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_recycler_view_layout_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new RecipeAdapter(mRecipeList);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    private class RecipeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Recipe mRecipe;
        private ImageView mImageView;
        private TextView mTitleTextView;

        public RecipeHolder(View itemView){
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.recycler_list_item_image_view);
            mTitleTextView = (TextView) itemView.findViewById(R.id.recycler_list_item_title_text_view);
            itemView.setOnClickListener(this);
        }

        public void bind(Recipe recipe){
            mRecipe = recipe;
            mTitleTextView.setText(mRecipe.getName());
        }

        @Override
        public void onClick(View v) {
            Intent intent = RecipeActivity.newIntent(getActivity(),mRecipe.getId());
            startActivity(intent);
        }
    }

    private class RecipeAdapter extends RecyclerView.Adapter<RecipeHolder>{
        private List<Recipe> mRecipes;

        public RecipeAdapter(List<Recipe> recipes){
            mRecipes = recipes;
        }

        @Override
        public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.recycler_list_item,parent,false);
            return new RecipeHolder(view);
        }

        @Override
        public void onBindViewHolder(RecipeHolder holder, int position) {
            Recipe recipe = mRecipes.get(position);
            holder.bind(recipe);
        }

        @Override
        public int getItemCount() {
            return mRecipes.size();
        }
    }
}
