package com.example.smartrefrigerator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

    TextView txtRemaining, txtUsed ,txtExpirayStatus, thresholdValue,txtSeconds,txtMinutes,txtHours;
    String status,notificationStatus;
    int used;
    int thresholdComparison;
    double thresholdValues;
    Button btnReset;

    /////Only for Notification////
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        txtRemaining=(TextView) findViewById(R.id.txtremaining);
        txtUsed=(TextView) findViewById(R.id.txtused);
        LinearLayout EStatus=findViewById(R.id.linear_layout_1);
        LinearLayout TimerLayout=findViewById(R.id.linear_layout_2);
        btnReset = findViewById(R.id.txtReset);
        txtHours = (TextView) findViewById(R.id.txtHours);
        txtMinutes  = (TextView) findViewById(R.id.txtMinute);
        txtSeconds = (TextView) findViewById(R.id.txtSec);
        txtExpirayStatus =findViewById(R.id.txtFExpiryStatus);


        //Firebase Data Base
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    //  String  status=dataSnapshot.child("ExpiryNotify/Fruit/tittle").getValue().toString();
                    notificationStatus=snapshot.child("Setting/OffNotification").getValue().toString();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dref.addValueEventListener(new ValueEventListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String  Threshold=dataSnapshot.child("ExpiryNotify/Fruit/tittle").getValue().toString();
                    if(Threshold.equals("No Food Expired")){

                        //Timer CountDown
                        new CountDownTimer(40000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                txtExpirayStatus.setText("Fruit Expiry");
                                EStatus.setBackgroundColor(Color.parseColor("#1F45FC"));                                btnReset.setVisibility(View.GONE);

                                // Used for formatting digit to be in 2 digits only
                                NumberFormat f = new DecimalFormat("00");
                                long hour = (millisUntilFinished / 3600000) % 24;
                                long min = (millisUntilFinished / 60000) % 60;
                                long sec = (millisUntilFinished / 1000) % 60;
                                txtHours.setText(f.format(hour));
                                txtMinutes.setText(f.format(min));
                                txtSeconds.setText(f.format(sec));
                                dref.child("ExpiryNotify/Fruit/tittle").setValue( "No Food Expired" );
                                int ab= parseInt( txtSeconds.getText().toString());

                                if(ab<30)
                                {
                                    final int from = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryD);
                                    final int to   = ContextCompat.getColor(getApplicationContext(), R.color.red);

                                    ValueAnimator anim = new ValueAnimator();
                                    anim.setIntValues(from, to);
                                    anim.setEvaluator(new ArgbEvaluator());
                                    anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                        @Override
                                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                            EStatus.setBackgroundColor((Integer)valueAnimator.getAnimatedValue());
                                        }
                                    });

                                    anim.setDuration(1000);
                                    anim.start();
                                }


                            }

                            // When the
                            // task is over it will print 00
                            @SuppressLint("ResourceAsColor")
                            public void onFinish() {
                                dref.child("ExpiryNotify/Fruit/tittle").setValue( "Fruit Expired" );
                                dref.child("ExpiryNotify/Fruit/description").setValue( "Your Fruit expired please destroy it" );
                                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                                dref.child("ExpiryNotify/Fruit/datetime").setValue(currentDateTimeString);
                                // onExpire();
                                txtHours.setText("00");
                                txtMinutes.setText("00");
                                txtSeconds.setText("00");
                                txtExpirayStatus.setText("Your Fruit expired please destroy");

                            }
                        }.start();

                    } else{
                        txtHours.setText("00");
                        txtMinutes.setText("00");
                        txtSeconds.setText("00");
                        txtExpirayStatus.setText("Your Fruit expired please destroy");
                        EStatus.setBackgroundColor(Color.parseColor("#DF8602"));
                        btnReset.setVisibility(View.VISIBLE);
                    }

                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dref.child("ExpiryNotify/Fruit/tittle").setValue( "No Food Expired" );

            }
        });

        //Firebase Data Base
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
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

        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                status=dataSnapshot.child("Fruit").child("amount").getValue().toString();
                txtRemaining.setText(status  + " g");
                thresholdComparison=parseInt(status);

                used=1000-parseInt(status);
                txtUsed.setText(Integer.toString(used) + " g");
                //  AddData();
                if(thresholdValues>thresholdComparison){
                    if(notificationStatus.equals("ON"))
                    {
                        //  onReceive();
                        scheduleNotification(getNotification( "Smart Refrigerator Alert" ) , 5000 ) ;
                        saveNotificationfirebase();
                    }else{
                        saveNotificationfirebase();
                    }


                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
        Intent intent = new Intent(this, Notification2.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setTicker("Fruits");
        builder.setContentTitle("Fruit Below 500g");
        builder.setContentText("Please Refill Fruit Box");
        builder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
        builder.setAutoCancel( true ) ;
        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
        return builder.build() ;
    }

    //Save notification on firebase

    public void saveNotificationfirebase(){

        dref.child("Notifications/Fruit/title").setValue( "Please Refill Fruit Box" );
        dref.child("Notifications/Fruit/description").setValue( "Fruit amount below 500g" );
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        dref.child("Notifications/Fruit/datetime").setValue(currentDateTimeString);


    }


    @Override
    int getContentViewId() {
        return R.layout.activity_fruit;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_home;
    }


}