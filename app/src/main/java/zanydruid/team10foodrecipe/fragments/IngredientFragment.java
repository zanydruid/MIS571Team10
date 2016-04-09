package zanydruid.team10foodrecipe.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import zanydruid.team10foodrecipe.Models.Ingredient;
import zanydruid.team10foodrecipe.R;
import zanydruid.team10foodrecipe.utility.Kitchen;

/**
 * Created by yizhu on 4/8/16.
 */
public class IngredientFragment extends Fragment{
    public static final String ARG_INGREDIENT = "ingredient";
    private List<Ingredient> mIngredients;

    private TextView mTitleTextView;
    private ListView mListView;
    private IngredientsAdapter mAdapter;

    private int mId;

    public static IngredientFragment newInstance(int id){
        Bundle args = new Bundle();
        args.putSerializable(ARG_INGREDIENT, id);
        IngredientFragment fragment = new IngredientFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mId = (int) getArguments().getSerializable(ARG_INGREDIENT);
        mIngredients = Kitchen.getInstance(getActivity()).getIngredientsById(mId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_view,container,false);
        mTitleTextView = (TextView) view.findViewById(R.id.fragment_list_view_title_text_view);
        mTitleTextView.setText("INGREDIENTS");
        mListView = (ListView) view.findViewById(R.id.fragment_list_view_list_view);
        mAdapter = new IngredientsAdapter(getActivity(),mIngredients);
        mListView.setAdapter(mAdapter);

        return view;
    }

    private class IngredientsAdapter extends ArrayAdapter<Ingredient>{

        public IngredientsAdapter(Context context,List<Ingredient> ingredients){
            super(context,0,ingredients);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Ingredient ingredient = getItem(position);
            if(convertView==null){
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.list_view_item,parent,false);
            }
            TextView name = (TextView) convertView.findViewById(R.id.list_view_item_name);
            name.setText(ingredient.getIngreName());

            TextView amount = (TextView) convertView.findViewById(R.id.list_view_item_amount);
            amount.setText(String.valueOf(ingredient.getAmount()));

            TextView unit = (TextView) convertView.findViewById(R.id.list_view_item_unit);
            int id = ingredient.getUnitId();
            unit.setText(Kitchen.getInstance(getActivity()).getUnitById(id).getUnit());

            return convertView;
        }
    }
}
