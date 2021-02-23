package com.example.smartrefrigerator;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedServices {
    public static final String MY_PREF = "MyPreferences";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedServices(Context context) {
        this.sharedPreferences = context.getSharedPreferences(MY_PREF, 0);
        this.editor = this.sharedPreferences.edit();
    }

    public void setString(String key, String value) {
        this.editor.putString(key, value);
        this.editor.apply();
    }

    public String getString(String key) {

        return this.sharedPreferences.getString(key, "");
    }

    public void setBool(String key, boolean value) {
        this.editor.putBoolean(key, value);
        this.editor.apply();
    }

    public boolean getBool(String key) {
        return this.sharedPreferences.getBoolean(key, false);
    }

    public void setInt(String key, int value) {
        this.editor.putInt(key, value);
        this.editor.apply();

    }


    public int getInt(String key) {
        return this.sharedPreferences.getInt(key,0);
    }







}
