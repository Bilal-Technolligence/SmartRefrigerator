package com.example.smartrefrigerator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class senddiet extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senddiet);
        databaseReference.child("diet").child("monday").child("b").setValue("3 boiled eggs\n Grapefruit\n Toast, Black Coffee");
        databaseReference.child("diet").child("monday").child("l").setValue("2 hard boiled eggs\n Banana\n Black Coffee");
        databaseReference.child("diet").child("monday").child("d").setValue("Vegetable Salad");

        databaseReference.child("diet").child("tuesday").child("b").setValue("1 boiled egg\n Grapefruit\n Black Coffee");
        databaseReference.child("diet").child("tuesday").child("l").setValue("2 hard boiled eggs\n Grapefruit\n Black Coffee");
        databaseReference.child("diet").child("tuesday").child("d").setValue("Beef steak 200g\n Vegetable salad");

        databaseReference.child("diet").child("wednesday").child("b").setValue("1 hard boiled egg\n Grapefruit\n Black Coffee");
        databaseReference.child("diet").child("wednesday").child("l").setValue("Vegetable salad\n Grapefruit\n Black Coffee");
        databaseReference.child("diet").child("wednesday").child("d").setValue("1 hard boiled egg\n Chicken Breast");

        databaseReference.child("diet").child("thursday").child("b").setValue("1 boiled egg\n Grapefruit\n Black Coffee");
        databaseReference.child("diet").child("thursday").child("l").setValue("Vegetable salad\n Grapefruit\n Banana");
        databaseReference.child("diet").child("thursday").child("d").setValue("Boiled spinich\n Black Coffee");

        databaseReference.child("diet").child("friday").child("b").setValue("1 hard boiled egg\n Grapefruit\n Black Coffee");
        databaseReference.child("diet").child("friday").child("l").setValue("Rice\n Tea");
        databaseReference.child("diet").child("friday").child("d").setValue("Fish\n Vegetable salad");

        databaseReference.child("diet").child("saturday").child("b").setValue("2 boiled egg\n Grapefruit\n Black Coffee");
        databaseReference.child("diet").child("saturday").child("l").setValue("Fruit salad");
        databaseReference.child("diet").child("saturday").child("d").setValue("Beef steak 200g\n Celery");

        databaseReference.child("diet").child("sunday").child("b").setValue("2 hard boiled egg\n Grapefruit\n Black Coffee");
        databaseReference.child("diet").child("sunday").child("l").setValue("1 chicken breast\n Celery\n Tomato");
        databaseReference.child("diet").child("sunday").child("d").setValue("1 chicken breast\n Tomato\n Grapefruit");
        Toast.makeText(getApplicationContext(),"Done" , Toast.LENGTH_LONG).show();

    }
}