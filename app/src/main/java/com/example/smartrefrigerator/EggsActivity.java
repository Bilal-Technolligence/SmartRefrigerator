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

public class EggsActivity extends BaseClass {
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
      //  setContentView(R.layout.activity_eggs);

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
                dref.child("ExpiryNotify/Eggs/tittle").setValue( "No Food Expired" );


            }
            // When the task is over it will print 00
            public void onFinish() {
                dref.child("ExpiryNotify/Eggs/tittle").setValue( "Eggs Expired" );
                dref.child("ExpiryNotify/Eggs/description").setValue( "Your Eggs expired please destroy it" );
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                dref.child("ExpiryNotify/Eggs/datetime").setValue(currentDateTimeString);
                Toast.makeText(EggsActivity.this, "Eggs Expired", Toast.LENGTH_SHORT).show();
               // onExpire();
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
                    String  egg=dataSnapshot.child("Threshhold/Eggs/value").getValue().toString();
                    thresholdValues =Integer.parseInt(egg);

                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                status=dataSnapshot.child("Eggs").child("amount").getValue().toString();
              //  String  fruitss=dataSnapshot.child("Threshhold/Eggs/value").getValue().toString();
               // Toast.makeText(EggsActivity.this, "Value"+fruitss, Toast.LENGTH_SHORT).show();
                txtRemaining.setText(status);
                thresholdComparison=parseInt(status);
                // String value=thresholdValue.getText().toString();

                // thresholddata= parseInt(value);
             //   thresholddata = Integer.parseInt(fruit);

                //Compare threshold value and generate alert

                used=12-parseInt(status);
                txtUsed.setText(Integer.toString(used));
                //  AddData();

                if(thresholdValues>thresholdComparison){
                   // onReceive();
                    scheduleNotification(getNotification( "Smart Refrigerator Alert" ) , 5000 ) ;
                    saveNotificationfirebase();
                    //   Toast.makeText(IngredientDetailActivity.this, "Refill Box", Toast.LENGTH_SHORT).show();

                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //


    }

    @Override
    int getContentViewId() {
        return R.layout.activity_eggs;
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
        builder.setTicker("Eggs");
        builder.setContentTitle("Eggs amount Below 5");
        builder.setContentText("Please Refill Eggs Box");
        builder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
        builder.setAutoCancel( true ) ;
        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
        return builder.build() ;

    }

    //Save notification on firebase

    public void saveNotificationfirebase(){

        dref.child("Notifications/Eggs/tittle").setValue( "Please Refill Box" );
        dref.child("Notifications/Eggs/description").setValue( "only 4 eggs remaning" );
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        dref.child("Notifications/Eggs/datetime").setValue(currentDateTimeString);


    }


}