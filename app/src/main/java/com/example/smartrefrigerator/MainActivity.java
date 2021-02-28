package com.example.smartrefrigerator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static java.lang.Integer.parseInt;

public class MainActivity extends BaseClass {
    CardView fruits,vegetables,eggs;
    TextView fruitExipry,detail;
    SharedServices sharedPref;
    SharedPreferences myPrefs;
    DatabaseReference dref= FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //  setContentView(R.layout.activity_main);
        sharedPref = new SharedServices(MainActivity.this);
        //First time when App Installed\\\
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);//this==context
        if (!prefs.contains("FirstTime")) {

            sharedPref.setInt("Key_Eggs", 5);
            sharedPref.setInt("Key_Fruits", 500);
            sharedPref.setInt("Key_Vegetables", 500);
         //   sharedPref.setBool("Key_OnVibration", true);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("FirstTime", true);
            editor.commit();
            //more code....

        }
        fruitExipry =(TextView)findViewById(R.id.txtfoodExipry);

         //DataBase
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                 String  status=dataSnapshot.child("ExpiryNotify/Fruit/tittle").getValue().toString();

                   // String  detail=dataSnapshot.child("ExpiryNotify/Fruit/tittle").child("amount").getValue().toString();
                    fruitExipry.setText(status);

                }
                else {
                    fruitExipry.setText("No Food Expired");
                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        detail = findViewById(R.id.txtDetail);
        fruits = (CardView) findViewById(R.id.fruit);
        vegetables = (CardView) findViewById(R.id.vegetable);

        eggs = (CardView) findViewById(R.id.egg);
     //int abc = sharedPref.getInt("Key_Eggs");
       //    detail.setText(String.valueOf(abc));
        fruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FruitActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        vegetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VegetableActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        eggs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EggsActivity.class);
                startActivity(intent);
                //finish();
            }
        });

    }

    @Override
    int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_home;
    }

//    @Override
//    int getContentViewId() {
//        return R.layout.activity_main;
//    }
//
//    @Override
//    int getNavigationMenuItemId() {
//        return R.id.nav_diet;
//    }
}