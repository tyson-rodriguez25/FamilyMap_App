package com.example.familymap.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.familymap.R;
import com.example.familymap.model.localStorage;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

public class EventActivity extends AppCompatActivity {
    public static final String eventKey = "com.example.familymap.ui.main.eventKey";
    private String eventId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        MapFragment mapFragment = MapFragment.newInstance();

        eventId = getIntent().getStringExtra(eventKey);

        mapFragment.setEventId(eventId);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, mapFragment)
                .commitNow();


    }

}
