package zanydruid.team10foodrecipe.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zanydruid.team10foodrecipe.Models.Recipe;
import zanydruid.team10foodrecipe.R;
import zanydruid.team10foodrecipe.activities.RecipeActivity;
import zanydruid.team10foodrecipe.activities.RecipeCreateActivity;
import zanydruid.team10foodrecipe.utility.Kitchen;
import zanydruid.team10foodrecipe.utility.PictureUtils;

/**
 * Created by yizhu on 4/9/16.
 */
public class RecipeListFragment extends Fragment {

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
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view_layout,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_recycler_view_layout_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI(mRecipeList);
        return view;
    }

    public void updateUI(List<Recipe> recipes){
        if(mAdapter==null){
            mAdapter = new RecipeAdapter(recipes);
            mRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.notifyDataSetChanged();
        }
    }

    private class RecipeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Recipe mRecipe;
        private ImageView mImageView;
        private TextView mTitleTextView;
        private TextView mTimeTextView;

        public RecipeHolder(View itemView){
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.recycler_list_item_image_view);
            mTitleTextView = (TextView) itemView.findViewById(R.id.recycler_list_item_title_text_view);
            mTimeTextView = (TextView) itemView.findViewById(R.id.recycler_list_item_time_text_view);
            itemView.setOnClickListener(this);
        }

        public void bind(Recipe recipe){
            mRecipe = recipe;
            mTitleTextView.setText(mRecipe.getName());
            mTimeTextView.setText(String.valueOf(mRecipe.getTime()) + " min");
            if(!(mRecipe.getPhotoUri()==null)) {
//                Drawable tempDrawable = Drawable.createFromPath(mRecipe.getPhotoUri().getPath());
//                mImageView.setImageDrawable(tempDrawable);
//                if(!(tempDrawable==null)){
//
//                }

                Bitmap bitmap = PictureUtils.getScaleBitmap(mRecipe.getPhotoUri().getPath(), mImageView.getWidth(), mImageView.getHeight());
                mImageView.setImageBitmap(bitmap);
            }
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
            mRecipes = new ArrayList<>(recipes);
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

        public Recipe removeItem(int position) {
            final Recipe recipe = mRecipes.remove(position);
            notifyItemRemoved(position);
            return recipe;
        }

        public void addItem(int position, Recipe model) {
            mRecipes.add(position, model);
            notifyItemInserted(position);
        }

        public void moveItem(int fromPosition, int toPosition) {
            final Recipe model = mRecipes.remove(fromPosition);
            mRecipes.add(toPosition, model);
            notifyItemMoved(fromPosition, toPosition);
        }

        public void animateTo(List<Recipe> recipes) {
            applyAndAnimateRemovals(recipes);
            applyAndAnimateAdditions(recipes);
            applyAndAnimateMovedItems(recipes);
        }

        private void applyAndAnimateRemovals(List<Recipe> newRecipes) {
            for (int i = mRecipes.size() - 1; i >= 0; i--) {
                final Recipe item = mRecipes.get(i);
                if (!newRecipes.contains(item)) {
                    removeItem(i);
                }
            }
        }

        private void applyAndAnimateAdditions(List<Recipe> newRecipes) {
            for (int i = 0, count = newRecipes.size(); i < count; i++) {
                final Recipe item = newRecipes.get(i);
                if (!mRecipes.contains(item)) {
                    addItem(i, item);
                }
            }
        }

        private void applyAndAnimateMovedItems(List<Recipe> newRecipes) {
            for (int toPosition = newRecipes.size() - 1; toPosition >= 0; toPosition--) {
                final Recipe item = newRecipes.get(toPosition);
                final int fromPosition = mRecipes.indexOf(item);
                if (fromPosition >= 0 && fromPosition != toPosition) {
                    moveItem(fromPosition, toPosition);
                }
            }
        }
    }

    private List<Recipe> filter(List<Recipe> items, String query) {
        query = query.toLowerCase();

        final List<Recipe> filteredModelList = new ArrayList<>();
        for (Recipe item : items) {
            final String name = item.getName().toLowerCase();
            if (name.contains(query)) {
                filteredModelList.add(item);
            }
        }
        return filteredModelList;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.recipe_list_menu, menu);
        SearchManager manager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        SearchView search = (SearchView) menu.findItem(R.id.menu_recipelist_search).getActionView();
        search.setSearchableInfo(manager.getSearchableInfo(getActivity().getComponentName()));
        search.setIconifiedByDefault(false);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final List<Recipe> filteredModelList = filter(mRecipeList, newText);
                mAdapter.animateTo(filteredModelList);
                mRecyclerView.scrollToPosition(0);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_recipelist_new:
                int newId = mRecipeList.size()+1;
                Intent intent = RecipeCreateActivity.newIntent(getActivity(),newId);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
