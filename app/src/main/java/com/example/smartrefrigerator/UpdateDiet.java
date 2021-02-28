package com.example.smartrefrigerator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateDiet extends BaseClass {
    EditText d1b, d1l, d1d;
    EditText d2b, d2l, d2d;
    EditText d3b, d3l, d3d;
    EditText d4b, d4l, d4d;
    EditText d5b, d5l, d5d;
    EditText d6b, d6l, d6d;
    EditText d7b, d7l, d7d;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    Button save;
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
        save = findViewById(R.id.btnSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("diet").child("monday").child("b").setValue(d1b.getText().toString());
                databaseReference.child("diet").child("monday").child("l").setValue(d1l.getText().toString());
                databaseReference.child("diet").child("monday").child("d").setValue(d1d.getText().toString());

                databaseReference.child("diet").child("tuesday").child("b").setValue(d2b.getText().toString());
                databaseReference.child("diet").child("tuesday").child("l").setValue(d2l.getText().toString());
                databaseReference.child("diet").child("tuesday").child("d").setValue(d2d.getText().toString());

                databaseReference.child("diet").child("wednesday").child("b").setValue(d3b.getText().toString());
                databaseReference.child("diet").child("wednesday").child("l").setValue(d3l.getText().toString());
                databaseReference.child("diet").child("wednesday").child("d").setValue(d3d.getText().toString());

                databaseReference.child("diet").child("thursday").child("b").setValue(d4b.getText().toString());
                databaseReference.child("diet").child("thursday").child("l").setValue(d4l.getText().toString());
                databaseReference.child("diet").child("thursday").child("d").setValue(d4d.getText().toString());

                databaseReference.child("diet").child("friday").child("b").setValue(d5b.getText().toString());
                databaseReference.child("diet").child("friday").child("l").setValue(d5l.getText().toString());
                databaseReference.child("diet").child("friday").child("d").setValue(d5d.getText().toString());

                databaseReference.child("diet").child("saturday").child("b").setValue(d6b.getText().toString());
                databaseReference.child("diet").child("saturday").child("l").setValue(d6l.getText().toString());
                databaseReference.child("diet").child("saturday").child("d").setValue(d6d.getText().toString());

                databaseReference.child("diet").child("sunday").child("b").setValue(d7b.getText().toString());
                databaseReference.child("diet").child("sunday").child("l").setValue(d7l.getText().toString());
                databaseReference.child("diet").child("sunday").child("d").setValue(d7d.getText().toString());
                Toast.makeText(getApplicationContext(),"Updated!" , Toast.LENGTH_LONG).show();
                startActivity( new Intent(UpdateDiet.this , DietActivity.class));
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
        //setContentView(R.layout.activity_update_diet);
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_update_diet;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_diet;
    }
}