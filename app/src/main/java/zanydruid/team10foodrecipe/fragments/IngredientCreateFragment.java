package zanydruid.team10foodrecipe.fragments;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import zanydruid.team10foodrecipe.Models.Ingredient;
import zanydruid.team10foodrecipe.R;
import zanydruid.team10foodrecipe.activities.RecipeListActivity;
import zanydruid.team10foodrecipe.utility.Kitchen;
import zanydruid.team10foodrecipe.utility.LinearLayoutAbsListView;

/**
 * Created by yizhu on 4/9/16.
 */
public class IngredientCreateFragment extends Fragment {

    private int mId;
    private static final String TAG = "ingrCreateFragment";
    private static final String ARG_ID = "ingredientCreate";

    private ListView optionList;
    private ListView selectedList;
    private IngredientCallBack mCallBack;

    private List<Ingredient> optionIngredients;
    private List<Ingredient> selectedIngredients;
    private ItemOptionListAdapter optionAdapter;
    private ItemSelectedListAdapter selectedAdapter;
    LinearLayoutAbsListView optionArea, selectedArea;
    private Button preButton;
    private Button finishButton;

    public static IngredientCreateFragment newInstance(int id){
        Bundle args = new Bundle();
        args.putSerializable(ARG_ID, id);
        IngredientCreateFragment fragment = new IngredientCreateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface IngredientCallBack{
        public void preStep(int id);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallBack = (IngredientCallBack) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallBack=null;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mId = (int)getArguments().getSerializable(ARG_ID);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

        preButton = (Button) view.findViewById(R.id.fragment_ingredient_create_pre_button);
        preButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.preStep(mId);
            }
        });

        finishButton = (Button) view.findViewById(R.id.fragment_ingredient_create_finsh_button);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(String name: selectedAdapter.ingredientMap.keySet()){
                    for(Ingredient ingredient: selectedIngredients){
                        if(ingredient.getIngreName().equals(name)){
                            ingredient.setAmount(selectedAdapter.ingredientMap.get(name));
                        }
                    }
                }

//                for(Ingredient ingredient: selectedIngredients){
//                    Log.i(TAG,ingredient.getIngredientId()+" "+ingredient.getIngreName()+" "+String.valueOf(ingredient.getAmount()));
//                }
                Kitchen.getInstance(getActivity()).insertIngredients(selectedIngredients,mId);
                Intent intent = new Intent(getActivity(), RecipeListActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    /**
     * Container Object for passing around
     *
     */
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
        TextView unitText;
        Button cancelButton;
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

        public HashMap<String,Integer> ingredientMap;

        public ItemSelectedListAdapter(Context context, List<Ingredient> ingredients) {
            super(context, ingredients);
            ingredientMap = new HashMap<>();
        }

        public List<Ingredient>  getSelectedList(){
            return list;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;

            // reuse views
            if (rowView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                rowView = inflater.inflate(R.layout.list_view_item_ingredient, null);

                SelectedViewHolder viewHolder = new SelectedViewHolder();
                viewHolder.text = (TextView) rowView.findViewById(R.id.list_view_item_ingredient_title_text_view);
                viewHolder.editText = (EditText) rowView.findViewById(R.id.list_view_item_ingredient_amount_edit_view);
                viewHolder.unitText = (TextView) rowView.findViewById(R.id.list_view_item_ingredient_unit);
                viewHolder.cancelButton = (Button) rowView.findViewById(R.id.list_view_item_ingredient_delete_button);
                rowView.setTag(viewHolder);
            }

            SelectedViewHolder holder = (SelectedViewHolder) rowView.getTag();
            holder.text.setText(list.get(position).getIngreName());
            holder.editText.setTag(list.get(position).getIngreName());
            holder.editText.addTextChangedListener(new IngredientTextWatcher(holder.editText));
            String unit = Kitchen.getInstance(getActivity()).getUnitById(list.get(position).getUnitId()).getUnit();
            holder.unitText.setText(unit);
            Ingredient tempIngredient = list.get(position);
            holder.cancelButton.setOnClickListener(new IngredientOnClickListener(tempIngredient));
            rowView.setOnDragListener(new ItemOnDragListener(list.get(position)));

            return rowView;
        }

    }

    private class IngredientOnClickListener implements View.OnClickListener{
        private Ingredient ingredient;

        public IngredientOnClickListener(Ingredient ingredient){
            this.ingredient = ingredient;
        }

        @Override
        public void onClick(View v) {
            if(removeItemToList(selectedIngredients,ingredient)){
                addItemToList(optionIngredients,ingredient);
            }
            optionAdapter.notifyDataSetChanged();
            selectedAdapter.notifyDataSetChanged();
        }
    }

    private class IngredientTextWatcher implements TextWatcher{
        private EditText view;

        public IngredientTextWatcher(EditText view){
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            selectedAdapter.ingredientMap.put((String)view.getTag(),Integer.parseInt(s.toString()));
        }
    }
}