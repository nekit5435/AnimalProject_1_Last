package animalproject.animalproject_1.fragments;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;

import com.example.animalproject.animalproject_1.R;

import java.util.ArrayList;

public class CustomListView extends ArrayAdapter<String> {

    private Ads a;
    private ArrayList<String> typesL = new ArrayList<String>();
    private Activity context;

    public CustomListView(@NonNull Activity context, String[] typesA) {
        super(context, R.layout.fragment_list_linear, typesA);
    }
}
