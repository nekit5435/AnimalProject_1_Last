package animalproject.animalproject_1.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.animalproject.animalproject_1.MainActivity;
import com.example.animalproject.animalproject_1.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link set_cords_map.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link set_cords_map#newInstance} factory method to
 * create an instance of this fragment.
 */
public class set_cords_map extends Fragment implements OnMapReadyCallback {
    Ads MyAds;
    GoogleMap map;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Ads ads;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Ad ad;
    private OnFragmentInteractionListener mListener;
    private MarkerOptions markerDefault;
    private FragmentActivity myContext;
    private android.support.v4.app.FragmentManager sfrm;
    private android.support.v4.app.Fragment backPage;
    public set_cords_map() {
        // Required empty public constructor
    }


    public static set_cords_map newInstance(Ad ad) {
        set_cords_map fragment = new set_cords_map();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, ad);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ad = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        SupportMapFragment mapFr=(SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map2);
        mapFr.getMapAsync(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_set_cords_map, container, false);
        FloatingActionButton fab = (FloatingActionButton)v.findViewById(R.id.fab_go);
        sfrm = myContext.getSupportFragmentManager();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad.cords = markerDefault.getPosition();

                ad.id = Ads.adsList.get(Ads.adsList.size()-1).id+1;
                MainActivity.stringToSend = "send " + ad.cords.latitude+";"+ ad.cords.longitude+";"+ad.type+";"+ad.breed+";"+ad.gender+";"+ad.name+";"+ad.phone+";"+ad.other+";"+ad.age+";"+ad.reward+";"+ad.id;
                ad.sendToServer();
                Toast.makeText(getContext(),"Объявление успешно создано!",Toast.LENGTH_LONG).show();
                backPage= new MainFragment();
                sfrm.beginTransaction().replace(R.id.content_frame, backPage).commit();
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng l = new LatLng(55.7558, 37.6173);
        map = googleMap;
        markerDefault = new MarkerOptions();
        markerDefault.position(l).title("Установи меня на место пропажи!").snippet("Удерживай меня для перемещения!").draggable(true);
        map.addMarker(markerDefault);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(l, 10));
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
