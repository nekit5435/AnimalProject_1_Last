package animalproject.animalproject_1.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.animalproject.animalproject_1.R;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddNewAd.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddNewAd#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewAd extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private android.support.v4.app.FragmentManager sfrm;
    private android.support.v4.app.Fragment mapPage;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button nextBtn;
    private EditText type;
    private EditText breed;
    private Spinner gender;
    private EditText age;
    private EditText reward;
    private EditText desc;
    private EditText name;
    private EditText phone;
    private String genderVal;
    private Ad ad;
    private View v;
    private Bitmap imageAd;
    private FragmentActivity myContext;
    private OnFragmentInteractionListener mListener;
    private AppCompatImageButton setImageButton;

    public AddNewAd() {
        // Required empty public constructor
    }

    public static AdInfo newInstance(Ad ad) {
        AdInfo fragment = new AdInfo();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, ad);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_add_new_ad, container, false);
        nextBtn = (Button) v.findViewById(R.id.button_next);
        type = (EditText) v.findViewById(R.id.editText_type);
        breed = (EditText) v.findViewById(R.id.editText_breed);
        gender = (Spinner) v.findViewById(R.id.spinner_sex);
        age = (EditText) v.findViewById(R.id.editText_age);
        reward = (EditText) v.findViewById(R.id.editText_money);
        desc = (EditText) v.findViewById(R.id.editText_description);
        name = (EditText) v.findViewById(R.id.editText_name);
        phone = (EditText) v.findViewById(R.id.editText_number);
        setImageButton = (AppCompatImageButton)v.findViewById(R.id.add_image_button);
        imageAd = BitmapFactory.decodeResource(getResources(), R.drawable.no_photo);
        sfrm = myContext.getSupportFragmentManager();
        final String[] genders = {"Не указано", "Мужской", "Женский"};

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_spinner_item,
                genders
        );
        gender.setAdapter(adapterSpinner);
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                genderVal = genders[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                ad = new Ad(new LatLng(55.7558, 37.6173),
                        type.getText().toString(),
                        breed.getText().toString(),
                        genderVal,
                        name.getText().toString(),
                        phone.getText().toString(),
                        desc.getText().toString(),
                        age.getText().toString(),
                        reward.getText().toString(),
                        0,
                        imageAd);
                if (type.getText().toString().equals(""))
                    ad.type = "Не указано";
                if (breed.getText().toString().equals(""))
                    ad.breed = "Не указано";
                if (name.getText().toString().equals(""))
                    ad.name = "Не указано";
                if (phone.getText().toString().equals(""))
                    ad.phone = "Не указано";
                if (desc.getText().toString().equals(""))
                    ad.other = "Не указано";
                if (age.getText().toString().equals(""))
                    ad.age = "Не указано";
                if (reward.getText().toString().equals(""))
                    ad.reward = "По договоренности";

                args.putParcelable(ARG_PARAM1, ad);

                mapPage = new set_cords_map();
                mapPage.setArguments(args);
                sfrm.beginTransaction().replace(R.id.content_frame, mapPage).commit();
            }
        });
        setImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Выберите изображение"),1);
            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        myContext=(FragmentActivity) context;
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 1)&&(resultCode==RESULT_OK)&&(data!=null)&&(data.getData()!=null))
        {
            Uri uri = data.getData();
            try
            {
                imageAd = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(),uri);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
