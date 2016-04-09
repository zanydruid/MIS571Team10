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

import zanydruid.team10foodrecipe.Models.Nutrition;
import zanydruid.team10foodrecipe.R;
import zanydruid.team10foodrecipe.utility.Kitchen;

/**
 * Created by yizhu on 4/5/16.
 */
public class NutritionFragment extends Fragment {
    public static final String ARG_NUTRITION = "nutrition";
    private List<Nutrition> mNutritions;

    private TextView mTitleTextView;
    private ListView mListView;
    private NutritionAdapter mAdapter;

    private int mId;

    public static NutritionFragment newInstance(int id){
        Bundle args = new Bundle();
        args.putSerializable(ARG_NUTRITION, id);
        NutritionFragment fragment = new NutritionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mId = (int) getArguments().getSerializable(ARG_NUTRITION);
        mNutritions = Kitchen.getInstance(getActivity()).getNutritionsFromDB(mId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_view,container,false);
        mTitleTextView = (TextView) view.findViewById(R.id.fragment_list_view_title_text_view);
        mTitleTextView.setText("NUTRITIONS");
        mListView = (ListView) view.findViewById(R.id.fragment_list_view_list_view);
        mAdapter = new NutritionAdapter(getActivity(),mNutritions);
        mListView.setAdapter(mAdapter);

        return view;
    }

    private class NutritionAdapter extends ArrayAdapter<Nutrition>{

        public NutritionAdapter(Context context, List<Nutrition> nutritions){
            super(context,0,nutritions);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Nutrition nutrition = getItem(position);
            if(convertView==null){
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.list_view_item,parent,false);
            }
            TextView name = (TextView) convertView.findViewById(R.id.list_view_item_name);
            name.setText(nutrition.getNutriName());

            TextView amountAndUnit = (TextView) convertView.findViewById(R.id.list_view_item_amount_and_unit);

            double amount = nutrition.getAmount();
            String unit = Kitchen.getInstance(getActivity()).getUnitById(nutrition.getUnitId()).getUnit();

            amountAndUnit.setText(amount + " " + unit);

            return convertView;
        }
    }
}
