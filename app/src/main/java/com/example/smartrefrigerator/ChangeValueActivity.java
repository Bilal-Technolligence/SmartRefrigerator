package com.example.smartrefrigerator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangeValueActivity extends BaseClass {
    EditText vegTh,fruitTh,eggsTh;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference dref = firebaseDatabase.getReference();
   String thEggs,thFruits,thVegetables;

    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_change_value);

        vegTh = (EditText) findViewById(R.id.txtvagetableTh);

        fruitTh = (EditText) findViewById(R.id.txtfruitTh);

        eggsTh = (EditText) findViewById(R.id.txteggsTh);
        btnSave = (Button) findViewById(R.id.btnSaveValue);




        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thEggs = (String) eggsTh.getText().toString();
                thFruits = (String) fruitTh.getText().toString();
                thVegetables = (String) vegTh.getText().toString();
                dref.child("Threshhold/Eggs/value").setValue(thEggs).toString();
                dref.child("Threshhold/Fruits/value").setValue(thFruits).toString();
                dref.child("Threshhold/Vagetables/value").setValue(thVegetables).toString();
                Toast.makeText(ChangeValueActivity.this, "Values saved Successfully", Toast.LENGTH_SHORT).show();
            }
        });

        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String  egg=dataSnapshot.child("Threshhold/Eggs/value").getValue().toString();
                String  vagetable=dataSnapshot.child("Threshhold/Fruits/value").getValue().toString();
                String  fruit=dataSnapshot.child("Threshhold/Vagetables/value").getValue().toString();
                eggsTh.setText(String.valueOf(egg));
                fruitTh.setText(String.valueOf(fruit));
                vegTh.setText(String.valueOf(vagetable));


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_change_value;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_th;
    }
}