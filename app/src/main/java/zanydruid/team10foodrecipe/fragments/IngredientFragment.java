package zanydruid.team10foodrecipe.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import zanydruid.team10foodrecipe.Adapters.ListAdapter;
import zanydruid.team10foodrecipe.Models.Ingredient;
import zanydruid.team10foodrecipe.R;
import zanydruid.team10foodrecipe.utility.Kitchen;

/**
 * Created by yizhu on 4/5/16.
 */
public class IngredientFragment extends Fragment{

    private static final String ARG_INGREDIENT = "ingredient";
      private List<Ingredient> mIngredients;
    private ListView mListView;
    private ListAdapter mAdapter;
//    private RecyclerView mRecyclerView;
//    private IngredientAdapter mAdapter;
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
        mListView = (ListView) view.findViewById(R.id.fragment_list_view_list_view);
        mAdapter = new ListAdapter(getActivity(),mIngredients);
        mListView.setAdapter(mAdapter);
//        List<Ingredient> ingredients = Kitchen.getInstance(getActivity()).getIngredientsById(mId);


//        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_list_recycler_view);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mAdapter = new IngredientAdapter(mIngredients);
//        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    private class IngredientHolder extends RecyclerView.ViewHolder{

        private TextView nameTextView;
        private TextView amountTextView;
        private TextView unitTextView;
        private Ingredient mIngredient;

        public IngredientHolder(View itemView){
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.list_view_item_name);
            amountTextView = (TextView) itemView.findViewById(R.id.list_view_item_amount);
            unitTextView = (TextView) itemView.findViewById(R.id.list_view_item_unit);
        }

        public void bindIngredient(Ingredient ingredient){
            mIngredient = ingredient;
            nameTextView.setText(mIngredient.getIngreName());
            amountTextView.setText(String.valueOf(mIngredient.getAmount()));
            unitTextView.setText(Kitchen.getInstance(getActivity())
                    .getUnitById(mIngredient.getUnitId()).getUnit());
        }
    }

    private class IngredientAdapter extends RecyclerView.Adapter<IngredientHolder>{

        private List<Ingredient> mIngredients;

        public IngredientAdapter(List<Ingredient> ingredients){
            mIngredients = ingredients;
        }

        @Override
        public IngredientHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_view_item,parent,false);
            return new IngredientHolder(view);
        }

        @Override
        public void onBindViewHolder(IngredientHolder holder, int position) {
            Ingredient ingredient = mIngredients.get(position);
            holder.bindIngredient(ingredient);
        }

        @Override
        public int getItemCount() {
            return mIngredients.size();
        }
    }
}
