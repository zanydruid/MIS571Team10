package zanydruid.team10foodrecipe.fragments;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import zanydruid.team10foodrecipe.Models.Ingredient;
import zanydruid.team10foodrecipe.R;
import zanydruid.team10foodrecipe.utility.Kitchen;
import zanydruid.team10foodrecipe.utility.LinearLayoutAbsListView;

/**
 * Created by yizhu on 4/9/16.
 */
public class IngredientCreateFragment extends Fragment {

    private int mId;
    private static final String TAG = "ingrCreateFragment";

    private ListView optionList;
    private ListView selectedList;

    private List<Ingredient> optionIngredients;
    private List<Ingredient> selectedIngredients;
    private ItemOptionListAdapter optionAdapter;
    private ItemSelectedListAdapter selectedAdapter;
    LinearLayoutAbsListView optionArea, selectedArea;

    public static IngredientCreateFragment newInstance(){
        IngredientCreateFragment fragment = new IngredientCreateFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient_create_layout,container,false);
        optionList = (ListView) view.findViewById(R.id.fragment_ingredient_create_option_listview);
        selectedList = (ListView) view.findViewById(R.id.fragment_ingredient_create_selected_listview);

        optionArea = (LinearLayoutAbsListView) view.findViewById(R.id.option_area);
        selectedArea = (LinearLayoutAbsListView) view.findViewById(R.id.selected_area);
        optionArea.setOnDragListener(myOnDragListener);
        selectedArea.setOnDragListener(myOnDragListener);
        optionArea.setAbsListView(optionList);
        selectedArea.setAbsListView(selectedList);

        optionIngredients = Kitchen.getInstance(getActivity()).getAllIngredients();
        selectedIngredients = new ArrayList<>();
        optionAdapter = new ItemOptionListAdapter(getActivity(),optionIngredients);
        selectedAdapter = new ItemSelectedListAdapter(getActivity(),selectedIngredients);

        optionList.setAdapter(optionAdapter);
        selectedList.setAdapter(selectedAdapter);

        optionList.setOnItemClickListener(listOnItemClickListener);
        selectedList.setOnItemClickListener(listOnItemClickListener);

        optionList.setOnItemLongClickListener(myOnItemLongClickListener);
        selectedList.setOnItemLongClickListener(myOnItemLongClickListener);
        return view;
    }

    private class PassObject{
        View view;
        Ingredient ingredient;
        List<Ingredient> srcList;

        public PassObject(View view, Ingredient ingredient, List<Ingredient> list){
            this.view = view;
            this.ingredient = ingredient;
            srcList = list;
        }
    }

    private boolean removeItemToList(List<Ingredient> l, Ingredient it){
        boolean result = l.remove(it);
        return result;
    }

    private boolean addItemToList(List<Ingredient> l, Ingredient it){
        boolean result = l.add(it);
        return result;
    }

    /**
     * Custom OnItemLongClickListener
     *
     */
    AdapterView.OnItemLongClickListener myOnItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            Ingredient selectedIngredient = (Ingredient)parent.getItemAtPosition(position);
            ItemBaseAdapter associatedAdapter = (ItemBaseAdapter)parent.getAdapter();
            List<Ingredient> associatedList = associatedAdapter.getIngredients();


            PassObject passObject = new PassObject(view,selectedIngredient,associatedList);

            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, shadowBuilder, passObject, 0);

            return true;
        }
    };

    /**
     * Custom OnDragListener
     *
     */
    View.OnDragListener myOnDragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            String area;
            if(v == optionArea){
                area = "optionArea";
            } else if(v == selectedArea){
                area = "selectedArea";
            } else {
                area = "unknown";
            }

            switch (event.getAction()){
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.i(TAG,"ACTION_DRAG_STARTED: " + area);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.i(TAG,"ACTION_DRAG_ENTERED: " + area);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.i(TAG,"ACTION_DRAG_EXITED: " + area);
                    break;
                case DragEvent.ACTION_DROP:
                    Log.i(TAG,"ACTION_DROP: " + area);

                    PassObject passObject = (PassObject) event.getLocalState();
                    View view = passObject.view;
                    Ingredient passedItem = passObject.ingredient;
                    List<Ingredient> srcList = passObject.srcList;
                    AbsListView oldParent = (AbsListView) view.getParent();
                    ItemBaseAdapter srcAdapter = (ItemBaseAdapter) (oldParent.getAdapter());

                    LinearLayoutAbsListView newParent = (LinearLayoutAbsListView) v;
                    ItemBaseAdapter destAdapter = (ItemBaseAdapter) (newParent.absListView.getAdapter());
                    List<Ingredient> destList = destAdapter.getIngredients();

                    if(removeItemToList(srcList,passedItem)){
                        addItemToList(destList,passedItem);
                    }

                    srcAdapter.notifyDataSetChanged();
                    destAdapter.notifyDataSetChanged();

                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.i(TAG,"ACTION_DRAG_ENDED: " + area);
                default:
                    break;
            }
            return true;
        }
    };


    public class ItemOnDragListener implements View.OnDragListener{

        private Ingredient mIngredient;

        public ItemOnDragListener(Ingredient i){
            mIngredient = i;
        }

        @Override
        public boolean onDrag(View v, DragEvent event) {

            switch (event.getAction()){
                case DragEvent.ACTION_DROP:
                    PassObject passObject = (PassObject)event.getLocalState();
                    View view = passObject.view;
                    Ingredient ingredient = passObject.ingredient;
                    List<Ingredient> srcList = passObject.srcList;
                    AbsListView oldParent = (AbsListView) view.getParent();
                    ItemBaseAdapter srcAdapter = (ItemBaseAdapter)(oldParent.getAdapter());

                    AbsListView newParent = (AbsListView) v.getParent();
                    ItemBaseAdapter destAdapter = (ItemBaseAdapter)(newParent.getAdapter());
                    List<Ingredient> destList = destAdapter.getIngredients();

                    int removeLocation = srcList.indexOf(ingredient);
                    int insertLocation = destList.indexOf(mIngredient);

                    if(srcList != destList || removeLocation != insertLocation){
                        if(removeItemToList(srcList,ingredient)){
                            destList.add(insertLocation,ingredient);
                        }
                        srcAdapter.notifyDataSetChanged();
                        destAdapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    break;
            }
            return true;
        }
    }

    /**
     * Custom OnItemClickListener
     *
     */
    AdapterView.OnItemClickListener listOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getActivity(),
                    ((Ingredient) (parent.getItemAtPosition(position))).getIngreName(),
                    Toast.LENGTH_SHORT).show();
        }
    };



    public class ItemBaseAdapter extends BaseAdapter{

        Context context;
        List<Ingredient> list;

        ItemBaseAdapter(Context context, List<Ingredient> list){
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public List<Ingredient> getIngredients(){
            return this.list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }

    static class OptionViewHolder{
        TextView text;
    }

    static class SelectedViewHolder{
        TextView text;
        EditText editText;
    }

    /**
     * Ingredient list of options, Adapter
     *
     */
    public class ItemOptionListAdapter extends ItemBaseAdapter {

        public ItemOptionListAdapter(Context context, List<Ingredient> ingredients){
            super(context,ingredients);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;

            // reuse views
            if (rowView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                rowView = inflater.inflate(R.layout.list_view_item_ingredient_option, null);

                OptionViewHolder viewHolder = new OptionViewHolder();

                viewHolder.text = (TextView) rowView.findViewById(R.id.list_view_item_title);
                rowView.setTag(viewHolder);
            }

            OptionViewHolder holder = (OptionViewHolder) rowView.getTag();

            holder.text.setText(list.get(position).getIngreName());

            rowView.setOnDragListener(new ItemOnDragListener(list.get(position)));

            return rowView;
        }

    }


    /**
     * Ingredient list of user's choice, Adapter
     *
     */
    public class ItemSelectedListAdapter extends ItemBaseAdapter {


        public ItemSelectedListAdapter(Context context, List<Ingredient> ingredients) {
            super(context, ingredients);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;

            // reuse views
            if (rowView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                rowView = inflater.inflate(R.layout.list_view_item_ingredient, null);

                SelectedViewHolder viewHolder = new SelectedViewHolder();
                viewHolder.text = (TextView) rowView.findViewById(R.id.list_view_item_ingredient_title_text_view);
                viewHolder.editText = (EditText) rowView.findViewById(R.id.list_view_item_ingredient_amount_edit_view);
                rowView.setTag(viewHolder);
            }

            SelectedViewHolder holder = (SelectedViewHolder) rowView.getTag();
            holder.text.setText(list.get(position).getIngreName());

            rowView.setOnDragListener(new ItemOnDragListener(list.get(position)));

            return rowView;
        }

    }
}