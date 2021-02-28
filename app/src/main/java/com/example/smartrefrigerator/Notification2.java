package com.example.smartrefrigerator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class Notification2 extends BaseClass {
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    ArrayList<notificationAttr> notificationAttrs;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_notification2);
        recyclerView = findViewById(R.id.recycler);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..... ");
        progressDialog.show();
        notificationAttrs = new ArrayList<>();//constructor
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference.child("Notifications").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        notificationAttrs.clear();
                        //profiledata.clear();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            notificationAttr p = dataSnapshot1.getValue(notificationAttr.class);
                            notificationAttrs.add(p);
                        }
                        recyclerView.setAdapter(new NotificationListAdapter(notificationAttrs, getApplicationContext()));
                        progressDialog.dismiss();
                    } catch (Exception e) {
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No Notification Found", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_notification2;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_notification;
    }
}