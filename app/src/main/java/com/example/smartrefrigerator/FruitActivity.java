package com.example.smartrefrigerator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
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

public class FruitActivity extends BaseClass {
    DatabaseReference dref= FirebaseDatabase.getInstance().getReference();

    TextView txtRemaining, txtUsed ,txtThreshold, thresholdValue,txtDays,txtMinutes,txtHours;
    String status;
    int used;
    int thresholdComparison;
    double thresholdValues;
    /////Only for Notification////
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.activity_fruit);
        //https://stackoverflow.com/questions/10032003/how-to-make-a-countdown-timer-in-android
        txtRemaining=(TextView) findViewById(R.id.txtremaining);
        txtUsed=(TextView) findViewById(R.id.txtused);
        txtDays = (TextView) findViewById(R.id.tv_days);
        txtHours = (TextView) findViewById(R.id.tv_hour);
        txtMinutes = (TextView) findViewById(R.id.tv_minute);
       // thresholdValue= (TextView) findViewById(R.id.txtthresholdvalue);


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

//                dref.child("ExpiryTime/Fruit/hours").setValue(hour );
//                dref.child("ExpiryNotify/Fruit/min").setValue( min );
//                dref.child("ExpiryNotify/Fruit/sec").setValue( sec );

                dref.child("ExpiryNotify/Fruit/tittle").setValue( "No Food Expired" );
//                dref.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                        String  hor=dataSnapshot.child("ExpiryTime/Fruit/hours").getValue().toString();
//                        String  mins=dataSnapshot.child("ExpiryNotify/Fruit/min").getValue().toString();
//                        String  secd=dataSnapshot.child("ExpiryNotify/Fruit/sec").getValue().toString();
//                        txtDays.setText(f.format(hor));
//                        txtHours.setText(f.format(mins));
//                        txtMinutes.setText(f.format(secd));
//
//
//                    }
//
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });

            }
            // When the task is over it will print 00
            public void onFinish() {
                dref.child("ExpiryNotify/Fruit/tittle").setValue( "Fruit Expired" );
                dref.child("ExpiryNotify/Fruit/description").setValue( "Your Fruit expired please destroy it" );
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                dref.child("ExpiryNotify/Fruit/datetime").setValue(currentDateTimeString);
                Toast.makeText(FruitActivity.this, "Fruit Expired", Toast.LENGTH_SHORT).show();
                txtDays.setText("00");
                txtMinutes.setText("00");
                txtHours.setText("00");

            }
        }.start();


        //Firebase Data Base
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    //  String  status=dataSnapshot.child("ExpiryNotify/Fruit/tittle").getValue().toString();
                    String  fruit=dataSnapshot.child("Threshhold/Fruits/value").getValue().toString();
                    thresholdValues =Integer.parseInt(fruit);

                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                status=dataSnapshot.child("Fruit").child("amount").getValue().toString();
                txtRemaining.setText(status  + " g");
                thresholdComparison=parseInt(status);
               // String value=thresholdValue.getText().toString();

               // thresholddata= parseInt(value);

                //Compare threshold value and generate alert

                used=1000-parseInt(status);
                txtUsed.setText(Integer.toString(used) + " g");
                //  AddData();

                if(thresholdValues>thresholdComparison){
                  //  onReceive();
                    scheduleNotification(getNotification( "Smart Refrigerator Alert" ) , 5000 ) ;
                    saveNotificationfirebase();
                    //   Toast.makeText(IngredientDetailActivity.this, "Refill Box", Toast.LENGTH_SHORT).show();

                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    int getContentViewId() {
        return R.layout.activity_fruit;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_home;
    }

    //To Genrate a notification on threshold value
    //To Genrate a notification on threshold value

    private void scheduleNotification(Notification notification , int delay) {
        Intent notificationIntent = new Intent( this, MyNotificationPublisher.class ) ;
        // Intent notificationIintent = new Intent(this, NotificationActivity.class);
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION_ID , 1 ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION , notification) ;
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( this, 0 , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
        long futureInMillis = SystemClock. elapsedRealtime () + delay ;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        alarmManager.set(AlarmManager. ELAPSED_REALTIME_WAKEUP , futureInMillis , pendingIntent) ;
    }
    private Notification getNotification (String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder( this, default_notification_channel_id ) ;
        builder.setContentTitle( "Threshold alert" ) ;
        Intent intent = new Intent(this, NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setTicker("Fruits");
        builder.setContentTitle("Fruit Below 500g");
        builder.setContentText("Please Refill Vegetable Box");
        builder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
        builder.setAutoCancel( true ) ;
        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
        return builder.build() ;
    }

    //Save notification on firebase

    public void saveNotificationfirebase(){

        dref.child("Notifications/Fruit/tittle").setValue( "Please Refill Box" );
        dref.child("Notifications/Fruit/description").setValue( "Fruit amount below 500g" );
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        dref.child("Notifications/Fruit/datetime").setValue(currentDateTimeString);


    }

//    @Override
//    int getContentViewId() {
//        return R.layout.activity_fruit;
//    }
//
//    @Override
//    int getNavigationMenuItemId() {
//        return R.id.nav_diet;
    //}
}