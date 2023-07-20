package com.Mkandeel.passwordmanager;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class State {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private Context context;


    public State(Context context) {
        this.context = context;
    }

    public void setState(boolean isDark) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sp.edit();
        editor.putBoolean("Dark_Mode", isDark);
        editor.apply();
    }

    public boolean getState() {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sp.edit();

        boolean isDark = sp.getBoolean("Dark_Mode", false);
        return isDark;
    }


}