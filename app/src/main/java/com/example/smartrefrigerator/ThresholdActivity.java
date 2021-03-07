package com.example.smartrefrigerator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ThresholdActivity extends BaseClass {
    private static final String TAG = "myLog";
    //   DiscreteSeekBar whistleSeekBar, volumeSeekBar, vibrationSeekBar;
    // SharedPreferences myPrefs;
    // SharedServices sharedPref;
    String eggs,fruits,vegetables;
    EditText txtVagetables, txtFruits, txtEggs;
    Switch txtVibration,txtNoticationSound,txtOffNotification;
    //  DatabaseReference dref= FirebaseDatabase.getInstance().getReference();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference dref = firebaseDatabase.getReference();
    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_threshold);
        txtEggs = (EditText) findViewById(R.id.txtEggsvalue);
        txtFruits = (EditText) findViewById(R.id.txtFruitsvalue);
        txtVagetables = (EditText) findViewById(R.id.txtvegvalue);
        btnSave = findViewById(R.id.btnSaveValue);
        ////Switch Button ////.

        //  dref.child("Threshhold/Eggs/value").setValue(eggs).toString();
        //  dref.child("Threshhold/Fruits/value").setValue(fruits).toString();
        //  dref.child("Threshhold/Vagetables/value").setValue(vegetables).toString();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eggs= (String) txtEggs.getText().toString();
                fruits= (String)txtFruits.getText().toString();
                vegetables= (String) txtVagetables.getText().toString();
                dref.child("Threshhold/Eggs/value").setValue(eggs).toString();
                dref.child("Threshhold/Fruits/value").setValue(fruits).toString();
                dref.child("Threshhold/Vagetables/value").setValue(vegetables).toString();
                Toast.makeText(ThresholdActivity.this, "Values saved Successfully", Toast.LENGTH_SHORT).show();
            }
        });


        // dref.child("Notifications/Eggs/datetime").setValue(currentDateTimeString);


//        txtNoticationSound =(Switch) findViewById(R.id.notisoundswitch);
//        txtOffNotification =(Switch) findViewById(R.id.notishowswitch);
//        txtVibration =(Switch) findViewById(R.id.vibswitch);

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String  egg=dataSnapshot.child("Threshhold/Eggs/value").getValue().toString();
                String  vagetable=dataSnapshot.child("Threshhold/Fruits/value").getValue().toString();
                String  fruit=dataSnapshot.child("Threshhold/Vagetables/value").getValue().toString();
                txtEggs.setText(String.valueOf(egg));
                txtFruits.setText(String.valueOf(fruit));
                txtVagetables.setText(String.valueOf(vagetable));


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        txtVibration.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(txtVibration.isChecked()){
//                   // sharedPref.setBool("Key_OnVibration", true);
//                    Toast.makeText(SettingsActivity.this, "ON", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Toast.makeText(SettingsActivity.this, "OFF", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        } );
//        txtNoticationSound.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(txtVibration.isChecked()){
//                    Toast.makeText(SettingsActivity.this, "ON", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Toast.makeText(SettingsActivity.this, "OFF", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        } );
//        txtOffNotification.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(txtVibration.isChecked()){
//                    Toast.makeText(SettingsActivity.this, "ON", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Toast.makeText(SettingsActivity.this, "OFF", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        } );


    }


    @Override
    int getContentViewId() {
        return R.layout.settings_activity;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_th;
    }


}