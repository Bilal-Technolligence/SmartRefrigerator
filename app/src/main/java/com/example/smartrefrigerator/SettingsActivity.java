package com.example.smartrefrigerator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsActivity extends BaseClass {

    CardView btn;
    DatabaseReference dref= FirebaseDatabase.getInstance().getReference();
    Switch btnNotificationSound,btnVibration,btnOffNotifications;
    String sound,vibration,offnotificationn;
    TextView txtView;
    AudioManager am;
    CardView GuideLine,userFeedBack,RateUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.settings_activity);


        GuideLine =(CardView) findViewById(R.id.guidLine);
        userFeedBack =(CardView) findViewById(R.id.btnFeedBack);
        RateUs =(CardView) findViewById(R.id.btnRateUS);
        txtView = findViewById(R.id.tvRingtone);
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        btn = findViewById(R.id.btnSelRingtone);
        btnNotificationSound=(Switch) findViewById(R.id.notisoundswitch);
        btnVibration=(Switch) findViewById(R.id.vibswitch);
        btnOffNotifications=(Switch) findViewById(R.id.notishowswitch);

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    //  String  status=dataSnapshot.child("ExpiryNotify/Fruit/tittle").getValue().toString();
                    sound=snapshot.child("Setting/SoundNotifiation").getValue().toString();
                    vibration=snapshot.child("Setting/Vibration").getValue().toString();
                    offnotificationn=snapshot.child("Setting/OffNotification").getValue().toString();

                    if(sound.equals("ON")){
                        btnNotificationSound.setChecked(true);
                    }

                    if(vibration.equals("ON")){
                        btnVibration.setChecked(true);
                    }
                    if(offnotificationn.equals("ON")){
                        btnOffNotifications.setChecked(true);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        GuideLine.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       startActivity(new Intent(getApplicationContext(),GuideLine.class));
    }
});
        userFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),FeedbackActivity.class));
            }
        });
        btnOffNotifications.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(btnOffNotifications.isChecked()){
                    dref.child( "Setting/OffNotification" ).setValue("ON").toString();



                }
                else
                {
                    dref.child( "Setting/OffNotification" ).setValue("OFF");

                }
            }
        } );
        btnVibration.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(btnOffNotifications.isChecked()){
                    dref.child( "Setting/Vibration" ).setValue("ON").toString();



                }
                else
                {
                    dref.child( "Setting/Vibration" ).setValue("OFF");

                }
            }
        } );
        btnNotificationSound.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(btnOffNotifications.isChecked()){
                    dref.child( "Setting/SoundNotifiation" ).setValue("ON").toString();
                    am.setRingerMode(1);

                }
                else
                {
                    dref.child( "Setting/SoundNotifiation" ).setValue("OFF");
                    am.setRingerMode(2);

                }
            }
        } );

        RateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dref.child("Applink").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                final String url = dataSnapshot.getValue().toString();
                                Intent viewIntent =
                                        new Intent("android.intent.action.VIEW",
                                                Uri.parse(url));
                                try{startActivity(viewIntent);}
                                catch (Exception e){}
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }catch(Exception e) {
                    Toast.makeText(getApplicationContext(),"Unable to Connect Try Again...",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent to select Ringtone.
                final Uri currentTone=
                        RingtoneManager.getActualDefaultRingtoneUri(SettingsActivity.this,
                                RingtoneManager.TYPE_ALARM);
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_RINGTONE);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, currentTone);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
                startActivityForResult(intent, 999);
            }
        });
    }
    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == RESULT_OK) {
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            txtView.setText("From :" + uri.getPath());
        }
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