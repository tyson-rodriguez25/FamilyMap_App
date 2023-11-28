package com.example.familymap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.familymap.model.localStorage;
import com.example.familymap.ui.main.LoginFragment;
import com.example.familymap.ui.main.MapFragment;
//import com.example.familymap.ui.main.MainFragment;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;


public class MainActivity extends AppCompatActivity {
    localStorage ls = localStorage.getInstance();
    String auth = ls.getAuthToken();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Iconify.with(new FontAwesomeModule());

        if (auth == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, LoginFragment.newInstance())
                    .commitNow();
        }
        else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MapFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ls.getAuthToken() == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, LoginFragment.newInstance())
                    .commitNow();
        }
    }

    public void switchToMapFragment()
    {
        Fragment fragment = new MapFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }


}
