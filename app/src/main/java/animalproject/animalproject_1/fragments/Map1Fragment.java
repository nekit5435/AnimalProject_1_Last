package animalproject.animalproject_1.fragments;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.*;
import com.example.animalproject.animalproject_1.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * A simple {@link Fragment} subclass.
 */
public class Map1Fragment extends Fragment implements OnMapReadyCallback {
    GoogleMap map;
    private android.support.v4.app.FragmentManager sfrm;
    private FragmentActivity myContext;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private android.support.v4.app.Fragment infoPage;
    private Switch sw;
    private FusedLocationProviderClient mFusedLocationClient;
    private MarkerOptions[] myMarkersOptions;
    private MarkerOptions[] nearbyMarkers;
    private Marker[] myMarkers;
    private Marker[] nearMarkers;
    private Ad[] nearAds;
    private LatLng l;
    private Location myLoc;
    private int countShow;
    private Location tmp, tmp1;
    private Task tsk;
    private Ad adParam;
    private boolean flag;
    public Map1Fragment() {
        // Required empty public constructor
    }
    public static Map1Fragment newInstance(Ad ad, boolean flag) {
        Map1Fragment fragment = new Map1Fragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, ad);
        args.putBoolean(ARG_PARAM2, flag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        myContext = (FragmentActivity) context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map1, null);
        sfrm = myContext.getSupportFragmentManager();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        sw = (Switch) v.findViewById(R.id.switch_near);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        SupportMapFragment mapFr = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map1);
        mapFr.getMapAsync(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        }
        l = new LatLng(55.7558, 37.6173);
        flag = false;
        if (getArguments() != null) {
            adParam = getArguments().getParcelable(ARG_PARAM1);
            flag = getArguments().getBoolean(ARG_PARAM2);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(adParam.cords, 16));
        }
        else {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(l, 10));
        }
        ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();

        myMarkersOptions = new MarkerOptions[Ads.adsList.size()];
        countShow = 7 >= Ads.adsList.size() ? Ads.adsList.size()/2: 7;
        nearAds = new Ad[countShow];
        nearbyMarkers = new MarkerOptions[countShow];
        nearMarkers = new Marker[countShow];
        if (Ads.adsList.size() == 0) countShow = 0;
        myMarkers = new Marker[Ads.adsList.size()];
        for (int i = 0; i < Ads.adsList.size(); i++) {
            myMarkersOptions[i] = new MarkerOptions();
            myMarkersOptions[i].snippet("Нажмите для подробной информации").position(Ads.adsList.get(i).cords).title(
                    String.format("%s (%s), вознаграждение: %s", Ads.adsList.get(i).type, Ads.adsList.get(i).breed, Ads.adsList.get(i).reward)
            );
            myMarkers[i] = map.addMarker(myMarkersOptions[i]);
            myMarkers[i].setTag(Ads.adsList.get(i));
        }
        for (int i = 0; i < countShow; i++) {
            nearbyMarkers[i] = new MarkerOptions();
            nearbyMarkers[i].position(myMarkersOptions[i].getPosition()).snippet(myMarkersOptions[i].getSnippet()).title(myMarkersOptions[i].getTitle());
            nearAds[i] = Ads.adsList.get(i);
        }

        tmp = new Location("Test");
        tmp1 = new Location("Test1");

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Bundle args = new Bundle();
                args.putParcelable(ARG_PARAM1, (Ad) marker.getTag());
                args.putInt(ARG_PARAM2, 2);
                infoPage = new AdInfo();
                infoPage.setArguments(args);
                sfrm.beginTransaction().replace(R.id.content_frame, infoPage).commit();
            }
        });
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

                    tsk = mFusedLocationClient.getLastLocation();
                    tsk.addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                myLoc = (Location) tsk.getResult();
                                for (int i = countShow; i < myMarkersOptions.length; i++) {
                                    for (int j = 0; j < countShow; j++) {
                                        tmp.setLatitude(nearbyMarkers[j].getPosition().latitude);
                                        tmp.setLongitude(nearbyMarkers[j].getPosition().longitude);
                                        tmp1.setLatitude(myMarkersOptions[i].getPosition().latitude);
                                        tmp1.setLongitude(myMarkersOptions[i].getPosition().longitude);
                                        if (myLoc.distanceTo(tmp) > myLoc.distanceTo(tmp1)) {
                                            nearbyMarkers[j].position(myMarkersOptions[i].getPosition()).snippet(myMarkersOptions[i].getSnippet()).title(myMarkersOptions[i].getTitle());
                                            nearAds[j] = Ads.adsList.get(i);
                                        }
                                    }
                                }
                                for (int j = 0; j < countShow; j++) {
                                    nearMarkers[j] = map.addMarker(nearbyMarkers[j]);
                                    nearMarkers[j].setVisible(false);
                                    nearMarkers[j].setTag(nearAds[j]);
                                }

                                if (sw.isChecked()) {
                                    Toast.makeText(getContext(), "Показаны ближайшие объявления", Toast.LENGTH_SHORT).show();
                                    for (int i = 0; i < myMarkersOptions.length; i++) {
                                        myMarkers[i].setVisible(false);
                                    }
                                    for (int i = 0; i < countShow; i++) {
                                        nearMarkers[i].setVisible(true);
                                    }
                                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLoc.getLatitude(), myLoc.getLongitude()), 10));
                                } else {
                                    Toast.makeText(getContext(), "Показаны все объявления", Toast.LENGTH_SHORT).show();
                                    for (int i = 0; i < myMarkersOptions.length; i++) {
                                        myMarkers[i].setVisible(true);
                                    }
                                    for (int i = 0; i < countShow; i++) {
                                        nearMarkers[i].setVisible(false);
                                    }
                                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(l, 10));
                                }
                            }else Toast.makeText(getContext(), "Не удалось получить текущее местоположение, ближайшие объявления недоступны", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
