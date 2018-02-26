package com.example.animalproject.animalproject_1;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import animalproject.animalproject_1.fragments.Ads;
import newfirebasepackage.FirebaseConstants;

public class MainActivity extends AppCompatActivity {
    private Spinner spinnerServer;
    private Button appStartButton;
    public static String stringToSend = "";
    public static String serverIp = "79.104.195.46";
    public static int serverPort = 3333;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appStartButton = (Button)findViewById(R.id.start_app_button);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel mChannel =
                    new NotificationChannel(FirebaseConstants.CHANNEL_ID,FirebaseConstants.CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT);
            mChannel.setDescription(FirebaseConstants.CHANNEL_DESCRIPTION);
            mChannel.enableLights(true);
            mChannel.enableVibration(true);
            mNotificationManager.createNotificationChannel(mChannel);
        }
        spinnerServer = (Spinner)findViewById(R.id.spinnerServer);
        final String[] genders = {"Сервер 1", "Сервер 2", "Сервер 3"};

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                genders
        );
        spinnerServer.setAdapter(adapterSpinner);
        spinnerServer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Toast.makeText(getApplicationContext(),"Выбран сервер 1",Toast.LENGTH_SHORT).show();
                    serverIp = "79.104.195.46";
                    serverPort = 3333;
                }
                if (i == 1) {
                    Toast.makeText(getApplicationContext(),"Выбран сервер 2",Toast.LENGTH_SHORT).show();
                    serverIp = "2";
                    serverPort = 13267;
                }
                if (i == 2) {
                    Toast.makeText(getApplicationContext(),"Выбран сервер 3",Toast.LENGTH_SHORT).show();
                    serverIp = "3";
                    serverPort = 13267;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        appStartButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent toNavigation = new Intent(MainActivity.this,NavigationActivity.class);
                        Ads ads = new Ads();
                        startActivity(toNavigation);
                    }
                }
        );
    }

}
