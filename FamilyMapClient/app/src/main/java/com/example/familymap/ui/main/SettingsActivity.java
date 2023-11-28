package com.example.familymap.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TableRow;

import com.example.familymap.MainActivity;
import com.example.familymap.R;
import com.example.familymap.model.localStorage;

public class SettingsActivity extends AppCompatActivity {

    private Switch maleSwitch;
    private Switch femaleSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Family Map: Settings");

        maleSwitch = findViewById(R.id.maleSwitch);
        femaleSwitch = findViewById(R.id.femaleSwitch);

        maleSwitch.setChecked(localStorage.getInstance().isMaleFilter());

        femaleSwitch.setChecked(localStorage.getInstance().isFemaleFilter());


        maleSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    localStorage.getInstance().setMaleFilter(isChecked);

            }
        });

        femaleSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                localStorage.getInstance().setFemaleFilter(isChecked);
            }

        });

        findViewById(R.id.logoutAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                localStorage.getInstance().clearStorage();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                SettingsActivity.this.startActivity(intent);
            }
        });



    }


}
