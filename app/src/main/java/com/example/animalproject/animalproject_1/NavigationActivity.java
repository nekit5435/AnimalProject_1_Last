package com.example.animalproject.animalproject_1;

import android.app.*;
import android.app.ListFragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import animalproject.animalproject_1.fragments.AdInfo;
import animalproject.animalproject_1.fragments.AddNewAd;
import animalproject.animalproject_1.fragments.Ads;
import animalproject.animalproject_1.fragments.MainFragment;
import animalproject.animalproject_1.fragments.Map1Fragment;
import animalproject.animalproject_1.fragments.listview;
import animalproject.animalproject_1.fragments.set_cords_map;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback, listview.OnFragmentInteractionListener,
        AdInfo.OnFragmentInteractionListener,
        AddNewAd.OnFragmentInteractionListener,
        set_cords_map.OnFragmentInteractionListener {

    Fragment animal_list_fr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        android.support.v4.app.FragmentManager sfrm = getSupportFragmentManager();
        sfrm.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();

        animal_list_fr = new ListFragment();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        android.support.v4.app.FragmentManager sfrm = getSupportFragmentManager();
        if (id == R.id.as_list) {
            sfrm.beginTransaction().replace(R.id.content_frame, new listview()).commit();
        } else if (id == R.id.as_map) {
            sfrm.beginTransaction().replace(R.id.content_frame, new Map1Fragment()).commit();
        } else if (id == R.id.new_ad) {
            sfrm.beginTransaction().replace(R.id.content_frame, new AddNewAd()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
