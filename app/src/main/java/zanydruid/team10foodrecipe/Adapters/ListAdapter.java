package zanydruid.team10foodrecipe.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import zanydruid.team10foodrecipe.Models.Ingredient;
import zanydruid.team10foodrecipe.R;
import zanydruid.team10foodrecipe.utility.Kitchen;

/**
 * Created by yizhu on 4/5/16.
 */
public class ListAdapter extends ArrayAdapter<Ingredient> {

    private List<Ingredient> mIngredientList;
    private Context mContext;

    public ListAdapter(Context context,List<Ingredient> ingredients){
        super(context,-1,ingredients);
        mContext = context;
        mIngredientList = ingredients;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_view_item,parent,false);
        TextView nameTextView = (TextView) view.findViewById(R.id.list_view_item_name);
        nameTextView.setText(mIngredientList.get(position).getIngreName());
        TextView amountTextView = (TextView) view.findViewById(R.id.list_view_item_amount);
        amountTextView.setText(String.valueOf(mIngredientList.get(position).getAmount()));
        TextView unitTextView = (TextView) view.findViewById(R.id.list_view_item_unit);
        int id = mIngredientList.get(position).getUnitId();
        unitTextView.setText(Kitchen.getInstance(mContext).getUnitById(id).getUnit());

        return view;
    }
}
