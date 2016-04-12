package zanydruid.team10foodrecipe.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import zanydruid.team10foodrecipe.Models.Ingredient;

/**
 * Created by yizhu on 4/9/16.
 */
public class IngredientCreateFragment extends Fragment {

    private int mId;

    public static IngredientCreateFragment newInstance(){
        IngredientCreateFragment fragment = new IngredientCreateFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private class PassObject{
        View view;
        Ingredient ingredient;

        public PassObject(View view, Ingredient ingredient){
            this.view = view;
            this.ingredient = ingredient;
        }

    }
}
