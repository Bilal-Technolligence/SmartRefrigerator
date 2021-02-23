package com.example.smartrefrigerator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends BaseClass {
    private static final String TAG = "myLog";
 //   DiscreteSeekBar whistleSeekBar, volumeSeekBar, vibrationSeekBar;
    SharedPreferences myPrefs;
    SharedServices sharedPref;
    private int eggs;
    private int fruits;
    private int vegetables;
    private boolean vibration;
    private int noNotification;
    private int noSound;
    EditText txtVagetables, txtFruits, txtEggs;
    Switch txtVibration,txtNoticationSound,txtOffNotification;
    private int flashProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.settings_activity);

        sharedPref = new SharedServices(SettingsActivity.this);
        myPrefs = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        eggs = sharedPref.getInt("Key_Eggs");
        fruits = sharedPref.getInt("Key_Fruits");
       vegetables = sharedPref.getInt("Key_Vegetables");
        vibration = sharedPref.getBool("Key_OnVibration");

        txtEggs = findViewById(R.id.txtEggsvalue);
        txtFruits = findViewById(R.id.txtFruitsvalue);
        txtVagetables = findViewById(R.id.txtvegvalue);
        ////Switch Button ////
        txtNoticationSound =(Switch) findViewById(R.id.notisoundswitch);
        txtOffNotification =(Switch) findViewById(R.id.notishowswitch);
        txtVibration =(Switch) findViewById(R.id.vibswitch);




        txtEggs.setText(String.valueOf(eggs));
        txtFruits.setText(String.valueOf(fruits));

        txtVagetables.setText(String.valueOf(vegetables));

        int egg = Integer.parseInt(txtEggs.getText().toString());
        int veg = Integer.parseInt(txtVagetables.getText().toString());
        int fruit = Integer.parseInt(txtFruits.getText().toString());


        sharedPref.setInt("Key_Eggs", egg);
        sharedPref.setInt("Key_Fruits", fruit);
        sharedPref.setInt("Key_Vegetables", veg);


        txtVibration.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(txtVibration.isChecked()){
                    sharedPref.setBool("Key_OnVibration", true);
                }
                else
                {
                    sharedPref.setBool("Key_OnVibration", false);

                }
            }
        } );
        txtNoticationSound.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(txtVibration.isChecked()){
                    sharedPref.setBool("Key_NoSound", true);
                }
                else
                {
                    sharedPref.setBool("Key_NoSound", false);

                }
            }
        } );
        txtOffNotification.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(txtVibration.isChecked()){
                    sharedPref.setBool("Key_NoNotify", true);
                }
                else
                {
                    sharedPref.setBool("Key_NoNotify", false);

                }
            }
        } );


    }


    @Override
    int getContentViewId() {
        return R.layout.settings_activity;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_setting;
    }


}