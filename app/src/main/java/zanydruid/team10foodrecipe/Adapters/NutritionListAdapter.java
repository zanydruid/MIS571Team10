package zanydruid.team10foodrecipe.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import zanydruid.team10foodrecipe.Models.Nutrition;
import zanydruid.team10foodrecipe.R;
import zanydruid.team10foodrecipe.utility.Kitchen;

/**
 * Created by yizhu on 4/8/16.
 */
public class NutritionListAdapter extends ArrayAdapter<Nutrition> {
    private List<Nutrition> mNutritions;
    private Context mContext;

    public NutritionListAdapter(Context context, List<Nutrition> nutritions){
        super(context,R.layout.list_view_item,nutritions);
        mContext = context;
        mNutritions = nutritions;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_view_item,parent,false);
        TextView nameTextView = (TextView) view.findViewById(R.id.list_view_item_name);
        nameTextView.setText(mNutritions.get(position).getNutriName());
        TextView amountTextView = (TextView) view.findViewById(R.id.list_view_item_amount);
        amountTextView.setText(String.valueOf(mNutritions.get(position).getAmount()));
        TextView unitTextView = (TextView) view.findViewById(R.id.list_view_item_unit);
        int id = mNutritions.get(position).getUnitId();
        unitTextView.setText(Kitchen.getInstance(mContext).getUnitById(id).getUnit());

        return view;
    }
}
