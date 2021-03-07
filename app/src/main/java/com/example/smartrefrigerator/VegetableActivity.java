package com.example.smartrefrigerator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

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
import android.content.SharedPreferences;
import android.graphics.Color;
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

public class VegetableActivity extends BaseClass {
    DatabaseReference dref= FirebaseDatabase.getInstance().getReference();
    TextView txtRemaining, txtUsed ,txtExpirayStatus, thresholdValue,txtSeconds,txtMinutes,txtHours;
    String status,notificationStatus;
    int used;
    int thresholdComparison;
    double thresholdValues;
    Button btnReset;
    NotificationCompat.Builder notification;
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
        txtExpirayStatus =findViewById(R.id.txtVExpiryStatus);
        //Firebase Data Base
        dref.addValueEventListener(new ValueEventListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String  Threshold=dataSnapshot.child("ExpiryNotify/Vegetables/tittle").getValue().toString();
                    if(Threshold.equals("No Food Expired")){

                        //Timer CountDown
                        new CountDownTimer(40000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                txtExpirayStatus.setText("Vegetables Expiry");
                                EStatus.setBackgroundColor(Color.parseColor("#1F45FC"));                                btnReset.setVisibility(View.GONE);

                                // Used for formatting digit to be in 2 digits only
                                NumberFormat f = new DecimalFormat("00");
                                long hour = (millisUntilFinished / 3600000) % 24;
                                long min = (millisUntilFinished / 60000) % 60;
                                long sec = (millisUntilFinished / 1000) % 60;
                                txtHours.setText(f.format(hour));
                                txtMinutes.setText(f.format(min));
                                txtSeconds.setText(f.format(sec));
                                dref.child("ExpiryNotify/Vegetables/tittle").setValue( "No Food Expired" );
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
                                dref.child("ExpiryNotify/Vegetables/tittle").setValue( "Vagetables Expired" );
                                dref.child("ExpiryNotify/Vegetables/description").setValue( "Your Vagetables expired please destroy it" );
                                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                                dref.child("ExpiryNotify/Vegetables/datetime").setValue(currentDateTimeString);
                                txtHours.setText("00");
                                txtMinutes.setText("00");
                                txtSeconds.setText("00");
                                txtExpirayStatus.setText("Your Vegetables expired please destroy");

                            }
                        }.start();

                    } else{
                        txtHours.setText("00");
                        txtMinutes.setText("00");
                        txtSeconds.setText("00");
                        txtExpirayStatus.setText("Your Vegetables expired please destroy");
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
                dref.child("ExpiryNotify/Vegetables/tittle").setValue( "No Food Expired" );

            }
        });

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
        //Firebase Data Base
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    //  String  status=dataSnapshot.child("ExpiryNotify/Fruit/tittle").getValue().toString();
                    String  vage=dataSnapshot.child("Threshhold/Vagetables/value").getValue().toString();
                    thresholdValues =Integer.parseInt(vage);

                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                status=dataSnapshot.child("Vagetables").child("amount").getValue().toString();
                txtRemaining.setText(status  + " g");
                thresholdComparison=parseInt(status);
                // String value=thresholdValue.getText().toString();
                //First time when App Installed\\\

                // thresholddata= parseInt(value);
              //  thresholddata  = sharedPref.getInt("Key_Vegetables");
                //thresholddata = 550;

                //Compare threshold value and generate alert

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
        //

    }

    @Override
    int getContentViewId() {
        return R.layout.activity_vegetable;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_home;
    }

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
       builder.setTicker("Vegetables");
       builder.setContentTitle("Amount Below 500g");
       builder.setContentText("Please Refill Vegetable Box");
        builder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
        builder.setAutoCancel( true ) ;
        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
        return builder.build() ;
    }
    //Save notification on firebase

    public void saveNotificationfirebase(){

        dref.child("Notifications/Vegetables/title").setValue( "Please Refill Vegetables Box" );
        dref.child("Notifications/Vegetables/description").setValue( "Vegetables amount below 500g" );
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        dref.child("Notifications/Vegetables/datetime").setValue(currentDateTimeString);


    }
}