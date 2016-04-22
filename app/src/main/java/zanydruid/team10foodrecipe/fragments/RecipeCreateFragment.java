package zanydruid.team10foodrecipe.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import zanydruid.team10foodrecipe.Models.Flavor;
import zanydruid.team10foodrecipe.Models.Recipe;
import zanydruid.team10foodrecipe.R;
import zanydruid.team10foodrecipe.utility.Kitchen;

/**
 * Created by yizhu on 4/9/16.
 */
public class RecipeCreateFragment extends Fragment {

    private Recipe mRecipe;
    private int cookTime;
    private List<Flavor> flavorList;
    private List<String> flavorStringList;
    private Flavor flavor1;
    private Flavor flavor2;
    private Flavor flavor3;
    private static final String ARG_RECIPE_ID = "recipeId";
    private static final String TAG = "recipeCreate";
    private static final int REQUEST_GALLERY = 0;
    private static final int REQUEST_CAMERA = 1;
    private int ifUpdate;
    private CallBack mCallBack;

    private EditText recipeName;
    private ImageView recipePhoto;
    private ImageButton recipeCamera;
    private ImageButton recipeGallery;
    private EditText recipeDescription;
    private EditText recipeHour;
    private EditText recipeMinute;
    private EditText recipePeople;
    private EditText recipeSource;
    private Spinner recipeFlavor1;
    private Spinner recipeFlavor2;
    private Spinner recipeFlavor3;
    private EditText recipeFlavorAmount1;
    private EditText recipeFlavorAmount2;
    private EditText recipeFlavorAmount3;
    private Button nextButton;

    public static RecipeCreateFragment newInstance(int id){
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECIPE_ID, id);
        RecipeCreateFragment fragment = new RecipeCreateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface CallBack{
        public void nextStep(int id);
    }

    /**
     * Delegate the callback to parent activity
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallBack = (CallBack) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallBack=null;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cookTime = 0;
        ifUpdate = 1;
        int id = (int)getArguments().getSerializable(ARG_RECIPE_ID);
        mRecipe = Kitchen.getInstance(getActivity()).getRecipeById(id);
        if(mRecipe == null){
            mRecipe = new Recipe();
            mRecipe.setId(id);
            ifUpdate = 0;
        }
        flavorList = Kitchen.getInstance(getActivity()).getFlavorList();
        flavorStringList = new ArrayList<>();
        for(Flavor flavor: flavorList){
            flavorStringList.add(flavor.getFlavorName());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_create_layout,container,false);

        recipeName = (EditText) view.findViewById(R.id.recipe_create_name_edit_view);
        if(!(mRecipe.getName()==null)){
            recipeName.setText(mRecipe.getName());
        }
        recipeDescription = (EditText) view.findViewById(R.id.fragment_recipe_create_description_edit_text_view);
        if(!(mRecipe.getDescription()==null)){
           recipeDescription.setText(mRecipe.getDescription());
        }
        recipeHour = (EditText) view.findViewById(R.id.fragment_recipe_create_hour_text_view);
        if(!(mRecipe.getTime()==0)){

        }
        recipeMinute = (EditText) view.findViewById(R.id.fragment_recipe_create_minute_text_view);
        if(!(mRecipe.getTime()==0)){
            recipeMinute.setText(String.valueOf(mRecipe.getTime()));
        }
        recipePeople = (EditText) view.findViewById(R.id.fragment_recipe_create_people_text_view);
        if(!(mRecipe.getServePeople()==0)){
            recipePeople.setText(String.valueOf(mRecipe.getServePeople()));
        }
        recipeSource = (EditText) view.findViewById(R.id.fragment_recipe_create_source_text_view);
        if(!(mRecipe.getSource()==null)){
            recipeSource.setText(mRecipe.getSource());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,flavorStringList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recipeFlavor1 = (Spinner) view.findViewById(R.id.fragment_recipe_create_flavor1);
        recipeFlavor1.setAdapter(adapter);
        recipeFlavor1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String flavorName = (String) parent.getAdapter().getItem(position);
                int flavorId = getFlavorIdByName(flavorName);
                flavor1 = new Flavor(flavorId, flavorName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        recipeFlavorAmount1 = (EditText) view.findViewById(R.id.fragment_recipe_create_flavor1_text_view);

        recipeFlavor2 = (Spinner) view.findViewById(R.id.fragment_recipe_create_flavor2);
        recipeFlavor2.setAdapter(adapter);
        recipeFlavor2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String flavorName = (String) parent.getAdapter().getItem(position);
                int flavorId = getFlavorIdByName(flavorName);
                flavor2 = new Flavor(flavorId, flavorName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        recipeFlavorAmount2 = (EditText) view.findViewById(R.id.fragment_recipe_create_flavor2_text_view);

        recipeFlavor3 = (Spinner) view.findViewById(R.id.fragment_recipe_create_flavor3);
        recipeFlavor3.setAdapter(adapter);
        recipeFlavor3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String flavorName = (String) parent.getAdapter().getItem(position);
                int flavorId = getFlavorIdByName(flavorName);
                flavor3 = new Flavor(flavorId, flavorName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        recipeFlavorAmount3 = (EditText) view.findViewById(R.id.fragment_recipe_create_flavor3_text_view);

        nextButton = (Button) view.findViewById(R.id.fragment_recipe_create_next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(recipeName.getText().toString().equals(""))) {
                    String name = recipeName.getText().toString();
                    mRecipe.setName(name);
                }

                if(!(recipePeople.getText().toString().equals(""))){
                    int servePeople = Integer.parseInt(recipePeople.getText().toString());
                    mRecipe.setServePeople(servePeople);
                }

                if(!recipeHour.getText().toString().equals("")) {
                    cookTime += Integer.parseInt(recipeHour.getText().toString()) * 60;
                    mRecipe.setTime(cookTime);
                }

                if(!recipeMinute.getText().toString().equals("")) {
                    cookTime += Integer.parseInt(recipeMinute.getText().toString());
                    mRecipe.setTime(cookTime);
                }

                if(!recipeDescription.getText().toString().equals("")){
                    mRecipe.setDescription(recipeDescription.getText().toString());
                }

                if(!recipeSource.getText().toString().equals("")){
                    mRecipe.setSource(recipeSource.getText().toString());
                }

                if (!(recipeFlavorAmount1.getText().toString().trim().equals(""))) {
                    flavor1.setAmount(Integer.parseInt(recipeFlavorAmount1.getText().toString()));
                }
                if (!(recipeFlavorAmount2.getText().toString().trim().equals(""))) {
                    flavor2.setAmount(Integer.parseInt(recipeFlavorAmount2.getText().toString()));
                }
                if (!(recipeFlavorAmount3.getText().toString().trim().equals(""))) {
                    flavor3.setAmount(Integer.parseInt(recipeFlavorAmount3.getText().toString()));
                }

                List<Flavor> flavorList = new ArrayList<>();
                flavorList.add(flavor1);
                flavorList.add(flavor2);
                flavorList.add(flavor3);
                if(ifUpdate==0) {
                    Kitchen.getInstance(getActivity()).insertFlavors(flavorList, mRecipe.getId());
                    Kitchen.getInstance(getActivity()).insertRecipe(mRecipe);
                }else{
                    Kitchen.getInstance(getActivity()).updateRecipe(mRecipe);
                }
                mCallBack.nextStep(mRecipe.getId());
            }
        });

        recipePhoto = (ImageView)view.findViewById(R.id.recipe_create_photo);
        if(!(mRecipe.getPhotoUri()==null)){
            recipePhoto.setImageURI(mRecipe.getPhotoUri());
        }
        ViewTreeObserver observer = recipePhoto.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                updatePhotoView();
            }
        });

        recipeGallery = (ImageButton) view.findViewById(R.id.recipe_create_photo_gallery);
        recipeGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , REQUEST_GALLERY);
            }
        });

        recipeCamera = (ImageButton) view.findViewById(R.id.recipe_create_photo_camera);
        recipeCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(takePicture, REQUEST_CAMERA);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!= Activity.RESULT_OK){
            return;
        }
        switch(requestCode){
            case REQUEST_GALLERY:
                Uri selectedImage = data.getData();
                mRecipe.setPhotoUri(selectedImage);
                updatePhotoView();
                break;
            case REQUEST_CAMERA:
                Uri takenImage = data.getData();
                mRecipe.setPhotoUri(takenImage);
                updatePhotoView();
                break;
        }
    }

    public void updatePhotoView(){
            recipePhoto.setImageURI(mRecipe.getPhotoUri());
    }

    /**
     * Helper method to find flavor id by its name
     *
     * @param flavorName
     * @return
     */
    public int getFlavorIdByName(String flavorName){
        for(Flavor flavor: flavorList){
            if(flavor.getFlavorName().equals(flavorName)){
                return flavor.getFlavorId();
            }
        }
        return 0;
    }

}
