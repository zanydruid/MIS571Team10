package zanydruid.team10foodrecipe.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import zanydruid.team10foodrecipe.Models.Ingredient;
import zanydruid.team10foodrecipe.utility.Kitchen;

/**
 * Created by yizhu on 4/9/16.
 */
public class IngredientCreateFragment extends Fragment {

    private int mId;
    private List<Ingredient> mIngredientList;

    public static IngredientCreateFragment newInstance(){
        IngredientCreateFragment fragment = new IngredientCreateFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIngredientList = Kitchen.getInstance(getActivity()).getAllIngredients();

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

    static class ViewHolder {
        TextView textView;
    }
}
