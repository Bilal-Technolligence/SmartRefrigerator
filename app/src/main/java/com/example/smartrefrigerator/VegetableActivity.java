package com.example.smartrefrigerator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

import static java.lang.Integer.parseInt;

public class VegetableActivity extends AppCompatActivity {
    DatabaseReference dref= FirebaseDatabase.getInstance().getReference();
    TextView txtRemaining, txtUsed ,txtThreshold, thresholdValue,txtDays,txtMinutes,txtHours;
    String status;
    int used;
    int thresholdComparison;
    double thresholddata;
    NotificationCompat.Builder notification;
    private static final int uniqueID = 45612;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vegetable);

        //
        txtRemaining=(TextView) findViewById(R.id.txtremaining);
        txtUsed=(TextView) findViewById(R.id.txtused);
        txtDays = (TextView) findViewById(R.id.tv_days);
        txtHours = (TextView) findViewById(R.id.tv_hour);
        txtMinutes = (TextView) findViewById(R.id.tv_minute);
        //
        //Timer CountDown
        new CountDownTimer(40000, 1000) {
            public void onTick(long millisUntilFinished) {
                // Used for formatting digit to be in 2 digits only
                NumberFormat f = new DecimalFormat("00");
                long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                txtDays.setText(f.format(hour));
                txtHours.setText(f.format(min));
                txtMinutes.setText(f.format(sec));
                dref.child("ExpiryNotify/Vegetables/tittle").setValue( "No Food Expired" );


            }
            // When the task is over it will print 00
            public void onFinish() {
                dref.child("ExpiryNotify/Vegetables/tittle").setValue( "Vegetable Expired" );
                dref.child("ExpiryNotify/Vegetables/description").setValue( "Your Vegetable expired please destroy it" );
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                dref.child("ExpiryNotify/Vegetables/datetime").setValue(currentDateTimeString);
                Toast.makeText(VegetableActivity.this, "Vegetable Expired", Toast.LENGTH_SHORT).show();
                txtDays.setText("00");
                txtMinutes.setText("00");
                txtHours.setText("00");

            }
        }.start();


        //Firebase Data Base

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                status=dataSnapshot.child("Vegetables").child("amount").getValue().toString();
                txtRemaining.setText(status  + " g");
                thresholdComparison=parseInt(status);
                // String value=thresholdValue.getText().toString();

                // thresholddata= parseInt(value);
                thresholddata = 550;

                //Compare threshold value and generate alert

                used=1000-parseInt(status);
                txtUsed.setText(Integer.toString(used) + " g");
                //  AddData();

                if(thresholddata>thresholdComparison){
                    onReceive();
                    saveNotificationfirebase();
                    //   Toast.makeText(IngredientDetailActivity.this, "Refill Box", Toast.LENGTH_SHORT).show();

                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        //set Home Seleceted
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_diet:
                        startActivity(new Intent(getApplicationContext(), DietActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_setting:
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_notification:
                        startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    //To Genrate a notification on threshold value

    public void onReceive(){


        Intent notificationIintent = new Intent(VegetableActivity.this, NotificationActivity.class);
        TaskStackBuilder taskStackBuilder= TaskStackBuilder.create(VegetableActivity.this);
        taskStackBuilder.addNextIntent(notificationIintent);
        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(100, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(VegetableActivity.this);
        Uri alarmSound = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);

        Notification notification =builder.setContentTitle("Threshold alert")
                .setAutoCancel(true)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                .setTicker("Salt")
                .setContentTitle("Amount Below 500g")
                .setContentText("Please Refill Vegetable Box")
                .setAutoCancel(true)
                // .setNumber(messageCount)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)

                .setContentIntent(pendingIntent).build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(uniqueID, notification);
    }

    //Save notification on firebase

    public void saveNotificationfirebase(){

        dref.child("Notifications/Fruit/tittle").setValue( "Please Refill Box" );
        dref.child("Notifications/Fruit/description").setValue( "Fruit amount below 500g" );
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        dref.child("Notifications/Fruit/datetime").setValue(currentDateTimeString);


    }
}