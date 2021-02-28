package com.example.smartrefrigerator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DietActivity extends BaseClass {
    TextView d1b, d1l, d1d;
    TextView d2b, d2l, d2d;
    TextView d3b, d3l, d3d;
    TextView d4b, d4l, d4d;
    TextView d5b, d5l, d5d;
    TextView d6b, d6l, d6d;
    TextView d7b, d7l, d7d;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    Button update;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        d1b = findViewById(R.id.day1B);
        d1l = findViewById(R.id.day1L);
        d1d = findViewById(R.id.day1D);
        d2b = findViewById(R.id.day2B);
        d2l = findViewById(R.id.day2L);
        d2d = findViewById(R.id.day2D);
        d3b = findViewById(R.id.day3B);
        d3l = findViewById(R.id.day3L);
        d3d = findViewById(R.id.day3D);
        d4b = findViewById(R.id.day4B);
        d4l = findViewById(R.id.day4L);
        d4d = findViewById(R.id.day4D);
        d5b = findViewById(R.id.day5B);
        d5l = findViewById(R.id.day5L);
        d5d = findViewById(R.id.day5D);
        d6b = findViewById(R.id.day6B);
        d6l = findViewById(R.id.day6L);
        d6d = findViewById(R.id.day6D);
        d7b = findViewById(R.id.day7B);
        d7l = findViewById(R.id.day7L);
        d7d = findViewById(R.id.day7D);
        update = findViewById(R.id.btnUpdate);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(DietActivity.this , UpdateDiet.class));
            }
        });
        databaseReference.child("diet").child("monday").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    d1b.setText(snapshot.child("b").getValue().toString());
                    d1l.setText(snapshot.child("l").getValue().toString());
                    d1d.setText(snapshot.child("d").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child("diet").child("tuesday").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    d2b.setText(snapshot.child("b").getValue().toString());
                    d2l.setText(snapshot.child("l").getValue().toString());
                    d2d.setText(snapshot.child("d").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child("diet").child("wednesday").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    d3b.setText(snapshot.child("b").getValue().toString());
                    d3l.setText(snapshot.child("l").getValue().toString());
                    d3d.setText(snapshot.child("d").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child("diet").child("thursday").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    d4b.setText(snapshot.child("b").getValue().toString());
                    d4l.setText(snapshot.child("l").getValue().toString());
                    d4d.setText(snapshot.child("d").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child("diet").child("friday").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    d5b.setText(snapshot.child("b").getValue().toString());
                    d5l.setText(snapshot.child("l").getValue().toString());
                    d5d.setText(snapshot.child("d").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child("diet").child("saturday").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    d6b.setText(snapshot.child("b").getValue().toString());
                    d6l.setText(snapshot.child("l").getValue().toString());
                    d6d.setText(snapshot.child("d").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child("diet").child("sunday").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    d7b.setText(snapshot.child("b").getValue().toString());
                    d7l.setText(snapshot.child("l").getValue().toString());
                    d7d.setText(snapshot.child("d").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // setContentView(R.layout.activity_diet);
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_diet;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_diet;
    }
}