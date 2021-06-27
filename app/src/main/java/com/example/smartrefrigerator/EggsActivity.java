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
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

public class EggsActivity extends BaseClass {
    DatabaseReference dref= FirebaseDatabase.getInstance().getReference();
    TextView txtRemaining, txtUsed ,txtExpirayStatus, thresholdValue,txtSeconds,txtMinutes,txtHours;
    String status,notificationStatus;
    double statuss;
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
      //  setContentView(R.layout.activity_eggs);

        //

        txtRemaining=(TextView) findViewById(R.id.txtremaining);
        txtUsed=(TextView) findViewById(R.id.txtused);
        LinearLayout EStatus=findViewById(R.id.linear_layout_1);
        LinearLayout TimerLayout=findViewById(R.id.linear_layout_2);
        btnReset = findViewById(R.id.txtReset);
        txtHours = (TextView) findViewById(R.id.txtHours);
        txtMinutes  = (TextView) findViewById(R.id.txtMinute);
        txtSeconds = (TextView) findViewById(R.id.txtSec);
        txtExpirayStatus =findViewById(R.id.txtExpiryStatus);

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
                    String  Threshold=dataSnapshot.child("ExpiryNotify/Eggs/tittle").getValue().toString();
                    if(Threshold.equals("No Food Expired")){

                        //Timer CountDown
                        new CountDownTimer(40000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                txtExpirayStatus.setText("Eggs Expiry");
                                EStatus.setBackgroundColor(Color.parseColor("#1F45FC"));
                                btnReset.setVisibility(View.GONE);

                                // Used for formatting digit to be in 2 digits only
                                NumberFormat f = new DecimalFormat("00");
                                long hour = (millisUntilFinished / 3600000) % 24;
                                long min = (millisUntilFinished / 60000) % 60;
                                long sec = (millisUntilFinished / 1000) % 60;
                                txtHours.setText(f.format(hour));
                                txtMinutes.setText(f.format(min));
                                txtSeconds.setText(f.format(sec));
                                dref.child("ExpiryNotify/Eggs/tittle").setValue( "No Food Expired" );
                                int ab= parseInt( txtSeconds.getText().toString());

                                if(ab<30)
                                {
                                   // EStatus.setBackgroundColor(Color.parseColor("#FF0000"));
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
                                dref.child("ExpiryNotify/Eggs/tittle").setValue( "Eggs Expired" );
                                dref.child("ExpiryNotify/Eggs/description").setValue( "Your Eggs expired please destroy it" );
                                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                                dref.child("ExpiryNotify/Eggs/datetime").setValue(currentDateTimeString);
                                txtHours.setText("00");
                                txtMinutes.setText("00");
                                txtSeconds.setText("00");
                                txtExpirayStatus.setText("Your Eggs expired please destroy");

                            }
                        }.start();

                    } else{
                        txtHours.setText("00");
                        txtMinutes.setText("00");
                        txtSeconds.setText("00");
                        txtExpirayStatus.setText("Your Eggs expired please destroy");
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
        dref.child("ExpiryNotify/Eggs/tittle").setValue( "No Food Expired" );

    }
});

        //Firebase Data Base
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
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

        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                statuss= Double.parseDouble(dataSnapshot.child("Eggs").child("amount").getValue().toString());
               // status=dataSnapshot.child("Eggs").child("amount").getValue().toString();
              //  String  fruitss=dataSnapshot.child("Threshhold/Eggs/value").getValue().toString();
               // Toast.makeText(EggsActivity.this, "Value"+fruitss, Toast.LENGTH_SHORT).show();
                int a =(int) Math.round(statuss);
               // Toast.makeText(EggsActivity.this, "Int Value...."+a, Toast.LENGTH_SHORT).show();
                if(a>=6&&a<12) {
                    status= String.valueOf("1");
                } else if (a>13&&a<20){
                    status= String.valueOf("2");
                }else if (a>20&&a<29){
                    status= String.valueOf("3");
                }else if (a>30&&a<38){
                    status= String.valueOf("4");
                }else if (a>39&&a<46){
                    status= String.valueOf("5");
                }else if (a>46&&a<55){
                    status= String.valueOf("6");
                } else {
                    status= String.valueOf(a);
                }

                txtRemaining.setText(status);
                thresholdComparison=parseInt(String.valueOf(status));
                used=6-parseInt(status);
                txtUsed.setText(Integer.toString(used));
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
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
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
        Intent intent = new Intent(this, Notification2.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setTicker("Eggs");
        builder.setContentTitle("Eggs amount Below Th");
        builder.setContentText("Please Refill Eggs Box");
        builder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
        builder.setAutoCancel( true ) ;
        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
        return builder.build() ;

    }

    //Save notification on firebase

    public void saveNotificationfirebase(){

        dref.child("Notifications/Eggs/title").setValue( "Please Refill Egg Box" );
        dref.child("Notifications/Eggs/description").setValue( "only 4 eggs remaning" );
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        dref.child("Notifications/Eggs/datetime").setValue(currentDateTimeString);


    }


}