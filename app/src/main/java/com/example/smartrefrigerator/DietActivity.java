package com.example.smartrefrigerator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DietActivity extends BaseClass {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

//    @Override
//    int getContentViewId() {
//        return R.layout.activity_diet;
//    }
//
//    @Override
//    int getNavigationMenuItemId() {
//        return R.id.nav_diet;
//    }
}