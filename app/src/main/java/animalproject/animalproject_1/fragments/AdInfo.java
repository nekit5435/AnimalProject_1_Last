package animalproject.animalproject_1.fragments;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.animalproject.animalproject_1.MainActivity;
import com.example.animalproject.animalproject_1.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AdInfo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AdInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdInfo extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private android.support.v4.app.FragmentManager sfrm;
    private android.support.v4.app.Fragment mapDispPage;
    private Ad ad;
    private int frag;
    private FragmentActivity myContext;

    private OnFragmentInteractionListener mListener;

    public AdInfo() {
        // Required empty public constructor
    }


    public static AdInfo newInstance(Ad ad, int frag) {
        AdInfo fragment = new AdInfo();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, ad);
        args.putInt(ARG_PARAM2,frag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ad = getArguments().getParcelable(ARG_PARAM1);
            frag = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ad_info, container, false);
        sfrm = myContext.getSupportFragmentManager();
        ImageView imageView = (ImageView) v.findViewById(R.id.imageView3);
        TextView typeText = (TextView) v.findViewById(R.id.textView_type);
        TextView breedText = (TextView) v.findViewById(R.id.textView_race);
        TextView genderText = (TextView) v.findViewById(R.id.textView_sex);
        TextView ageText = (TextView) v.findViewById(R.id.textView_age);
        TextView descText = (TextView) v.findViewById(R.id.textView_description);
        TextView rewardText = (TextView) v.findViewById(R.id.textView_money);
        TextView nameText = (TextView) v.findViewById(R.id.textView_name);
        TextView numberText = (TextView) v.findViewById(R.id.textView_number);
        Button mapBtn = (Button) v.findViewById(R.id.show_map_button);
        FloatingActionButton backBtn = (FloatingActionButton) v.findViewById(R.id.button_back_float);
        //exampleText.setText(ad.type);
        try {
            Picasso.with(myContext).load("http://213.221.28.7:13267/test/test.jpg").into(imageView);
        } catch (Exception e)
        {
            Log.getStackTraceString(e);
        }
        finally {
            imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.no_photo));
        }
        typeText.setText(String.format("Вид: %s",ad.type));
        breedText.setText(String.format("Порода: %s",ad.breed));
        genderText.setText(String.format("Пол: %s",ad.gender));
        ageText.setText(String.format("Возраст: %s",ad.age));
        descText.setText(String.format("Описание: %s",ad.other));
        rewardText.setText(String.format("Вознаграждение: %s",ad.reward));
        nameText.setText(String.format("Имя: %s",ad.name));
        numberText.setText(String.format("Телефон: %s",ad.phone));

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putParcelable(ARG_PARAM1, ad);
                args.putBoolean(ARG_PARAM2, true);
                mapDispPage = new Map1Fragment();
                mapDispPage.setArguments(args);
                sfrm.beginTransaction().replace(R.id.content_frame,mapDispPage).commit();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (frag == 1) sfrm.beginTransaction().replace(R.id.content_frame,new listview()).commit();
                if (frag == 2) sfrm.beginTransaction().replace(R.id.content_frame,new Map1Fragment()).commit();
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
        super.onAttach(context);
        myContext=(FragmentActivity) context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
